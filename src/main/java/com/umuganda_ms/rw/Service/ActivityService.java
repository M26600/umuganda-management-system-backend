package com.umuganda_ms.rw.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.umuganda_ms.rw.Repository.ActivityReportRepository;
import com.umuganda_ms.rw.Repository.ActivityRepository;
import com.umuganda_ms.rw.domain.Activity;
import com.umuganda_ms.rw.domain.ActivityStatus;

@Service
public class ActivityService {

    private final ActivityRepository activityRepository;
    private final ActivityReportRepository activityReportRepository;

    public ActivityService(ActivityRepository activityRepository, ActivityReportRepository activityReportRepository) {

        this.activityRepository = activityRepository;
        this.activityReportRepository = activityReportRepository;

    }

    public List<Activity> getAllActivities() {
        return activityRepository.findAll();
    }

    public Optional<Activity> getActivityById(UUID id) {
        return activityRepository.findById(id);
    }

    // A2 & D1
    public String saveActivity(Activity activity) {

        if (activity.getActivityDate() == null
                || activity.getActivityDate().isBefore(LocalDate.now())) {
            return "Activity date cannot be in the past";
        }

        boolean conflict = activityRepository.existsByCoordinator_IdAndActivityDateAndStatusNot(
                activity.getCoordinator().getId(),
                activity.getActivityDate(),
                ActivityStatus.POSTPONED
        );

        if (conflict) {
            return "Coordinator already has an activity on that date";
        }

        activity.setStatus(ActivityStatus.PLANNED);
        activityRepository.save(activity);

        return "Activity saved successfully";
    }

    // A2 & D2
    public String updateActivity(UUID id, Activity updatedActivity) {
        Optional<Activity> existingActivity = activityRepository.findById(id);
        if (existingActivity.isEmpty()) {
            return "The activity with that id does not exist";
        }

        Activity activity = existingActivity.get();
        activity.setActivityDate(updatedActivity.getActivityDate());
        activity.setObjective(updatedActivity.getObjective());
        activity.setVillage(updatedActivity.getVillage());
        activity.setCoordinator(updatedActivity.getCoordinator());

        activityRepository.save(activity);
        return "Activity updated successfully";
    }

    // A2 & E3 
    public String deleteActivity(UUID id) {
        Optional<Activity> existingActivity = activityRepository.findById(id);
        if (existingActivity.isEmpty()) {
            return "The activity with that id does not exist";
        }

        Activity activity = existingActivity.get();
        if (activityReportRepository.existsByActivity(activity)) {
            return "That activity has activity reports, delete them first";
        }

        activityRepository.deleteById(id);
        return "Activity deleted successfully";
    }

    // B2
    public List<Activity> getActivityByStatus(ActivityStatus status) {
        return activityRepository.findByStatusOrderByActivityDateAsc(status);
    }

    // B3
    public List<Activity> getActivitiesBetween(LocalDate start, LocalDate end) {
        return activityRepository.findByActivityDateBetweenOrderByActivityDateAsc(start, end);
    }

    // D2
    public String updateActivityStatus(UUID id, ActivityStatus newStatus) {
        Optional<Activity> existingActivity = activityRepository.findById(id);
        if (existingActivity.isEmpty()) {
            return "The activity with that id does not exist";
        }

        Activity activity = existingActivity.get();
        ActivityStatus currentStatus = activity.getStatus();

        boolean allowed
                = (currentStatus == ActivityStatus.PLANNED
                && (newStatus == ActivityStatus.ONGOING || newStatus == ActivityStatus.POSTPONED))
                || (currentStatus == ActivityStatus.ONGOING
                && (newStatus == ActivityStatus.ACCOMPLISHED || newStatus == ActivityStatus.POSTPONED));

        if (!allowed) {
            return "Cannot change status from " + currentStatus + " to " + newStatus;
        }

        activity.setStatus(newStatus);
        activityRepository.save(activity);
        return "Activity status updated successfully";
    }

    // F1
    public List<Object[]> getActivitiesStatsByStatus() {
        return activityRepository.countActivitiesByStatus();
    }

    // F2
    @Transactional
    public String cancelActivitiesForAbsentCoordinator(UUID coordinatorId, LocalDate date) {
        int cancelledCount = activityRepository.cancelActivitiesForCoordinatorOnDate(
                coordinatorId,
                date,
                ActivityStatus.POSTPONED,
                ActivityStatus.ACCOMPLISHED
        );
        return cancelledCount + " activities cancelled";
    }

    // Bonus of ctivities page by page
    public Page<Activity> getActivitiesPage(Pageable pageable) {
        return activityRepository.findAll(pageable);
    }
}

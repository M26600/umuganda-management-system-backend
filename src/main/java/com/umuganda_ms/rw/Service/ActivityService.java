package com.umuganda_ms.rw.Service;

import java.time.LocalDate;
import java.util.*;
import org.springframework.stereotype.*;


import com.umuganda_ms.rw.Repository.ActivityRepository;
import com.umuganda_ms.rw.domain.Activity;

import com.umuganda_ms.rw.domain.ActivityStatus;


@Service
public class ActivityService {
    private final ActivityRepository activityRepository;
    public ActivityService(ActivityRepository activityRepository){
        this.activityRepository = activityRepository;
    }
    public List<Activity> getAllActivities(){
        return activityRepository.findAll();
    }
    public Optional<Activity> getActivityById(UUID id){
        return activityRepository.findById(id);
    }
    public String saveActivity(Activity activity){
        if(activity.getActivityDate().isBefore(LocalDate.now())){
            return "Activity date cannot be in the past";
        }
        if(activity.getCoordinator()!=null 
    && activityRepository.existsByCoordinator_IdAndActivityDateAndStatusNot(
        activity.getCoordinator().getId(),
        activity.getActivityDate(),
        ActivityStatus.POSTPONED)){
            return "Coordinator already has an activity on that date";
        }
        activity.setStatus(ActivityStatus.PLANNED);
        activityRepository.save(activity);
        return "Activity saved successfully";
    }
    public String updateActivity(UUID id, Activity updatedActivity){
        Optional<Activity> existingActivity = activityRepository.findById(id);
        if(existingActivity.isEmpty()){
            return "The activity with that id does not exist";
        }
        Activity activity = existingActivity.get();
        activity.setActivityDate(updatedActivity.getActivityDate());
        activity.setObjective(updatedActivity.getObjective());
        activity.setVillage(updatedActivity.getVillage());
        activity.setCoordinator(updatedActivity.getCoordinator());
        activity.setStatus(updatedActivity.getStatus());

        activityRepository.save(activity);
        return "Activity updated successfully";
    }
    public String deleteActivity(UUID id){
        Optional<Activity> existingActivity = activityRepository.findById(id);
        if(existingActivity.isEmpty()){
            return "The activity with that id does not exist";
        }
        activityRepository.deleteById(id);
        return "Activity deleted successfully";
    }
    public List<Activity> getActivityByStatus(ActivityStatus status){
        return activityRepository.findByStatusOrderByActivityDateAsc(status);
    }
    public List<Activity> getActivitiesBetween(LocalDate start, LocalDate end){
        return activityRepository.findByActivityDateBetweenOrderByActivityDateAsc(start, end);
    }
    public String updateActivityStatus(UUID id, ActivityStatus newStatus) {
    Optional<Activity> existingActivity = activityRepository.findById(id);

    if (existingActivity.isEmpty()) {
        return "The activity with that id does not exist";
    }

    Activity activity = existingActivity.get();
    ActivityStatus currentStatus = activity.getStatus();

    boolean allowed =
        (currentStatus == ActivityStatus.PLANNED &&
            (newStatus == ActivityStatus.ONGOING ||
             newStatus == ActivityStatus.POSTPONED))
        ||
        (currentStatus == ActivityStatus.ONGOING &&
            (newStatus == ActivityStatus.ACCOMPLISHED ||
             newStatus == ActivityStatus.POSTPONED));

    if (!allowed) {
        return "Cannot change status from " + currentStatus
                + " to " + newStatus;
    }

    activity.setStatus(newStatus);
    activityRepository.save(activity);

    return "Activity status updated successfully";
}
    
    
}

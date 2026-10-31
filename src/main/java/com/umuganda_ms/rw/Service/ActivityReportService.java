package com.umuganda_ms.rw.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.umuganda_ms.rw.Repository.ActivityReportRepository;
import com.umuganda_ms.rw.Repository.ActivityRepository;
import com.umuganda_ms.rw.domain.Activity;
import com.umuganda_ms.rw.domain.ActivityReport;
import com.umuganda_ms.rw.domain.ActivityStatus;

@Service
public class ActivityReportService {

    private final ActivityReportRepository activityReportRepository;
    private final ActivityRepository activityRepository;

    public ActivityReportService(ActivityReportRepository activityReportRepository,
                                 ActivityRepository activityRepository) {
        this.activityReportRepository = activityReportRepository;
        this.activityRepository = activityRepository;
    }

    public List<ActivityReport> getAllActivityReports() {
        return activityReportRepository.findAll();
    }

    public Optional<ActivityReport> getActivityReportById(UUID id) {
        return activityReportRepository.findById(id);
    }

    // E1: Write an activity report
    public String saveActivityReport(UUID activityId, ActivityReport report) {
        Optional<Activity> existingActivity = activityRepository.findById(activityId);

        if (existingActivity.isEmpty()) {
            return "The activity with that id does not exist";
        }

        Activity activity = existingActivity.get();

        if (activity.getStatus() != ActivityStatus.ACCOMPLISHED) {
            return "Activity reports are only for ACCOMPLISHED activities";
        }

        report.setActivity(activity);
        report.setSubmittedDate(LocalDate.now());
        activityReportRepository.save(report);

        return "Activity report saved successfully";
    }

    // E2: Activity report history of a village
    public List<ActivityReport> getReportsByVillageCode(String villageCode) {
        return activityReportRepository.findReportsByVillageCode(villageCode);
    }

    // E3: Delete activity report
    public String deleteActivityReport(UUID id) {
        Optional<ActivityReport> existingReport = activityReportRepository.findById(id);

        if (existingReport.isEmpty()) {
            return "The activity report with that id does not exist";
        }

        activityReportRepository.deleteById(id);
        return "Activity report deleted successfully";
    }
}

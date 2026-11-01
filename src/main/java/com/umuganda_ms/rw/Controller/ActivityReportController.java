package com.umuganda_ms.rw.Controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.umuganda_ms.rw.Service.ActivityReportService;
import com.umuganda_ms.rw.domain.ActivityReport;

@RestController
@RequestMapping("/api/activity-reports")
public class ActivityReportController {

    private final ActivityReportService activityReportService;

    public ActivityReportController(ActivityReportService activityReportService) {
        this.activityReportService = activityReportService;
    }

    // E1: Write an activity report
    @PostMapping("/save")
    public ResponseEntity<String> saveActivityReport(
            @RequestParam UUID activityId,
            @RequestBody ActivityReport report) {

        String message = activityReportService.saveActivityReport(activityId, report);

        if (message.equals("The activity with that id does not exist")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        } else if (message.equals("Activity reports are only for ACCOMPLISHED activities")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
        } else {
            return ResponseEntity.status(HttpStatus.CREATED).body(message);
        }
    }

    // E2
    @GetMapping("/village/{villageCode}")
    public ResponseEntity<List<ActivityReport>> getReportsByVillageCode(
            @PathVariable String villageCode) {

        List<ActivityReport> reports
                = activityReportService.getReportsByVillageCode(villageCode);

        return ResponseEntity.ok(reports);
    }

    // E3
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteActivityReport(@PathVariable UUID id) {

        String message = activityReportService.deleteActivityReport(id);

        if (message.equals("The activity report with that id does not exist")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }

        return ResponseEntity.ok(message);
    }

    @GetMapping("/all")
    public ResponseEntity<List<ActivityReport>> getAllActivityReports() {
        List<ActivityReport> reports = activityReportService.getAllActivityReports();
        return ResponseEntity.ok(reports);
    }
}

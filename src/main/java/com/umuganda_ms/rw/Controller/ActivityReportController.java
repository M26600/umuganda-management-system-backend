package com.umuganda_ms.rw.Controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import com.umuganda_ms.rw.Service.ActivityService;
import com.umuganda_ms.rw.domain.ActivityReport;

@RestController
@RequestMapping("/api/activity-reports")
public class ActivityReportController {

    private final ActivityService activityService;

    public ActivityReportController(ActivityService activityService) {
        this.activityService = activityService;
    }

    // E1
    @PostMapping("/save")
    public ResponseEntity<String> saveActivityReport(
            @RequestParam UUID activityId,
            @RequestBody ActivityReport report) {

        String message = activityService.saveActivityReport(activityId, report);

        if (message.equals("The activity with that id does not exist")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        } else if (message.equals("Activity reports are only for ACCOMPLISHED activities")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
        } else {
            return ResponseEntity.status(HttpStatus.CREATED).body(message);
        }
    }


    @GetMapping("/village/{villageCode}")
    public ResponseEntity<List<ActivityReport>> getReportsByVillageCode(
            @PathVariable String villageCode) {

        List<ActivityReport> reports =
                activityService.getReportsByVillageCode(villageCode);

        return ResponseEntity.ok(reports);
    }

@DeleteMapping("/delete/{id}")
public ResponseEntity<String> deleteActivityReport(
        @PathVariable UUID id) {

    String message = activityService.deleteActivityReport(id);

    if (message.equals("The activity report with that id does not exist")) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
    }

    return ResponseEntity.ok(message);
}

@GetMapping("/all")
public ResponseEntity<List<ActivityReport>> getAllActivityReports() {

    List<ActivityReport> reports =
            activityService.getAllActivityReports();

    return ResponseEntity.ok(reports);
}
}
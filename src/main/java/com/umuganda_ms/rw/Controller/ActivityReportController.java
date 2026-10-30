package com.umuganda_ms.rw.Controller;

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
}
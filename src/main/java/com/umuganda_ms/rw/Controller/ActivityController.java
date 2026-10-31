package com.umuganda_ms.rw.Controller;

import java.time.LocalDate;
import java.util.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.umuganda_ms.rw.Service.ActivityService;
import com.umuganda_ms.rw.domain.Activity;
import com.umuganda_ms.rw.domain.ActivityStatus;

@RestController
@RequestMapping("/api/activities")
public class ActivityController {

    private final ActivityService activityService;

    public ActivityController(ActivityService activityService) {
        this.activityService = activityService;
    }

    // A2 & D1: Save activity
    @PostMapping("/save")
    public ResponseEntity<String> saveActivity(@RequestBody Activity activity) {
        String message = activityService.saveActivity(activity);
        if (message.equals("Activity date cannot be in the past")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
        } else if (message.equals("Coordinator already has an activity on that date")) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(message);
        } else {
            return ResponseEntity.status(HttpStatus.CREATED).body(message);
        }
    }

    // A2: Get all activities
    @GetMapping("/all")
    public ResponseEntity<List<Activity>> getAllActivities() {
        List<Activity> activities = activityService.getAllActivities();
        return ResponseEntity.ok(activities);
    }

    // A2: Get activity by id
    @GetMapping("/{id}")
    public ResponseEntity<?> getActivityById(@PathVariable UUID id) {
        Optional<Activity> activity = activityService.getActivityById(id);
        if (activity.isPresent()) {
            return ResponseEntity.ok(activity.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("The activity with that id does not exist");
        }
    }

    // A2 & D2: Update activity (status does not change here)
    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateActivity(
            @PathVariable UUID id,
            @RequestBody Activity updatedActivity) {
        String message = activityService.updateActivity(id, updatedActivity);
        if (message.equals("The activity with that id does not exist")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        } else {
            return ResponseEntity.ok(message);
        }
    }

    // A2 & E3: Delete activity
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteActivity(@PathVariable UUID id) {
        String message = activityService.deleteActivity(id);
        if (message.equals("The activity with that id does not exist")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        } else if (message.equals("That activity has activity reports, delete them first")) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(message);
        } else {
            return ResponseEntity.ok(message);
        }
    }

    // B2: Activities by status
    @GetMapping("/by-status")
    public ResponseEntity<List<Activity>> getActivityByStatus(@RequestParam ActivityStatus status) {
        List<Activity> activities = activityService.getActivityByStatus(status);
        return ResponseEntity.ok(activities);
    }

    // B3: Activities in a date range
    @GetMapping("/between")
    public ResponseEntity<List<Activity>> getActivitiesBetween(
            @RequestParam String start,
            @RequestParam String end) {
        LocalDate startDate = LocalDate.parse(start);
        LocalDate endDate = LocalDate.parse(end);
        List<Activity> activities = activityService.getActivitiesBetween(startDate, endDate);
        return ResponseEntity.ok(activities);
    }

    // D2: Status flow
    @PatchMapping("/{id}/status")
    public ResponseEntity<String> updateActivityStatus(
            @PathVariable UUID id,
            @RequestParam ActivityStatus status) {
        String message = activityService.updateActivityStatus(id, status);
        if (message.equals("The activity with that id does not exist")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        } else if (message.startsWith("Cannot change status from")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
        } else {
            return ResponseEntity.ok(message);
        }
    }

    // F1: Activities per status
    @GetMapping("/stats/by-status")
    public ResponseEntity<List<Object[]>> getActivitiesStatsByStatus() {
        return ResponseEntity.ok(activityService.getActivitiesStatsByStatus());
    }

    // F2: Coordinator is absent
    @PatchMapping("/cancel-day")
    public ResponseEntity<String> cancelActivitiesForAbsentCoordinator(
            @RequestParam UUID coordinatorId,
            @RequestParam @org.springframework.format.annotation.DateTimeFormat(
                    iso = org.springframework.format.annotation.DateTimeFormat.ISO.DATE)
            LocalDate date) {
        String message = activityService.cancelActivitiesForAbsentCoordinator(coordinatorId, date);
        return ResponseEntity.ok(message);
    }

    // Bonus: Activities page by page
    @GetMapping("/page")
    public ResponseEntity<Page<Activity>> getActivitiesPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "activityDate,desc") String sort) {

        String[] sortParams = sort.split(",");
        String sortField = sortParams[0];
        Sort.Direction direction = Sort.Direction.fromString(sortParams[1]);

        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortField));
        Page<Activity> activities = activityService.getActivitiesPage(pageable);
        return ResponseEntity.ok(activities);
    }
}

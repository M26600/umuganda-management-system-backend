package com.umuganda_ms.rw.Controller;

import java.util.*;
import java.time.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import com.umuganda_ms.rw.domain.Activity;
import com.umuganda_ms.rw.Service.ActivityService;
import com.umuganda_ms.rw.domain.ActivityStatus;

@RestController 
@RequestMapping("/api/activities")
public class ActivityController {
    private final ActivityService activityService;
    public ActivityController(ActivityService activityService){
        this.activityService = activityService;
    }

    @PostMapping("/save")
    public ResponseEntity<String> saveActivity(
        @RequestBody Activity activity){
            String message = activityService.saveActivity(activity);
            if(message.equals("Activity date cannot be in the past")){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(message);
            }else if (message.equals("Coordinator already has an activity on that date")){
                return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(message);
            }else{
            return ResponseEntity.status(HttpStatus.CREATED)
            .body(message);
            }
        }
    @GetMapping("/all")
    public ResponseEntity<List<Activity>> getAllActivities(){
        List<Activity> activities = activityService.getAllActivities();
        return ResponseEntity.ok(activities);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getActivityById(@PathVariable UUID id){
        Optional<Activity> activity = activityService.getActivityById(id);
        if(activity.isPresent()){
            return ResponseEntity.ok(activity.get());
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body("The activity with that id does not exist");
        }
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateActivity(
        @PathVariable UUID id,
        @RequestBody Activity updatedActivity){
            String message = activityService.updateActivity(id, updatedActivity);
            if (message.equals("The activity with that id does not exist")){
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(message);
            }else{
                return ResponseEntity.ok(message);
            }
        }
  @DeleteMapping("/delete/{id}")
public ResponseEntity<String> deleteActivity(@PathVariable UUID id) {

    String message = activityService.deleteActivity(id);

    if (message.equals("The activity with that id does not exist")) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(message);

    } else if (message.equals("That activity has activity reports, delete them first")) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(message);

    } else {
        return ResponseEntity.ok(message);
    }
}
    @GetMapping ("/by-status")
    public ResponseEntity<List<Activity>> getActivityByStatus(@RequestParam ActivityStatus status){
        List<Activity> activities = activityService.getActivityByStatus(status);
        return ResponseEntity.ok(activities);
    }
    @GetMapping("/between")
    public ResponseEntity<List<Activity>> getActivitiesBetween(@RequestParam String start, 
        @RequestParam String end){
            LocalDate startDate = LocalDate.parse(start);
            LocalDate endDate = LocalDate.parse(end);
            List<Activity> activities = activityService.getActivitiesBetween(startDate, endDate);
            return ResponseEntity.ok(activities);
        }
    @PatchMapping("/{id}/status")
public ResponseEntity<String> updateActivityStatus(
        @PathVariable UUID id,
        @RequestParam ActivityStatus status) {

    String message = activityService.updateActivityStatus(id, status);

    if (message.equals("The activity with that id does not exist")) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(message);
    } else if (message.startsWith("Cannot change status from")) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(message);
    } else {
        return ResponseEntity.ok(message);
    }
}
@GetMapping("/count-by-status")
public ResponseEntity<Map<String, Long>> countActivitiesByStatus() {
    return ResponseEntity.ok(activityService.countActivitiesByStatus());
}

@PatchMapping("/cancel-day")
public ResponseEntity<String> cancelActivitiesForAbsentCoordinator(
        @RequestParam UUID coordinatorId,
        @RequestParam
        @org.springframework.format.annotation.DateTimeFormat(
                iso = org.springframework.format.annotation.DateTimeFormat.ISO.DATE)
        LocalDate date) {

    String message = activityService
            .cancelActivitiesForAbsentCoordinator(coordinatorId, date);

    return ResponseEntity.ok(message);
}
}

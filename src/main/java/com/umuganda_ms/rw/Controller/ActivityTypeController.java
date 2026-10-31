package com.umuganda_ms.rw.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.umuganda_ms.rw.Service.ActivityTypeService;
import com.umuganda_ms.rw.domain.ActivityType;

@RestController 
@RequestMapping("/api/activity-types")
public class ActivityTypeController {

    private final ActivityTypeService activityTypeService;

    public ActivityTypeController(ActivityTypeService activityTypeService) {
        this.activityTypeService = activityTypeService;
    }

    // A2: Save activity type
    @PostMapping("/save")
    public ResponseEntity<String> saveActivityType(@RequestBody ActivityType activityType) {
        String message = activityTypeService.saveActivityType(activityType);
        return ResponseEntity.status(HttpStatus.CREATED).body(message);
    }

    // A2: Get all activity types
    @GetMapping("/all")
    public ResponseEntity<List<ActivityType>> getAllActivityTypes() {
        List<ActivityType> activityTypes = activityTypeService.getAllActivityTypes();
        return ResponseEntity.ok(activityTypes);
    }

    // A2: Get activity type by id
    @GetMapping("/{id}")
    public ResponseEntity<?> getActivityTypeById(@PathVariable UUID id) {
        Optional<ActivityType> existingActivityType = activityTypeService.getActivityTypeById(id);
        if (existingActivityType.isPresent()) {
            return ResponseEntity.ok(existingActivityType.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("The activity type with that id does not exist");
        }
    }

    // A2: Update activity type
    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateActivityType(
            @PathVariable UUID id,
            @RequestBody ActivityType updatedActivityType) {
        String message = activityTypeService.updateActivityType(id, updatedActivityType);
        if (message.equals("The activity type with that id does not exist")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        } else {
            return ResponseEntity.ok(message);
        }
    }

    // A2: Delete activity type
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteActivityType(@PathVariable UUID id) {
        String message = activityTypeService.deleteActivityType(id);
        if (message.equals("The activity type with that id does not exist")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        } else {
            return ResponseEntity.ok(message);
        }
    }

    // C3: Activity types nobody holds
    @GetMapping("/unused")
    public ResponseEntity<List<ActivityType>> getUnusedActivityTypes() {
        List<ActivityType> activityTypes = activityTypeService.getUnusedActivityTypes();
        return ResponseEntity.ok(activityTypes);
    }
}

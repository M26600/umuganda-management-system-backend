package com.umuganda_ms.rw.Controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.umuganda_ms.rw.Service.ActivityTypeService;
import com.umuganda_ms.rw.domain.ActivityType;

@RestController
@RequestMapping("/api/activity-types")
public class ActivityTypeController {

    private final ActivityTypeService activityTypeService;

    public ActivityTypeController(ActivityTypeService activityTypeService) {
        this.activityTypeService = activityTypeService;
    }

    // A2
    @PostMapping("/save")
    public ResponseEntity<String> saveActivityType(@RequestBody ActivityType activityType) {
        String message = activityTypeService.saveActivityType(activityType);
        return ResponseEntity.status(HttpStatus.CREATED).body(message);
    }

    // A2
    @GetMapping("/all")
    public ResponseEntity<List<ActivityType>> getAllActivityTypes() {
        List<ActivityType> activityTypes = activityTypeService.getAllActivityTypes();
        return ResponseEntity.ok(activityTypes);
    }

    // A2
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

    // A2
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

    // A2
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteActivityType(@PathVariable UUID id) {
        String message = activityTypeService.deleteActivityType(id);
        if (message.equals("The activity type with that id does not exist")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        } else {
            return ResponseEntity.ok(message);
        }
    }

    // C3
    @GetMapping("/unused")
    public ResponseEntity<List<ActivityType>> getUnusedActivityTypes() {
        List<ActivityType> activityTypes = activityTypeService.getUnusedActivityTypes();
        return ResponseEntity.ok(activityTypes);
    }
}

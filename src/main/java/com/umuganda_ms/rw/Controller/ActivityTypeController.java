package com.umuganda_ms.rw.Controller;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.*;


import com.umuganda_ms.rw.Service.ActivityTypeService;
import com.umuganda_ms.rw.domain.ActivityType;

@RestController 
@RequestMapping("/api/activity-types")
public class ActivityTypeController {
    private final ActivityTypeService activityTypeService;
    public ActivityTypeController(ActivityTypeService activityTypeService){
        this.activityTypeService = activityTypeService;
    }
    @PostMapping("/save")
    public ResponseEntity<String> saveActivityType(@RequestBody ActivityType activityType){
        String message = activityTypeService.saveActivityType(activityType);
        return ResponseEntity.status(HttpStatus.CREATED)
        .body(message);
    }

    @GetMapping("/all")
    public ResponseEntity<List<ActivityType>> getAllActivityTypes(){
        List<ActivityType> activityTypes = activityTypeService.getAllActivityTypes();
        return ResponseEntity.ok(activityTypes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getActivityTypeById(@PathVariable UUID id){
        Optional<ActivityType> existingActivityType = activityTypeService.getActivityTypeById(id);
        if(existingActivityType.isPresent()){
            return ResponseEntity.ok(existingActivityType.get());
        }
        else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body("The activity type with that id does not exist");
        }
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateActivity(@PathVariable UUID id, @RequestBody ActivityType updatedActivityType){
        String message = activityTypeService.updateActivityType(id, updatedActivityType);
        if(message.equals("The activity type with that id does not exist")){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(message);
        }else{
            return ResponseEntity.ok(message);
        }
    }
    @DeleteMapping ("/delete/{id}")
    public ResponseEntity<String> deleteActivityType(@PathVariable UUID id){
        String message = activityTypeService.deleteActivityType(id);
        if(message.equals("The activity type with that id does not exist")){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(message);
        }else{
            return ResponseEntity.ok(message);
        }
    }
    @GetMapping("/unused")
    public ResponseEntity<List<ActivityType>> getUnusedActivityTypes(){
        List<ActivityType> activityTypes = activityTypeService.getUnusedActivityTypes();
        return ResponseEntity.ok(activityTypes);
    }


}

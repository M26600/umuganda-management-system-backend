package com.umuganda_ms.rw.Controller;

import java.util.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import com.umuganda_ms.rw.domain.Activity;
import com.umuganda_ms.rw.Service.ActivityService;

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
            return ResponseEntity.status(HttpStatus.CREATED)
            .body(message);
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
    public ResponseEntity<String> deleteActivity(@PathVariable UUID id){
        String message = activityService.deleteActivity(id);
        if(message.equals("The activity with that id does not exist")){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(message);

        } else{
            return ResponseEntity.ok(message);
        }
    }

}

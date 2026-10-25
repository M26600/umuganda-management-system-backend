package com.umuganda_ms.rw.Service;

import java.util.*;
import org.springframework.stereotype.*;


import com.umuganda_ms.rw.Repository.ActivityRepository;
import com.umuganda_ms.rw.domain.Activity;

@Service
public class ActivityService {
    private final ActivityRepository activityRepository;
    public ActivityService(ActivityRepository activityRepository){
        this.activityRepository = activityRepository;
    }
    public List<Activity> getAllActivities(){
        return activityRepository.findAll();
    }
    public Optional<Activity> getActivityById(UUID id){
        return activityRepository.findById(id);
    }
    public String saveActivity(Activity activity){
        activityRepository.save(activity);
        return "Activity saved Successfully";
    }
    public String updateActivity(UUID id, Activity updatedActivity){
        Optional<Activity> existingActivity = activityRepository.findById(id);
        if(existingActivity.isEmpty()){
            return "The activity with that id does not exist";
        }
        Activity activity = existingActivity.get();
        activity.setActivityDate(updatedActivity.getActivityDate());
        activity.setObjective(updatedActivity.getObjective());
        activity.setVillage(updatedActivity.getVillage());
        activity.setCoordinator(updatedActivity.getCoordinator());
        activity.setStatus(updatedActivity.getStatus());

        activityRepository.save(activity);
        return "Activity updated successfully";
    }
    public String deleteActivity(UUID id){
        Optional<Activity> existingActivity = activityRepository.findById(id);
        if(existingActivity.isEmpty()){
            return "The activity does not exist.";
        }
        activityRepository.deleteById(id);
        return "Activity deleted successfully!";
    }
    
    
}

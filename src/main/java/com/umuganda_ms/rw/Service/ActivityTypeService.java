package com.umuganda_ms.rw.Service;
import java.util.*;

import org.springframework.stereotype.Service;

import com.umuganda_ms.rw.Repository.ActivityTypeRepository;
import com.umuganda_ms.rw.domain.ActivityType;

@Service 
public class ActivityTypeService {
    private final ActivityTypeRepository activityTypeRepository;
    public ActivityTypeService(ActivityTypeRepository activityTypeRepository){
        this.activityTypeRepository = activityTypeRepository;
    }
    public List<ActivityType> getAllActivityTypes(){
        return activityTypeRepository.findAll();
    }
    public Optional<ActivityType> getActivityTypeById(UUID id){
        return activityTypeRepository.findById(id);
    }
    public String saveActivityType(ActivityType activityType){
        activityTypeRepository.save(activityType);
        return "Activity Type saved successfully!";
    }
    public String updateActivityType(UUID id, ActivityType updatedActivityType){
        Optional<ActivityType> existingActivityType = activityTypeRepository.findById(id);
        if(existingActivityType.isEmpty()){
            return "The activity type with that id does not exist";
        }
        ActivityType activityType = existingActivityType.get();
        activityType.setName(updatedActivityType.getName());
        activityTypeRepository.save(activityType);
        return "Activity Type updated successfully!";
    }
    public String deleteActivityType(UUID id){
        Optional<ActivityType> existingActivityType = activityTypeRepository.findById(id);
        if(existingActivityType.isEmpty()){
            return "The activity type with that id does not exist";
        }
        activityTypeRepository.deleteById(id);
        return "Activity Type is deleted successfully!";
    }
    public List<ActivityType> getUnusedActivityTypes(){
        return activityTypeRepository.findnusedActivityTypes();
    }
}

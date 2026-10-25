package com.umuganda_ms.rw.Service;

import java.util.*;
import org.springframework.stereotype.*;
import com.umuganda_ms.rw.Repository.CoordinatorRepository;
import com.umuganda_ms.rw.domain.Coordinator;

@Service
public class CoordinatorService {
    private final CoordinatorRepository coordinatorRepository;
    public CoordinatorService(CoordinatorRepository coordinatorRepository){
        this.coordinatorRepository = coordinatorRepository;
    }

    public List<Coordinator> getAllCoordinators(){
        return coordinatorRepository.findAll();
    }
    public Optional<Coordinator> getCoordinatorById(UUID id){
        return coordinatorRepository.findById(id);
    }
    public String saveCoordinator(Coordinator coordinator){
        coordinatorRepository.save(coordinator);
        return "Coordinator saved successfully";
    }
    public String updateCoordinator(UUID id, Coordinator updatedCoordinator){
        Optional<Coordinator> existingCoordinator = coordinatorRepository.findById(id);
        if(existingCoordinator.isEmpty()){
            return "The coordinator with that id does not exist";
        }
        Coordinator coordinator = existingCoordinator.get();
        coordinator.setFirstName(updatedCoordinator.getFirstName());
        coordinator.setLastName(updatedCoordinator.getLastName());
        coordinator.setPhone(updatedCoordinator.getPhone());
        coordinator.setCell(updatedCoordinator.getCell());
        coordinator.setActivityTypes(updatedCoordinator.getActivityTypes());
        coordinatorRepository.save(coordinator);
        return "Coordinator updated successfully";
    }
    public String deleteCoordinatorById(UUID id){
        Optional<Coordinator> existingCoordinator = coordinatorRepository.findById(id);
        if(existingCoordinator.isEmpty()){
            return "The coordinator with that id does not exist";
        }
        coordinatorRepository.deleteById(id);
        return "Coordinator deleted successfully";
    }
}

package com.umuganda_ms.rw.Service;

import java.util.*;
import org.springframework.stereotype.*;

import com.umuganda_ms.rw.Repository.CoordinatorRepository;
import com.umuganda_ms.rw.Repository.CellRepository;
import com.umuganda_ms.rw.Repository.ActivityTypeRepository;

import com.umuganda_ms.rw.domain.Coordinator;
import com.umuganda_ms.rw.domain.Cell;
import com.umuganda_ms.rw.domain.ActivityType;

@Service
public class CoordinatorService {
    private final CoordinatorRepository coordinatorRepository;
    private final CellRepository cellRepository;
    private final ActivityTypeRepository activityTypeRepository;
    public CoordinatorService(CoordinatorRepository coordinatorRepository, CellRepository cellRepository,
        ActivityTypeRepository activityTypeRepository
    ){
        this.coordinatorRepository = coordinatorRepository;
        this.activityTypeRepository = activityTypeRepository;
        this.cellRepository = cellRepository;
    }

    public List<Coordinator> getAllCoordinators(){
        return coordinatorRepository.findAll();
    }
    public Optional<Coordinator> getCoordinatorById(UUID id){
        return coordinatorRepository.findById(id);
    }
    public String saveCoordinator(Coordinator coordinator) {

    // Find the existing cell
    if (coordinator.getCell() != null) {
        UUID cellId = coordinator.getCell().getId();

        if (cellId == null) {
            return "A valid cell ID is required";
        }

        Optional<Cell> existingCell = cellRepository.findById(cellId);

        if (existingCell.isEmpty()) {
            return "The cell with that id does not exist";
        }

        coordinator.setCell(existingCell.get());
    }

    // Find the existing activity types
    List<ActivityType> existingTypes = new ArrayList<>();

    if (coordinator.getActivityTypes() != null) {
        for (ActivityType activityType : coordinator.getActivityTypes()) {

            if (activityType.getId() == null) {
                return "A valid activity type ID is required";
            }

            Optional<ActivityType> existingType =
                    activityTypeRepository.findById(activityType.getId());

            if (existingType.isEmpty()) {
                return "The activity type with that id does not exist";
            }

            existingTypes.add(existingType.get());
        }
    }

    coordinator.setActivityTypes(existingTypes);

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

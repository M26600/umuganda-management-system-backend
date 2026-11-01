package com.umuganda_ms.rw.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.umuganda_ms.rw.Repository.ActivityTypeRepository;
import com.umuganda_ms.rw.Repository.CellRepository;
import com.umuganda_ms.rw.Repository.CoordinatorRepository;
import com.umuganda_ms.rw.domain.ActivityType;
import com.umuganda_ms.rw.domain.Cell;
import com.umuganda_ms.rw.domain.Coordinator;

@Service
public class CoordinatorService {

    private final CoordinatorRepository coordinatorRepository;
    private final CellRepository cellRepository;
    private final ActivityTypeRepository activityTypeRepository;

    public CoordinatorService(CoordinatorRepository coordinatorRepository,
            CellRepository cellRepository,
            ActivityTypeRepository activityTypeRepository) {
        this.coordinatorRepository = coordinatorRepository;
        this.activityTypeRepository = activityTypeRepository;
        this.cellRepository = cellRepository;
    }

    public List<Coordinator> getAllCoordinators() {
        return coordinatorRepository.findAll();
    }

    public Optional<Coordinator> getCoordinatorById(UUID id) {
        return coordinatorRepository.findById(id);
    }

    public String saveCoordinator(Coordinator coordinator) {

        if (coordinator.getCell() != null && coordinator.getCell().getId() != null) {
            Optional<Cell> existingCell = cellRepository.findById(coordinator.getCell().getId());
            if (existingCell.isEmpty()) {
                return "The cell with that id does not exist";
            }
            coordinator.setCell(existingCell.get());
        }

        if (coordinator.getActivityTypes() != null && !coordinator.getActivityTypes().isEmpty()) {
            List<ActivityType> existingTypes = new ArrayList<>();
            for (ActivityType activityType : coordinator.getActivityTypes()) {
                if (activityType.getId() != null) {
                    Optional<ActivityType> existingType
                            = activityTypeRepository.findById(activityType.getId());
                    if (existingType.isEmpty()) {
                        return "The activity type with that id does not exist";
                    }
                    existingTypes.add(existingType.get());
                }
            }
            coordinator.setActivityTypes(existingTypes);
        }

        coordinatorRepository.save(coordinator);
        return "Coordinator saved successfully";
    }

    public String updateCoordinator(UUID id, Coordinator updatedCoordinator) {
        Optional<Coordinator> existingCoordinator = coordinatorRepository.findById(id);
        if (existingCoordinator.isEmpty()) {
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

    public String deleteCoordinatorById(UUID id) {
        Optional<Coordinator> existingCoordinator = coordinatorRepository.findById(id);
        if (existingCoordinator.isEmpty()) {
            return "The coordinator with that id does not exist";
        }

        coordinatorRepository.deleteById(id);
        return "Coordinator deleted successfully";
    }

    // C1
    public List<Coordinator> getCoordinatorsByActivityType(String name) {
        return coordinatorRepository.findByActivityTypeName(name);
    }

    // D3
    public String addActivityType(UUID coordinatorId, UUID activityTypeId) {
        Optional<Coordinator> coordinatorOptional = coordinatorRepository.findById(coordinatorId);
        if (coordinatorOptional.isEmpty()) {
            return "The coordinator with that id does not exist";
        }

        Optional<ActivityType> activityTypeOptional = activityTypeRepository.findById(activityTypeId);
        if (activityTypeOptional.isEmpty()) {
            return "That activity type does not exist";
        }

        Coordinator coordinator = coordinatorOptional.get();
        ActivityType activityType = activityTypeOptional.get();

        if (coordinator.getActivityTypes() == null) {
            coordinator.setActivityTypes(new ArrayList<>());
        }
        if (coordinator.getActivityTypes().contains(activityType)) {
            return "The coordinator already has that activity type";
        }

        coordinator.getActivityTypes().add(activityType);
        coordinatorRepository.save(coordinator);

        return "Activity type added to the coordinator";
    }

    // D4
    public String changeCoordinatorCell(UUID coordinatorId, int cellNumber) {
        Optional<Coordinator> coordinatorOptional = coordinatorRepository.findById(coordinatorId);
        if (coordinatorOptional.isEmpty()) {
            return "The coordinator with that id does not exist";
        }

        Optional<Cell> cellOptional = cellRepository.findByCellNumber(cellNumber);
        if (cellOptional.isEmpty()) {
            return "The cell with that cellNumber does not exist";
        }

        Coordinator coordinator = coordinatorOptional.get();
        Cell newCell = cellOptional.get();

        if (coordinator.getCell() != null
                && coordinator.getCell().getId().equals(newCell.getId())) {
            return "Cell changed successfully";
        }

        if (coordinatorRepository.existsByCell(newCell)) {
            return "That cell is taken by another coordinator";
        }

        coordinator.setCell(newCell);
        coordinatorRepository.save(coordinator);
        return "Cell changed successfully";
    }
}

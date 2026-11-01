package com.umuganda_ms.rw.Controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.umuganda_ms.rw.Service.CoordinatorService;
import com.umuganda_ms.rw.domain.Coordinator;

@RestController
@RequestMapping("/api/coordinators")
public class CoordinatorController {

    private final CoordinatorService coordinatorService;

    public CoordinatorController(CoordinatorService coordinatorService) {
        this.coordinatorService = coordinatorService;
    }

    // A2
    @PostMapping("/save")
    public ResponseEntity<String> saveCoordinator(@RequestBody Coordinator coordinator) {
        String message = coordinatorService.saveCoordinator(coordinator);
        if (message.equals("The cell with that id does not exist") || message.equals("The activity type with that id does not exist")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(message);
    }

    // A2
    @GetMapping("/all")
    public ResponseEntity<List<Coordinator>> getAllCoordinators() {
        List<Coordinator> coordinators = coordinatorService.getAllCoordinators();
        return ResponseEntity.ok(coordinators);
    }

    // A2
    @GetMapping("/{id}")
    public ResponseEntity<?> getCoordinatorById(@PathVariable UUID id) {
        Optional<Coordinator> existingCoordinator = coordinatorService.getCoordinatorById(id);
        if (existingCoordinator.isPresent()) {
            return ResponseEntity.ok(existingCoordinator.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("The coordinator with that id does not exist");
        }
    }

    // A2
    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateCoordinator(
            @PathVariable UUID id,
            @RequestBody Coordinator updatedCoordinator) {
        String message = coordinatorService.updateCoordinator(id, updatedCoordinator);
        if (message.equals("The coordinator with that id does not exist")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        } else {
            return ResponseEntity.ok(message);
        }
    }

    // A2
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteCoordinator(@PathVariable UUID id) {
        String message = coordinatorService.deleteCoordinatorById(id);
        if (message.equals("The coordinator with that id does not exist")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        } else {
            return ResponseEntity.ok(message);
        }
    }

    // C1
    @GetMapping("/by-activity-type")
    public ResponseEntity<List<Coordinator>> getCoordinatorsByActivityType(
            @RequestParam String name) {
        List<Coordinator> coordinators = coordinatorService.getCoordinatorsByActivityType(name);
        return ResponseEntity.ok(coordinators);
    }

    // D3
    @PutMapping("/{coordinatorId}/activity-types/{activityTypeId}")
    public ResponseEntity<String> addActivityType(
            @PathVariable UUID coordinatorId,
            @PathVariable UUID activityTypeId) {
        String message = coordinatorService.addActivityType(coordinatorId, activityTypeId);
        if (message.equals("The coordinator with that id does not exist")
                || message.equals("That activity type does not exist")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        } else if (message.equals("The coordinator already has that activity type")) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(message);
        } else {
            return ResponseEntity.ok(message);
        }
    }

    // D4
    @PatchMapping("/{coordinatorId}/cell")
    public ResponseEntity<String> changeCoordinatorCell(
            @PathVariable UUID coordinatorId,
            @RequestParam int cellNumber) {
        String message = coordinatorService.changeCoordinatorCell(coordinatorId, cellNumber);
        if (message.equals("The coordinator with that id does not exist")
                || message.equals("The cell with that cellNumber does not exist")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        } else if (message.equals("That cell is taken by another coordinator")) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(message);
        } else {
            return ResponseEntity.ok(message);
        }
    }
}

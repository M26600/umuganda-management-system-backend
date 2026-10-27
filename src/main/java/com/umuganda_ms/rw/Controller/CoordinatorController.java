package com.umuganda_ms.rw.Controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.*;
import java.util.*;

import com.umuganda_ms.rw.Service.CoordinatorService;
import com.umuganda_ms.rw.domain.Coordinator;

@RestController 
@RequestMapping ("/api/coordinators")
public class CoordinatorController {
    private final CoordinatorService coordinatorService;
    public CoordinatorController(CoordinatorService coordinatorService){
        this.coordinatorService = coordinatorService;
    }
    @PostMapping ("/save")
    public ResponseEntity<String> saveCoordinator(@RequestBody Coordinator coordinator){
        String message = coordinatorService.saveCoordinator(coordinator);
        return ResponseEntity.status(HttpStatus.CREATED)
        .body(message);
    }
    @GetMapping ("/all")
    public ResponseEntity<List<Coordinator>> getAllCoordinators(){
        List<Coordinator> coordinators = coordinatorService.getAllCoordinators();
        return ResponseEntity.ok(coordinators);
    }
    @GetMapping ("/{id}")
    public ResponseEntity<?> getCoordinatorById(@PathVariable UUID id){
        Optional<Coordinator> existingCoordinator = coordinatorService.getCoordinatorById(id);
        if(existingCoordinator.isPresent()){
            return ResponseEntity.ok(existingCoordinator.get());
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body("The coordinator with that id does not exist");
        }
    }
    @PutMapping ("/update/{id}")
    public ResponseEntity<String> updateCoordinator(@PathVariable UUID id, @RequestBody Coordinator updatedCoordinator){
        String message = coordinatorService.updateCoordinator(id, updatedCoordinator);
        if(message.equals("The coordinator with that id does not exist")){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(message);
        }else{
            return ResponseEntity.ok(message);
        }
    }
    @DeleteMapping ("/delete/{id}")
    public ResponseEntity<String> deleteCoordinator(@PathVariable UUID id){
        String message = coordinatorService.deleteCoordinatorById(id);
        if(message.equals("The coordinator with that id does not exist")){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(message);
        }else{
            return ResponseEntity.ok(message);
        }
    }
    @GetMapping("/by-activity-type")
    public ResponseEntity<List<Coordinator>> getCoordinatorsByActivityType(
        @RequestParam String name
    ){
        List<Coordinator> coordinators = coordinatorService.getCoordinatorsByActivityType(name);
        return ResponseEntity.ok(coordinators);
    }
}

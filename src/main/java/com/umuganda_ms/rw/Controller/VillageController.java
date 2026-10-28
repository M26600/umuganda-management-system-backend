package com.umuganda_ms.rw.Controller;


import org.springframework.web.bind.annotation.*;
import org.springframework.http.*;
import java.util.*;

import com.umuganda_ms.rw.Service.VillageService;
import com.umuganda_ms.rw.domain.Village;
import com.umuganda_ms.rw.Repository.CoordinatorRepository;
@RestController 
@RequestMapping ("/api/villages")
public class VillageController {
    private final VillageService villageService;
    private final CoordinatorRepository coordinatorRepository;
    public VillageController(VillageService villageService, CoordinatorRepository coordinatorRepository){
        this.villageService = villageService;
        this.coordinatorRepository = coordinatorRepository;
    }
    @PostMapping ("/save")
    public ResponseEntity<String> saveVillage(@RequestBody Village village){
        String message = villageService.saveVillage(village);
        return ResponseEntity.status(HttpStatus.CREATED)
        .body(message);
    }
    @GetMapping ("/all")
    public ResponseEntity<List<Village>> getAllVillages(){
        List<Village> villages = villageService.getAllVillages();
        return ResponseEntity.ok(villages);
    }
    @GetMapping ("/{id}")
    public ResponseEntity<?> getVillageById(@PathVariable UUID id){
        Optional<Village> existingVillage  = villageService.getVillageById(id);
        if(existingVillage.isPresent()){
            return ResponseEntity.ok(existingVillage.get());
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body("The village with that id does not exist");
        }
    }
    @PutMapping ("/update/{id}")
    public ResponseEntity<String> updateVillage(@PathVariable UUID id,@RequestBody Village updatedVillage){
        String message = villageService.updateVillage(id, updatedVillage);
        if(message.equals("The village with that id does not exist")){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(message);
        }else{
            return ResponseEntity.ok(message);
        }
    }
    @DeleteMapping ("/delete/{id}")
    public ResponseEntity<String> deleteVillage(@PathVariable UUID id){
        String message = villageService.deleteVillage(id);
        if(message.equals("The village with that id does not exist")){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(message);
        }
        else{
            return ResponseEntity.ok(message);
        }
    }
    @GetMapping("/by-sector-name")
public ResponseEntity<List<Village>> getVillagesBySectorName(
        @RequestParam String sectorName) {

    List<Village> villages =
            villageService.getVillagesBySectorName(sectorName);

    return ResponseEntity.ok(villages);
}
@GetMapping("/of-coordinator/{coordinatorId}")
public ResponseEntity<?> getVillagesOfCoordinator(
    @PathVariable UUID coordinatorId){
        if(!coordinatorRepository.existsById(coordinatorId)){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body("The coordinator with that id does not exist");
        }else {
            List<Village> villages = villageService.getVillagesOfCoordinator(coordinatorId);
            return ResponseEntity.ok(villages);
        }
    }
}

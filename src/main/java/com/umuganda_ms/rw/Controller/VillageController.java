package com.umuganda_ms.rw.Controller;


import org.springframework.web.bind.annotation.*;
import org.springframework.http.*;

import java.lang.annotation.Repeatable;
import java.util.*;

import com.umuganda_ms.rw.Service.VillageService;
import com.umuganda_ms.rw.domain.Village;

@RestController 
@RequestMapping ("/api/villages")
public class VillageController {
    private final VillageService villageService;
    public VillageController(VillageService villageService){
        this.villageService = villageService;
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
}

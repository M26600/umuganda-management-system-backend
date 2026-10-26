package com.umuganda_ms.rw.Service;


import java.util.*;
import org.springframework.stereotype.*;
import com.umuganda_ms.rw.Repository.VillageRepository;
import com.umuganda_ms.rw.domain.Village;

@Service
public class VillageService {
    private final VillageRepository villageRepository;
    public VillageService(VillageRepository villageRepository){
        this.villageRepository = villageRepository;
    }
    public List<Village> getAllVillages(){
        return villageRepository.findAll();
    }
    public Optional<Village> getVillageById(UUID id){
        return villageRepository.findById(id);
    }
    public String saveVillage(Village village){
        villageRepository.save(village);
        return "Village saved successfully";
    }
    public String updateVillage(UUID id, Village updatedVillage){
        Optional<Village> existingVillage = villageRepository.findById(id);
        if(existingVillage.isEmpty()){
            return "The village with that id does not exist";
        }
        Village village = existingVillage.get();
        village.setVillageName(updatedVillage.getVillageName());
        village.setLeaderName(updatedVillage.getLeaderName());
        village.setVillageCode(updatedVillage.getVillageCode());
        village.setSectorName(updatedVillage.getSectorName());
        villageRepository.save(village);
        return "Village updated successfully";
    }
    public String deleteVillage(UUID id){
        Optional<Village> existingVillage = villageRepository.findById(id);
        if(existingVillage.isEmpty()){
            return "The village with that id does not exist";
        }
        villageRepository.deleteById(id);
        return "Village deleted successfully";
    }
    public List<Village> getVillagesBySectorName(String sectorName) {
    return villageRepository
            .findBySectorNameIgnoreCaseOrderByVillageNameAsc(sectorName);
}

}

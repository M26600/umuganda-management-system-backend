package com.umuganda_ms.rw.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.umuganda_ms.rw.Repository.CoordinatorRepository;
import com.umuganda_ms.rw.Repository.VillageRepository;
import com.umuganda_ms.rw.domain.Village;

@Service
public class VillageService {

    private final VillageRepository villageRepository;
    private final CoordinatorRepository coordinatorRepository;

    public VillageService(VillageRepository villageRepository,
            CoordinatorRepository coordinatorRepository) {
        this.villageRepository = villageRepository;
        this.coordinatorRepository = coordinatorRepository;
    }

    public List<Village> getAllVillages() {
        return villageRepository.findAll();
    }

    public Optional<Village> getVillageById(UUID id) {
        return villageRepository.findById(id);
    }

    // A2
    public String saveVillage(Village village) {
        villageRepository.save(village);
        return "Village saved successfully";
    }

    // A2
    public String updateVillage(UUID id, Village updatedVillage) {
        Optional<Village> existingVillage = villageRepository.findById(id);
        if (existingVillage.isEmpty()) {
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

    // A2
    public String deleteVillage(UUID id) {
        Optional<Village> existingVillage = villageRepository.findById(id);
        if (existingVillage.isEmpty()) {
            return "The village with that id does not exist";
        }
        villageRepository.deleteById(id);
        return "Village deleted successfully";
    }

    // B1
    public List<Village> getVillagesBySectorName(String sectorName) {
        return villageRepository.findBySectorNameIgnoreCaseOrderByVillageNameAsc(sectorName);
    }

    // C4
    public Optional<List<Village>> getVillagesOfCoordinator(UUID coordinatorId) {
        if (!coordinatorRepository.existsById(coordinatorId)) {
            return Optional.empty();
        }
        return Optional.of(villageRepository.findVillagesByCoordinatorId(coordinatorId));
    }
}

package com.umuganda_ms.rw.Repository;

import org.springframework.data.jpa.repository.*;
import java.util.*;
import org.springframework.data.repository.query.*;
import com.umuganda_ms.rw.domain.Village;
public interface VillageRepository extends JpaRepository <Village, UUID>{
//B1 task
List <Village> findBySectorNameIgnoreCaseOrderByVillageNameAsc(String sectorName);
//C4 task for finding village activities assigned to a coordinator
@Query("SELECT DISTINCT a.village FROM Activity a "+ "WHERE a.coordinator.id = :coordinatorId")
List<Village> findVillagesByCoordinatorId(
    @Param("coordinatorId") UUID coordinatorId);

}

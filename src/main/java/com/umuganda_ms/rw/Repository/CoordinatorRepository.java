package com.umuganda_ms.rw.Repository;

import org.springframework.data.jpa.repository.*;
import java.util.*;
import com.umuganda_ms.rw.domain.Coordinator;
import com.umuganda_ms.rw.domain.Cell;
import org.springframework.data.repository.query.Param;

public interface CoordinatorRepository extends JpaRepository<Coordinator, UUID> {
//C1
@Query("SELECT DISTINCT c FROM Coordinator c "+
    "JOIN c.activityTypes at " + 
    "WHERE LOWER(at.name) = LOWER(:name)")
List<Coordinator> findByActivityTypeName(
    @Param("name") String name);

//D4 moving the coordinator to another cell.
boolean existsByCell(Cell cell);
}

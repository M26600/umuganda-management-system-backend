package com.umuganda_ms.rw.Repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.umuganda_ms.rw.domain.Cell;
import com.umuganda_ms.rw.domain.Coordinator;

public interface CoordinatorRepository extends JpaRepository<Coordinator, UUID> {

    // C1
    @Query("SELECT DISTINCT c FROM Coordinator c JOIN c.activityTypes at WHERE LOWER(at.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Coordinator> findByActivityTypeName(@Param("name") String name);

    // D4
    boolean existsByCell(Cell cell);
}

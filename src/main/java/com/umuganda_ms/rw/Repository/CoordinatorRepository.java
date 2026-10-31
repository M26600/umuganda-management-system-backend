package com.umuganda_ms.rw.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

import com.umuganda_ms.rw.domain.Coordinator;
import com.umuganda_ms.rw.domain.Cell;

public interface CoordinatorRepository extends JpaRepository<Coordinator, UUID> {

    // C1: Coordinators by activity type (JOIN on collection, LOWER)
    @Query("SELECT DISTINCT c FROM Coordinator c JOIN c.activityTypes at WHERE LOWER(at.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Coordinator> findByActivityTypeName(@Param("name") String name);

    // D4: Check if cell is occupied
    boolean existsByCell(Cell cell);
}

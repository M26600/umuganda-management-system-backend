package com.umuganda_ms.rw.Repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.umuganda_ms.rw.domain.ActivityType;

public interface ActivityTypeRepository extends JpaRepository<ActivityType, UUID> {

    // C3
    @Query("SELECT at FROM ActivityType at WHERE at.coordinators IS EMPTY")
    List<ActivityType> findUnusedActivityTypes();
}

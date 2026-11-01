package com.umuganda_ms.rw.Repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.umuganda_ms.rw.domain.Activity;
import com.umuganda_ms.rw.domain.ActivityReport;

public interface ActivityReportRepository extends JpaRepository<ActivityReport, UUID> {

    // E2
    @Query("SELECT r FROM ActivityReport r WHERE r.activity.village.villageCode = :villageCode ORDER BY r.submittedDate DESC")
    List<ActivityReport> findReportsByVillageCode(@Param("villageCode") String villageCode);

    // E3
    boolean existsByActivity(Activity activity);
}

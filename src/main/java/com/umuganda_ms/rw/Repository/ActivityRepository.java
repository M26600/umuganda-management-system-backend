package com.umuganda_ms.rw.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.umuganda_ms.rw.domain.Activity;
import com.umuganda_ms.rw.domain.ActivityStatus;

public interface ActivityRepository extends JpaRepository<Activity, UUID> {
//B2

    List<Activity> findByStatusOrderByActivityDateAsc(ActivityStatus status);

    List<Activity> findByActivityDateBetweenOrderByActivityDateAsc(
            LocalDate start, LocalDate end);
//D1

    boolean existsByCoordinator_IdAndActivityDateAndStatusNot(
            UUID coordinatorId,
            LocalDate activityDate,
            ActivityStatus status
    );

    @Query("SELECT a.status, COUNT(a) FROM Activity a GROUP BY a.status")
    List<Object[]> countActivitiesByStatus();

    @Modifying
    @Query("UPDATE Activity a SET a.status = :newStatus "
            + "WHERE a.coordinator.id = :coordinatorId "
            + "AND a.activityDate = :date "
            + "AND a.status <> :excludedStatus ")
    int cancelActivitiesForCoordinatorOnDate(
            @Param("coordinatorId") UUID coordinatorId,
            @Param("date") LocalDate date,
            @Param("newStatus") ActivityStatus newStatus,
            @Param("excludedStatus") ActivityStatus excludedStatus
    );

    long countByActivityDate(LocalDate activityDate);

    long countByStatus(ActivityStatus status);

    Page<Activity> findAll(Pageable pageable);
}

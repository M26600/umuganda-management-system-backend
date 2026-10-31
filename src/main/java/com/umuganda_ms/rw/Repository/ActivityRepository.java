package com.umuganda_ms.rw.Repository;
import java.util.*;
import com.umuganda_ms.rw.domain.Activity;
import com.umuganda_ms.rw.domain.ActivityStatus;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;



public interface ActivityRepository extends JpaRepository<Activity, UUID>{
//B2
List<Activity> findByStatusOrderByActivityDateAsc(ActivityStatus status);
List<Activity> findByActivityDateBetweenOrderByActivityDateAsc(
    LocalDate start, LocalDate end);
//D1: Check for coordinator scheduling the conflicts
boolean existsByCoordinator_IdAndActivityDateAndStatusNot(
    UUID coordinatorId,
    LocalDate activityDate,
    ActivityStatus status
);
@Query("SELECT a.status, COUNT(a) FROM Activity a GROUP BY a.status")
List<Object[]> countActivitiesByStatus();

@Modifying
@Query("UPDATE Activity a SET a.status = :newStatus " +
       "WHERE a.coordinator.id = :coordinatorId " +
       "AND a.activityDate = :date " +
       "AND a.status <> :excludedStatus " +
       "AND a.status <> :newStatus")
int cancelActivitiesForCoordinatorOnDate(
        @Param("coordinatorId") UUID coordinatorId,
        @Param("date") LocalDate date,
        @Param("newStatus") ActivityStatus newStatus,
        @Param("excludedStatus") ActivityStatus excludedStatus
);
}

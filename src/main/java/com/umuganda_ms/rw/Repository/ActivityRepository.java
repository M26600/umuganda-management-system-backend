package com.umuganda_ms.rw.Repository;
import java.util.*;
import com.umuganda_ms.rw.domain.Activity;
import com.umuganda_ms.rw.domain.ActivityStatus;
import org.springframework.data.jpa.repository.*;
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
}

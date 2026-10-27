package com.umuganda_ms.rw.Repository;
import java.util.*;
import com.umuganda_ms.rw.domain.Activity;
import com.umuganda_ms.rw.domain.ActivityStatus;
import org.springframework.data.jpa.repository.*;



public interface ActivityRepository extends JpaRepository<Activity, UUID>{
//B2
List<Activity> findByStatusOrderByActivityDateAsc(ActivityStatus status);
}

package com.umuganda_ms.rw.Repository;
import com.umuganda_ms.rw.domain.ActivityType;
import java.util.*;
import org.springframework.data.jpa.repository.*;

public interface ActivityTypeRepository extends JpaRepository<ActivityType, UUID> {
@Query("SELECT at FROM ActivityType at "+
    "WHERE at.coordinators IS EMPTY")
List<ActivityType> findnusedActivityTypes();
}

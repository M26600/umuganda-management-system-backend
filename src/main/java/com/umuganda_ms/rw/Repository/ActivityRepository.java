package com.umuganda_ms.rw.Repository;
import java.util.*;
import com.umuganda_ms.rw.domain.Activity;
import org.springframework.data.jpa.repository.*;


public interface ActivityRepository extends JpaRepository<Activity, UUID>{

}

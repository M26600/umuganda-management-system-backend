package com.umuganda_ms.rw.Repository;

import org.springframework.data.jpa.repository.*;
import java.util.*;
import com.umuganda_ms.rw.domain.Coordinator;

public interface CoordinatorRepository extends JpaRepository<Coordinator, UUID> {

}

package com.umuganda_ms.rw.Repository;

import java.util.*;

import org.springframework.data.jpa.repository.JpaRepository;

import com.umuganda_ms.rw.domain.ActivityReport;

public interface ActivityReportRepository extends JpaRepository<ActivityReport, UUID>{

}

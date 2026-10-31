package com.umuganda_ms.rw.Service;

import java.time.*;
import java.util.*;

import org.springframework.stereotype.Service;

import com.umuganda_ms.rw.Repository.VillageRepository;
import com.umuganda_ms.rw.Repository.CoordinatorRepository;
import com.umuganda_ms.rw.Repository.CellRepository;
import com.umuganda_ms.rw.Repository.ActivityRepository;
import com.umuganda_ms.rw.Repository.ActivityReportRepository;
import com.umuganda_ms.rw.domain.ActivityStatus;

@Service
public class DashboardService {
     private final VillageRepository villageRepository;
    private final CoordinatorRepository coordinatorRepository;
    private final CellRepository cellRepository;
    private final ActivityRepository activityRepository;
    private final ActivityReportRepository activityReportRepository;

    public DashboardService(
            VillageRepository villageRepository,
            CoordinatorRepository coordinatorRepository,
            CellRepository cellRepository,
            ActivityRepository activityRepository,
            ActivityReportRepository activityReportRepository) {

        this.villageRepository = villageRepository;
        this.coordinatorRepository = coordinatorRepository;
        this.cellRepository = cellRepository;
        this.activityRepository = activityRepository;
        this.activityReportRepository = activityReportRepository;
    }

    public Map<String, Long> getDashboardSummary() {

        Map<String, Long> summary = new LinkedHashMap<>();

        summary.put("villages", villageRepository.count());
        summary.put("coordinators", coordinatorRepository.count());
        summary.put("freeCells", (long) cellRepository.findFreeCells().size());
        summary.put("activitiesToday",
                activityRepository.countByActivityDate(LocalDate.now()));
        summary.put("planned",
                activityRepository.countByStatus(ActivityStatus.PLANNED));
        summary.put("activityReports", activityReportRepository.count());

        return summary;
    }
}

package com.umuganda_ms.rw.Controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.umuganda_ms.rw.Service.DashboardService;

@RestController
@RequestMapping("/api/dashboard")

public class DashboardController {
     private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping("/summary")
    public ResponseEntity<Map<String, Long>> getDashboardSummary() {
        return ResponseEntity.ok(
                dashboardService.getDashboardSummary()
        );
    }
}

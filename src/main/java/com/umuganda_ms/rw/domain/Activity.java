package com.umuganda_ms.rw.domain;

import jakarta.persistence.*;
import java.util.*;
import java.time.*;

@Entity 
@Table(name = "activity")
public class Activity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private LocalDate activityDate;
    private String objective;
    @ManyToOne 
    @JoinColumn(name = "village_id")
    private Village village;
    @ManyToOne
    @JoinColumn(name = "coordinator_id")
    private Coordinator coordinator;
    @Enumerated(EnumType.STRING)
    private ActivityStatus status;
    public Activity() {
    }
    public Activity(LocalDate activityDate, String objective, Village village, Coordinator coordinator,
            ActivityStatus status) {
        this.activityDate = activityDate;
        this.objective = objective;
        this.village = village;
        this.coordinator = coordinator;
        this.status = status;
    }
    public UUID getId() {
        return id;
    }
    public void setId(UUID id) {
        this.id = id;
    }
    public LocalDate getActivityDate() {
        return activityDate;
    }
    public void setActivityDate(LocalDate activityDate) {
        this.activityDate = activityDate;
    }
    public String getObjective() {
        return objective;
    }
    public void setObjective(String objective) {
        this.objective = objective;
    }
    public Village getVillage() {
        return village;
    }
    public void setVillage(Village village) {
        this.village = village;
    }
    public Coordinator getCoordinator() {
        return coordinator;
    }
    public void setCoordinator(Coordinator coordinator) {
        this.coordinator = coordinator;
    }
    public ActivityStatus getStatus() {
        return status;
    }
    public void setStatus(ActivityStatus status) {
        this.status = status;
    }
    
    
    
}

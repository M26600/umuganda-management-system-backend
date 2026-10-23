package com.umuganda_ms.rw.domain;

import java.util.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name="activity_type")
public class ActivityType {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;
    @ManyToMany(mappedBy = "activityTypes")
    @JsonIgnore
    private List<Coordinator> coordinators = new ArrayList<>();
    public ActivityType(){

    }
    public ActivityType(String name){
        this.name = name;
    }
    public void setId(UUID id){
        this.id = id;
    }
    public UUID getId(){
        return id;
    }
    public void setName(String name){
        this.name = name;
    }
    public String getName(){
        return name;
    }
    public void setCoordinators(List<Coordinator> coordinators){
        this.coordinators = coordinators;
    }
    public List<Coordinator> getCoordinators(){
        return coordinators;
    }
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                        
}

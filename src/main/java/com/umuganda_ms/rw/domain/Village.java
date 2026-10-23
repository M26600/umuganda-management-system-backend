package com.umuganda_ms.rw.domain;

import jakarta.persistence.*;

import java.util.*;

@Entity 
@Table(name = "village")
public class Village {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    
    private UUID id;
    private String villageName;
    private String leaderName;
    @Column(unique = true)
    private String villageCode;
    private String sectorName;
    public Village() {
    }
    public Village(String villageName, String leaderName, String villageCode, String sectorName) {
        this.villageName = villageName;
        this.leaderName = leaderName;
        this.villageCode = villageCode;
        this.sectorName = sectorName;
    }
    public UUID getId() {
        return id;
    }
    public void setId(UUID id) {
        this.id = id;
    }
    public String getVillageName() {
        return villageName;
    }
    public void setVillageName(String villageName) {
        this.villageName = villageName;
    }
    public String getLeaderName() {
        return leaderName;
    }
    public void setLeaderName(String leaderName) {
        this.leaderName = leaderName;
    }
    public String getVillageCode() {
        return villageCode;
    }
    public void setVillageCode(String villageCode) {
        this.villageCode = villageCode;
    }
    public String getSectorName() {
        return sectorName;
    }
    public void setSectorName(String sectorName) {
        this.sectorName = sectorName;
    }

    
}

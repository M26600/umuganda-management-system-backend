/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.umuganda_ms.rw.domain;
import java.util.*;
import jakarta.persistence.*;
/**
 *
 * @author Administrator
 */
@Entity
@Table(name="coordinator")
public class Coordinator {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(nullable=false, name = "first_name")
    private String firstName;
    @Column(nullable = false, name = "last_name")
    private String lastName;
    @Column(name = "phone", nullable = false)
    private String phone;
    @ManyToMany
    @JoinTable(
        name = "coordinator_activity_type",
        joinColumns = @JoinColumn(name = "coordinator_id"),
        inverseJoinColumns = @JoinColumn(name = "activity_type_id")
    )
    private List<ActivityType> activityTypes = new ArrayList<>();
    @ManyToOne
    @JoinColumn(name = "cell_id", nullable = true)
    private Cell cell;

    public Coordinator() {
    }

    
    public Coordinator(String firstName, String lastName, String phone, List<ActivityType> activityTypes,
            Cell cell) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.activityTypes = activityTypes;
        this.cell = cell;
    }


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<ActivityType> getActivityTypes() {
        return activityTypes;
    }

    public void setActivityTypes(List<ActivityType> activityTypes) {
        this.activityTypes = activityTypes;
    }

    public Cell getCell() {
        return cell;
    }

    public void setCell(Cell cell) {
        this.cell = cell;
    }
    
}

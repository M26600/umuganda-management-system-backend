package com.umuganda_ms.rw.domain;

import jakarta.persistence.*;
import java.util.*;

@Entity 
@Table(name = "cell")
public class Cell {
    @Id 
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private Integer cellNumber;
    private String name;
    public Cell() {
    }
    public Cell(Integer cellNumber, String name) {
        this.cellNumber = cellNumber;
        this.name = name;
    }
    public UUID getId() {
        return id;
    }
    public void setId(UUID id) {
        this.id = id;
    }
    public Integer getCellNumber() {
        return cellNumber;
    }
    public void setCellNumber(Integer  cellNumber) {
        this.cellNumber = cellNumber;
    }
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }
    
}

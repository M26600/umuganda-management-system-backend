package com.umuganda_ms.rw.domain;

import jakarta.persistence.*;
import java.util.*;

@Entity 
@Table(name = "cell")
public class Cell {

    @Id 
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private int cellNumber;

    private String name;

    public Cell() {
    }

    public Cell(int cellNumber, String name) {
        this.cellNumber = cellNumber;
        this.name = name;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public int getCellNumber() {
        return cellNumber;
    }

    public void setCellNumber(int cellNumber) {
        this.cellNumber = cellNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cell cell = (Cell) o;
        return id != null && id.equals(cell.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}

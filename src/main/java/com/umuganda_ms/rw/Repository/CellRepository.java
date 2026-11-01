package com.umuganda_ms.rw.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.umuganda_ms.rw.domain.Cell;

public interface CellRepository extends JpaRepository<Cell, UUID> {

    Optional<Cell> findByCellNumber(int cellNumber);

    @Query("SELECT c FROM Cell c " + "WHERE NOT EXISTS ("
            + "SELECT co FROM Coordinator co "
            + "WHERE co.cell = c) "
            + "ORDER BY c.cellNumber ASC")
    List<Cell> findFreeCells();

}

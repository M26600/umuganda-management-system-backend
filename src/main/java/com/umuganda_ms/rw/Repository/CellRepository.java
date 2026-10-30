package com.umuganda_ms.rw.Repository;

import com.umuganda_ms.rw.domain.Cell;
import java.util.*;
import org.springframework.data.jpa.repository.*;


public interface CellRepository extends JpaRepository<Cell, UUID>{
Optional<Cell> findByCellNumber(int cellNumber);
@Query("SELECT c FROM Cell c "+ "WHERE NOT EXISTS ("+
    "SELECT co FROM Coordinator co "+
    "WHERE co.cell = c) " +
    "ORDER BY c.cellNumber ASC")
List<Cell> findFreeCells();


}

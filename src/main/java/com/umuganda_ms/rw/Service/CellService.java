package com.umuganda_ms.rw.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.umuganda_ms.rw.Repository.CellRepository;
import com.umuganda_ms.rw.domain.Cell;

@Service
public class CellService {

    private final CellRepository cellRepository;

    public CellService(CellRepository cellRepository) {
        this.cellRepository = cellRepository;
    }

    public List<Cell> getAllCells() {
        return cellRepository.findAll();
    }

    public Optional<Cell> getCellById(UUID id) {
        return cellRepository.findById(id);
    }

    // A2
    public String saveCell(Cell cell) {
        cellRepository.save(cell);
        return "Cell saved successfully";
    }

    // A2
    public String updateCell(UUID id, Cell updatedCell) {
        Optional<Cell> existingCell = cellRepository.findById(id);
        if (existingCell.isEmpty()) {
            return "The cell with that id does not exist";
        }
        Cell cell = existingCell.get();
        cell.setCellNumber(updatedCell.getCellNumber());
        cell.setName(updatedCell.getName());
        cellRepository.save(cell);
        return "Cell updated successfully";
    }

    // A2
    public String deleteCell(UUID id) {
        Optional<Cell> existingCell = cellRepository.findById(id);
        if (existingCell.isEmpty()) {
            return "The cell with that id does not exist";
        }
        cellRepository.deleteById(id);
        return "Cell deleted successfully";
    }

    // C2
    public List<Cell> getFreeCells() {
        return cellRepository.findFreeCells();
    }
}

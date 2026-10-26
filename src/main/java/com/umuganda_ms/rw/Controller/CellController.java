package com.umuganda_ms.rw.Controller;

import java.util.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.*;

import com.umuganda_ms.rw.Service.CellService;
import com.umuganda_ms.rw.domain.Cell;

@RestController 
@RequestMapping("/api/cells")
public class CellController {
    private final CellService cellService;
    public CellController(CellService cellService){
        this.cellService = cellService;
    }
    @PostMapping("/save")
    public ResponseEntity<String> saveCell(@RequestBody Cell cell){
        String message = cellService.saveCell(cell);
        return ResponseEntity.status(HttpStatus.CREATED)
        .body(message);
    }
    @GetMapping ("/all")
    public ResponseEntity<List<Cell>> getAllCells(){
        List<Cell> cells = cellService.getAllCells();
        return ResponseEntity.ok(cells);
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getCellById(@PathVariable UUID id){
        Optional<Cell> existingCell = cellService.getCellById(id);
        if(existingCell.isPresent()){
            return ResponseEntity.ok(existingCell.get());
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body("The cell with that id does not exist");
        }
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateCell(@PathVariable UUID id, @RequestBody Cell updatedCell){
        String message = cellService.updateCell(id, updatedCell);
        if(message.equals("The cell with that id does not exist")){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(message);
        }else{
            return ResponseEntity.ok(message);
        }
    }
    @DeleteMapping ("/delete/{id}")
    public ResponseEntity<String> deleteCell(@PathVariable UUID id){
        String message = cellService.deleteCell(id);
        if(message.equals("The cell with that id does not exist")){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(message);
        }
        else{
            return ResponseEntity.ok(message);
        }
    }

}

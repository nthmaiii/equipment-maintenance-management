package equipment_management.equipment.controller;

import equipment_management.equipment.dto.EquipmentRequest;
import equipment_management.equipment.dto.EquipmentResponse;
import equipment_management.equipment.service.EquipmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/equipments")
@RequiredArgsConstructor
public class EquipmentController {
    private final EquipmentService equipmentService;

    @GetMapping
    public ResponseEntity<List<EquipmentResponse>> getAll ()
    {
        return ResponseEntity.ok(equipmentService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EquipmentResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(equipmentService.getById(id));
    }

    @PostMapping
    public ResponseEntity<EquipmentResponse> create (@RequestBody EquipmentRequest request)
    {
        return ResponseEntity.status(201).body(equipmentService.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EquipmentResponse> update(@PathVariable Long id, @RequestBody EquipmentRequest request) {
        return ResponseEntity.ok(equipmentService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        equipmentService.delete(id);
        return ResponseEntity.noContent().build();
    }

}

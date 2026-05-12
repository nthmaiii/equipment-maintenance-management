package equipment_management.equipment.controller;

import equipment_management.equipment.repository.WorkOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/work-order")
@RequiredArgsConstructor
public class WorkOrderController {
    private final WorkOrderRepository workOrderRepository;
    @GetMapping
    public ResponseEntity<?> getAllWorkOrders() {
        return ResponseEntity.ok(workOrderRepository.findAll());
    }
}

package equipment_management.equipment.controller;

import equipment_management.common.dto.ApiResponse;
import equipment_management.equipment.dto.UnscheduledWorkOrderRequest;
import equipment_management.equipment.dto.WorkOrderResponse;
import equipment_management.equipment.dto.WorkOrderUpdateRequest;
import equipment_management.equipment.service.WorkOrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/work-orders")
@RequiredArgsConstructor
public class WorkOrderController {

    private final WorkOrderService service;

    @PostMapping("/unscheduled")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<WorkOrderResponse>> createUnscheduled(@Valid @RequestBody UnscheduledWorkOrderRequest request) {
        WorkOrderResponse response = service.createUnscheduled(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Unscheduled work order created", response));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<ApiResponse<List<WorkOrderResponse>>> getAll() {
        List<WorkOrderResponse> response = service.getAll();
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<ApiResponse<WorkOrderResponse>> getById(@PathVariable UUID id) {
        WorkOrderResponse response = service.getById(id);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<WorkOrderResponse>> update(@PathVariable UUID id, @Valid @RequestBody WorkOrderUpdateRequest request) {
        WorkOrderResponse response = service.update(id, request);
        return ResponseEntity.ok(ApiResponse.success("Work order updated", response));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.ok(ApiResponse.success("Work order deleted", null));
    }
}
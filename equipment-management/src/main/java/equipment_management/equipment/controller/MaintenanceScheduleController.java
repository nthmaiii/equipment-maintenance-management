package equipment_management.equipment.controller;

import equipment_management.common.dto.ApiResponse;
import equipment_management.equipment.dto.MaintenanceScheduleRequest;
import equipment_management.equipment.dto.MaintenanceScheduleResponse;
import equipment_management.equipment.dto.MaintenanceScheduleUpdateRequest;
import equipment_management.equipment.service.MaintenanceScheduleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/maintenance-schedules")
@RequiredArgsConstructor
public class MaintenanceScheduleController {

    private final MaintenanceScheduleService service;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<MaintenanceScheduleResponse>> create(@Valid @RequestBody MaintenanceScheduleRequest request) {
        MaintenanceScheduleResponse response = service.create(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Maintenance schedule created", response));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<ApiResponse<List<MaintenanceScheduleResponse>>> getAll() {
        List<MaintenanceScheduleResponse> response = service.getAll();
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<ApiResponse<MaintenanceScheduleResponse>> getById(@PathVariable UUID id) {
        MaintenanceScheduleResponse response = service.getById(id);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<MaintenanceScheduleResponse>> update(@PathVariable UUID id, @Valid @RequestBody MaintenanceScheduleUpdateRequest request) {
        MaintenanceScheduleResponse response = service.update(id, request);
        return ResponseEntity.ok(ApiResponse.success("Schedule updated", response));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.ok(ApiResponse.success("Schedule deleted", null));
    }
}
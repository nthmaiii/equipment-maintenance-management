package equipment_management.equipment.service;

import equipment_management.equipment.dto.UnscheduledWorkOrderRequest;
import equipment_management.equipment.dto.WorkOrderResponse;
import equipment_management.equipment.dto.WorkOrderUpdateRequest;
import equipment_management.equipment.entity.Equipment;
import equipment_management.equipment.entity.MaintenanceSchedule;
import equipment_management.equipment.entity.WorkOrder;
import equipment_management.equipment.enums.MaintenanceStatus;
import equipment_management.equipment.enums.WorkOrderStatus;
import equipment_management.equipment.repository.EquipmentRepository;
import equipment_management.equipment.repository.WorkOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WorkOrderService {

    private final WorkOrderRepository workOrderRepository;
    private final EquipmentRepository equipmentRepository;

    // CREATE UNSCHEDULED
    @Transactional
    public WorkOrderResponse createUnscheduled(UnscheduledWorkOrderRequest request) {
        Equipment equipment = equipmentRepository.findById(request.equipmentId())
                .orElseThrow(() -> new RuntimeException("Equipment not found"));
        WorkOrder order = WorkOrder.builder()
                .equipment(equipment)
                .title(request.title())
                .description(request.description())
                .dueDate(request.dueDate())
                .status(WorkOrderStatus.OPEN)
                .build();
        return convertToResponse(workOrderRepository.save(order));
    }

    // GET ALL
    @Transactional(readOnly = true)
    public List<WorkOrderResponse> getAll() {
        return workOrderRepository.findAll().stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    // GET BY ID
    @Transactional(readOnly = true)
    public WorkOrderResponse getById(UUID id) {
        WorkOrder order = workOrderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Work order not found"));
        return convertToResponse(order);
    }

    // UPDATE
    @Transactional
    public WorkOrderResponse update(UUID id, WorkOrderUpdateRequest request) {
        WorkOrder order = workOrderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Work order not found"));
        if (request.title() != null) order.setTitle(request.title());
        if (request.description() != null) order.setDescription(request.description());
        if (request.dueDate() != null) order.setDueDate(request.dueDate());
        if (request.status() != null) {
            WorkOrderStatus newStatus = WorkOrderStatus.valueOf(request.status());
            order.setStatus(newStatus);

            // Nếu work order được đóng (CLOSED) và nó có liên kết với maintenance schedule
            if (newStatus == WorkOrderStatus.CLOSED && order.getMaintenanceSchedule() != null) {
                MaintenanceSchedule schedule = order.getMaintenanceSchedule();
                schedule.setStatus(MaintenanceStatus.COMPLETED);
                // Không cần gọi save riêng vì @Transactional sẽ tự động flush
            }
        }
        return convertToResponse(workOrderRepository.save(order));
    }

    // DELETE
    @Transactional
    public void delete(UUID id) {
        if (!workOrderRepository.existsById(id)) {
            throw new RuntimeException("Work order not found");
        }
        workOrderRepository.deleteById(id);
    }

    private WorkOrderResponse convertToResponse(WorkOrder order) {
        UUID scheduleId = order.getMaintenanceSchedule() != null ? order.getMaintenanceSchedule().getId() : null;
        return new WorkOrderResponse(
                order.getId(),
                scheduleId,
                order.getEquipment().getId(),
                order.getEquipment().getName(),
                order.getTitle(),
                order.getDescription(),
                order.getDueDate(),
                order.getStatus().name()
        );
    }
}
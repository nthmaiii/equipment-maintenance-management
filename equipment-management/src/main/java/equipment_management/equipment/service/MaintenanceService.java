package equipment_management.equipment.service;

import equipment_management.equipment.constant.WorkOrderStatus;
import equipment_management.equipment.entity.MaintenanceSchedule;
import equipment_management.equipment.entity.WorkOrder;
import equipment_management.equipment.repository.MaintenanceScheduleRepository;
import equipment_management.equipment.repository.WorkOrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class MaintenanceService {

    private final MaintenanceScheduleRepository scheduleRepository;
    private final WorkOrderRepository workOrderRepository; // Tiêm thêm Repository này vào

    @Transactional
    public void processMaintenance(UUID scheduleId) {
        // 1. Tìm lịch trình
        MaintenanceSchedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy lịch"));

        // 2. TẠO WORK ORDER (Phiếu việc)
        WorkOrder workOrder = WorkOrder.builder()
                .equipment(schedule.getEquipment())
                .description("Bảo trì định kỳ: " + schedule.getDescription())
                .status(WorkOrderStatus.OPEN)
                .build();

        workOrderRepository.save(workOrder);
        log.info("--- Đã tạo WorkOrder cho thiết bị: {}", schedule.getEquipment().getName());

        // 3. CẬP NHẬT NGÀY TIẾP THEO (Như đã làm ở task trước)
        LocalDate nextDate = schedule.getNextMaintenanceDate().plusDays(schedule.getIntervalDays());
        schedule.setLastMaintenanceDate(LocalDate.now());
        schedule.setNextMaintenanceDate(nextDate);

        scheduleRepository.save(schedule);
    }
}
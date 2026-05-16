package equipment_management.equipment.scheduler;

import equipment_management.equipment.entity.Equipment;
import equipment_management.equipment.entity.MaintenanceSchedule;
import equipment_management.equipment.entity.WorkOrder;
import equipment_management.equipment.enums.MaintenanceStatus;
import equipment_management.equipment.enums.WorkOrderStatus;
import equipment_management.equipment.repository.EquipmentRepository;
import equipment_management.equipment.repository.MaintenanceScheduleRepository;
import equipment_management.equipment.repository.WorkOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SchedulingService {

    private final EquipmentRepository equipmentRepository;
    private final MaintenanceScheduleRepository scheduleRepository;
    private final WorkOrderRepository workOrderRepository;

    // Cron: 0 giờ 0 phút 0 giây mỗi ngày

    @Scheduled(cron = "0 0 0 * * *")
    @Transactional
    public void generateMonthlyMaintenance() {
        LocalDate nextMonthStart = LocalDate.now().plusMonths(1).withDayOfMonth(1);
        for (Equipment eq : equipmentRepository.findAll()) {
            boolean alreadyExists = scheduleRepository.findAll().stream()
                    .anyMatch(s -> s.getEquipment().getId().equals(eq.getId())
                            && s.getScheduledDate().getYear() == nextMonthStart.getYear()
                            && s.getScheduledDate().getMonth() == nextMonthStart.getMonth());
            if (!alreadyExists) {
                MaintenanceSchedule schedule = MaintenanceSchedule.builder()
                        .equipment(eq)
                        .scheduledDate(nextMonthStart)
                        .status(MaintenanceStatus.PENDING)
                        .description("Hệ thống tự tạo lịch bảo trì tháng " + nextMonthStart.getMonthValue())
                        .build();
                scheduleRepository.save(schedule);
            }
        }
    }

    @Scheduled(cron = "0 0 1 * * *") // chạy lúc 1:00 AM mỗi ngày
    @Transactional
    public void generateWorkOrdersForToday() {
        LocalDate today = LocalDate.now();
        List<MaintenanceSchedule> schedules = scheduleRepository.findByScheduledDateAndStatus(today, MaintenanceStatus.PENDING);

        for (MaintenanceSchedule schedule : schedules) {
            // Kiểm tra xem đã có work order cho schedule này chưa (tránh tạo lại)
            boolean alreadyHasWorkOrder = workOrderRepository.existsByMaintenanceScheduleId(schedule.getId());
            if (!alreadyHasWorkOrder) {
                WorkOrder workOrder = WorkOrder.builder()
                        .maintenanceSchedule(schedule)
                        .equipment(schedule.getEquipment())
                        .title("Bảo trì định kỳ - " + schedule.getEquipment().getName())
                        .description(schedule.getDescription() != null ? schedule.getDescription() : "Thực hiện bảo trì theo lịch")
                        .dueDate(LocalDateTime.now().plusDays(3)) // hạn 3 ngày
                        .status(WorkOrderStatus.OPEN)
                        .build();
                workOrderRepository.save(workOrder);


            }
        }
    }
}
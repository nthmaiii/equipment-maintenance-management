package equipment_management.equipment.scheduler;

import equipment_management.equipment.entity.Equipment;
import equipment_management.equipment.entity.MaintenanceSchedule;
import equipment_management.equipment.enums.MaintenanceStatus;
import equipment_management.equipment.repository.EquipmentRepository;
import equipment_management.equipment.repository.MaintenanceScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class SchedulingService {

    private final EquipmentRepository equipmentRepository;
    private final MaintenanceScheduleRepository scheduleRepository;

    // Cron: 0 giờ 0 phút 0 giây mỗi ngày
    @Scheduled(fixedDelay = 60000)
    //@Scheduled(cron = "0 0 0 * * *")
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
}
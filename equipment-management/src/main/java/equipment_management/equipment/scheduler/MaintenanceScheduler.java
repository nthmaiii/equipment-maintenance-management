package equipment_management.equipment.scheduler;

import equipment_management.equipment.constant.ScheduleStatus;
import equipment_management.equipment.entity.MaintenanceSchedule;
import equipment_management.equipment.repository.MaintenanceScheduleRepository;
import equipment_management.equipment.service.MaintenanceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class MaintenanceScheduler {
    private final MaintenanceScheduleRepository scheduleRepository;
    private final MaintenanceService maintenanceService;

    @Scheduled(cron = "0 0 0 * * *")
    public void scanAndProcessMaintenance() {
        log.info("Bắt đầu quét danh sách bảo trì định kỳ...");

        // 1. Tìm các lịch ACTIVE mà ngày hẹn <= hôm nay
        List<MaintenanceSchedule> dueSchedules = scheduleRepository.findSchedulesToProcess(
                ScheduleStatus.ACTIVE,
                LocalDate.now()
        );

        log.info("Tìm thấy {} thiết bị đến hạn bảo trì.", dueSchedules.size());

        // 2. Với mỗi lịch đến hạn, chúng ta xử lý (tạm thời là log ra, sau này sẽ tạo WorkOrder)
        for (MaintenanceSchedule schedule : dueSchedules) {
            log.warn("Thiết bị [{}] đã đến hạn bảo trì!", schedule.getEquipment().getName());

            // maintenanceService.updateNextMaintenanceDate(schedule.getId());
        }
    }
}

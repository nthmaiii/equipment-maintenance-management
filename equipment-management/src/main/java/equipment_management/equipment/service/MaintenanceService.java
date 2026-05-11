package equipment_management.equipment.service;

import equipment_management.equipment.entity.MaintenanceSchedule;
import equipment_management.equipment.repository.MaintenanceScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MaintenanceService {
    private final MaintenanceScheduleRepository scheduleRepository;

    public void updateNextMaintenanceDate(UUID scheduleId)
    {
        MaintenanceSchedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy lịch bảo trì có ID: " + scheduleId));
        LocalDate currentPlannedDate = schedule.getNextMaintenanceDate();
        LocalDate nextDate = currentPlannedDate.plusDays(schedule.getIntervalDays());
        schedule.setLastMaintenanceDate(LocalDate.now());
        schedule.setNextMaintenanceDate(nextDate);

        scheduleRepository.save(schedule);
    }
}

package equipment_management.equipment.repository;

import equipment_management.equipment.entity.MaintenanceSchedule;
import equipment_management.equipment.enums.MaintenanceStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface MaintenanceScheduleRepository extends JpaRepository<MaintenanceSchedule, UUID> {
    List<MaintenanceSchedule> findByScheduledDateAndStatus(LocalDate date, MaintenanceStatus status);
}
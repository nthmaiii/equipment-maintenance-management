package equipment_management.equipment.repository;

import equipment_management.equipment.entity.MaintenanceSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface MaintenanceScheduleRepository extends JpaRepository<MaintenanceSchedule, UUID> {
}
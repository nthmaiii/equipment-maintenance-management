package equipment_management.equipment.repository;
import equipment_management.equipment.constant.ScheduleStatus;
import equipment_management.equipment.entity.MaintenanceSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface MaintenanceScheduleRepository extends JpaRepository<MaintenanceSchedule, UUID> {

    @Query("SELECT s FROM MaintenanceSchedule s " +
            "WHERE s.status = :status " +
            "AND s.nextMaintenanceDate <= :now")
    List<MaintenanceSchedule> findSchedulesToProcess(
            @Param("status") ScheduleStatus status,
            @Param("now") LocalDate now
    );
}

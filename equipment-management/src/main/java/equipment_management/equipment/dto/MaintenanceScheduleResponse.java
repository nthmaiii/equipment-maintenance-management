package equipment_management.equipment.dto;

import java.time.LocalDate;
import java.util.UUID;

public record MaintenanceScheduleResponse(
        UUID id,
        UUID equipmentId,
        String equipmentName,
        LocalDate scheduledDate,
        String status,
        String description
) {}
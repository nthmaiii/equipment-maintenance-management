package equipment_management.equipment.dto;

import java.time.LocalDate;

public record MaintenanceScheduleUpdateRequest(
        LocalDate scheduledDate,
        String status,
        String description
) {}
package equipment_management.equipment.dto;

import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.util.UUID;

public record MaintenanceScheduleRequest(
        @NotNull UUID equipmentId,
        @NotNull LocalDate scheduledDate,
        String description
) {}
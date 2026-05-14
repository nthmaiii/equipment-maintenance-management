package equipment_management.equipment.dto;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;

public record UnscheduledWorkOrderRequest(
        @NotNull UUID equipmentId,
        @NotNull String title,
        String description,
        @NotNull LocalDateTime dueDate
) {}
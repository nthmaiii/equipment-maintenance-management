package equipment_management.equipment.dto;

import java.time.LocalDateTime;

public record WorkOrderUpdateRequest(
        String title,
        String description,
        LocalDateTime dueDate,
        String status
) {}
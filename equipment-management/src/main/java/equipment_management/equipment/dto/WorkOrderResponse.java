package equipment_management.equipment.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record WorkOrderResponse(
        UUID id,
        UUID maintenanceScheduleId,   // có thể null
        UUID equipmentId,
        String equipmentName,
        String title,
        String description,
        LocalDateTime dueDate,
        String status
) {}
package equipment_management.equipment.dto;

import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class EquipmentResponse {
    private UUID id;
    private String name;
    private String location;
    private String status;
    private String serialNumber;
    private LocalDate purchaseDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

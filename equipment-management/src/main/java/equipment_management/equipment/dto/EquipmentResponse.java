package equipment_management.equipment.dto;

import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class EquipmentResponse {
    private Long id;
    private String name;
    private String location;
    private String status;
    private String serialNumber;
    private LocalDate purchaseDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

package equipment_management.equipment.dto;


import lombok.Data;
import java.time.LocalDate;

@Data
public class EquipmentRequest {
    private String name;
    private String location;
    private String status;
    private String serialNumber;
    private LocalDate purchaseDate;
}

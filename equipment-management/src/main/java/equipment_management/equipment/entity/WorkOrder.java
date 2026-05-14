package equipment_management.equipment.entity;

import equipment_management.equipment.enums.WorkOrderStatus;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "work_orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WorkOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "maintenance_schedule_id", nullable = true)
    private MaintenanceSchedule maintenanceSchedule;

    @ManyToOne
    @JoinColumn(name = "equipment_id", nullable = true)
    private Equipment equipment;

    private String title;
    private String description;
    private LocalDateTime dueDate;

    @Enumerated(EnumType.STRING)
    private WorkOrderStatus status;
}
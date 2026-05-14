package equipment_management.equipment.service;

import equipment_management.equipment.dto.MaintenanceScheduleRequest;
import equipment_management.equipment.dto.MaintenanceScheduleResponse;
import equipment_management.equipment.dto.MaintenanceScheduleUpdateRequest;
import equipment_management.equipment.entity.Equipment;
import equipment_management.equipment.entity.MaintenanceSchedule;
import equipment_management.equipment.enums.MaintenanceStatus;
import equipment_management.equipment.repository.EquipmentRepository;
import equipment_management.equipment.repository.MaintenanceScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MaintenanceScheduleService {

    private final MaintenanceScheduleRepository scheduleRepository;
    private final EquipmentRepository equipmentRepository;

    // CREATE
    @Transactional
    public MaintenanceScheduleResponse create(MaintenanceScheduleRequest request) {
        Equipment equipment = equipmentRepository.findById(request.equipmentId())
                .orElseThrow(() -> new RuntimeException("Equipment not found"));
        MaintenanceSchedule schedule = MaintenanceSchedule.builder()
                .equipment(equipment)
                .scheduledDate(request.scheduledDate())
                .status(MaintenanceStatus.PENDING)
                .description(request.description())
                .build();
        return convertToResponse(scheduleRepository.save(schedule));
    }

    // GET ALL
    @Transactional(readOnly = true)
    public List<MaintenanceScheduleResponse> getAll() {
        return scheduleRepository.findAll().stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    // GET BY ID
    @Transactional(readOnly = true)
    public MaintenanceScheduleResponse getById(UUID id) {
        MaintenanceSchedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Schedule not found"));
        return convertToResponse(schedule);
    }

    // UPDATE
    @Transactional
    public MaintenanceScheduleResponse update(UUID id, MaintenanceScheduleUpdateRequest request) {
        MaintenanceSchedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Schedule not found"));
        if (request.scheduledDate() != null) {
            schedule.setScheduledDate(request.scheduledDate());
        }
        if (request.status() != null) {
            schedule.setStatus(MaintenanceStatus.valueOf(request.status()));
        }
        if (request.description() != null) {
            schedule.setDescription(request.description());
        }
        return convertToResponse(scheduleRepository.save(schedule));
    }

    // DELETE
    @Transactional
    public void delete(UUID id) {
        if (!scheduleRepository.existsById(id)) {
            throw new RuntimeException("Schedule not found");
        }
        scheduleRepository.deleteById(id);
    }

    private MaintenanceScheduleResponse convertToResponse(MaintenanceSchedule schedule) {
        return new MaintenanceScheduleResponse(
                schedule.getId(),
                schedule.getEquipment().getId(),
                schedule.getEquipment().getName(),
                schedule.getScheduledDate(),
                schedule.getStatus().name(),
                schedule.getDescription()
        );
    }
}
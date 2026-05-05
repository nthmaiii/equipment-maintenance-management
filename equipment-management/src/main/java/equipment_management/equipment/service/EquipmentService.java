package equipment_management.equipment.service;

import equipment_management.equipment.dto.EquipmentRequest;
import equipment_management.equipment.dto.EquipmentResponse;
import equipment_management.equipment.entity.Equipment;
import equipment_management.equipment.repository.EquipmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EquipmentService {
    private final EquipmentRepository equipmentRepository;

    public EquipmentResponse getById(Long id)
    {
        Equipment equipment = equipmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Equipment not found with id: " + id));
                return toResponse(equipment);
    }

    public EquipmentResponse create(EquipmentRequest request) {
        Equipment equipment = Equipment.builder()
                .name(request.getName())
                .location(request.getLocation())
                .status(request.getStatus())
                .serialNumber(request.getSerialNumber())
                .purchaseDate(request.getPurchaseDate())
                .build();
        return toResponse(equipmentRepository.save(equipment));
    }

    public EquipmentResponse update(Long id, EquipmentRequest request) {
        Equipment equipment = equipmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Equipment not found with id: " + id));
        equipment.setName(request.getName());
        equipment.setLocation(request.getLocation());
        equipment.setStatus(request.getStatus());
        equipment.setSerialNumber(request.getSerialNumber());
        equipment.setPurchaseDate(request.getPurchaseDate());
        return toResponse(equipmentRepository.save(equipment));
    }

    public void delete(Long id) {
        equipmentRepository.deleteById(id);
    }

    public List<EquipmentResponse> getAll() {
        return equipmentRepository.findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    private EquipmentResponse toResponse(Equipment equipment) {
        EquipmentResponse response = new EquipmentResponse();
        response.setId(equipment.getId());
        response.setName(equipment.getName());
        response.setLocation(equipment.getLocation());
        response.setStatus(equipment.getStatus());
        response.setSerialNumber(equipment.getSerialNumber());
        response.setPurchaseDate(equipment.getPurchaseDate());
        response.setCreatedAt(equipment.getCreatedAt());
        response.setUpdatedAt(equipment.getUpdatedAt());
        return response;
    }
    
}

package com.app.expense.tracker.mapper.holiday;
import org.springframework.stereotype.Component;
import com.app.expense.tracker.dto.holiday.CityStateDTO;
import com.app.expense.tracker.entity.holiday.CityState;

@Component
public class CityStateMapper {
    public CityState toEntity 
    (CityStateDTO dto) {
        return CityState
        .builder()
        .id(dto
        .getId())
        .name(dto
        .getName())
        .country(dto
        .getCountry())
        .build();
    }

    public CityStateDTO toDTO 
    (CityState cs) {
        return new CityStateDTO(
            cs.getId(),
            cs.getName(),
            cs.getCountry()
        );
    }

    public void update 
    (CityState cs, 
    CityStateDTO dto) {
        cs.setId(dto
        .getId());
        cs.setName(dto
        .getName());
        cs.setCountry(dto
        .getCountry());
    }
}
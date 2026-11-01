package com.app.expense.tracker.mapper.holiday;
import org.springframework.stereotype.Component;
import com.app.expense.tracker.dto.holiday.CountryDTO;
import com.app.expense.tracker.entity.holiday.Country;

@Component
public class CountryMapper {
    public Country toEntity 
    (CountryDTO dto) {
        return Country
        .builder()
        .id(dto
        .getId())
        .name(dto
        .getName())
        .code(dto
        .getCode())
        .build();
    }

    public CountryDTO toDTO
    (Country c) {
        return new CountryDTO(
            c.getId(),
            c.getName(),
            c.getCode()
        );
    }

    public void update 
    (Country c, CountryDTO dto) {
        c.setId(dto
        .getId());
        c.setName(dto
        .getName());
        c.setCode(dto
        .getCode());
    }
}
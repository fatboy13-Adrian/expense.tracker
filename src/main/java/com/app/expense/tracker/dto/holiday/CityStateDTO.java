package com.app.expense.tracker.dto.holiday;
import com.app.expense.tracker.entity.holiday.Country;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CityStateDTO {
    private Long id;
    private String name;
    private Country country;
}
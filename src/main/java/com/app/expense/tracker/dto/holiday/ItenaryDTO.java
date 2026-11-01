package com.app.expense.tracker.dto.holiday;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import com.app.expense.tracker.entity.holiday.CityState;
import com.app.expense.tracker.entity.holiday.Country;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ItenaryDTO {
    private LocalDate date;
    private LocalTime time;
    private Country country;
    private CityState cityState;
    private String busOrFlightNo;
    private String busOrTrainStation;
    private String venue;
    private String address;
    private String purpose;
    private BigDecimal amount = 
    BigDecimal.ZERO;
    private BigDecimal total = 
    BigDecimal.ZERO;
}
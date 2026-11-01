package com.app.expense.tracker.mapper.holiday;
import org.springframework.stereotype.Component;
import com.app.expense.tracker.dto.holiday.ItenaryDTO;
import com.app.expense.tracker.entity.holiday.Itenary;

@Component
public class ItenaryMapper {
    public Itenary toEntity 
    (ItenaryDTO dto) {
        return Itenary
        .builder()
        .date(dto
        .getDate())
        .time(dto
        .getTime())
        .country(dto
        .getCountry())
        .cityState(dto
        .getCityState())
        .busOrFlightNo(dto
        .getBusOrFlightNo())
        .busOrTrainStation(dto
        .getBusOrTrainStation())
        .venue(dto
        .getVenue())
        .address(dto
        .getAddress())
        .purpose(dto
        .getPurpose())
        .amount(dto
        .getAmount())
        .total(dto
        .getTotal())
        .build();
    }

    public ItenaryDTO toDTO 
    (Itenary i) {
        return new ItenaryDTO(
            i.getDate(),
            i.getTime(),
            i.getCountry(),
            i.getCityState(),
            i.getBusOrFlightNo(),
            i.getBusOrTrainStation(),
            i.getVenue(),
            i.getAddress(),
            i.getPurpose(),
            i.getAmount(),
            i.getTotal()
        );
    }

    public void update 
    (Itenary i, 
    ItenaryDTO dto) {
        i.setDate(dto
        .getDate());
        i.setTime(dto
        .getTime());
        i.setCountry(dto
        .getCountry());
        i.setCityState(dto
        .getCityState());
        i.setBusOrFlightNo(dto
        .getBusOrFlightNo());
        i.setBusOrTrainStation(dto
        .getBusOrTrainStation());
        i.setVenue(dto
        .getVenue());
        i.setAddress(dto
        .getAddress());
        i.setPurpose(dto
        .getPurpose());
        i.setAmount(dto
        .getAmount());
        i.setTotal(dto
        .getTotal());
    }
}

package com.app.expense.tracker;
import org.springframework.stereotype.Component;
import com.app.expense.tracker.dto.DailyDTO;

@Component
public class DailyMapper {
    /**
     * Converts DTO into an entity
     */
    public Daily toEntity(DailyDTO dto) {
        return Daily
        .builder()
        .date(dto.getDate())
        .income(dto.getIncome())
        .debt(dto.getDebt())
        .emergency(dto.getEmergency())
        .srs(dto.getSrs())
        .ssb(dto.getSsb())
        .cdac(dto.getCdac())
        .electricity(dto.getElectricity())
        .internet(dto.getInternet())
        .insurances(dto.getInsurances())
        .mobile(dto.getMobile())
        .mortgage(dto.getMortgage())
        .tax(dto.getTax())
        .food(dto.getFood())
        .groceries(dto.getGroceries())
        .haircut(dto.getHaircut())
        .household(dto.getHousehold())
        .medical(dto.getMedical())
        .parents(dto.getParents())
        .transport(dto.getTransport())
        .eating_out(dto.getEating_out())
        .holiday(dto.getHoliday())
        .recreational(dto.getRecreational())
        .shopping(dto.getShopping())
        .sports(dto.getSports())
        .tech(dto.getTech())
        .tithes(dto.getTithes())
        .wake(dto.getWake())
        .wedding(dto.getWedding())
        .build();
    }

    /**
     * Converts entity into DTO
     */
    public DailyDTO toDTO (Daily d) {
        return new DailyDTO(
            d.getDate(),
            d.getIncome(),
            d.getDebt(),
            d.getEmergency(),
            d.getSrs(),
            d.getSsb(),
            d.getCdac(),
            d.getElectricity(),
            d.getInternet(),
            d.getInsurances(),
            d.getMobile(),
            d.getMortgage(),
            d.getTax(),
            d.getFood(),
            d.getGroceries(),
            d.getHaircut(),
            d.getHousehold(),
            d.getMedical(),
            d.getParents(),
            d.getTransport(),
            d.getEating_out(),
            d.getHoliday(),
            d.getRecreational(),
            d.getShopping(),
            d.getSports(),
            d.getTech(),
            d.getTithes(),
            d.getWake(),
            d.getWedding());
    }

    public void update (Daily d, DailyDTO dto) {
        d.setIncome(dto.getIncome());
        d.setDebt(dto.getDebt());
        d.setEmergency(dto.getEmergency());
        d.setSrs(dto.getSrs());
        d.setSsb(dto.getSsb());
        d.setCdac(dto.getCdac());
        d.setElectricity(dto.getElectricity());
        d.setInternet(dto.getInternet());
        d.setInsurances(dto.getInsurances());
        d.setMobile(dto.getMobile());
        d.setMortgage(dto.getMortgage());
        d.setTax(dto.getTax());
        d.setFood(dto.getFood());
        d.setGroceries(dto.getGroceries());
        d.setHaircut(dto.getHaircut());
        d.setHousehold(dto.getHousehold());
        d.setMedical(dto.getMedical());
        d.setParents(dto.getParents());
        d.setTransport(dto.getTransport());
        d.setEating_out(dto.getEating_out());
        d.setHoliday(dto.getHoliday());
        d.setRecreational(dto.getRecreational());
        d.setShopping(dto.getShopping());
        d.setSports(dto.getSports());
        d.setTech(dto.getTech());
        d.setTithes(dto.getTithes());
        d.setWake(dto.getWake());
        d.setWedding(dto.getWedding());
    }
}
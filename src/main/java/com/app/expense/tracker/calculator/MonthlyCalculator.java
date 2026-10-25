package com.app.expense.tracker.calculator;
import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.List;
import org.springframework.stereotype.Component;
import com.app.expense.tracker.Daily;
import com.app.expense.tracker.DailyRepository;
import com.app.expense.tracker.dto.MonthlyDTO;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MonthlyCalculator {
    private final DailyRepository repository;
    
    public void calculateMonthly 
    (MonthlyDTO dto) {
        YearMonth month = 
        dto.getMonth();

        BigDecimal income = 
        BigDecimal.ZERO;
        BigDecimal debt = 
        BigDecimal.ZERO;
        BigDecimal emergency = 
        BigDecimal.ZERO;
        BigDecimal investments = 
        BigDecimal.ZERO;
        BigDecimal bills = 
        BigDecimal.ZERO;
        BigDecimal food = 
        BigDecimal.ZERO;
        BigDecimal groceries = 
        BigDecimal.ZERO;
        BigDecimal haircut = 
        BigDecimal.ZERO;
        BigDecimal household = 
        BigDecimal.ZERO;
        BigDecimal medical = 
        BigDecimal.ZERO;
        BigDecimal parents = 
        BigDecimal.ZERO;
        BigDecimal transport = 
        BigDecimal.ZERO;
        BigDecimal wants = 
        BigDecimal.ZERO;
        BigDecimal others = 
        BigDecimal.ZERO;

        /*Fetch all daily transactions 
        for specified month target*/
        List<Daily> dList = 
        repository
        .findByDateBetween(month
        .atDay(1), 
        month
        .atEndOfMonth()
        );

        /*Loop exclusively through standard 
        daily operational cash outflows*/
        for (Daily d : dList) {
            income = income
            .add(d.getIncome());
            debt = debt
            .add(d.getDebt());
            emergency = emergency
            .add(d.getEmergency());
            investments = investments
            .add(d.getSrs())
            .add(d.getSsb());
            bills = bills
            .add(d.getCdac())
            .add(d.getElectricity())
            .add(d.getInternet())
            .add(d.getInsurances())
            .add(d.getMobile())
            .add(d.getMortgage())
            .add(d.getTax());
            food = food
            .add(d.getFood());
            groceries = groceries
            .add(d.getGroceries());
            haircut = haircut
            .add(d.getHaircut());
            household = household
            .add(d.getHousehold());
            medical = medical
            .add(d.getMedical());
            parents = parents
            .add(d.getParents());
            transport = transport
            .add(d.getTransport());
            wants = wants
            .add(d.getEating_out())
            .add(d.getHoliday())
            .add(d.getRecreational())
            .add(d.getShopping())
            .add(d.getSports())
            .add(d.getTech());
            others = others
            .add(d.getWake())
            .add(d.getWedding())
            .add(d.getTithes());
        }

        /*Populate your metrics cleanly &
        run updated calculation*/
        dto.setIncome(income);
        dto.setDebt(debt);
        dto.setEmergency(emergency);
        dto.setInvestments(investments);
        dto.setBills(bills);
        dto.setFood(food);
        dto.setGroceries(groceries);
        dto.setHaircut(haircut);
        dto.setHousehold(household);
        dto.setMedical(medical);
        dto.setParents(parents);
        dto.setTransport(transport);
        dto.setWants(wants);
        dto.setOthers(others);
        dto.setSavings(calculateSavings(dto));
    }
    
    public BigDecimal calculateSavings 
    (MonthlyDTO dto) {
        //Calculate total expenses
        BigDecimal total = 
        dto
        .getDebt()
        .add(dto.getEmergency())
        .add(dto.getInvestments())
        .add(dto.getBills())
        .add(dto.getFood())
        .add(dto.getGroceries())
        .add(dto.getHaircut())
        .add(dto.getHousehold())
        .add(dto.getMedical())
        .add(dto.getParents())
        .add(dto.getTransport())
        .add(dto.getWants())
        .add(dto.getOthers());
        return dto.getIncome()
        .subtract(total);
    }
}
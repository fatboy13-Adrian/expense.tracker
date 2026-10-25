package com.app.expense.tracker.calculator;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.stereotype.Component;
import com.app.expense.tracker.dto.MonthlyDTO;
import com.app.expense.tracker.dto.YearlyDTO;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class YearlyCalculator {
    public void calculateYearly 
    (YearlyDTO ydto, 
    List <MonthlyDTO> mdto) {
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
        BigDecimal savings = 
        BigDecimal.ZERO;

        //Sum up daily expenses for current year
        for (MonthlyDTO dto : mdto) {
            income = income
            .add(dto.getIncome());
            debt = debt
            .add(dto.getDebt());
            emergency = emergency
            .add(dto.getEmergency());
            investments = investments
            .add(dto.getInvestments());
            bills = bills
            .add(dto.getBills());
            food = food
            .add(dto.getFood());
            groceries = groceries
            .add(dto.getGroceries());
            haircut = haircut
            .add(dto.getHaircut());
            household = household
            .add(dto.getHousehold());
            medical = medical
            .add(dto.getMedical());
            parents = parents
            .add(dto.getParents());
            transport = transport
            .add(dto.getTransport());
            wants = wants
            .add(dto.getWants());
            others = others
            .add(dto.getOthers());
            savings = savings
            .add(dto.getSavings());
        }

        //Store calculated values into yearly DTO
        ydto.setIncome(income);
        ydto.setDebt(debt);
        ydto.setEmergency(emergency);
        ydto.setInvestments(investments);
        ydto.setBills(bills);
        ydto.setFood(food);
        ydto.setGroceries(groceries);
        ydto.setHaircut(haircut);
        ydto.setHousehold(household);
        ydto.setMedical(medical);
        ydto.setParents(parents);
        ydto.setTransport(transport);
        ydto.setWants(wants);
        ydto.setOthers(others);
        ydto.setSavings(savings);
    }
}
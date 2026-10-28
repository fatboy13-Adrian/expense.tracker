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

        //Aggregate monthly totals for current year
        for (MonthlyDTO dto : mdto) {
            income = 
            income
            .add(dto
            .getIncome() 
            != null 
            ? dto
            .getIncome() 
            : BigDecimal
            .ZERO);

            debt = 
            debt
            .add(dto
            .getDebt() != null 
            ? dto
            .getDebt() 
            : BigDecimal
            .ZERO);

            emergency = 
            emergency
            .add(dto
            .getEmergency() 
            != null 
            ? dto
            .getEmergency() 
            : BigDecimal
            .ZERO);

            investments = 
            investments
            .add(dto
            .getInvestments() 
            != null 
            ? dto
            .getInvestments() 
            : BigDecimal
            .ZERO);

            bills = 
            bills
            .add(dto
            .getBills() 
            != null 
            ? dto
            .getBills() 
            : BigDecimal
            .ZERO);

            food = 
            food
            .add(dto
            .getFood() 
            != null 
            ? dto
            .getFood() 
            : BigDecimal
            .ZERO);

            groceries = 
            groceries
            .add(dto
            .getGroceries() 
            != null 
            ? dto
            .getGroceries() 
            : BigDecimal
            .ZERO);

            haircut = 
            haircut
            .add(dto
            .getHaircut() 
            != null 
            ? dto
            .getHaircut() 
            : BigDecimal
            .ZERO);

            household = 
            household
            .add(dto
            .getHousehold() 
            != null 
            ? dto
            .getHousehold() 
            : BigDecimal
            .ZERO);

            medical = 
            medical
            .add(dto
            .getMedical() 
            != null 
            ? dto
            .getMedical() 
            : BigDecimal
            .ZERO);

            parents = 
            parents
            .add(dto
            .getParents() 
            != null 
            ? dto
            .getParents() 
            : BigDecimal
            .ZERO);

            transport = 
            transport
            .add(dto
            .getTransport() 
            != null 
            ? dto
            .getTransport() 
            : BigDecimal
            .ZERO);

            wants = 
            wants
            .add(dto
            .getWants() 
            != null 
            ? dto
            .getWants() 
            : BigDecimal
            .ZERO);

            others = 
            others
            .add(dto
            .getOthers() 
            != null 
            ? dto
            .getOthers() 
            : BigDecimal
            .ZERO);

            savings = 
            savings
            .add(dto
            .getSavings() 
            != null 
            ? dto
            .getSavings() 
            : BigDecimal
            .ZERO);
        }

        //Populate yearly DTO
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
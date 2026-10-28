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
                dto
                .getMonth();

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

                //Fetch daily records fo specified month
                List<Daily> dailyList = 
                repository
                .findByDateBetween(
                month
                .atDay(1),
                month
                .atEndOfMonth());

                //Aggregate daily amounts
                for (Daily daily : dailyList) {
                        income = 
                        income
                        .add(daily
                        .getIncome() 
                        != null 
                        ? daily
                        .getIncome() 
                        : BigDecimal
                        .ZERO);

                        debt = 
                        debt
                        .add(daily
                        .getDebt() 
                        != null 
                        ? daily
                        .getDebt() 
                        : BigDecimal
                        .ZERO);

                        emergency = 
                        emergency
                        .add(daily
                        .getEmergency() 
                        != null ? 
                        daily
                        .getEmergency() 
                        : BigDecimal
                        .ZERO);

                        investments = 
                        investments
                        .add(daily
                        .getSrs() 
                        != null 
                        ? daily
                        .getSrs() 
                        : BigDecimal
                        .ZERO)
                        .add(daily
                        .getSsb() 
                        != null 
                        ? daily
                        .getSsb() 
                        : BigDecimal
                        .ZERO);

                        bills = 
                        bills
                        .add(daily
                        .getCdac() 
                        != null 
                        ? daily
                        .getCdac() 
                        : 
                        BigDecimal.ZERO)
                        .add(daily
                        .getElectricity() 
                        != null 
                        ? daily
                        .getElectricity() 
                        : BigDecimal
                        .ZERO)
                        .add(daily
                        .getInternet() 
                        != null 
                        ? daily
                        .getInternet() 
                        : BigDecimal
                        .ZERO)
                        .add(daily
                        .getInsurances() 
                        != null 
                        ? daily
                        .getInsurances() 
                        : BigDecimal
                        .ZERO)
                        .add(daily
                        .getMobile() 
                        != null 
                        ? daily
                        .getMobile() 
                        : BigDecimal
                        .ZERO)
                        .add(daily
                        .getMortgage() 
                        != null 
                        ? daily
                        .getMortgage() 
                        : BigDecimal
                        .ZERO)
                        .add(daily
                        .getTax() 
                        != null 
                        ? daily
                        .getTax() 
                        : BigDecimal
                        .ZERO);

                        food = 
                        food
                        .add(daily
                        .getFood() 
                        != null 
                        ? daily
                        .getFood() 
                        : BigDecimal
                        .ZERO);

                        groceries = 
                        groceries
                        .add(daily
                        .getGroceries() 
                        != null 
                        ? daily
                        .getGroceries() 
                        : BigDecimal
                        .ZERO);

                        haircut = 
                        haircut
                        .add(daily
                        .getHaircut() 
                        != null 
                        ? daily
                        .getHaircut() 
                        : BigDecimal
                        .ZERO);

                        household = 
                        household
                        .add(daily
                        .getHousehold() 
                        != null 
                        ? daily
                        .getHousehold() 
                        : BigDecimal
                        .ZERO);

                        medical = 
                        medical
                        .add(daily
                        .getMedical() 
                        != null 
                        ? daily
                        .getMedical() 
                        : BigDecimal
                        .ZERO);

                        parents = 
                        parents
                        .add(daily
                        .getParents() 
                        != null 
                        ? daily
                        .getParents() 
                        : BigDecimal
                        .ZERO);

                        transport = 
                        transport
                        .add(daily
                        .getTransport() 
                        != null 
                        ? daily
                        .getTransport() 
                        : BigDecimal
                        .ZERO);

                        wants = 
                        wants
                        .add(daily
                        .getEating_out() 
                        != null 
                        ? daily
                        .getEating_out() 
                        : BigDecimal
                        .ZERO)
                        .add(daily
                        .getHoliday() 
                        != null ? 
                        daily
                        .getHoliday() 
                        : BigDecimal
                        .ZERO)
                        .add(daily
                        .getRecreational() 
                        != null 
                        ? daily
                        .getRecreational() 
                        : BigDecimal
                        .ZERO)
                        .add(daily
                        .getShopping() 
                        != null 
                        ? daily
                        .getShopping() 
                        : BigDecimal
                        .ZERO)
                        .add(daily
                        .getSports() 
                        != null 
                        ? daily
                        .getSports() 
                        : BigDecimal
                        .ZERO)
                        .add(daily
                        .getTech() 
                        != null 
                        ? daily
                        .getTech() 
                        : BigDecimal
                        .ZERO);

                        others = 
                        others
                        .add(daily
                        .getWake() 
                        != null 
                        ? daily
                        .getWake() 
                        : BigDecimal
                        .ZERO)
                        .add(daily
                        .getWedding() 
                        != null 
                        ? daily
                        .getWedding() 
                        : BigDecimal
                        .ZERO)
                        .add(daily
                        .getTithes() 
                        != null 
                        ? daily
                        .getTithes() 
                        : BigDecimal
                        .ZERO);
                }

                //Populate monthly DTO
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
                BigDecimal total = 
                BigDecimal.ZERO;
                total = 
                total
                .add(dto
                .getDebt() 
                != null 
                ? dto
                .getDebt() 
                : BigDecimal
                .ZERO);

                total = 
                total
                .add(dto
                .getEmergency() 
                != null 
                ? dto
                .getEmergency() 
                : BigDecimal
                .ZERO);

                total = 
                total
                .add(dto
                .getInvestments() 
                != null 
                ? dto
                .getInvestments() 
                : BigDecimal
                .ZERO);

                total = 
                total
                .add(dto
                .getBills() 
                != null 
                ? dto
                .getBills() 
                : BigDecimal
                .ZERO);

                total = 
                total
                .add(dto
                .getFood() 
                != null 
                ? dto
                .getFood() 
                : BigDecimal
                .ZERO);

                total = 
                total
                .add(dto
                .getGroceries() 
                != null 
                ? dto
                .getGroceries() 
                : BigDecimal
                .ZERO);

                total = 
                total
                .add(dto
                .getHaircut() 
                != null 
                ? dto
                .getHaircut() 
                : BigDecimal
                .ZERO);

                total = 
                total
                .add(dto
                .getHousehold() 
                != null 
                ? dto
                .getHousehold() 
                : BigDecimal
                .ZERO);

                total = 
                total
                .add(dto
                .getMedical() 
                != null 
                ? dto
                .getMedical() 
                : BigDecimal
                .ZERO);

                total = 
                total
                .add(dto
                .getParents() 
                != null 
                ? dto
                .getParents() 
                : BigDecimal
                .ZERO);

                total = 
                total
                .add(dto
                .getTransport() 
                != null 
                ? dto
                .getTransport() 
                : BigDecimal
                .ZERO);

                total = 
                total
                .add(dto
                .getWants() 
                != null 
                ? dto
                .getWants() 
                : BigDecimal
                .ZERO);

                total = 
                total
                .add(dto
                .getOthers() 
                != null 
                ? dto
                .getOthers() 
                : BigDecimal
                .ZERO);

                BigDecimal income = 
                dto
                .getIncome() 
                != null
                ? dto
                .getIncome()
                : BigDecimal
                .ZERO;

                return income
                .subtract(total);
        }
}
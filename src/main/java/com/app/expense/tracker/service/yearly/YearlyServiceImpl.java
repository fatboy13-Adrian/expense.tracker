package com.app.expense.tracker.service.yearly;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import com.app.expense.tracker.Daily;
import com.app.expense.tracker.DailyRepository;
import com.app.expense.tracker.calculator.YearlyCalculator;
import com.app.expense.tracker.dto.MonthlyDTO;
import com.app.expense.tracker.dto.YearlyDTO;
import com.app.expense.tracker.service.monthly.MonthlyService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class YearlyServiceImpl implements YearlyService {
    private final DailyRepository repository;
    private final MonthlyService svc;
    private final YearlyCalculator calculator;

    @Override
    public YearlyDTO retrieve 
    (int year) {
        YearlyDTO dto = 
        new YearlyDTO();
        dto
        .setYear(year);

        //Define start & end of year
        LocalDate startOfYear = 
        LocalDate
        .of(year, 1, 
        1);
        LocalDate endOfYear = 
        LocalDate
        .of(year, 12, 
        31);

        //Query DB ONCE for whole year
        List <Daily> yearlyData = 
        repository
        .findByDateBetween(startOfYear, 
        endOfYear);

        //Build DTOs only for months that have data
        List <MonthlyDTO> mdtoList = 
        yearlyData
        .stream()
        .map(de -> 
        YearMonth
        .from(de.getDate()))
        .distinct()
        .map(svc::retrieve)
        .toList();

        //Run clean calculator
        calculator
        .calculateYearly(dto, 
        mdtoList);
        return dto;
    }

    @Override
    public Page <YearlyDTO> retrieveAll 
    (int page, int size) {
        //Retrieve all daily records
        List<Daily> dList = 
        repository
        .findAll();

        //Extract unique years & sort them in ascending order
        List<Integer> years = 
        dList
        .stream()
        .map(daily -> 
        daily
        .getDate()
        .getYear())
        .distinct()
        .sorted()
        .toList();

        //Create pagination info
        Pageable pageable = 
        PageRequest
        .of(page, 
        size);

        //Paginate list of unique years
        int start = 
        (int) pageable
        .getOffset();

        if (start >= years.size()) {
            return new PageImpl<>(
            List
            .of(),
            pageable,
            years
            .size());
        }

        int end = 
        Math
        .min(start + size, 
        years
        .size());

        //Retrieve yearly summaries for requested page
        List <YearlyDTO> yearlyDTOs = 
        years
        .subList(start, 
        end)
        .stream()
        .map(this::retrieve)
        .toList();

        //Return paginated yearly summaries
        return new PageImpl<>(
        yearlyDTOs,
        pageable,
        years
        .size());
    }
}
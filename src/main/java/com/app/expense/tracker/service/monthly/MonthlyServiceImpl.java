package com.app.expense.tracker.service.monthly;
import java.time.YearMonth;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.app.expense.tracker.Daily;
import com.app.expense.tracker.DailyRepository;
import com.app.expense.tracker.calculator.MonthlyCalculator;
import com.app.expense.tracker.dto.MonthlyDTO;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MonthlyServiceImpl implements MonthlyService {
    private final DailyRepository repository;
    private final MonthlyCalculator calculator;

    @Override
    public MonthlyDTO retrieve 
    (YearMonth month) {
        MonthlyDTO dto = 
        new MonthlyDTO();
        dto
        .setMonth(month);
        calculator
        .calculateMonthly(dto);
        return dto;
    }

    @Override
    public Page <MonthlyDTO> retrieveAll 
    (int page, int size) {
        /*
         * Maintain 0 based conversion
         * for internal processing bounds
         */
        Pageable pageable = 
        PageRequest
        .of(page - 1, 
        size);

        /*
         * Fetch records & extract
         * unique months in memory
         */
        List <Daily> deList = 
        repository
        .findAll();
        List <YearMonth> uniqueMonths = 
        deList
        .stream()
        .map(de -> YearMonth
        .from(de
        .getDate()))
        .distinct()
        .sorted()
        .toList();

        /*
         * Apply safe pagination
         * window slicing filters
         */
        int start = 
        Math
        .min((int) 
        pageable
        .getOffset(), 
        uniqueMonths
        .size());
        int end = Math
        .min(start + size, 
        uniqueMonths.size());
        
        /*
         * Map only sliced segment
         * into calculation models
         */
        List<MonthlyDTO> content = 
        uniqueMonths
        .subList(start, end)
        .stream()
        .map(this::retrieve)
        .toList();

        /*
         * Return standard page impl container
         * mapping framework targets
         */
        return new PageImpl <>(content, 
        pageable, 
        uniqueMonths
        .size());
    }
}
package com.app.expense.tracker.service.monthly;
import java.time.YearMonth;
import org.springframework.data.domain.Page;
import com.app.expense.tracker.dto.MonthlyDTO;

public interface MonthlyService {
    MonthlyDTO retrieve 
    (YearMonth month);
    Page <MonthlyDTO> retrieveAll 
    (int page, int size);
}
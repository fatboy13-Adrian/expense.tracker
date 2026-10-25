package com.app.expense.tracker.service.yearly;
import org.springframework.data.domain.Page;
import com.app.expense.tracker.dto.YearlyDTO;

public interface YearlyService {
    YearlyDTO retrieve (int year);
    Page <YearlyDTO> retrieveAll (int page, int size);
}
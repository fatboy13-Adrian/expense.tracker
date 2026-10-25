package com.app.expense.tracker.service.daily;
import java.time.LocalDate;
import org.springframework.data.domain.Page;
import com.app.expense.tracker.dto.DailyDTO;

public interface DailyService {
    DailyDTO save 
    (DailyDTO dto);
    DailyDTO retrieve 
    (LocalDate date);
    Page <DailyDTO> retrieveAll 
    (int page, int size);
    void delete 
    (LocalDate date);
}
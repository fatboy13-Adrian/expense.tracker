package com.app.expense.tracker.service.yearly;
import java.util.List;
import com.app.expense.tracker.dto.YearlyDTO;

public interface YearlyService {
    YearlyDTO retrieve (int year);
    List <YearlyDTO> retrieveAll ();
}
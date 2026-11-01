package com.app.expense.tracker.service.holiday;
import java.time.LocalDate;
import java.util.List;
import com.app.expense.tracker.dto.holiday.ItenaryDTO;

public interface ItenaryService {
    ItenaryDTO save 
    (ItenaryDTO dto);
    ItenaryDTO retrieve 
    (LocalDate date);
    List <ItenaryDTO> 
    retrieveAll ();
    void delete 
    (LocalDate date);
}
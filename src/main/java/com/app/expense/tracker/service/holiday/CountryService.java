package com.app.expense.tracker.service.holiday;
import java.util.List;
import com.app.expense.tracker.dto.holiday.CountryDTO;

public interface CountryService {
    CountryDTO save
    (CountryDTO dto);
    CountryDTO retrieve
    (Long id);
    List <CountryDTO> 
    retrieveAll ();
    void delete 
    (Long id);
}
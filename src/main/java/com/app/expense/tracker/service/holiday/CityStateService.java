package com.app.expense.tracker.service.holiday;
import java.util.List;
import com.app.expense.tracker.dto.holiday.CityStateDTO;

public interface CityStateService {
    CityStateDTO save
    (CityStateDTO dto);
    CityStateDTO retrieve
    (Long id);
    List <CityStateDTO> retrieveAll 
    ();
    void delete 
    (Long id);
}
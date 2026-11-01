package com.app.expense.tracker.repository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.app.expense.tracker.entity.holiday.Itenary;

@Repository
public interface ItenaryRepository extends JpaRepository 
<Itenary, LocalDate> {
    List <Itenary> findByCountryId 
    (Long countryId);
    List <Itenary> findByCityStateId 
    (Long cityStateId);
    Optional <Itenary> findByDate 
    (LocalDate date);
}
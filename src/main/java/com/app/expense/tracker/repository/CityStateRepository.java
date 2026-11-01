package com.app.expense.tracker.repository;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.app.expense.tracker.entity.holiday.CityState;

@Repository
public interface CityStateRepository extends JpaRepository 
<CityState, Long> {
    Optional <CityState> findByName 
    (String name);
    List <CityState> findByCountryId 
    (Long countryId);
}
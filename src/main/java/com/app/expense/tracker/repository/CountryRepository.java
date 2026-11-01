package com.app.expense.tracker.repository;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.expense.tracker.entity.holiday.Country;

@Repository
public interface CountryRepository extends JpaRepository 
<Country, Long> {
    Optional <Country> findByName 
    (String name);
}
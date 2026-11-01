package com.app.expense.tracker.repository;
import java.time.LocalDate; 
import java.util.List;
import org.springframework.data.domain.Page; 
import org.springframework.data.domain.Pageable; 
import org.springframework.data.jpa.repository.JpaRepository; 
import org.springframework.stereotype.Repository;

import com.app.expense.tracker.entity.Daily;

@Repository 
public interface DailyRepository extends JpaRepository <Daily, LocalDate> {
    List <Daily> findByDateBetween
    (LocalDate startDate, 
    LocalDate endDate);
    Page <Daily> findByDateBetween
    (LocalDate startDate, 
    LocalDate endDate, 
    Pageable pageable);
}
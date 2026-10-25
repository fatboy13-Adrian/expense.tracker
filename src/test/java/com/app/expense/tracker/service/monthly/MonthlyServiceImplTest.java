package com.app.expense.tracker.service.monthly;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import com.app.expense.tracker.Daily;
import com.app.expense.tracker.DailyRepository;
import com.app.expense.tracker.calculator.MonthlyCalculator;
import com.app.expense.tracker.dto.DailyDTO;
import com.app.expense.tracker.dto.MonthlyDTO;

@ExtendWith(MockitoExtension.class)
public class MonthlyServiceImplTest {
    @InjectMocks
    private MonthlyServiceImpl svc;
    
    @Mock
    private DailyRepository repository;

    @Mock
    private MonthlyCalculator calculator;

    private Daily d;

    @BeforeEach
    public void setUp () {
        //Declared missing date context variable
        LocalDate date = 
        LocalDate
        .of(2024, 
        12, 
        31);
        
        DailyDTO dto = 
        new DailyDTO(); 
        dto.setDate(date);
        dto.setFood(BigDecimal
        .valueOf(15.1));
        dto.setGroceries(BigDecimal
        .valueOf(9.99));
        dto.setMedical(BigDecimal
        .valueOf(22.34));

        //Entity representing DB record
        d = new Daily();
        d.setDate(date);
        d.setFood(BigDecimal
        .valueOf(15.1));
        d.setGroceries(BigDecimal
        .valueOf(9.99));
        d.setMedical(BigDecimal
        .valueOf(22.34));
    }

    @Test
    void testRetrieve () {
        //Define month to retrieve
        YearMonth month = 
        YearMonth
        .of(2026, 
        1);

        //Call service method
        MonthlyDTO result = 
        svc
        .retrieve(month);

        //Assertions
        assertNotNull(result);
        assertEquals(month, 
        result
        .getMonth());

        //Verify monthly calculator execution
        verify(calculator, 
        times(1))
        .calculateMonthly(result);
    }

    @Test 
    void testRetrieveByMonthNotFound () {
        //Define a target month
        YearMonth month = 
        YearMonth
        .of(2026, 
        1);

        //Call service method
        MonthlyDTO result = 
        svc
        .retrieve(month);
        
        /**Assert that a valid container
        DTO is still returned with month set**/
        assertNotNull(result);
        assertEquals(month, 
        result
        .getMonth());

        /**Verify that monthly calculator was 
        still invoked once to process empty DTO safely**/
        verify(calculator, 
        times(1))
        .calculateMonthly(result);
    }

    @Test
    void testRetrieveAllMonths () {
        //Define page
        int page = 1;
        int size = 2;

        /**Create unsorted records with duplicate 
        months to test streaming logic**/
        Daily marchExpense = 
        new Daily();
        marchExpense
        .setDate(LocalDate
        .of(2026, 
        3, 
        15));
        Daily januaryExpense = 
        new Daily();
        januaryExpense
        .setDate(LocalDate
        .of(2026, 
        1, 
        1));
        Daily februaryExpense = 
        new Daily();
        februaryExpense
        .setDate(LocalDate
        .of(2026, 
        2, 
        10));
        Daily duplicateJanExpense = 
        new Daily();
        duplicateJanExpense
        .setDate(LocalDate
        .of(2026, 
        1, 
        20));

        when(repository
        .findAll())
        .thenReturn(List
        .of(marchExpense, 
        januaryExpense, 
        februaryExpense, 
        duplicateJanExpense));

        //Call service with a page size of 2
        Page <MonthlyDTO> result = 
        svc
        .retrieveAll(page, 
        size);

        //Assertions
        assertNotNull(result);
        assertEquals(3, 
        result
        .getTotalElements()); 
        assertEquals(2, 
        result
        .getContent()
        .size()); 

        //Verifies distinct sorting logic order
        assertEquals(YearMonth
        .of(2026, 
        1), 
        result
        .getContent()
        
        .get(0)
        .getMonth());
        assertEquals(YearMonth
        .of(2026, 
        2), 
        result
        .getContent()
        .get(1)
        .getMonth());

        verify(repository, 
        times(1))
        .findAll();
        
        /**Calculator triggers only twice
        for sliced content context window**/
        verify(calculator, 
        times(2))
        .calculateMonthly(any(MonthlyDTO.class));
    }

    @Test
    void testRetrieveAllMonthsNotFound () {
        int page = 1;
        int size = 10;

        when(repository
        .findAll())
        .thenReturn(List
        .of());

        Page <MonthlyDTO> result = 
        svc
        .retrieveAll(page, 
        size);

        assertNotNull(result);
        assertEquals(0, 
        result
        .getContent()
        .size());
        assertTrue(result
        .isEmpty());
        assertEquals(0, 
        result
        .getTotalElements());

        verify(repository, 
        times(1))
        .findAll();
        verify(calculator, 
        times(0))
        .calculateMonthly(any(MonthlyDTO.class));
    }
}
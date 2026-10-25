package com.app.expense.tracker.service.yearly;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import com.app.expense.tracker.Daily;
import com.app.expense.tracker.DailyRepository;
import com.app.expense.tracker.calculator.YearlyCalculator;
import com.app.expense.tracker.dto.MonthlyDTO;
import com.app.expense.tracker.dto.YearlyDTO;
import com.app.expense.tracker.service.monthly.MonthlyService;

@ExtendWith(MockitoExtension.class)
public class YearlyServiceImplTest {
    @Mock
    private DailyRepository repository;

    @Mock
    private MonthlyService monthlySvc;

    @Mock
    private YearlyCalculator calculator;

    @InjectMocks
    private YearlyServiceImpl yearlySvc;

    private Daily jan;
    private Daily mar;
    private MonthlyDTO janDTO;
    private MonthlyDTO marDTO;

    @BeforeEach
    void setUp() {
        //Create daily expense records for 2 different months
        jan = 
        new Daily();
        jan
        .setDate(LocalDate
        .of(2026, 
        1, 
        15));

        mar = 
        new Daily();
        mar
        .setDate(LocalDate
        .of(2026,
        3, 
        20));

        //Create monthly DTOs for calculator
        janDTO = 
        new MonthlyDTO();
        marDTO = 
        new MonthlyDTO();
    }

    @Test
    void testRetrieve () {
        //Arrange
        int year = 
        2026;

        when(repository
        .findByDateBetween(
        LocalDate.of(2026, 
        1, 
        1),
        LocalDate
        .of(2026, 
        12, 
        31)))
        .thenReturn(List
        .of(jan, 
        mar));
        when(monthlySvc
        .retrieve(YearMonth
        .of(2026, 
        1)))
        .thenReturn(janDTO);
        when(monthlySvc
        .retrieve(YearMonth
        .of(2026, 
        3)))
        .thenReturn(marDTO);

        //Act
        YearlyDTO result = 
        yearlySvc
        .retrieve(year);

        // Assert
        assertNotNull(result);
        assertEquals(year, 
        result
        .getYear());

        verify(repository)
        .findByDateBetween(
        LocalDate
        .of(2026, 
        1, 
        1),
        LocalDate
        .of(2026, 
        12, 
        31));

        verify(monthlySvc)
        .retrieve(YearMonth
        .of(2026, 
        1));
        verify(monthlySvc)
        .retrieve(YearMonth
        .of(2026, 
        3));
        verify(calculator)
        .calculateYearly(
        result,
        List
        .of(janDTO, 
        marDTO));
    }

    @Test
    void testRetrieve_noRecordsFound () {
        //Arrange
        int year = 
        2026;

        when(repository
        .findByDateBetween(
        LocalDate.of(2026, 
        1, 
        1),
        LocalDate
        .of(2026, 
        12, 
        31)))
        .thenReturn(List
        .of());

        //Act
        YearlyDTO result = 
        yearlySvc
        .retrieve(year);

        //Assert
        assertNotNull(result);
        assertEquals(year, 
        result
        .getYear());

        verify(repository)
        .findByDateBetween(
        LocalDate
        .of(2026, 
        1, 
        1),
        LocalDate
        .of(2026, 
        12, 
        31));
        verify(calculator)
        .calculateYearly(result, 
        List
        .of());
    }

    @Test
    void testRetrieveAll() {
        // Arrange
        Daily daily24 = 
        new Daily();
        daily24
        .setDate(LocalDate
        .of(2024, 
        6, 
        10));

        Daily daily25 = 
        new Daily();
        daily25
        .setDate(LocalDate
        .of(2025, 
        8, 
        15));

        Daily anotherDaily24 = 
        new Daily();
        anotherDaily24
        .setDate(LocalDate
        .of(2024, 
        12, 
        20));

        when(repository
        .findAll())
        .thenReturn(List
        .of(
        daily24,
        daily25,
        anotherDaily24));

        //Mock repository calls made by retrieve
        when(repository
        .findByDateBetween(
        LocalDate
        .of(2024, 
        1, 
        1),
        LocalDate
        .of(2024, 
        12, 
        31)
        )).thenReturn(List
        .of(daily24, 
        anotherDaily24));

        //Mock repository calls made by retrieve
        when(repository.findByDateBetween(
        LocalDate
        .of(2025, 
        1, 
        1),
        LocalDate
        .of(2025,
         12, 
         31)
        )).thenReturn(List
        .of(daily25));

        //Act
        Page <YearlyDTO> result = 
        yearlySvc
        .retrieveAll(0, 
        10);

        //Assert
        assertNotNull(result);

        //There should be 2 distinct years
        assertEquals(2, 
        result
        .getContent()
        .size());
        assertEquals(2, 
        result
        .getTotalElements());

        // Verify years are sorted in ascending order
        assertEquals(
        List
        .of(2024, 
        2025),
        result
        .getContent()
        .stream()
        .map(YearlyDTO::getYear)
        .toList());

        //Verify repository calls
        verify(repository)
        .findAll();
        verify(repository)
        .findByDateBetween(
        LocalDate
        .of(2024, 
        1, 
        1),
        LocalDate
        .of(2024, 
        12, 
        31));
        verify(repository)
        .findByDateBetween(
        LocalDate
        .of(2025, 
        1, 
        1),
        LocalDate.of(2025, 
        12, 
        31));
    }

    @Test
    void testRetrieveAll_noRecordsFound 
    () {
        //Arrange
        when(repository
        .findAll())
        .thenReturn(List
        .of());

        //Act
        Page<YearlyDTO> result = 
        yearlySvc
        .retrieveAll(0, 
        10);

        //Assert
        assertNotNull(result);
        assertEquals(0, 
        result
        .getContent()
        .size());
        assertEquals(0, 
        result
        .getTotalElements());
        assertEquals(0, 
        result
        .getTotalPages());

        //Verify repository call
        verify(repository)
        .findAll();
    }
}
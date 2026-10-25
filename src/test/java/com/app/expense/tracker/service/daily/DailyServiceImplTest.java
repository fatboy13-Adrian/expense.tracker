package com.app.expense.tracker.service.daily;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import com.app.expense.tracker.Daily;
import com.app.expense.tracker.DailyMapper;
import com.app.expense.tracker.DailyRepository;
import com.app.expense.tracker.dto.DailyDTO;
import com.app.expense.tracker.exception.date.DateNotFoundException;

@ExtendWith(MockitoExtension.class)
public class DailyServiceImplTest {
    @InjectMocks
    private DailyServiceImpl svc;

    @Mock
    private DailyRepository repository;

    @Mock
    private DailyMapper mapper;

    private LocalDate date;
    private Daily d;
    private DailyDTO dto;

    @BeforeEach
    public void setUp () {
        date = 
        LocalDate
        .of(2026, 
        1, 
        1);

        //DTO used as input for service methods
        dto = 
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
    void testSave () {
        //Arrange
        when(repository
        .findById(date))
        .thenReturn(Optional
        .empty());
        when(mapper
        .toEntity(dto))
        .thenReturn(d);
        when(repository
        .save(d))
        .thenReturn(d);
        when(mapper
        .toDTO(d))
        .thenReturn(dto);

        //Act
        DailyDTO result = 
        svc
        .save(dto);

        //Assert
        assertNotNull(result);
        assertEquals(date, 
        result
        .getDate());

        verify(repository)
        .findById(date);
        verify(mapper)
        .toEntity(dto);
        verify(mapper)
        .update(d, dto);
        verify(repository)
        .save(d);
        verify(mapper)
        .toDTO(d);
    }

    @Test
    void testSave_RecordAlreadyExists () {
        //Arrange
        when(repository
        .findById(date))
        .thenReturn(Optional
        .of(d));
        when(repository
        .save(d))
        .thenReturn(d);
        when(mapper
        .toDTO(d))
        .thenReturn(dto);

        //Act
        DailyDTO result = 
        svc
        .save(dto);

        // Assert
        assertNotNull(result);
        assertEquals(date, 
        result
        .getDate());

        verify(repository)
        .findById(date);
        verify(mapper)
        .update(d, dto);
        verify(repository)
        .save(d);
        verify(mapper)
        .toDTO(d);
    }
    
    @Test
    void testRetrieve () {
        //Mock: expense exists for given date
        when(repository
        .findById(date))
        .thenReturn(Optional
        .of(d));
        when(mapper
        .toDTO(d))
        .thenReturn(dto);
        DailyDTO result = 
        svc
        .retrieve(date);
        assertNotNull(result);
        assertEquals(dto
        .getDate(), 
        result
        .getDate());
        verify(repository, 
        times(1))
        .findById(date);
    }

    @Test
    void testRetrieveNotFound () {
        //Mock: no expense found
        when(repository
        .findById(date))
        .thenReturn(Optional
        .empty());

        //Expect exception when not found
        DateNotFoundException exception =
        assertThrows(DateNotFoundException.class,
        () -> svc
        .retrieve(date));
        assertNotNull(exception);
        verify(repository, 
        times(1))
        .findById(date);
    }

    @Test
    void testRetrieveAll () {
        //Define page number & size
        int page = 1;
        int size = 10;

        //Create a Pageable that repository is expected to receive
        Pageable pageable;
        pageable = 
        PageRequest
        .of(page - 1, 
        size);

        //Create a list of expense entities
        List <Daily> dList = 
        List
        .of(d);

        //Create a Page containing expense entities
        Page <Daily> dPage =
        new PageImpl <>(dList, 
        pageable, 
        dList
        .size());

        //Mock repository response
        when(repository
        .findAll(pageable))
        .thenReturn(dPage);

        //Mock entity-to-DTO mapping
        when(mapper
        .toDTO(d))
        .thenReturn(dto);

        //Call service method
        Page <DailyDTO> result =
        svc.retrieveAll(page, 
        size);

        //Verify that a result was returned
        assertNotNull(result);

        //Verify page contains one DTO
        assertEquals(1, 
        result
        .getContent()
        .size());

        //Verify that correct DTO was returned
        assertEquals(dto, 
        result
        .getContent()
        .get(0));

        //Verify page number
        assertEquals(0, 
        result
        .getNumber());

        //Verify page size
        assertEquals(size, 
        result
        .getSize());

        //Verify that repository was called once
        verify(repository, 
        times(1))
        .findAll(pageable);

        //Verify that mapper was called once
        verify(mapper, 
        times(1))
        .toDTO(d);
    }

    @Test
    void testRetrieveAllDatesNotFound () {
        //Define page number & size
        int page = 1;
        int size = 10;

        //Create expected Pageable
        Pageable pageable = 
        PageRequest
        .of(page - 1, 
        size);

        //Mock repository to return an empty page
        Page <Daily> empty = 
        new PageImpl<>(List
        .of(), 
        pageable, 
        0);

        when(repository
        .findAll(pageable))
        .thenReturn(empty);

        //Call service method
        Page <DailyDTO> result = 
        svc
        .retrieveAll(page, 
        size);

        //Verify that a result was returned
        assertNotNull(result);

        //Verify that page contains no records
        assertEquals(0, 
        result
        .getContent()
        .size());

        //Verify that page is empty
        assertTrue(result
        .isEmpty());

        //Verify that total number of records is zero
        assertEquals(0, 
        result
        .getTotalElements());

        //Verify that repository was called once
        verify(repository, 
        times(1))
        .findAll(pageable);

        //Verify that mapper was never called
        verify(mapper, 
        times(0))
        .toDTO(any(Daily.class));
    }

    @Test
    void testDelete () {
        //Arrange
        date = 
        LocalDate
        .of(2026, 
        1, 
        15);

        Daily d = new Daily();

        when(repository
        .findById(date))
        .thenReturn(Optional
        .of(d));

        //Act
        svc
        .delete(date);

        //Assert
        verify(repository, 
        times(1))
        .findById(date);
        verify(repository, 
        times(1))
        .deleteById(date);
    }

    @Test
    void testDeleteNotFound () {
        //Arrange
        date = 
        LocalDate
        .of(2026, 
        01, 
        15);
        when(repository
        .findById(date))
        .thenReturn(Optional
        .empty());

        //Act & Assert
        DateNotFoundException exception = 
        assertThrows(DateNotFoundException.class,
        () -> svc
        .delete(date));

        //Verify exception was thrown
        assertNotNull(exception);

        //Verify that findById was called
        verify(repository, 
        times(1))
        .findById(date);

        //deleteById must NOT be called because date doesn't exist
        verify(repository, 
        never())
        .deleteById(date);
    }
}
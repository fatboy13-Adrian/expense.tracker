package com.app.expense.tracker.service.holiday;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import com.app.expense.tracker.dto.holiday.CityStateDTO;
import com.app.expense.tracker.entity.holiday.CityState;
import com.app.expense.tracker.dto.holiday.CountryDTO;
import com.app.expense.tracker.dto.holiday.ItenaryDTO;
import com.app.expense.tracker.entity.holiday.Country;
import com.app.expense.tracker.entity.holiday.Itenary;
import com.app.expense.tracker.exception.date.DateNotFoundException;
import com.app.expense.tracker.mapper.holiday.ItenaryMapper;
import com.app.expense.tracker.repository.ItenaryRepository;

@ExtendWith(MockitoExtension.class)
public class ItenaryServiceImplTest {
    @InjectMocks
    private ItenaryServiceImpl svc;

    @Mock
    private ItenaryRepository repository;

    @Mock
    private ItenaryMapper mapper;

    private LocalDate date;
    private LocalTime time;
    private Itenary i;
    private ItenaryDTO dto;
    private Country c;
    private CityState cs;
    private CountryDTO cdto;
    private CityStateDTO csdto;

    @BeforeEach
    public void setUp 
    () {
        date = 
        LocalDate
        .of(2026, 
        1, 
        1);
        time = 
        LocalTime
        .of(10, 
        30);

        //Arrange Country entity
        c = 
        new Country();
        c
        .setId(1L);
        c
        .setName("Singapore");
        c
        .setCode("SG");

        //Arrange CityState entity
        cs = 
        new CityState();
        cs
        .setId(1L);
        cs
        .setName("Singapore");
        cs
        .setCountry(c);

        //Arrange CountryDTO
        cdto = 
        new CountryDTO();
        cdto
        .setId(1L);
        cdto
        .setName("Singapore");
        cdto
        .setCode("SG");

        //Arrange CityStateDTO
        csdto = 
        new CityStateDTO();
        csdto
        .setId(1L);
        csdto
        .setName("Singapore");
        csdto
        .setCountry(c);

        //DTO used as input for service methods
        dto = 
        new ItenaryDTO();
        dto
        .setDate(date);
        dto
        .setTime(time);
        dto
        .setCountry(c);
        dto
        .setCityState(cs);
        dto
        .setBusOrFlightNo("SQ123");
        dto
        .setBusOrTrainStation("Changi Airport");
        dto
        .setVenue("Marina Bay Sands");
        dto
        .setAddress("10 Bayfront Ave, Singapore 018956");
        dto
        .setPurpose("Business Meeting");
        dto
        .setAmount(BigDecimal
        .valueOf(100.50));
        dto
        .setTotal(BigDecimal
        .valueOf(150.75));

        //Entity representing DB record
        i = 
        new Itenary();
        i
        .setDate(date);
        i
        .setTime(time);
        i
        .setCountry(c);
        i
        .setCityState(cs);
        i
        .setBusOrFlightNo("SQ123");
        i
        .setBusOrTrainStation("Changi Airport");
        i
        .setVenue("Marina Bay Sands");
        i
        .setAddress("10 Bayfront Ave, Singapore 018956");
        i
        .setPurpose("Business Meeting");
        i
        .setAmount(BigDecimal
        .valueOf(100.50));
        i
        .setTotal(BigDecimal
        .valueOf(150.75));
    }

    @Test
    void testSave 
    () {
        //Arrange
        when(repository
        .findByDate(date))
        .thenReturn(Optional
        .of(i));
        when(repository
        .save(i))
        .thenReturn(i);
        when(mapper
        .toDTO(i))
        .thenReturn(dto);

        //Act
        ItenaryDTO 
        result = 
        svc
        .save(dto);

        //Assert
        assertNotNull(result);
        assertEquals(date, 
        result
        .getDate());
        verify(repository)
        .findByDate(date);
        verify(mapper)
        .update(i, dto);
        verify(repository)
        .save(i);
        verify(mapper)
        .toDTO(i);
    }

    @Test
    void testRetrieve 
    () {
        //Arrange
        when(repository
        .findByDate(date))
        .thenReturn(Optional
        .of(i));
        when(mapper
        .toDTO(i))
        .thenReturn(dto);

        //Act
        ItenaryDTO 
        result = 
        svc
        .retrieve(date);

        //Assert
        assertNotNull(result);
        assertEquals(date, 
        result
        .getDate());
        verify(repository)
        .findByDate(date);
        verify(mapper)
        .toDTO(i);
    }

    @Test
    void testRetrieveNotFound 
    () {
        //Arrange
        when(repository
        .findByDate(date))
        .thenReturn(Optional
        .empty());

        //Act & Assert
        assertThrows(
        DateNotFoundException.class,
        () -> svc
        .retrieve(date));
        verify(repository)
        .findByDate(date);
    }

    @Test
    void testRetrieveAll 
    () {
        //Arrange
        Itenary i1 = 
        new Itenary();
        i1
        .setDate(LocalDate
        .of(2026, 
        1, 
        1));
        i1
        .setCountry(c);
        i1
        .setCityState(cs);
        i1
        .setPurpose("Business Meeting");

        Itenary i2 = 
        new Itenary();
        i2
        .setDate(LocalDate
        .of(2026, 
        1, 
        2));
        i2
        .setCountry(c);
        i2
        .setCityState(cs);
        i2
        .setPurpose("Holiday");

        ItenaryDTO dto1 = 
        new ItenaryDTO();
        dto1
        .setDate(i1
        .getDate());
        dto1
        .setCountry(c);
        dto1
        .setCityState(cs);
        dto1
        .setPurpose("Business Meeting");

        ItenaryDTO dto2 = 
        new ItenaryDTO();
        dto2
        .setDate(i2.getDate());
        dto2
        .setCountry(c);
        dto2
        .setCityState(cs);
        dto2
        .setPurpose("Holiday");

        List <Itenary> 
        itineraries = 
        List
        .of(i1, 
        i2);

        when(repository
        .findAll())
        .thenReturn(itineraries);
        when(mapper
        .toDTO(i1))
        .thenReturn(dto1);
        when(mapper
        .toDTO(i2))
        .thenReturn(dto2);

        //Act
        List <ItenaryDTO> 
        result = 
        svc
        .retrieveAll();

        //Assert
        assertNotNull(result);
        assertEquals(2, 
        result
        .size());
        assertEquals(LocalDate
        .of(2026, 
        1, 
        1), 
        result
        .get(0)
        .getDate());
        assertEquals("Business Meeting", 
        result
        .get(0)
        .getPurpose());
        assertEquals(LocalDate
        .of(2026, 
        1, 
        2), 
        result
        .get(1)
        .getDate());
        assertEquals("Holiday", 
        result
        .get(1)
        .getPurpose());
        verify(repository)
        .findAll();
        verify(mapper)
        .toDTO(i1);
        verify(mapper)
        .toDTO(i2);
    }

    
    @Test
    void testDelete 
    () {
        //Arrange
        when(repository
        .existsById(date))
        .thenReturn(true);

        //Act
        svc
        .delete(date);

        //Assert
        verify(repository, 
        times(1))
        .existsById(date);
        verify(repository, 
        times(1))
        .deleteById(date);
    }

    @Test
    void testDeleteNotFound 
    () {
        //Arrange
        when(repository
        .existsById(date))
        .thenReturn(false);

        //Act & Assert
        assertThrows(
        DateNotFoundException.class,
        () -> svc
        .delete(date));
        verify(repository)
        .existsById(date);
        verify(repository, 
        never())
        .deleteById(date);
    }
}
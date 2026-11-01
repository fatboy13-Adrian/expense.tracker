package com.app.expense.tracker.service.holiday;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.app.expense.tracker.dto.holiday.CityStateDTO;
import com.app.expense.tracker.entity.holiday.CityState;
import com.app.expense.tracker.entity.holiday.Country;
import com.app.expense.tracker.exception.holiday.CityStateNotFoundException;
import com.app.expense.tracker.mapper.holiday.CityStateMapper;
import com.app.expense.tracker.repository.CityStateRepository;

@ExtendWith(MockitoExtension.class)
public class CityStateServiceImplTest {
    @InjectMocks
    private CityStateServiceImpl svc;

    @Mock
    private CityStateRepository repository;

    @Mock
    private CityStateMapper mapper;

    private Country c;
    private CityState cs;
    private Long id;
    private CityStateDTO dto;

    @BeforeEach
    public void setUp 
    () {
        id = 1L;

        //Arrange Country entity
        c = 
        new Country();
        c
        .setId(id);
        c
        .setName("Singapore");

        //Arrange CityStateDTO
        dto = 
        new CityStateDTO();
        dto
        .setId(id);
        dto
        .setName("Singapore");
        dto
        .setCountry(c);

        //Arrange CityState entity
        cs = 
        new CityState();
        cs
        .setId(id);
        cs
        .setName("Singapore");
        cs
        .setCountry(c);
    }

    @Test
    void testSave 
    () {
        //Arrange
        when(repository
        .findById(id))
        .thenReturn(Optional
        .of(cs));
        when(repository
        .save(cs))
        .thenReturn(cs);
        when(mapper
        .toDTO(cs))
        .thenReturn(dto);

        //Act
        CityStateDTO 
        result = 
        svc
        .save(dto);

        //Assert
        assertNotNull(result);
        assertEquals(id, 
        result
        .getId());

        verify(repository)
        .findById(id);
        verify(mapper)
        .update(cs, dto);
        verify(repository)
        .save(cs);
        verify(mapper)
        .toDTO(cs);
    }

    @Test
    void testRetrieve 
    () {
        //Arrange: CityState exists for given ID
        when(repository
        .findById(id))
        .thenReturn(Optional
        .of(cs));

        when(mapper
        .toDTO(cs))
        .thenReturn(dto);

        //Act
        CityStateDTO result = 
        svc
        .retrieve(id);

        //Assert
        assertNotNull(result);
        assertEquals(dto
        .getId(), 
        result
        .getId());

        verify(repository, 
        times(1))
        .findById(id);
        verify(mapper)
        .toDTO(cs);
    }

    @Test
    void testRetrieveAll 
    () {
        //Arrange
        CityState cityState1 = 
        new CityState();
        cityState1
        .setId(1L);
        cityState1
        .setName("Singapore");
        cityState1
        .setCountry(c);
        CityState cityState2 = 
        new CityState();
        cityState2
        .setId(2L);
        cityState2
        .setName("Kuala Lumpur");
        cityState2
        .setCountry(c);

        CityStateDTO dto1 = 
        new CityStateDTO();
        dto1
        .setId(1L);
        dto1
        .setName("Singapore");
        dto1
        .setCountry(c);
        CityStateDTO dto2 = 
        new CityStateDTO();
        dto2
        .setId(2L);
        dto2
        .setName("Kuala Lumpur");
        dto2
        .setCountry(c);

        List <CityState> 
        cityStates =
        List
        .of(cityState1, 
        cityState2);

        when(repository
        .findAll())
        .thenReturn(cityStates);
        when(mapper
        .toDTO(cityState1))
        .thenReturn(dto1);
        when(mapper
        .toDTO(cityState2))
        .thenReturn(dto2);

        //Act
        List <CityStateDTO> 
        result = 
        svc
        .retrieveAll();

        //Assert
        assertNotNull(result);
        assertEquals(2, 
        result
        .size());
        assertEquals(1L, 
        result
        .get(0)
        .getId());
        assertEquals("Singapore", 
        result
        .get(0)
        .getName());
        assertEquals(2L, 
        result
        .get(1)
        .getId());
        assertEquals("Kuala Lumpur", 
        result
        .get(1)
        .getName());
        verify(repository)
        .findAll();
        verify(mapper)
        .toDTO(cityState1);
        verify(mapper)
        .toDTO(cityState2);
    }

    @Test
    void testRetrieveNotFound 
    () {
        //Arrange: No CityState found
        when(repository
        .findById(id))
        .thenReturn(Optional
        .empty());

        //Act & Assert
        CityStateNotFoundException exception =
        assertThrows(
        CityStateNotFoundException.class,
        () -> svc
        .retrieve(id));
        assertNotNull(exception);
        verify(repository, 
        times(1))
        .findById(id);
    }

    @Test
    void testDelete 
    () {
        //Arrange
        when(repository
        .existsById(id))
        .thenReturn(true);

        //Act
        svc
        .delete(id);

        //Assert
        verify(repository, 
        times(1))
        .existsById(id);
        verify(repository, 
        times(1))
        .deleteById(id);
    }

    @Test
    void testDeleteNotFound 
    () {
        //Arrange
        when(repository
        .existsById(id))
        .thenReturn(false);

        //Act & Assert
        CityStateNotFoundException exception =
        assertThrows(
        CityStateNotFoundException.class,
        () -> svc
        .delete(id));
        assertNotNull(exception);
        verify(repository, 
        times(1))
        .existsById(id);
        verify(repository, 
        never())
        .deleteById(id);
    }
}
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
import com.app.expense.tracker.dto.holiday.CountryDTO;
import com.app.expense.tracker.entity.holiday.Country;
import com.app.expense.tracker.exception.holiday.CountryNotFoundException;
import com.app.expense.tracker.mapper.holiday.CountryMapper;
import com.app.expense.tracker.repository.CountryRepository;

@ExtendWith(MockitoExtension.class)
public class CountryServiceImplTest {
    @InjectMocks
    private CountryServiceImpl svc;

    @Mock
    private CountryRepository repository;

    @Mock
    private CountryMapper mapper;

    private Country c;
    private Long id;
    private CountryDTO dto;

    @BeforeEach
    public void setUp 
    () {
        id = 1L;
        dto = new CountryDTO();
        dto.setId(id);
        dto.setName("Singapore");
        dto.setCode("SG");
        c = new Country();
        c.setId(id);
        c.setName("Singapore");
        c.setCode("SG");
    }

    @Test
    void testSave 
    () {
        //Arrange
        when(repository
        .findById(id))
        .thenReturn(Optional
        .of(c));
        when(repository
        .save(c))
        .thenReturn(c);
        when(mapper
        .toDTO(c))
        .thenReturn(dto);

        //Act
        CountryDTO 
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
        .update(c, 
        dto);
        verify(repository)
        .save(c);
        verify(mapper)
        .toDTO(c);
    }

    @Test
    void testSave_RecordAlreadyExists 
    () {
        //Arrange
        when(repository
        .findById(id))
        .thenReturn(Optional
        .of(c));
        when(repository
        .save(c))
        .thenReturn(c);
        when(mapper
        .toDTO(c))
        .thenReturn(dto);

        //Act
        CountryDTO result = 
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
        .update(c, dto);
        verify(repository)
        .save(c);
        verify(mapper)
        .toDTO(c);
    }
    
    @Test
    void testRetrieve 
    () {
        //Mock: country exists for given id
        when(repository
        .findById(id))
        .thenReturn(Optional
        .of(c));
        when(mapper
        .toDTO(c))
        .thenReturn(dto);
        CountryDTO result = 
        svc
        .retrieve(id);
        assertNotNull(result);
        assertEquals(dto
        .getId(), 
        result
        .getId());
        verify(repository, 
        times(1))
        .findById(id);
    }

    @Test
    void testRetrieveNotFound 
    () {
        //Mock: no record found
        when(repository
        .findById(id))
        .thenReturn(Optional
        .empty());

        //Expect exception when not found
        CountryNotFoundException exception =
        assertThrows(CountryNotFoundException.class,
        () -> svc
        .retrieve(id));
        assertNotNull(exception);
        verify(repository, 
        times(1))
        .findById(id);
    }

    @Test
    void testRetrieveAll 
    () {
        //Arrange
        Country country1 = 
        new Country();
        country1
        .setId(1L);
        country1
        .setName("Singapore");
        Country country2 = 
        new Country();
        country2
        .setId(2L);
        country2
        .setName("Malaysia");

        CountryDTO dto1 = 
        new CountryDTO();
        dto1
        .setId(1L);
        dto1
        .setName("Singapore");
        CountryDTO dto2 = 
        new CountryDTO();
        dto2
        .setId(2L);
        dto2.setName("Malaysia");

        List< Country> 
        countries = 
        List
        .of(country1, 
        country2);

        when(repository
        .findAll())
        .thenReturn(countries);
        when(mapper
        .toDTO(country1))
        .thenReturn(dto1);
        when(mapper
        .toDTO(country2))
        .thenReturn(dto2);

        //Act
        List <CountryDTO> 
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
        assertEquals("Malaysia", 
        result
        .get(1).getName());
        verify(repository)
        .findAll();
        verify(mapper)
        .toDTO(country1);
        verify(mapper)
        .toDTO(country2);
    }

    @Test
    void testRetrieveAllWhenNoAccountsExist 
    () {
        //Arrange
        when(repository
        .findAll())
        .thenReturn(List
        .of());

        //Act
        List <CountryDTO> 
        result = 
        svc
        .retrieveAll();

        //Assert
        assertNotNull(result);
        assertTrue(result
        .isEmpty());
        verify(repository)
        .findAll();
        verifyNoInteractions(mapper);
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
        CountryNotFoundException exception =
        assertThrows(
        CountryNotFoundException.class,
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
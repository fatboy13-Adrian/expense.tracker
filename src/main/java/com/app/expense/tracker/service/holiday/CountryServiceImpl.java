package com.app.expense.tracker.service.holiday;
import java.util.List;
import org.springframework.stereotype.Service;
import com.app.expense.tracker.dto.holiday.CountryDTO;
import com.app.expense.tracker.entity.holiday.Country;
import com.app.expense.tracker.exception.holiday.CountryNotFoundException;
import com.app.expense.tracker.mapper.holiday.CountryMapper;
import com.app.expense.tracker.repository.CountryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CountryServiceImpl implements CountryService {
    private final CountryRepository repository;
    private final CountryMapper mapper;
    
    @Override
    @Transactional
    public CountryDTO save
    (CountryDTO dto) {
        Country c;
        if (dto.getId() != null) { 
            c = 
            repository
            .findById(dto
            .getId())
            .orElseThrow(() -> 
            new CountryNotFoundException(dto
            .getId())); 
            mapper
            .update(c, dto); 
        } else { 
            c = 
            mapper
            .toEntity(dto); 
        }

        Country saved = 
        repository
        .save(c);
        return mapper
        .toDTO(saved);
    }

    @Override
    public CountryDTO retrieve 
    (Long id) {
        Country c = 
        repository
        .findById(id)
        .orElseThrow(() -> 
        new CountryNotFoundException(id)); 
        return mapper
        .toDTO(c);
    }

    @Override
    public List <CountryDTO> retrieveAll 
    () {
        return repository
        .findAll()
        .stream()
        .map(mapper::toDTO)
        .toList();
    }

    @Override 
    @Transactional 
    public void delete 
    (Long id) { 
        if (!repository
        .existsById(id))
        throw new CountryNotFoundException(id); 
        repository
        .deleteById(id); 
    }
}
package com.app.expense.tracker.service.holiday;
import java.util.List;
import org.springframework.stereotype.Service;
import com.app.expense.tracker.dto.holiday.CityStateDTO;
import com.app.expense.tracker.entity.holiday.CityState;
import com.app.expense.tracker.exception.holiday.CityStateNotFoundException;
import com.app.expense.tracker.mapper.holiday.CityStateMapper;
import com.app.expense.tracker.repository.CityStateRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CityStateServiceImpl implements CityStateService {
    private final CityStateRepository repository;
    private final CityStateMapper mapper;
    
    @Override
    @Transactional
    public CityStateDTO save
    (CityStateDTO dto) {
        CityState cs;
        if (dto.getId() != null) { 
            cs = 
            repository
            .findById(dto
            .getId())
            .orElseThrow(() -> 
            new CityStateNotFoundException(dto
            .getId())); 
            mapper
            .update(cs, dto); 
        } else { 
            cs = 
            mapper
            .toEntity(dto); 
        }

        CityState saved = 
        repository
        .save(cs);
        return mapper
        .toDTO(saved);
    }

    @Override
    public CityStateDTO 
    retrieve (Long id) {
        CityState cs = 
        repository
        .findById(id)
        .orElseThrow(() -> 
        new CityStateNotFoundException(id)); 
        return mapper
        .toDTO(cs);
    }

    @Override
    public List <CityStateDTO> 
    retrieveAll () {
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
        throw new CityStateNotFoundException(id); 
        repository
        .deleteById(id); 
    }
}
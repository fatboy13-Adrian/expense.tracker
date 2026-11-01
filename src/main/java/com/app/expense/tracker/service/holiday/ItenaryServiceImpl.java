package com.app.expense.tracker.service.holiday;
import java.time.LocalDate;
import java.util.List;
import org.springframework.stereotype.Service;
import com.app.expense.tracker.dto.holiday.ItenaryDTO;
import com.app.expense.tracker.entity.holiday.Itenary;
import com.app.expense.tracker.exception.date.DateNotFoundException;
import com.app.expense.tracker.mapper.holiday.ItenaryMapper;
import com.app.expense.tracker.repository.ItenaryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ItenaryServiceImpl implements ItenaryService {
    private final ItenaryRepository repository;
    private final ItenaryMapper mapper;

    @Override
    @Transactional
    public ItenaryDTO save 
    (ItenaryDTO dto) {
        Itenary i;
        if (dto
        .getDate() 
        != null) {
            i = 
            repository
            .findByDate(dto
            .getDate())
            .orElseThrow(() ->
            new DateNotFoundException(dto
            .getDate()));
            mapper
            .update(i, 
            dto);
        } else {
            i = 
            mapper
            .toEntity(dto);
        }

        Itenary saved = 
        repository
        .save(i);
        return mapper
        .toDTO(saved);
    }

    @Override
    public ItenaryDTO retrieve 
    (LocalDate date) {
        Itenary i = 
        repository
        .findByDate(date)
        .orElseThrow(() ->
        new DateNotFoundException(date));
        return mapper
        .toDTO(i);
    }

    @Override
    public List<ItenaryDTO> 
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
    (LocalDate date) {
        if (!repository
        .existsById(date))
        throw new DateNotFoundException(date);
        repository.deleteById(date);
    }
}
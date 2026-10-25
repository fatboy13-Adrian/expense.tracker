package com.app.expense.tracker.service.daily;
import java.time.LocalDate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.app.expense.tracker.Daily;
import com.app.expense.tracker.DailyMapper;
import com.app.expense.tracker.DailyRepository;
import com.app.expense.tracker.dto.DailyDTO;
import com.app.expense.tracker.exception.date.DateNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DailyServiceImpl implements DailyService {
    private final DailyRepository repository;
    private final DailyMapper mapper;

    @Override
    @Transactional
    public DailyDTO save 
    (DailyDTO dto) {
        Daily d = 
        repository
        .findById(dto
        .getDate())
        .orElseGet(() -> 
        mapper.toEntity(dto));

        if (d.getDate()
        .equals(dto.getDate())) {
            mapper
            .update(d, dto);
        }

        Daily saved = 
        repository
        .save(d);
        return mapper
        .toDTO(saved);
    }

    @Override
    public DailyDTO retrieve 
    (LocalDate date) {
        Daily d = 
        repository
        .findById(date)
        .orElseThrow(() ->
        new DateNotFoundException(date));
        return mapper
        .toDTO(d);
    }

    @Override 
    public Page <DailyDTO> retrieveAll 
    (int page, int size) {
        Pageable pageable = 
        PageRequest
        .of(page - 1, 
        size);
        return repository
        .findAll(pageable)
        .map(mapper::toDTO);
    }

    @Override
    @Transactional
    public void delete 
    (LocalDate date) {
        repository
        .findById(date)
        .orElseThrow(() -> 
        new DateNotFoundException(date));
        repository
        .deleteById(date);
    }
}
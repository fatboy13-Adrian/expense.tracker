package com.app.expense.tracker.controller.daily;
import com.app.expense.tracker.service.daily.MonthlyReportSvc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.io.ByteArrayInputStream;
import java.time.YearMonth;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MonthlyReportControllerTest {
    @Mock
    private MonthlyReportSvc svc;

    @InjectMocks
    private MonthlyReportController controller;

    private YearMonth yearMonth;
    private ByteArrayInputStream file;

    @BeforeEach
    void setUp () {
        yearMonth = 
        YearMonth
        .of(2026, 
        3);

        //Simulates generated Excel file content
        byte [] content = 
        "Test Excel Content"
        .getBytes();
        file = 
        new ByteArrayInputStream(content);
    }

    @Test
    void testExportMonthlyRecords () {
        //Arrange
        when(svc
        .exportMonthlyRecords(yearMonth))
        .thenReturn(file);

        //Act
        ResponseEntity <InputStreamResource> response =
        controller
        .exportMonthlyRecords(yearMonth);

        //Assert
        assertEquals(HttpStatus
        .OK, 
        response
        .getStatusCode());
        assertNotNull(response
        .getBody());

        verify(svc)
        .exportMonthlyRecords(yearMonth);
    }
}
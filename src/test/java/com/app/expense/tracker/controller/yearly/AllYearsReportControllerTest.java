package com.app.expense.tracker.controller.yearly;
import com.app.expense.tracker.service.yearly.AllYearsReportSvc;
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
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AllYearsReportControllerTest {
    @Mock
    private AllYearsReportSvc svc;

    @InjectMocks
    private AllYearsReportController controller;

    private ByteArrayInputStream file;

    @BeforeEach
    void setUp () {
        //Simulate content of an Excel file
        byte [] content = 
        "Test Excel Content"
        .getBytes();
        file = 
        new ByteArrayInputStream(content);
    }

    @Test
    void testExportYearlyRecords () {
        //Arrange
        when(svc
        .exportYearlyRecords())
        .thenReturn(file);

        //Act
        ResponseEntity <InputStreamResource> response =
        controller
        .exportYearlyExpenseRecords();

        //Assert
        assertEquals(HttpStatus
        .OK, 
        response
        .getStatusCode());
        assertNotNull(response
        .getBody());

        verify(svc)
        .exportYearlyRecords();
    }
}
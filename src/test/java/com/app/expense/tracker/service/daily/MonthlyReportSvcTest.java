package com.app.expense.tracker.service.daily;
import com.app.expense.tracker.dto.DailyDTO;
import com.app.expense.tracker.exception.date.DateNotFoundException;
import com.app.expense.tracker.exception.month.MonthNotFoundException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MonthlyReportSvcTest {
    @Mock
    private DailyService svc;

    @InjectMocks
    private MonthlyReportSvc reportSvc;

    private DailyDTO dto;

    @BeforeEach
    void setUp () {
        //Create a sample daily expense record
        dto = 
        new DailyDTO();
        dto
        .setDate(LocalDate
        .of(2026, 
        1, 
        15));
    }

    @Test
    void testGetDateRange () {
        //Arrange
        YearMonth month = 
        YearMonth
        .of(2026, 
        1);

        when(svc
        .retrieve(LocalDate
        .of(2026, 
        1, 
        15)))
        .thenReturn(dto);

        //Simulate dates without expense records
        when(svc
        .retrieve(
        argThat(date -> 
        !date
        .equals(LocalDate
        .of(2026, 
        1, 
        15)))))
        .thenThrow(new DateNotFoundException(LocalDate
        .of(2026, 
        1, 
        1)));

        //Act
        List<DailyDTO> result = 
        reportSvc
        .getDateRange(month);

        //Assert
        assertNotNull(result);
        assertEquals(1, 
        result
        .size());
        assertEquals(LocalDate
        .of(2026, 
        1, 
        15),
        result
        .get(0)
        .getDate());
    }

    @Test
    void testDateRange_noRecordsExists () {
        //Arrange
        YearMonth month = 
        YearMonth
        .of(2026, 
        2);

        //Simulate missing records for every date in February
        when(svc
        .retrieve(any(LocalDate
        .class)))
        .thenThrow(new DateNotFoundException(LocalDate
        .of(2026, 
        2, 
        1)));

        //Act & Assert
        assertThrows(MonthNotFoundException.class,
        () -> reportSvc
        .getDateRange(month));
    }

    @Test
    void testExportMonthlyRecords () 
    throws IOException {
        //Arrange
        YearMonth month = 
        YearMonth
        .of(2026, 
        1);
        LocalDate expDate = 
        LocalDate.of(2026, 
        1, 
        15);

        //Return DTO when requested date is January 15
        when(svc
        .retrieve(expDate))
        .thenReturn(dto);

        //Throw DateNotFoundException for all other dates
        when(svc
        .retrieve(
        argThat(requestedDate ->
        requestedDate != null
        && !requestedDate
        .equals(expDate))))
        .thenThrow(
        new DateNotFoundException(LocalDate
        .of(2026, 
        1, 
        1)));

        //Act
        ByteArrayInputStream result =
        reportSvc
        .exportMonthlyRecords(month);

        //Assert
        assertNotNull(result);

        try (Workbook workbook = 
        new XSSFWorkbook(result)) {
            Sheet sheet = 
            workbook
            .getSheetAt(0);

            //Verify worksheet name
            assertEquals(
            "Expense Records for January 202",
            sheet
            .getSheetName());

            //Verify header row
            Row header = 
            sheet
            .getRow(0);
            assertNotNull(header);

            assertEquals(
            "Date",
            header
            .getCell(0)
            .getStringCellValue());
            assertEquals(
            "Income",
            header
            .getCell(1)
            .getStringCellValue());

            //Verify expense record row
            Row dataRow = 
            sheet
            .getRow(1);
            assertNotNull(dataRow);

            assertEquals(
            "2026-01-15",
            dataRow
            .getCell(0)
            .getStringCellValue());

            //1 header row plus 1 expense record
            assertEquals(2, 
            sheet
            .getPhysicalNumberOfRows());
        }
    }

    @Test
    void testExportMonthlyRecords_noRecordsExists () {
        //Arrange
        YearMonth month = 
        YearMonth
        .of(2026, 
        2);

        when(svc
        .retrieve(any(LocalDate.class))
        ).thenThrow(
        new DateNotFoundException(LocalDate
        .of(2026, 
        2, 
        1)));

        //Act & Assert
        assertThrows(
        MonthNotFoundException.class,
        () -> reportSvc
        .exportMonthlyRecords(month));
    }
}
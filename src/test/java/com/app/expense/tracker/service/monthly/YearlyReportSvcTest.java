package com.app.expense.tracker.service.monthly;
import com.app.expense.tracker.dto.MonthlyDTO;
import com.app.expense.tracker.exception.YearNotFoundException;
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
import java.time.Year;
import java.time.YearMonth;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class YearlyReportSvcTest {
    @Mock
    private MonthlyService svc;

    @InjectMocks
    private YearlyReportSvc reportSvc;

    private MonthlyDTO dto;

    @BeforeEach
    void setUp () {
        //Create a sample monthly expense record
        dto = 
        new MonthlyDTO();
        dto
        .setMonth(YearMonth
        .of(2026, 
        1));
    }

    @Test
    void testGetMonthRange 
    () {
        //Arrange
        Year year = 
        Year
        .of(2026);
        YearMonth jan = 
        YearMonth
        .of(2026, 
        1);

        //Return DTO for jan & throw an exception for other months
        when(svc
        .retrieve(
        argThat(month -> 
        month != null 
        && month
        .equals(jan))))
        .thenReturn(dto);
        when(svc
        .retrieve(
        argThat(month -> 
        month != null && 
        !month
        .equals(jan))))
        .thenThrow(
        new MonthNotFoundException(YearMonth
        .of(2026, 
        2)));

        //Act
        List <MonthlyDTO> result = 
        reportSvc
        .getMonthRange(year);

        //Assert
        assertNotNull(result);
        assertEquals(1, 
        result
        .size());
        assertEquals(jan, 
        result
        .get(0)
        .getMonth());

        verify(svc)
        .retrieve(jan);
    }

    @Test
    void testGetDateRange_noRecordsExists 
    () {
        //Arrange
        Year year = 
        Year
        .of(2026);

        when(svc
        .retrieve(any(YearMonth.class)))
        .thenThrow(
        new MonthNotFoundException(YearMonth
        .of(2026, 
        1)));

        //Act & Assert
        assertThrows(
        YearNotFoundException.class,
        () -> reportSvc
        .getMonthRange(year));
    }

    @Test
    void testExportYearlyRecords ()
    throws IOException {
        //Arrange
        Year year = 
        Year
        .of(2026);
        YearMonth jan = 
        YearMonth
        .of(2026, 
        1);

        //Return DTO for jan & throw an exception for other months
        when(svc
        .retrieve(
        argThat(month -> 
        month != null && 
        month
        .equals(jan))))
        .thenReturn(dto);
        when(svc
        .retrieve(
        argThat(month -> 
        month != null && 
        !month
        .equals(jan))))
        .thenThrow(
        new MonthNotFoundException(YearMonth
        .of(2026, 
        2)));

        //Act
        ByteArrayInputStream result =
        reportSvc
        .exportYearlyRecords(year);

        //Assert
        assertNotNull(result);

        try (Workbook workbook = 
        new XSSFWorkbook(result)) {
            Sheet sheet = 
            workbook
            .getSheetAt(0);

            //Verify worksheet name
            assertEquals(
            "Expense Records for Year 2026",
            sheet
            .getSheetName());

            //Verify header row
            Row header = 
            sheet
            .getRow(0);
            assertNotNull(header);

            assertEquals(
            "Month",
            header
            .getCell(0)
            .getStringCellValue());
            assertEquals(
            "Income",
            header
            .getCell(1)
            .getStringCellValue());
            assertEquals(
            "Savings",
            header
            .getCell(15)
            .getStringCellValue());

            //Verify jan data row
            Row dataRow = 
            sheet
            .getRow(1);
            assertNotNull(dataRow);

            assertEquals(
            "2026-01",
            dataRow
            .getCell(0)
            .getStringCellValue());

            //Verify 1 header row & 1 data row
            assertEquals(2, 
            sheet
            .getPhysicalNumberOfRows());
        }
    }

    @Test
    void testExportYearlyRecords_noRecordsExists 
    () {
        //Arrange
        Year year = 
        Year
        .of(2026);

        when(svc
        .retrieve(any(YearMonth.class)))
        .thenThrow(
        new MonthNotFoundException(YearMonth
        .of(2026, 
        1)));

        //Act & Assert
        assertThrows(
        YearNotFoundException.class,
        () -> reportSvc
        .exportYearlyRecords(year));
    }
}
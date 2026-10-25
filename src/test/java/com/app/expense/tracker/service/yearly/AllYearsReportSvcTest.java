package com.app.expense.tracker.service.yearly;
import com.app.expense.tracker.dto.YearlyDTO;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AllYearsReportSvcTest {
    @Mock
    private YearlyService svc;

    @InjectMocks
    private AllYearsReportSvc reportSvc;

    @Test
    void testExportYearlyRecords () 
    throws IOException {
        //Arrange: Create sample yearly expense records
        YearlyDTO dto = 
        new YearlyDTO();
        dto.setYear(2025);
        dto.setIncome(new BigDecimal("50000.00"));
        dto.setDebt(new BigDecimal("5000.00"));
        dto.setEmergency(new BigDecimal("2000.00"));
        dto.setInvestments(new BigDecimal("3000.00"));
        dto.setBills(new BigDecimal("1200.00"));
        dto.setFood(new BigDecimal("2500.00"));
        dto.setGroceries(new BigDecimal("1800.00"));
        dto.setHaircut(new BigDecimal("100.00"));
        dto.setHousehold(new BigDecimal("600.00"));
        dto.setMedical(new BigDecimal("300.00"));
        dto.setParents(new BigDecimal("2400.00"));
        dto.setTransport(new BigDecimal("1000.00"));
        dto.setWants(new BigDecimal("1500.00"));
        dto.setOthers(new BigDecimal("500.00"));
        dto.setSavings(new BigDecimal("10000.00"));

        when(svc
        .retrieveAll())
        .thenReturn(List
        .of(dto));

        //Act: Export yearly records
        ByteArrayInputStream result = 
        reportSvc
        .exportYearlyRecords();

        //Assert: Verify generated Excel file
        assertNotNull(result);
        verify(svc)
        .retrieveAll();

        //Read generated Excel workbook
        try (Workbook workbook = 
        new XSSFWorkbook(result)) {
            Sheet sheet = 
            workbook
            .getSheetAt(0);

            //Verify worksheet name
            assertEquals(
            "Expense Records for All Years",
            sheet
            .getSheetName());

            //Verify header row
            Row header = 
            sheet
            .getRow(0);
            assertNotNull(header);
            assertEquals("Year", 
            header
            .getCell(0)
            .getStringCellValue());
            assertEquals("Income", 
            header
            .getCell(1)
            .getStringCellValue());
            assertEquals("Savings", 
            header
            .getCell(15)
            .getStringCellValue());

            //Verify yearly expense data
            Row dataRow = 
            sheet
            .getRow(1);
            assertNotNull(dataRow);

            assertEquals(2025, 
            (int) dataRow
            .getCell(0)
            .getNumericCellValue());
            assertEquals(50000.00, 
            dataRow
            .getCell(1)
            .getNumericCellValue(), 
            0.01);
            assertEquals(5000.00, 
            dataRow
            .getCell(2)
            .getNumericCellValue(), 
            0.01);
            assertEquals(10000.00, 
            dataRow
            .getCell(15)
            .getNumericCellValue(), 
            0.01);
        }
    }

    @Test
    void testExportYearlyRecords_noRecordsExists ()
    throws IOException {
        //Arrange: Return an empty list
        when(svc
        .retrieveAll())
        .thenReturn(List
        .of());

        //Act: Export yearly records
        ByteArrayInputStream result = 
        reportSvc
        .exportYearlyRecords();

        //Assert: Excel file should still be generated with a header row
        assertNotNull(result);
        verify(svc)
        .retrieveAll();

        try (Workbook workbook = 
        new XSSFWorkbook(result)) {
            Sheet sheet = 
            workbook
            .getSheetAt(0);

            //Header row should exist
            assertNotNull(sheet
            .getRow(0));

            //No yearly data rows should exist
            assertEquals(1, 
            sheet
            .getPhysicalNumberOfRows());
        }
    }
}
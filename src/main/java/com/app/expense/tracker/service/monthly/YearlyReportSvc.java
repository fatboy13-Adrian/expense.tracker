package com.app.expense.tracker.service.monthly;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.time.Year;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;
import com.app.expense.tracker.dto.MonthlyDTO;
import com.app.expense.tracker.exception.ExportExcelFailedException;
import com.app.expense.tracker.exception.YearNotFoundException;
import com.app.expense.tracker.exception.month.MonthNotFoundException;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class YearlyReportSvc {
    private final MonthlyService svc;

    public List<MonthlyDTO> getMonthRange 
    (Year year) {
        /*
         * Initialize list to store
         * monthly expense records
         */
        List<MonthlyDTO> dto = 
        new ArrayList<>();

        for (int month = 1; month <= 12; month++) {
            YearMonth yearMonth = 
            YearMonth
            .of(year
            .getValue(), 
            month);
            try {
                dto
                .add(svc
                .retrieve(yearMonth));
            } catch (MonthNotFoundException e) {
                // Skip months without expense records
            }
        }

        if (dto.isEmpty()) 
        throw new YearNotFoundException(year);
        
        return dto;
    }

    private ByteArrayInputStream buildMonthlyReport 
    (List <MonthlyDTO> dto, String sheetName) {
        try (Workbook workbook = 
        new XSSFWorkbook();
        ByteArrayOutputStream output =
        new ByteArrayOutputStream()) {
            //Create worksheet
            Sheet sheet = 
            workbook
            .createSheet(sheetName);

            //Create header style
            CellStyle headerStyle = 
            workbook
            .createCellStyle();
            Font font = 
            workbook
            .createFont();
            font
            .setBold(true);
            headerStyle
            .setFont(font);
            headerStyle
            .setAlignment(
            HorizontalAlignment
            .CENTER);

            //Create currency style
            CellStyle currencyStyle =
            workbook
            .createCellStyle();
            currencyStyle
            .setDataFormat(
            workbook
            .createDataFormat()
            .getFormat("[$$-en-SG]#,##0.00"));
            currencyStyle
            .setAlignment(
            HorizontalAlignment
            .CENTER);

            //Define column headers
            String [] columns = {
                "Month",
                "Income",
                "Debt",
                "Emergency",
                "Investments",
                "Bills",
                "Food",
                "Groceries",
                "Haircut",
                "Household",
                "Medical",
                "Parents",
                "Transport",
                "Wants",
                "Others",
                "Savings"
            };

            //Create header row
            Row header = 
            sheet
            .createRow(0);
            for (int i = 0; i < columns.length; i++) {
                Cell cell = 
                header
                .createCell(i);
                cell
                .setCellValue(columns[i]);
                cell
                .setCellStyle(headerStyle);
            }

            //Create monthly data rows
            for (int i = 0; i < dto.size(); i++) {
                //Retrieve current monthly expense DTO
                MonthlyDTO mdto = 
                dto.get(i);

                //Create new row
                Row row = 
                sheet
                .createRow(i + 1);

                //Set month
                row
                .createCell(0)
                .setCellValue(mdto
                .getMonth()
                .toString());

                //Store financial values
                BigDecimal [] values = {
                    mdto.getIncome(),
                    mdto.getDebt(),
                    mdto.getEmergency(),
                    mdto.getInvestments(),
                    mdto.getBills(),
                    mdto.getFood(),
                    mdto.getGroceries(),
                    mdto.getHaircut(),
                    mdto.getHousehold(),
                    mdto.getMedical(),
                    mdto.getParents(),
                    mdto.getTransport(),
                    mdto.getWants(),
                    mdto.getOthers(),
                    mdto.getSavings()
                };

                //Create financial cells
                for (int j = 0; j < values.length; j++) {
                    Cell cell = 
                    row
                    .createCell(j + 1);

                    //Prevent null values
                    BigDecimal value =
                    values[j] != null
                    ? values[j]
                    : BigDecimal.ZERO;

                    cell
                    .setCellValue(value
                    .doubleValue());
                    cell
                    .setCellStyle(currencyStyle);
                }
            }

            //Automatically resize columns
            for (int i = 0; i < columns.length; i++) {
                sheet
                .autoSizeColumn(i);
            }

            //Write workbook into output stream
            workbook
            .write(output);

            //Return Excel file
            return new ByteArrayInputStream(
            output
            .toByteArray());
        } catch (Exception e) {
            //Handle Excel export failure
            throw new ExportExcelFailedException(
            "Failed to export monthly expense records", e);
        }
    }

    public ByteArrayInputStream exportYearlyRecords 
    (Year year) {
        //Retrieve monthly expense records
        List <MonthlyDTO> mdto = 
        getMonthRange(year);

        //Build & return Excel file
        return buildMonthlyReport(
        mdto, 
        "Expense Records for Year " 
        + year);
    }
}
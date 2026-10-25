package com.app.expense.tracker.service.yearly;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
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
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import com.app.expense.tracker.dto.YearlyDTO;
import com.app.expense.tracker.exception.ExportExcelFailedException;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AllYearsReportSvc {
    /*
     * Service used to retrieve 
     * yearly expense records
     */
    private final YearlyService svc;

    public ByteArrayInputStream exportYearlyRecords() {
        /*
        * Retrieve all yearly expense records
        * from service, 1 page at a time
        */
        List<YearlyDTO> yearlyRecords = 
        new ArrayList<>();

        int size = 100;

        //Retrieve 1st page to determine total number of pages
        Page <YearlyDTO> firstPage = 
        svc
        .retrieveAll(0, 
        size);

        //Add records from first page
        yearlyRecords
        .addAll(firstPage
        .getContent());

        //Retrieve remaining pages
        for (int page = 1; 
        page < firstPage
        .getTotalPages(); 
        page++) {
            Page <YearlyDTO> yearlyPage = 
            svc
            .retrieveAll(page, 
            size);

            yearlyRecords
            .addAll(yearlyPage
            .getContent());
        }

        //Build & return Excel workbook
        return buildYearlyReport(yearlyRecords);
    }

    private ByteArrayInputStream buildYearlyReport 
    (List<YearlyDTO> dto) {
        try (Workbook workbook = 
        new XSSFWorkbook();
        ByteArrayOutputStream output = 
        new ByteArrayOutputStream()) {
            //Create worksheet
            Sheet sheet = 
            workbook
            .createSheet("Expense Records for All Years");

            //Create header cell style
            CellStyle headerStyle = 
            workbook
            .createCellStyle();

            //Configure bold text for column headers
            Font font = 
            workbook
            .createFont();
            font
            .setBold(true);
            headerStyle
            .setFont(font);
            headerStyle
            .setAlignment(HorizontalAlignment
            .CENTER);

            //Create currency cell style
            CellStyle currencyStyle = 
            workbook
            .createCellStyle();

            //Format financial values using SGD
            currencyStyle
            .setDataFormat(workbook
            .createDataFormat()
            .getFormat("[$$-en-SG]#,##0.00"));
            currencyStyle
            .setAlignment(HorizontalAlignment
            .CENTER);

            //Define Excel column headers
            String[] columns = {
                "Year",
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

            //Populate header cells
            for (int i = 0; 
            i < columns.length; i++) {
                Cell cell = 
                header
                .createCell(i);
                cell
                .setCellValue(columns[i]);
                cell
                .setCellStyle(headerStyle);
            }

            //Create 1 data row for each yearly expense record
            for (int i = 0; i < dto.size(); i++) {
                //Retrieve current yearly DTO
                YearlyDTO ydto = 
                dto
                .get(i);

                //Create a row below header
                Row row = 
                sheet
                .createRow(i + 1);

                //Write year in first column
                row
                .createCell(0)
                .setCellValue(ydto
                .getYear());

                /*
                 * Store financial values in same order
                 * as their corresponding excel column headers
                 */
                BigDecimal[] values = {
                    ydto.getIncome(),
                    ydto.getDebt(),
                    ydto.getEmergency(),
                    ydto.getInvestments(),
                    ydto.getBills(),
                    ydto.getFood(),
                    ydto.getGroceries(),
                    ydto.getHaircut(),
                    ydto.getHousehold(),
                    ydto.getMedical(),
                    ydto.getParents(),
                    ydto.getTransport(),
                    ydto.getWants(),
                    ydto.getOthers(),
                    ydto.getSavings()
                };

                // Populate financial cells
                for (int j = 0; j < values.length; j++) {
                    Cell cell = 
                    row
                    .createCell(j + 1);

                    //Replace null values with 0 to avoid errors
                    BigDecimal value = 
                    values[j] 
                    != null
                    ? values[j]
                    : BigDecimal.ZERO;

                    /*
                     * Store financial amount
                     * as a numeric value
                     */
                    cell
                    .setCellValue(value
                    .doubleValue());

                    //Apply currency format
                    cell
                    .setCellStyle(currencyStyle);
                }
            }

            //Automatically adjust column widths to fit their contents
            for (int i = 0; i < columns.length; i++) {
                sheet
                .autoSizeColumn(i);
            }

            //Write workbook into output stream
            workbook
            .write(output);

            //Return generated Excel file as an input stream
            return new ByteArrayInputStream(output
            .toByteArray());

        } catch (Exception e) {
            //Handle Excel export failure
            throw new ExportExcelFailedException(
            "Failed to export yearly expense records",
            e);
        }
    }
}
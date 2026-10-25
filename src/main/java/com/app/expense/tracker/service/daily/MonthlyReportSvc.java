package com.app.expense.tracker.service.daily;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import com.app.expense.tracker.dto.DailyDTO;
import com.app.expense.tracker.exception.ExportExcelFailedException;
import com.app.expense.tracker.exception.date.DateNotFoundException;
import com.app.expense.tracker.exception.month.MonthNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MonthlyReportSvc {
    private final DailyService svc;
    /*
     * Retrieve all existing daily 
     * expense records for 
     * specified month & year
     */
    public List <DailyDTO> getDateRange 
    (YearMonth month) {
        List <DailyDTO> records = 
        new ArrayList<>();

        //Determine 1st & last dates of selected month
        LocalDate startDate = 
        month
        .atDay(1);
        LocalDate endDate = 
        month
        .atEndOfMonth();

        //Retrieve each daily record within selected month
        for (LocalDate date = startDate;
        !date.isAfter(endDate);
        date = date.plusDays(1)) {
            try {
                DailyDTO record = 
                svc
                .retrieve(date);
                records
                .add(record);
            } catch (DateNotFoundException e) {
                //Skip dates without expense records
            }
        }

        //Reject the export if selected month has no records
        if (records.isEmpty()) 
            throw new MonthNotFoundException(month);

        return records;
    }

    /*
     * Generate an Excel workbook 
     * containing selected month's
     * daily expense records
     */
    private ByteArrayInputStream buildMonthlyReport
    (List<DailyDTO> records, String sheetName) {
        try (Workbook workbook = 
        new XSSFWorkbook();
        ByteArrayOutputStream output = 
        new ByteArrayOutputStream()) {
            //Create worksheet.
            Sheet sheet = 
            workbook
            .createSheet(sheetName);

            //Configure header style
            CellStyle headerStyle = 
            workbook
            .createCellStyle();

            Font headerFont = 
            workbook
            .createFont();
            headerFont
            .setBold(true);

            headerStyle
            .setFont(headerFont);
            headerStyle
            .setAlignment(HorizontalAlignment
            .CENTER);

            //Configure currency style
            CellStyle currencyStyle = 
            workbook
            .createCellStyle();

            currencyStyle
            .setDataFormat(
            workbook
            .createDataFormat()
            .getFormat("[$$-en-SG]#,##0.00"));
            currencyStyle
            .setAlignment(HorizontalAlignment
            .CENTER);

            //Define Excel columns in their intended order
            String[] columns = {
                "Date",
                "Income",
                "Cdac",
                "Debt",
                "Emergency",
                "SRS",
                "SSB",
                "Electricity",
                "Food",
                "Groceries",
                "Haircut",
                "Household",
                "Insurances",
                "Internet",
                "Medical",
                "Mobile",
                "Mortgage",
                "Parents",
                "Tax",
                "Transport",
                "Eating Out",
                "Holiday",
                "Recreational",
                "Shopping",
                "Sports",
                "Tech",
                "Tithes",
                "Wake",
                "Wedding"
            };

            //Create header row.
            Row header = 
            sheet
            .createRow(0);

            for (int i = 0; 
            i < columns.length; 
            i++) {
                Cell cell = 
                header
                .createCell(i);
                cell
                .setCellValue(columns[i]);
                cell
                .setCellStyle(headerStyle);
            }

            //Write each daily expense record into its own row
            for (int i = 0; 
            i < records.size(); 
            i++) {
                DailyDTO record = 
                records
                .get(i);
                Row row = 
                sheet
                .createRow(i + 1);

                //Write date into 1st column.
                row
                .createCell(0)
                .setCellValue(record
                .getDate()
                .toString());

                //Define financial values in column order
                BigDecimal[] values = {
                    record.getIncome(),
                    record.getCdac(),
                    record.getDebt(),
                    record.getEmergency(),
                    record.getSrs(),
                    record.getSsb(),
                    record.getElectricity(),
                    record.getFood(),
                    record.getGroceries(),
                    record.getHaircut(),
                    record.getHousehold(),
                    record.getInsurances(),
                    record.getInternet(),
                    record.getMedical(),
                    record.getMobile(),
                    record.getMortgage(),
                    record.getParents(),
                    record.getTax(),
                    record.getTransport(),
                    record.getEating_out(),
                    record.getHoliday(),
                    record.getRecreational(),
                    record.getShopping(),
                    record.getSports(),
                    record.getTech(),
                    record.getTithes(),
                    record.getWake(),
                    record.getWedding()
                };

                //Write each financial value into its corresponding cell
                for (int j = 0; 
                j < values.length; 
                j++) {
                    Cell cell = 
                    row
                    .createCell(j + 1);
                    BigDecimal value = 
                    values[j];

                    //Treat null financial values as zero.
                    cell.setCellValue(
                    value != null ? 
                    value.doubleValue() : 0.0);
                    cell
                    .setCellStyle(currencyStyle);
                }
            }

            //Automatically adjust each column's width
            for (int i = 0; 
            i < columns.length; 
            i++) {
                sheet
                .autoSizeColumn(i);
            }

            //Write workbook to output stream
            workbook
            .write(output);

            //Return the generated Excel file
            return new ByteArrayInputStream(output
            .toByteArray());

        } catch (Exception e) {
            // Wrap Excel generation failures in a custom exception.
            throw new ExportExcelFailedException(
            "Failed to generate expense records for " 
            + sheetName,
            e);
        }
    }

    /*
     * Export expense records 
     * for any selected month & year.
     */
    public ByteArrayInputStream exportMonthlyRecords 
    (YearMonth month) {
        //Retrieve records for requested month
        List <DailyDTO> records = 
        getDateRange(month);

        //Format month & year for worksheet name
        String monthYear =
        month
        .getMonth()
        .getDisplayName(TextStyle
        .FULL, 
        Locale
        .ENGLISH)
        + " "
        + month
        .getYear();

        //Generate & return Excel report
        return buildMonthlyReport(
        records,
        "Expense Records for " 
        + monthYear);
    }
}
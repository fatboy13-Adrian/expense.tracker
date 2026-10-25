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
import org.springframework.stereotype.Component;
import com.app.expense.tracker.dto.DailyDTO;
import com.app.expense.tracker.exception.ExportExcelFailedException;
import com.app.expense.tracker.exception.date.DateNotFoundException;
import com.app.expense.tracker.exception.month.MonthNotFoundException;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MonthlyReportSvc {
    private final DailyService svc;

    public List<DailyDTO> getDateRange 
    (YearMonth month) {
        List<DailyDTO> dto = 
        new ArrayList<>();

        /*
         * Determine 1st & last 
         * dates of selected month
         */
        LocalDate start = 
        month
        .atDay(1);
        LocalDate end = 
        month
        .atEndOfMonth();

        /*
         * Retrieve daily record & 
         * skip dates without records
         */
        for (LocalDate current = 
        start;
        !current
        .isAfter(end);
        current = 
        current
        .plusDays(1)) {
            try {
                dto
                .add(svc
                .retrieve(current));
            } catch (DateNotFoundException e) {
                //Skip dates without expense records
            }
        }

        /*
         * Throw an exception if no record/s 
         * exists for selected month
         */
        if (dto.isEmpty())
        throw new MonthNotFoundException(month);

        return dto;
    }

    private ByteArrayInputStream buildCurrentMonthReport 
    (List<DailyDTO> dto, 
    String sheetName) {
        try (Workbook workbook = 
        new XSSFWorkbook();
        ByteArrayOutputStream output = 
        new ByteArrayOutputStream()) {
            //Create worksheet
            Sheet sheet = 
            workbook
            .createSheet(sheetName);

            //Configure header style
            CellStyle headerStyle = 
            workbook.createCellStyle();
            Font font = workbook
            .createFont();
            font
            .setBold(true);
            headerStyle
            .setFont(font);
            headerStyle
            .setAlignment(HorizontalAlignment.CENTER);

            //Configure currency style
            CellStyle currencyStyle =
            workbook.createCellStyle();
            currencyStyle.setDataFormat(
            workbook.createDataFormat()
            .getFormat("[$$-en-SG]#,##0.00"));
            currencyStyle
            .setAlignment(HorizontalAlignment.CENTER);

            /*
             * Define excel columns in
             * their intended order
             */
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

            //Write each daily expense record into its own row
            for (int i = 0; i < dto.size(); i++) {
                DailyDTO dDto = 
                dto.get(i);

                /*
                 * Create excel header rows
                 */
                Row row = 
                sheet
                .createRow(i + 1);

                //Write date into column zero
                row.createCell(0)
                .setCellValue(dDto
                .getDate()
                .toString());

                BigDecimal[] values = {
                    dDto.getIncome(), 
                    dDto.getCdac(),
                    dDto.getDebt(),
                    dDto.getEmergency(),
                    dDto.getSrs(),
                    dDto.getSsb(),
                    dDto.getElectricity(),
                    dDto.getFood(),
                    dDto.getGroceries(),
                    dDto.getHaircut(),
                    dDto.getHousehold(),
                    dDto.getInsurances(),
                    dDto.getInternet(),
                    dDto.getMedical(),
                    dDto.getMobile(),
                    dDto.getMortgage(),
                    dDto.getParents(),
                    dDto.getTax(),
                    dDto.getTransport(),
                    dDto.getEating_out(),
                    dDto.getHoliday(),
                    dDto.getRecreational(),
                    dDto.getShopping(),
                    dDto.getSports(),
                    dDto.getTech(),
                    dDto.getTithes(),
                    dDto.getWake(),
                    dDto.getWedding()
                };

                //Write each financial value into its corresponding column
                for (int j = 0; j < values.length; j++) {
                    Cell cell = 
                    row
                    .createCell(j + 1);
                    cell
                    .setCellValue(values[j]
                    .doubleValue());
                    cell
                    .setCellStyle(currencyStyle);
                }
            }

            //Automatically adjust each column's width
            for (int i = 0; i < columns.length; i++) {
                sheet
                .autoSizeColumn(i);
            }

            //Write workbook to output stream
            workbook
            .write(output);

            //Return generated Excel file
            return new ByteArrayInputStream(output
            .toByteArray());
        } catch (Exception e) {
            throw new ExportExcelFailedException(
            "Failed to generate expense records",
            e);
        }
    }

    public ByteArrayInputStream exportMonthlyRecords 
    (YearMonth month) {
        /*
         * Retrieve records for
         * selected month
         */
        List<DailyDTO> dto = 
        getDateRange(month);

        /*
         * Formate month & yar
         * for worksheet name
         */
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
        return buildCurrentMonthReport(
        dto, 
        "Expense Records for " 
        + monthYear);
    }
}
package net.codebuff.intentio.parser;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;


/**
 * Created by deepankar on 11/11/14.
 */
public class Parser {
    private Context context = null;
    private File excel;
    // some list and variables which will be hold the data parsed from file.
    Parser(Context context){
        // will be used when we move to restricted file storage.
        this.context = context;
    }

   public Parser(){

    }

    // following methods shows a giant middle finger, everytime somene does careless implementation,be carefull
    public File open_file(){
        // the file will be hardcoded in some way later
        File dir = Environment.getExternalStorageDirectory(); // dangerous implementation but serves practical purpose
       // File dir = context.getFilesDir(); // maybe we can switch to this later.
        Log.i("external storage diretory", dir.getPath());
        excel = new File(dir, "test.xls");

        if(excel.isFile()) Log.i("file","file opened");
        return excel;
    }

    public String parse_excel() throws IOException {
        HSSFWorkbook wb = null;
        String content = "";
        excel = open_file();
        InputStream inputStream = new FileInputStream(excel);

        wb = new HSSFWorkbook(inputStream);

        if(wb != null){
            Log.e("excel","starting dump");
            for (int k = 0; k < wb.getNumberOfSheets(); k++) {
                HSSFSheet sheet = wb.getSheetAt(k);
                int rows = sheet.getPhysicalNumberOfRows();
                System.out.println("Sheet " + k + " \"" + wb.getSheetName(k) + "\" has " + rows
                        + " row(s).");
                content = content + "Sheet " + k + " \"" + wb.getSheetName(k) + "\" has " + rows
                        + " row(s).\n";
                for (int r = 0; r < rows; r++) {
                    HSSFRow row = sheet.getRow(r);
                    if (row == null) {
                        continue;
                    }

                    int cells = row.getPhysicalNumberOfCells();
                    System.out.println("\nROW " + row.getRowNum() + " has " + cells
                            + " cell(s).");
                    content = content + "\nROW " + row.getRowNum() + " has " + cells
                            + " cell(s).\n";
                    for (int c = 0; c < cells; c++) {
                        HSSFCell cell = row.getCell(c);
                        String value = null;
                        if (cell == null) {
                            continue;
                        }
                        switch (cell.getCellType()) {

                            case HSSFCell.CELL_TYPE_FORMULA:
                                value = "FORMULA value=" + cell.getCellFormula();
                                break;

                            case HSSFCell.CELL_TYPE_NUMERIC:
                                value = "NUMERIC value=" + cell.getNumericCellValue();
                                break;

                            case HSSFCell.CELL_TYPE_STRING:
                                value = "STRING value=" + cell.getStringCellValue();
                                break;

                            default:
                        }
                        System.out.println("CELL col=" + cell.getColumnIndex() + " VALUE="
                                + value);
                        content = content + "CELL col=" + cell.getColumnIndex() + " VALUE="
                                + value + "\n";
                    }
                }
            }
        }
        return content;
    }
}



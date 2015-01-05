package net.codebuff.intentio.parser;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import net.codebuff.intentio.helpers.Utilities;
import net.codebuff.intentio.preferences.PrefsManager;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;


/**
 * Created by deepankar on 11/11/14.
 *
 * this class opens the file containing data and parses it and then hands the data over to
 * datacurator
 */
public class Parser {
    private Context context = null;
    private File excel;
    PrefsManager prefs ;
    // some list and variables which will be hold the data parsed from file.
  public  Parser(Context context){
        // will be used when we move to restricted file storage.
        this.context = context;
    }

   public Parser(){

    }

    // following methods shows a giant middle finger, everytime someone does careless
    // implementation,be carefull
    public File open_file(){
        // the file will be hardcoded in some way later
        File dir = Environment.getExternalStorageDirectory(); // dangerous implementation but serves practical purpose
       // File dir = context.getFilesDir(); // maybe we can switch to this later.
        Log.i("external storage diretory", dir.getPath());
        excel = new File(dir, "test.xls");

            Log.i("file","file opened");
        return excel;
    }

    public String parse_excel() throws IOException {
        HSSFWorkbook wb = null;

        HashMap<Integer ,String> slots  = new HashMap<Integer, String>();

        boolean slots_found = false;

        String content = "";

        Calendar calendar = Calendar.getInstance();
        Log.e("day",calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault()));
        // find and open the xl file.
        excel = open_file();
        if(!excel.isFile()) {
            Toast.makeText(context,"Incorrect or no File not found", Toast.LENGTH_LONG).show();
            return "file not found";
        }else {
            prefs = new PrefsManager(context);
        }


        InputStream inputStream = new FileInputStream(excel);

        wb = new HSSFWorkbook(inputStream);

        if(wb != null){
            Log.e("excel","starting dump");
            for (int k = 0; k < wb.getNumberOfSheets(); k++) {
                HSSFSheet sheet = wb.getSheetAt(k);
                int rows = sheet.getPhysicalNumberOfRows();
                content = content + "Sheet " + k + " \"" + wb.getSheetName(k) + "\" has " + rows
                        + " row(s).\n";
                for (int r = 0; r < rows; r++) {
                    HSSFRow row = sheet.getRow(r);
                    if (row == null) {
                        continue;
                    }

                    int cells = row.getPhysicalNumberOfCells();

                    content = content + "\nROW " + row.getRowNum() + " has " + cells
                            + " cell(s).\n";
                    for (int c = 0; c < cells; c++) {
                        HSSFCell cell = row.getCell(c);
                        String value = null;
                        String cell_data ;
                        if (cell == null) {
                            continue;
                        }
                        switch (cell.getCellType()) {
                            case HSSFCell.CELL_TYPE_FORMULA:
                                value = "FORMULA value=" + cell.getCellFormula();
                                cell_data = cell.getCellFormula();
                                break;

                            case HSSFCell.CELL_TYPE_NUMERIC:
                                value = "NUMERIC value=" + cell.getNumericCellValue();
                                cell_data = Integer.toString((int)cell.getNumericCellValue());
                                break;

                            case HSSFCell.CELL_TYPE_STRING:
                                value = "STRING value=" + cell.getStringCellValue();
                                cell_data = cell.getStringCellValue();
                                break;

                            default: cell_data = null; break;
                        }
                        if(!slots_found){
                           // Log.e("cell no",Integer.toString(cell.getColumnIndex()));
                            if(cell_data != null && cell.getColumnIndex() > 0){
                                slots.put(cell.getColumnIndex(),  cell_data.trim());

                                if(cell_data.trim().contains("20:00")){
                                    slots_found = true;
                                    prefs.save_slots("slots",slots.values().toString());
                                    //System.out.println(slots.keySet().toString());
                                    //System.out.println(slots.values().toString());
                                    }
                                }
                        }

                        if(slots_found && 5 < row.getRowNum() && row.getRowNum() < 13){
                           // Log.e("row no",Integer.toString(row.getRowNum()));
                            if(cell_data == null){
                                cell_data = "";
                            }
                            if(slots.containsKey(cell.getColumnIndex())){
                                prefs.save_schedule(Utilities.get_day_name(row.getRowNum()),slots.get(cell.getColumnIndex()),cell_data.trim());
                            }
                            }
                        content = content + "CELL col=" + cell.getColumnIndex() + " VALUE="
                                + value + "\n";
                    }
                }
            }
        }
        return content;
    }
}



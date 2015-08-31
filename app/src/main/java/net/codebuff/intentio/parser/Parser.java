package net.codebuff.intentio.parser;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.webkit.MimeTypeMap;

import net.codebuff.intentio.helpers.Constants;
import net.codebuff.intentio.helpers.Utilities;
import net.codebuff.intentio.preferences.PrefsManager;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;


/**
 * Created by deepankar on 11/11/14.
 * <p/>
 * this class opens the file containing data and parses it and then hands the data over to
 * datacurator
 */
public class Parser {
    // some list and variables which will be hold the data parsed from file.
    private Context context = null;
    private File excel;
    PrefsManager prefs;
    Uri uri;
    ContentResolver cR;

    public Parser(Context context) {
        // will be used when we move to restricted file storage.
        this.context = context;
    }

    public Parser(Context context, Uri uri) {
        // will be used when we move to restricted file storage.
        this.context = context;
        this.uri = uri;
    }

    public Parser() {

    }

    // following methods shows a giant middle finger, everytime someone does careless implementation,be careful
    public InputStream getInputStream() {
        cR = context.getContentResolver();
        String uriStr = uri.toString();
        //Log.i("uri",  uriStr );
        if (uriStr.startsWith("file://")) {
            // contentResolver can't get type from file links
            // falling back to check for xls in string
            if (uriStr.endsWith(".xls")) {
                try {
                    return cR.openInputStream(uri);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    return null;
                }
            }
        } else if (uriStr.startsWith("content://")) {
            // contentResolver can get the type
            MimeTypeMap mime = MimeTypeMap.getSingleton();
            String type = mime.getExtensionFromMimeType(cR.getType(uri));
            if (type.equals("xls")) {
                try {
                    return cR.openInputStream(uri);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    return null;
                }
            }
        }
        return null;
    }

    public String parse_excel() throws IOException {
        HSSFWorkbook wb = null;

        HashMap<Integer, String> slots = new HashMap<Integer, String>();

        boolean slots_found = false;
        boolean slots_processed = false;
        boolean schedule_found = false;
        boolean last_day_row_found = false;
        boolean schedule_processed = false;
        int day_column_number = -1;
        boolean day_column_found = false;
        String cell_data = "";
        String day = "";

        String content = "";

        InputStream inputStream = getInputStream();

        if (inputStream == null) {
            return "file not found";
        }

        //Calendar calendar = Calendar.getInstance();
        // Log.e("day", calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault()));

        prefs = new PrefsManager(context);
        try { // although we check at inputstream, a xlsx may still creep in.
            wb = new HSSFWorkbook(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
            return "file not found";
        }


        if (wb != null) {
            Log.e("excel", "starting dump");
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
                        cell_data = "";
                        if (cell == null) {
                            continue;
                        }
                        switch (cell.getCellType()) {
                            case HSSFCell.CELL_TYPE_FORMULA:
                                value = "FORMULA value=" + cell.getCellFormula();
                                cell_data = cell.getCellFormula();
                                if (cell_data.contains(":") && cell_data.contains("-")) {
                                    slots_found = true;
                                }
                                if (cell_data.toLowerCase().contains("mon")) {
                                    schedule_found = true;
                                    day_column_number = c;
                                    day_column_found = true;
                                }
                                if (cell_data.toLowerCase().contains("sun")) {
                                    last_day_row_found = true;
                                }
                                if (day_column_found && c == day_column_number) {
                                    day = Utilities.get_day_name(cell_data);
                                }
                                break;

                            case HSSFCell.CELL_TYPE_NUMERIC:
                                value = "NUMERIC value=" + cell.getNumericCellValue();
                                cell_data = Integer.toString((int) cell.getNumericCellValue());
                                if (cell_data.contains(":") && cell_data.contains("-")) {
                                    slots_found = true;

                                }
                                if (cell_data.toLowerCase().contains("mon")) {
                                    schedule_found = true;
                                    day_column_number = c;
                                    day_column_found = true;
                                }
                                if (cell_data.toLowerCase().contains("sun")) {
                                    last_day_row_found = true;
                                }
                                if (day_column_found && c == day_column_number) {
                                    day = Utilities.get_day_name(cell_data);
                                }
                                break;

                            case HSSFCell.CELL_TYPE_STRING:
                                value = "STRING value=" + cell.getStringCellValue();
                                cell_data = cell.getStringCellValue();
                                // todo the follwing four conditions needs to be refined at every level possible
                                if (cell_data.contains(":") && cell_data.contains("-")) {
                                    slots_found = true;

                                }
                                if (cell_data.toLowerCase().contains("mon")) {
                                    schedule_found = true;
                                    day_column_number = c;
                                    day_column_found = true;
                                }
                                if (cell_data.toLowerCase().contains("sun")) {
                                    last_day_row_found = true;
                                }
                                if (day_column_found && c == day_column_number) {
                                    day = Utilities.get_day_name(cell_data);
                                }
                                break;

                            default:
                                cell_data = "";
                                break;
                        }

                        if (!slots_processed) {


                            if (slots_found) {
                                // Log.e("cell no",Integer.toString(cell.getColumnIndex()));

                                if (cell_data != null && cell_data != "" && cell.getColumnIndex() > 0) {
                                    slots.put(cell.getColumnIndex(), cell_data.trim());
                                }
                                //  System.out.println(cells-c);
                                //  System.out.print("row " + row.getRowNum() + ":" );
                                if (c == (cells - 1)) {
                                    slots_processed = true;
                                    prefs.save_slots("slots", slots.values().toString());
                                    //System.out.println(slots.keySet().toString());
                                    // System.out.println(slots.values().toString());
                                }
                            }
                        }

                        if (schedule_found && !schedule_processed && slots_processed) {

                            // Log.e("row no",Integer.toString(row.getRowNum()));
                            if (cell_data == null || cell_data.trim() == "") {
                                cell_data = Constants.empty_slot;
                            }
                            if (slots.containsKey(cell.getColumnIndex()) && !day.equals("invalid")) {
                                prefs.save_schedule(day, slots.get(cell.getColumnIndex()), cell_data.trim());
                            }

                            if (last_day_row_found && (c == (cells - 1))) {
                                schedule_processed = true;
                            }
                        }
                        content = content + "CELL col=" + cell.getColumnIndex() + " VALUE="
                                + value + "\n";
                    }

                    if (schedule_processed) break;

                }

                if (schedule_processed) break;

            }
        } else {
            content = "file not found";
        }
        //Log.i("content", content);
        return content;
    }


}



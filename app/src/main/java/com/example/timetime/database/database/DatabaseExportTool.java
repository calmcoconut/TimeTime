package com.example.timetime.database.database;

import android.database.Cursor;
import android.util.Log;
import com.example.timetime.database.TimeLogic;
import com.opencsv.CSVWriter;

import java.io.IOException;
import java.io.StringWriter;

public class DatabaseExportTool {
    public final static String exportName = "timeTimeExport";
    public final static String exportTable = "timeLog_table";
    public static final int CREATE_FILE = 1;
    private static final TimeLogic timeLogic = TimeLogic.newInstance();

    public static String exportDatabaseToCsv(AppDatabase database) {
        StringWriter stringWriter = new StringWriter();
        try {
            stringWriter = new StringWriter();
            CSVWriter csvWrite = new CSVWriter(stringWriter);
            Cursor curCSV = database.query("SELECT * FROM " + exportTable, null);
            csvWrite.writeNext(curCSV.getColumnNames());
            while (curCSV.moveToNext()) {
                String[] arrStr = new String[curCSV.getColumnCount()];
                for (int i = 0; i < curCSV.getColumnCount() - 1; i++)
                    if (i == 1 || i == 2) {
                        arrStr[i] = timeLogic.getLocalTimeFromDatabase(curCSV.getLong(i));
                    }
                    else {
                        arrStr[i] = curCSV.getString(i);
                    }
                csvWrite.writeNext(arrStr);
            }
            csvWrite.close();
            curCSV.close();
            Log.d("isSetting", "success");
        } catch (IOException e) {
            Log.d("isSetting", "error");
            e.printStackTrace();
        }
        return stringWriter.toString();
    }
}

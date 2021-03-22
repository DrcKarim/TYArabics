package com.BestofallHealthYoga.TamarinYoga.dbhelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.github.mikephil.charting.utils.Utils;
import com.google.android.gms.actions.SearchIntents;
import com.BestofallHealthYoga.TamarinYoga.babyphotos.BabyPhotos;
import com.BestofallHealthYoga.TamarinYoga.models.ChartData;
import com.BestofallHealthYoga.TamarinYoga.models.Report;
import com.BestofallHealthYoga.TamarinYoga.models.WeightStateModel;
import com.BestofallHealthYoga.TamarinYoga.utils.Constant;

import java.util.ArrayList;
import java.util.List;

public class BaseAppDB extends SQLiteOpenHelper {
    private static final String COL_1 = "id";
    private static final String COL_2 = "title";
    private static final String COL_3 = "imageName";
    private static final String COL_4 = "date";
    private static final String COL_5 = "weight";
    private static final String COL_6 = "waistline";
    public static final String DB_NAME = "LoseWeightIN_30Days.db";

    public static final String KEY_ACTIVITY_INFROMATION_ID = "ActivityID";
    public static final String KEY_DAY_WISE_ACTIVITY_ACTIVITY_ID = "ActivityID";
    public static final String KEY_DAY_WISE_ACTIVITY_DAY_ID = "DayID";
    public static final String KEY_DAY_WISE_ACTIVITY_TYPE_ID = "TypeId";
    public static final String KEY_TYPE_ACTIVITY_INFORMATION = "ActivityInformation";
    public static final String KEY_TYPE_ACTIVITY_NAME = "ActivityName";
    public static final String KEY_TYPE_FINISH_OR_NOT = "FinishActivity";
    public static final String KEY_TYPE_GROUP_TEXT = "GroupText";
    public static final String KEY_TYPE_ID = "TypeId";
    public static final String KEY_TYPE_INTERVAL_TIME = "IntervalTime";
    public static final String KEY_TYPE_NAME = "TypeName";
    public static final String KEY_TYPE_ORDER = "Order_of_Activity";
    public static final String KEY_TYPE_TIPS = "Tips";
    public static final String KEY_TYPE_TOTATL_SET = "TotalSet";
    public static final String KEYDAYID = "Id";
    public static final String TABLE_ACTIVITY_INFORMATION = "ActivityInformation";
    public static final String TABLE_DAY_WISE_ACTIVITY = "DayWiseActivity";
    public static final String TABLE_EXERCISE_TYPE = "ExerciseType";
    private static final String TABLE_NAME_EXERCISE_PHOTO = "tbl_exercisePhoto";
    private static final String TABLE_NAME_REPORT = "report";
    String blobtype = " BLOB";
    String createTable = "CREATE TABLE ";
    String dropTable = "DROP TABLE IF EXISTS ";
    String integerType = " INTEGER";
    String numericType = " NUMERIC";
    String selectStarFrom = "SELECT  * FROM ";
    String textType = " TEXT";

    public BaseAppDB(Context context) {
        super(context, DB_NAME, (SQLiteDatabase.CursorFactory) null, 1);
    }

    public void onCreate(SQLiteDatabase sQLiteDatabase) {
        sQLiteDatabase.execSQL(createDayWiseActivityTable());
        sQLiteDatabase.execSQL(createActivityInformationTable());
        sQLiteDatabase.execSQL(createExerciseTypeTable());
        sQLiteDatabase.execSQL("create table tbl_exercisePhoto (id TEXT PRIMARY KEY,title TEXT,imageName TEXT)");
        sQLiteDatabase.execSQL("create table report (date Long PRIMARY KEY,weight double,waistline double)");
    }

    public void addReportData(int i, long j, double d) {
        SQLiteDatabase writableDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_4, Long.valueOf(j));
        if (i == 1) {
            contentValues.put(COL_5, Double.valueOf(d));
            contentValues.put(COL_6, 0);
        } else {
            contentValues.put(COL_5, 0);
            contentValues.put(COL_6, Double.valueOf(d));
        }
        writableDatabase.insert(TABLE_NAME_REPORT, (String) null, contentValues);
    }

    public Report getRepostWeightWaistetLine(int i) {
        SQLiteDatabase writableDatabase = getWritableDatabase();
        String str = i == 1 ? COL_5 : COL_6;
        Cursor rawQuery = writableDatabase.rawQuery("select max(date) maxdate, " + str + " from  report where " + str + " > 0.0;", (String[]) null);
        Report report = new Report();
        while (rawQuery.moveToNext()) {
            long j = rawQuery.getLong(0);
            double d = rawQuery.getDouble(1);
            if (i == 1) {
                report = new Report(j, d, Utils.DOUBLE_EPSILON);
            } else {
                report = new Report(j, Utils.DOUBLE_EPSILON, d);
            }
        }
        return report;
    }

    public List<Report> getReportData(long j) {
        List<Report> arrayList = new ArrayList<>();
        SQLiteDatabase writableDatabase = getWritableDatabase();
        Cursor rawQuery = writableDatabase.rawQuery("SELECT * FROM report where date(date/1000,'unixepoch','localtime') = date(" + j + "/1000,'unixepoch','localtime')", (String[]) null);
        if (rawQuery.moveToFirst()) {
            do {
                Report report = new Report();
                report.setDate(rawQuery.getLong(0));
                report.setWeight(rawQuery.getDouble(1));
                report.setWeistline(rawQuery.getDouble(2));
                arrayList.add(report);
            } while (rawQuery.moveToNext());
        }
        rawQuery.close();
        return arrayList;
    }

    public List<Report> getReportData(boolean z) {
        List<Report> arrayList = new ArrayList<>();
        String str = !z ? COL_5 : COL_6;
        SQLiteDatabase writableDatabase = getWritableDatabase();
        Cursor rawQuery = writableDatabase.rawQuery("select date," + str + " from report where round(" + str + ",2) > 0.00 order by date desc", (String[]) null);
        if (rawQuery.moveToFirst()) {
            do {
                Report report = new Report();
                report.setDate(rawQuery.getLong(0));
                report.setWeight(rawQuery.getDouble(1));
                arrayList.add(report);
            } while (rawQuery.moveToNext());
        }
        rawQuery.close();
        return arrayList;
    }

    public void updateReportData(int i, long j, double d) {
        SQLiteDatabase writableDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_4, Long.valueOf(j));
        if (i == 1) {
            contentValues.put(COL_5, Double.valueOf(d));
        } else {
            contentValues.put(COL_6, Double.valueOf(d));
        }
        writableDatabase.update(TABLE_NAME_REPORT, contentValues, "date = ?", new String[]{String.valueOf(j)});
    }

    public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
        sQLiteDatabase.execSQL(this.dropTable + "ActivityInformation");
        sQLiteDatabase.execSQL(this.dropTable + TABLE_DAY_WISE_ACTIVITY);
        sQLiteDatabase.execSQL("DROP TABLE IF EXISTS tbl_exercisePhoto");
        sQLiteDatabase.execSQL("DROP TABLE IF EXISTS report");
        onCreate(sQLiteDatabase);
    }

    private String createDayWiseActivityTable() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.createTable + TABLE_DAY_WISE_ACTIVITY + " ( ");
        sb.append(KEYDAYID + this.integerType + " primary key AUTOINCREMENT ,");
        sb.append(KEY_DAY_WISE_ACTIVITY_DAY_ID + this.integerType + ",");
        sb.append("ActivityID" + this.integerType + ",");
        sb.append("TypeId" + this.integerType + ",");
        sb.append(KEY_TYPE_TOTATL_SET + this.integerType + ",");
        sb.append(KEY_TYPE_ORDER + this.integerType + ",");
        sb.append(KEY_TYPE_FINISH_OR_NOT + this.integerType + "");
        sb.append(" ) ");
        Log.d(SearchIntents.EXTRA_QUERY, sb.toString());
        return sb.toString();
    }

    private String createActivityInformationTable() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.createTable + "ActivityInformation" + " ( ");
        sb.append("ActivityID" + this.integerType + " primary key,");
        sb.append(KEY_TYPE_ACTIVITY_NAME + this.textType + ",");
        sb.append("ActivityInformation" + this.textType + ",");
        sb.append(KEY_TYPE_INTERVAL_TIME + this.numericType + ",");
        sb.append(KEY_TYPE_TIPS + this.textType + ",");
        sb.append(KEY_TYPE_GROUP_TEXT + this.textType + "");
        sb.append(" ) ");
        return sb.toString();
    }

    private String createExerciseTypeTable() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.createTable + TABLE_EXERCISE_TYPE + " ( ");
        sb.append("TypeId" + this.integerType + " primary key AUTOINCREMENT ,");
        sb.append(KEY_TYPE_NAME + this.textType + "");
        sb.append(" ) ");
        return sb.toString();
    }

    public void addExerciseData(BabyPhotos babyPhotos) {
        SQLiteDatabase writableDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1, babyPhotos.getId());
        contentValues.put(COL_2, babyPhotos.getTitle());
        contentValues.put(COL_3, babyPhotos.getImageName());
        writableDatabase.insert(TABLE_NAME_EXERCISE_PHOTO, (String) null, contentValues);
    }

    public boolean updateExerciseImage(String str, String str2) {
        SQLiteDatabase writableDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1, str);
        contentValues.put(COL_3, str2);
        writableDatabase.update(TABLE_NAME_EXERCISE_PHOTO, contentValues, "ID = ?", new String[]{str});
        return true;
    }

    public List<BabyPhotos> getPhotos() {
        SQLiteDatabase writableDatabase = getWritableDatabase();

        List<BabyPhotos> arrayList =new ArrayList<>();
        Cursor rawQuery = writableDatabase.rawQuery("select * from tbl_exercisePhoto", (String[]) null);
        while (rawQuery.moveToNext()) {
            arrayList.add(new BabyPhotos(rawQuery.getString(rawQuery.getColumnIndex(COL_1)), rawQuery.getString(rawQuery.getColumnIndex(COL_2)), rawQuery.getString(rawQuery.getColumnIndex(COL_3))));
        }
        return arrayList;
    }

    public List<ChartData> getAllWeight(String str) {
        List<ChartData> arrayList = new ArrayList<>();
        SQLiteDatabase readableDatabase = getReadableDatabase();
        String str2 = str.equals(COL_5) ? COL_5 : COL_6;
        Cursor rawQuery = readableDatabase.rawQuery("select cast (strftime('%d',date(`date`/1000,'unixepoch','localtime')) as int) || ' ' || case strftime('%m',date(`date`/1000,'unixepoch','localtime')) when '01' then 'Jan' when '02' then 'Feb' when '03' then 'Mar' when '04' then 'Apr' when '05' then 'May' when '06' then 'Jun' when '07' then 'Jul' when '08' then 'Aug' when '09' then 'Sep' when '10' then 'Oct' when '11' then 'Nov' when '12' then 'Dec' else '' end w_days, " + str2 + "  \nfrom report \nwhere round(" + str2 + ",2) > 0.00 \ngroup by date(date/1000,'unixepoch','localtime') order by date desc", (String[]) null);
        if (rawQuery != null) {
            while (rawQuery.moveToNext()) {
                ChartData chartData = new ChartData();
                chartData.setTimemille(rawQuery.getString(0));
                chartData.setMeasvalue(Double.parseDouble(Constant.decimalFormatNew(rawQuery.getDouble(1))));
                arrayList.add(chartData);
            }
            rawQuery.close();
        }
        return arrayList;
    }

    public WeightStateModel getWeightStates(String str) {
        WeightStateModel weightStateModel = new WeightStateModel();
        SQLiteDatabase readableDatabase = getReadableDatabase();
        String str2 = str.equals(COL_5) ? COL_5 : COL_6;
        Cursor rawQuery = readableDatabase.rawQuery("select ifnull(max(" + str2 + "),'-')maxWeight,ifnull(min(" + str2 + "),'-')minWeight,ifnull((select " + str2 + " from report where date =  (select max(date) from report where round(" + str2 + ",2) > 0.00)),'-') currentWeight from report where round(" + str2 + ",2) > 0.00 \n", (String[]) null);
        if (rawQuery != null) {
            while (rawQuery.moveToNext()) {
                weightStateModel.setMax(rawQuery.getDouble(0));
                weightStateModel.setMin(rawQuery.getDouble(1));
                weightStateModel.setCurrent(rawQuery.getDouble(2));


            }



        }
        return weightStateModel;
    }
}

package com.BestofallHealthYoga.TamarinYoga.dbhelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.google.android.gms.actions.SearchIntents;
import com.BestofallHealthYoga.TamarinYoga.models.DayWiseActivity;
import com.BestofallHealthYoga.TamarinYoga.models.DayWiseProgress;
import com.BestofallHealthYoga.TamarinYoga.models.ExerciseInformation;
import com.BestofallHealthYoga.TamarinYoga.models.MyTrainingModel;
import com.BestofallHealthYoga.TamarinYoga.models.UserExercise;
import com.BestofallHealthYoga.TamarinYoga.utils.Constant;

import java.util.ArrayList;
import java.util.List;

public class DemoDB extends BaseAppDB {
    DayWiseProgress dayWiseProgress;

    public DemoDB(Context context) {
        super(context);
    }


    public long addDayWiseActivityDetails(int i, int i2, int i3, int i4, int i5, int i6) {
        try {
            SQLiteDatabase writableDatabase = getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(BaseAppDB.KEY_DAY_WISE_ACTIVITY_DAY_ID, Integer.valueOf(i));
            contentValues.put("ActivityID", Integer.valueOf(i2));
            contentValues.put("TypeId", Integer.valueOf(i3));
            contentValues.put(BaseAppDB.KEY_TYPE_TOTATL_SET, Integer.valueOf(i4));
            contentValues.put(BaseAppDB.KEY_TYPE_ORDER, Integer.valueOf(i5));
            contentValues.put(BaseAppDB.KEY_TYPE_FINISH_OR_NOT, Integer.valueOf(i6));
            return writableDatabase.insert(BaseAppDB.TABLE_DAY_WISE_ACTIVITY, (String) null, contentValues);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public long addActivityInformationDetails(int i, String str, String str2, int i2, String str3, String str4) {
        try {
            SQLiteDatabase writableDatabase = getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("ActivityID", Integer.valueOf(i));
            contentValues.put(BaseAppDB.KEY_TYPE_ACTIVITY_NAME, str);
            contentValues.put("ActivityInformation", str2);
            contentValues.put(BaseAppDB.KEY_TYPE_INTERVAL_TIME, Integer.valueOf(i2));
            contentValues.put(BaseAppDB.KEY_TYPE_TIPS, str3);
            contentValues.put(BaseAppDB.KEY_TYPE_GROUP_TEXT, str4);
            return writableDatabase.insert("ActivityInformation", (String) null, contentValues);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public long addExerciseTypeDetailsFirsTime(int i, String str) {
        try {
            SQLiteDatabase writableDatabase = getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("TypeId", Integer.valueOf(i));
            contentValues.put(BaseAppDB.KEY_TYPE_NAME, str);
            return writableDatabase.insert(BaseAppDB.TABLE_EXERCISE_TYPE, (String) null, contentValues);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public long addExercieDetails(String str) {
        try {
            SQLiteDatabase writableDatabase = getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(BaseAppDB.KEY_TYPE_NAME, str);
            return writableDatabase.insert(BaseAppDB.TABLE_EXERCISE_TYPE, (String) null, contentValues);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public ArrayList<DayWiseActivity> getDayWiseActivity(int i, int i2) {
        ArrayList<DayWiseActivity> arrayList;
        try {
            arrayList = new ArrayList<>();


                String str = " select DayWiseActivity.DayID,DayWiseActivity.Id,DayWiseActivity.ActivityID,DayWiseActivity.TotalSet,DayWiseActivity.Order_of_Activity,DayWiseActivity.FinishActivity,ActivityInformation.ActivityName,ActivityInformation.ActivityInformation,ActivityInformation.IntervalTime  FROM DayWiseActivity left join ActivityInformation ON DayWiseActivity.ActivityID = ActivityInformation.ActivityID where DayWiseActivity.DayID =  " + i + " and " + BaseAppDB.TABLE_DAY_WISE_ACTIVITY + "." + "TypeId" + " =  " + i2;
                Cursor rawQuery = getReadableDatabase().rawQuery(str, (String[]) null);
                if (rawQuery != null) {
                    Log.d(SearchIntents.EXTRA_QUERY, str);
                    if (rawQuery.moveToFirst()) {
                        do {
                            DayWiseActivity dayWiseActivity = new DayWiseActivity();
                            dayWiseActivity.setDayid(rawQuery.getInt(rawQuery.getColumnIndex(BaseAppDB.KEY_DAY_WISE_ACTIVITY_DAY_ID)));
                            dayWiseActivity.setId(rawQuery.getInt(rawQuery.getColumnIndex(BaseAppDB.KEYDAYID)));
                            dayWiseActivity.setActivityid(rawQuery.getInt(rawQuery.getColumnIndex("ActivityID")));
                            dayWiseActivity.setTypeid(i2);
                            dayWiseActivity.setTotalset(rawQuery.getInt(rawQuery.getColumnIndex(BaseAppDB.KEY_TYPE_TOTATL_SET)));
                            dayWiseActivity.setOrderofactivity(rawQuery.getInt(rawQuery.getColumnIndex(BaseAppDB.KEY_TYPE_ORDER)));
                            dayWiseActivity.setIsFinishActivity(rawQuery.getInt(rawQuery.getColumnIndex(BaseAppDB.KEY_TYPE_FINISH_OR_NOT)));
                            dayWiseActivity.setActivityName(rawQuery.getString(rawQuery.getColumnIndex(BaseAppDB.KEY_TYPE_ACTIVITY_NAME)));
                            dayWiseActivity.setActivityInformation(rawQuery.getString(rawQuery.getColumnIndex("ActivityInformation")));
                            dayWiseActivity.setIntervalTime(rawQuery.getLong(rawQuery.getColumnIndex(BaseAppDB.KEY_TYPE_INTERVAL_TIME)));
                            arrayList.add(dayWiseActivity);
                        } while (rawQuery.moveToNext());


                    }
                }

        } catch (Exception e2) {

            arrayList = null;
            e2.printStackTrace();
            return arrayList;
        }
        return arrayList;
    }

    public List<DayWiseProgress> getDayList(int i) {
        List<DayWiseProgress> arrayList = new ArrayList<>();
        Cursor rawQuery = getReadableDatabase().rawQuery("  select dayid,(case when (sum(case when finishactivity = 0 then 1 else 0 end) > 0) then 1 else case when (activityid = 0 and finishactivity = 1) then 3 else 2 end end) result from DayWiseActivity where typeid =" + i + " group by dayid", (String[]) null);


        if (rawQuery != null && rawQuery.moveToFirst()) {
            do {
                DayWiseProgress dayWiseProgress2 = new DayWiseProgress();
                dayWiseProgress2.setDay(rawQuery.getInt(0));
                dayWiseProgress2.setComplteDay(rawQuery.getInt(1));
                arrayList.add(dayWiseProgress2);
            } while (rawQuery.moveToNext());
        }


        return arrayList;
    }

    public int getProgressOfType(int i) {
        SQLiteDatabase readableDatabase = getReadableDatabase();
        String str = "select  Round((sum(s.result)/30.00)*100) progress from(select case when (sum(case when finishactivity = 0 then 1 else 0 end) > 0) then 0 else 1 end result from DayWiseActivity where typeid = " + i + " group by dayid)s";
        Log.d("Log strquery", "" + str);
        Cursor rawQuery = readableDatabase.rawQuery(str, (String[]) null);
        int i2 = 0;
        if (rawQuery != null) {
            Log.d(SearchIntents.EXTRA_QUERY, str);
            if (rawQuery.moveToFirst()) {
                i2 = rawQuery.getInt(0);
            }


        }
        return i2;
    }

    public List<Integer> getProgressOfTypeWithCompleteDay(int i) {
        SQLiteDatabase readableDatabase = getReadableDatabase();
        String str = "select  30 - sum(s.result)completed, Round((sum(s.result)/30.00)*100) progress from(select case when (sum(case when finishactivity = 0 then 1 else 0 end) > 0) then 0 else 1 end result from DayWiseActivity where typeid = " + i + " group by dayid)s";
        Log.d("Log strquery", "" + str);
        Cursor rawQuery = readableDatabase.rawQuery(str, (String[]) null);
        ArrayList<Integer> arrayList = new ArrayList<>();
        if (rawQuery != null) {
            Log.d(SearchIntents.EXTRA_QUERY, str);
            if (rawQuery.moveToFirst()) {
                arrayList.add(0, Integer.valueOf(rawQuery.getInt(0)));
                arrayList.add(1, Integer.valueOf(rawQuery.getInt(1)));
            }


        }
        return arrayList;
    }

    public int getPreviousDayCompleteOrNot(int i, int i2) {
        String str = "select case when (sum(case when finishactivity = 0 then 1 else 0 end) > 0) then 0 else 1 end result from DayWiseActivity where dayid = " + i + " and typeid =  " + i2;
        Cursor rawQuery = getReadableDatabase().rawQuery(str, (String[]) null);
        int i3 = 0;
        if (rawQuery != null) {
            Log.d(SearchIntents.EXTRA_QUERY, str);
            if (rawQuery.moveToFirst()) {
                i3 = rawQuery.getInt(0);
            }


        }
        return i3;
    }

    public List<MyTrainingModel> getAllMyTraining() {
        ArrayList<MyTrainingModel> arrayList;
        Exception e;
        try {
            arrayList = new ArrayList<>();

            Cursor rawQuery = getReadableDatabase().rawQuery(" select  e.TypeId ,   e.TypeName ,  count(d. TypeId) from ExerciseType e  join DayWiseActivity d  on e.TypeId =  d.TypeId where  e.TypeId not in (2) group by e.TypeId", (String[]) null);
            if (rawQuery != null && rawQuery.moveToFirst()) {
                do {
                    MyTrainingModel myTrainingModel = new MyTrainingModel();
                    myTrainingModel.setTypeid(rawQuery.getInt(rawQuery.getColumnIndex("TypeId")));
                    myTrainingModel.setTypename(rawQuery.getString(rawQuery.getColumnIndex(BaseAppDB.KEY_TYPE_NAME)));
                    myTrainingModel.setTotalExercise(rawQuery.getInt(2));
                    arrayList.add(myTrainingModel);
                } while (rawQuery.moveToNext());


            }


        } catch (Exception e3) {
            arrayList = null;
            e = e3;
            e.printStackTrace();
            return arrayList;
        }
        return arrayList;
    }

    public long finishActivity(int i) {
        try {
            SQLiteDatabase writableDatabase = getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(BaseAppDB.KEY_TYPE_FINISH_OR_NOT, Integer.valueOf(Constant.ACTIVITYFINISH));
            return (long) writableDatabase.update(BaseAppDB.TABLE_DAY_WISE_ACTIVITY, contentValues, "Id =?", new String[]{String.valueOf(i)});
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public List<UserExercise> getAllUserExercises() {
        ArrayList<UserExercise> arrayList;
        Exception e;
        try {
            arrayList = new ArrayList<>();

                Cursor rawQuery = getReadableDatabase().rawQuery(" select ActivityID,ActivityName from ActivityInformation", (String[]) null);
                Log.d(SearchIntents.EXTRA_QUERY, "select ActivityID,ActivityName from ActivityInformation");
                if (rawQuery.moveToFirst()) {
                    do {
                        UserExercise userExercise = new UserExercise();
                        userExercise.setActivityId(rawQuery.getInt(rawQuery.getColumnIndex("ActivityID")));
                        userExercise.setExerciseName(rawQuery.getString(rawQuery.getColumnIndex(BaseAppDB.KEY_TYPE_ACTIVITY_NAME)));
                        arrayList.add(userExercise);
                    } while (rawQuery.moveToNext());


                }

        } catch (Exception e3) {
            Exception exc = e3;
            arrayList = null;
            e = exc;
            e.printStackTrace();
            return arrayList;
        }
        return arrayList;
    }

    public long setAllReset(int i) {
        try {
            SQLiteDatabase writableDatabase = getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(BaseAppDB.KEY_TYPE_FINISH_OR_NOT, Integer.valueOf(Constant.ACTIVIY_NOT_FINISH));
            return (long) writableDatabase.update(BaseAppDB.TABLE_DAY_WISE_ACTIVITY, contentValues, "TypeId =?", new String[]{String.valueOf(i)});
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public List<ExerciseInformation> getExerciseInfoForAll() {
        ArrayList<ExerciseInformation> arrayList;
        Exception e;
        try {
            arrayList = new ArrayList<>();

                Cursor rawQuery = getReadableDatabase().rawQuery(" select ActivityID,ActivityName , ActivityInformation from ActivityInformation", (String[]) null);
                Log.d(SearchIntents.EXTRA_QUERY, "select ActivityID,ActivityName from ActivityInformation");
                if (rawQuery.moveToFirst()) {
                    do {
                        ExerciseInformation exerciseInformation = new ExerciseInformation();
                        exerciseInformation.setActivityId(rawQuery.getInt(rawQuery.getColumnIndex("ActivityID")));
                        exerciseInformation.setExerciseName(rawQuery.getString(rawQuery.getColumnIndex(BaseAppDB.KEY_TYPE_ACTIVITY_NAME)));
                        exerciseInformation.setExerciseInformation(rawQuery.getString(rawQuery.getColumnIndex("ActivityInformation")));
                        arrayList.add(exerciseInformation);
                    } while (rawQuery.moveToNext());

                }


        } catch (Exception e3) {
            Exception exc = e3;
            arrayList = null;
            e = exc;
            e.printStackTrace();
            return arrayList;
        }
        return arrayList;
    }

    public long clearAllActivity(int i) {
        try {
            SQLiteDatabase writableDatabase = getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(BaseAppDB.KEY_TYPE_FINISH_OR_NOT, Integer.valueOf(Constant.ACTIVIY_NOT_FINISH));
            return (long) writableDatabase.update(BaseAppDB.TABLE_DAY_WISE_ACTIVITY, contentValues, "DayID =?", new String[]{String.valueOf(i)});
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public long deleteMyTrainingFromADayWiseActivityDetails(long j) {
        return (long) getWritableDatabase().delete(BaseAppDB.TABLE_DAY_WISE_ACTIVITY, "TypeId =?", new String[]{String.valueOf(j)});
    }

    public long deleteFromExerciseTypeDetails(long j) {
        return (long) getWritableDatabase().delete(BaseAppDB.TABLE_EXERCISE_TYPE, "TypeId =?", new String[]{String.valueOf(j)});
    }

    public long updateTitleForExerciseTypeDetails(String str, int i) {
        try {
            SQLiteDatabase writableDatabase = getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(BaseAppDB.KEY_TYPE_NAME, str);
            return (long) writableDatabase.update(BaseAppDB.TABLE_EXERCISE_TYPE, contentValues, "TypeId =? ", new String[]{String.valueOf(i)});
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
}

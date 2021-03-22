package com.BestofallHealthYoga.TamarinYoga.utils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import com.github.mikephil.charting.utils.Utils;
import com.BestofallHealthYoga.TamarinYoga.babyphotos.PhotoConstans;
import com.BestofallHealthYoga.TamarinYoga.dbhelper.BaseAppDB;
import com.BestofallHealthYoga.TamarinYoga.receiver.DailyAlarmReceiver;

import java.io.File;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static androidx.core.app.NotificationCompat.CATEGORY_ALARM;

public class Constant {
    public static final int ACTIVITYFINISH = 1;
    public static final int ACTIVIY_NOT_FINISH = 0;
    public static final int ADD_TRAINING_CODE = 5001;
    public static final int ALL_ACTIVITY_CODE = 6001;
    public static final int CODE_DELETE = 1;
    public static final int CODE_UPDATE = 2;


    public static final String PICTURE_STORE_DIR_NAME = "ExercisePicture";


    public static final int DAYWISEACTIVITYFINISH = 4001;
    public static final int DAY_WISECODE = PhotoConstans.PHOTO_EDITOR_CODE;
    public static final int DAY_WISE_EXERCISE_CODE = 2001;
    public static final int FROM_DAY_INFORMATION = 101;
    public static final int FROM_MAIN_CODE = 601;
    public static final int FROM_REST_ACTIVIRTY_CODE = 501;

    public static final int MEDIUM = 2;

    public static final int REQUEST_PERM_FILE = 1053;
    public static final int REST_OR_INTRO_TOTAL_MILISECONDS = 15000;
    public static final int TICKMILLI = 1000;
    public static final int TOTAL_EXERCISE_DAY = 31;
    public static final int TOTAL_SET_FOR_ACTIVITY_CODE = 7001;

    public static final String ASSETPATH = "file:///android_asset/";
    public static final DecimalFormat decimalFormat = new DecimalFormat("#0.00");


    public static boolean getacitivityminuison(int i) {
        return i == 2 || i == 3 || i == 4 || i == 5 || i == 7 || i == 8 || i == 9 || i == 13 || i == 17 || i == 18 || i == 19 || i == 20 || i == 21 || i == 22 || i == 29 || i == 30 || i == 34 || i == 35 || i == 36 || i == 37 || i == 38 || i == 44 || i == 48 || i == 49 || i == 50 || i == 53 || i == 59 || i == 70 || i == 72 || i == 73 || i == 74 || i == 75 || i == 78 || i == 79 || i == 80 || i == 83 || i == 84 || i == 87;

    }

    public static String getUniqueId() {
        return String.valueOf(UUID.randomUUID());
    }

    public static String getSingleDrawableImage(Context context,int i) {
        if (i == 1) {
            try {
                return ASSETPATH + "yoga_adho_mukha_sukhasana_b.png";
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        } else if (i == 2) {
            return ASSETPATH + "yoga_adho_mukha_svanasana_a.png";
        } else if (i == 3) {
            return ASSETPATH + "yoga_anahatasana_a.png";
        } else if (i == 4) {
            return ASSETPATH + "yoga_anjaneyasana_left_a.png";
        } else if (i == 5) {
            return ASSETPATH + "yoga_anjaneyasana_right_a.png";
        } else if (i == 6) {
            return ASSETPATH + "yoga_apanasana_b.png";
        } else if (i == 7) {
            return ASSETPATH + "yoga_ardha_matsyendrasana_left_a.png";
        } else if (i == 8) {
            return ASSETPATH + "yoga_ardha_matsyendrasana_right_a.png";
        } else if (i == 9) {
            return ASSETPATH + "yoga_ardha_navasana_a.png";
        } else if (i == 10) {
            return ASSETPATH + "yoga_ardha_purvottanasana_b.png";
        } else if (i == 11) {
            return ASSETPATH + "yoga_ardha_salabhasana_left_b.png";
        } else if (i == 12) {
            return ASSETPATH + "yoga_ardha_salabhasana_right_b.png";
        } else if (i == 13) {
            return ASSETPATH + "yoga_ardha_uttanasana_a.png";
        } else if (i == 14) {
            return ASSETPATH + "yoga_baddha_konasana_b.png";
        } else if (i == 15) {
            return ASSETPATH + "yoga_balasana_b.png";
        } else if (i == 16) {
            return ASSETPATH + "yoga_chakki_chalanasana_b.png";
        } else if (i == 17) {
            return ASSETPATH + "yoga_dandayamna_bharmanasana_left_b.png";
        } else if (i == 18) {
            return ASSETPATH + "yoga_dandayamna_bharmanasana_right_b.png";
        } else if (i == 19) {
            return ASSETPATH + "yoga_eka_pada_rajakapotasana_left_a.png";
        } else if (i == 20) {
            return ASSETPATH + "yoga_eka_pada_rajakapotasana_right_a.png";
        } else if (i == 21) {
            return ASSETPATH + "yoga_gomukhasana_left_a.png";
        } else if (i == 22) {
            return ASSETPATH + "yoga_gomukhasana_right_a.png";
        } else if (i == 23) {
            return ASSETPATH + "yoga_janu_sirsasana_left_b.png";
        } else if (i == 24) {
            return ASSETPATH + "yoga_janu_sirsasana_right_b.png";
        } else if (i == 25) {
            return ASSETPATH + "yoga_jathara_parivartanasana_a.png";
        } else if (i == 26) {
            return ASSETPATH + "yoga_jhulana_lurhakanasana_a.png";
        } else if (i == 27) {
            return ASSETPATH + "yoga_kapalabhati_pranayama_a.png";
        } else if (i == 28) {
            return ASSETPATH + "yoga_katichakrasana_b.png";
        } else if (i == 29) {
            return ASSETPATH + "yoga_makara_adho_mukha_svanasana_a.png";
        } else if (i == 30) {
            return ASSETPATH + "yoga_malasana_a.png";
        } else if (i == 31) {
            return ASSETPATH + "yoga_manibandha_chakrasana_b.png";
        } else if (i == 32) {
            return ASSETPATH + "yoga_marjaryasana_c.png";
        } else if (i == 33) {
            return ASSETPATH + "yoga_matsyasana_b.png";
        } else if (i == 34) {
            return ASSETPATH + "yoga_namaskara_a.webp";
        } else if (i == 35) {
            return ASSETPATH + "yoga_natarajasana_left_a.png";
        } else if (i == 36) {
            return ASSETPATH + "yoga_natarajasana_right_a.png";
        } else if (i == 37) {
            return ASSETPATH + "yoga_parivritta_sukasana_left_a.png";
        } else if (i == 38) {
            return ASSETPATH + "yoga_parivritta_sukasana_right_a.png";
        } else if (i == 39) {
            return ASSETPATH + "yoga_parsva_sukhasana_left_b.png";
        } else if (i == 40) {
            return ASSETPATH + "yoga_parsva_sukhasana_right_b.png";
        } else if (i == 41) {
            return ASSETPATH + "yoga_parsva_tadasana_b.png";
        } else if (i == 42) {
            return ASSETPATH + "yoga_parsvottanasana_left_a.png";
        } else if (i == 43) {
            return ASSETPATH + "yoga_parsvottanasana_right_b.png";
        } else if (i == 44) {
            return ASSETPATH + "yoga_parvatasana_a.png";
        } else if (i == 45) {
            return ASSETPATH + "yoga_paschimottanasana_left_b.png";
        } else if (i == 46) {
            return ASSETPATH + "yoga_paschimottanasana_right_b.png";
        } else if (i == 47) {
            return ASSETPATH + "yoga_pavanamuktasana_b.png";
        } else if (i == 48) {
            return ASSETPATH + "yoga_pranayama_a.png";
        } else if (i == 49) {
            return ASSETPATH + "yoga_prasarita_padottanasana_left_a.png";
        } else if (i == 50) {
            return ASSETPATH + "yoga_prasarita_padottanasana_right_a.png";
        } else if (i == 51) {
            return ASSETPATH + "yoga_purvottanasana_left_b.png";
        } else if (i == 52) {
            return ASSETPATH + "yoga_purvottanasana_right_b.png";
        } else if (i == 53) {
            return ASSETPATH + "yoga_salamba_bhujangasana_a.png";
        } else if (i == 54) {
            return ASSETPATH + "yoga_sasangasana_b.png";
        } else if (i == 55) {
            return ASSETPATH + "yoga_setu_bandha_sarvangasana_b.png";
        } else if (i == 56) {
            return ASSETPATH + "yoga_siddhasana_a.png";
        } else if (i == 57) {
            return ASSETPATH + "yoga_skandha_chakra_b.png";
        } else if (i == 58) {
            return ASSETPATH + "yoga_skandha_chakrasana_d.png";
        } else if (i == 59) {
            return ASSETPATH + "yoga_supta_baddha_konasana_a.png";
        } else if (i == 60) {
            return ASSETPATH + "yoga_supta_padangusthasana_left_c.png";
        } else if (i == 61) {
            return ASSETPATH + "yoga_supta_padangusthasana_right_c.png";
        } else if (i == 62) {
            return ASSETPATH + "yoga_surya_namaskara_c.png";
        } else if (i == 63) {
            return ASSETPATH + "yoga_tadasana_b.png";
        } else if (i == 64) {
            return ASSETPATH + "yoga_tiryaka_tadasana_b.png";
        } else if (i == 65) {
            return ASSETPATH + "yoga_trikonasana_b.png";
        } else if (i == 66) {
            return ASSETPATH + "yoga_upavistha_konasana_left_b.png";
        } else if (i == 67) {
            return ASSETPATH + "yoga_upavistha_konasana_right_b.png";
        } else if (i == 68) {
            return ASSETPATH + "yoga_urdhva_hastasana_b.png";
        } else if (i == 69) {
            return ASSETPATH + "yoga_utkatasana_b.png";
        } else if (i == 70) {
            return ASSETPATH + "yoga_uttanasana_a.png";
        } else if (i == 71) {
            return ASSETPATH + "yoga_uttanpadasana_c.png";
        } else if (i == 72) {
            return ASSETPATH + "yoga_utthita_ashwa_sanchalanasana_left_a.png";
        } else if (i == 73) {
            return ASSETPATH + "yoga_utthita_ashwa_sanchalanasana_right_a.png";
        } else if (i == 74) {
            return ASSETPATH + "yoga_utthita_parsvakonasana_left_a.png";
        } else if (i == 75) {
            return ASSETPATH + "yoga_utthita_parsvakonasana_right_a.png";
        } else if (i == 76) {
            return ASSETPATH + "yoga_utthita_trikonasana_left_b.png";
        } else if (i == 77) {
            return ASSETPATH + "yoga_vajrasana_c.png";
        } else if (i == 78) {
            return ASSETPATH + "yoga_vasisthasana_left_a.png";
        } else if (i == 79) {
            return ASSETPATH + "yoga_vasisthasana_right_a.png";
        } else if (i == 80) {
            return ASSETPATH + "yoga_viparita_salabhasana_a.png";
        } else if (i == 81) {
            return ASSETPATH + "yoga_virabhadrasana_left_b.png";
        } else if (i == 82) {
            return ASSETPATH + "yoga_virabhadrasana_right_b.png";
        } else if (i == 83) {
            return ASSETPATH + "yoga_vrikshasana_left_b.png";
        } else if (i == 84) {
            return ASSETPATH + "yoga_vrikshasana_right_b.png";
        } else if (i == 85) {
            return ASSETPATH + "yoga_vyaghrasana_left_b.png";
        } else if (i == 86) {
            return ASSETPATH + "yoga_vyaghrasana_right_b.png";
        } else {
            return ASSETPATH + "yoga_shavasana_a.png";
        }
    }

    public static String getSingleDrawableGIF(Context context,int i) {
        if (i == 1) {
            try {
                return ASSETPATH + "yoga_adho_mukha_sukhasana.gif";
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        } else if (i == 2) {
            return ASSETPATH + "yoga_adho_mukha_svanasana.gif";
        } else if (i == 3) {
            return ASSETPATH + "yoga_anahatasana.gif";
        } else if (i == 4) {
            return ASSETPATH + "yoga_anjaneyasana_left_a.png";
        } else if (i == 5) {
            return ASSETPATH + "yoga_anjaneyasana_right_a.png";
        } else if (i == 6) {
            return ASSETPATH + "yoga_apanasana.gif";
        } else if (i == 7) {
            return ASSETPATH + "yoga_ardha_matsyendrasana_left_a.png";
        } else if (i == 8) {
            return ASSETPATH + "yoga_ardha_matsyendrasana_right_a.png";
        } else if (i == 9) {
            return ASSETPATH + "yoga_ardha_navasana_a.png";
        } else if (i == 10) {
            return ASSETPATH + "yoga_ardha_purvottanasana.gif";
        } else if (i == 11) {
            return ASSETPATH + "yoga_ardha_salabhasana_left.gif";
        } else if (i == 12) {
            return ASSETPATH + "yoga_ardha_salabhasana_right.gif";
        } else if (i == 13) {
            return ASSETPATH + "yoga_ardha_uttanasana_a.png";
        } else if (i == 14) {
            return ASSETPATH + "yoga_baddha_konasana.gif";
        } else if (i == 15) {
            return ASSETPATH + "yoga_balasana.gif";
        } else if (i == 16) {
            return ASSETPATH + "yoga_chakki_chalanasana.gif";
        } else if (i == 17) {
            return ASSETPATH + "yoga_dandayamna_bharmanasana_left.gif";
        } else if (i == 18) {
            return ASSETPATH + "yoga_dandayamna_bharmanasana_right.gif";
        } else if (i == 19) {
            return ASSETPATH + "yoga_eka_pada_rajakapotasana_left_a.png";
        } else if (i == 20) {
            return ASSETPATH + "yoga_eka_pada_rajakapotasana_right_a.png";
        } else if (i == 21) {
            return ASSETPATH + "yoga_gomukhasana_left_a.png";
        } else if (i == 22) {
            return ASSETPATH + "yoga_gomukhasana_right_a.png";
        } else if (i == 23) {
            return ASSETPATH + "yoga_janu_sirsasana_left.gif";
        } else if (i == 24) {
            return ASSETPATH + "yoga_janu_sirsasana_right.gif";
        } else if (i == 25) {
            return ASSETPATH + "yoga_jathara_parivartanasana.gif";
        } else if (i == 26) {
            return ASSETPATH + "yoga_jhulana_lurhakanasana.gif";
        } else if (i == 27) {
            return ASSETPATH + "yoga_kapalabhati_pranayama.gif";
        } else if (i == 28) {
            return ASSETPATH + "yoga_katichakrasana.gif";
        } else if (i == 29) {
            return ASSETPATH + "yoga_makara_adho_mukha_svanasana_a.png";
        } else if (i == 30) {
            return ASSETPATH + "yoga_malasana_a.png";
        } else if (i == 31) {
            return ASSETPATH + "yoga_manibandha_chakrasana.gif";
        } else if (i == 32) {
            return ASSETPATH + "yoga_marjaryasana.gif";
        } else if (i == 33) {
            return ASSETPATH + "yoga_matsyasana.gif";
        } else if (i == 34) {
            return ASSETPATH + "yoga_namaskara_a.png";
        } else if (i == 35) {
            return ASSETPATH + "yoga_natarajasana_left_a.png";
        } else if (i == 36) {
            return ASSETPATH + "yoga_natarajasana_right_a.png";
        } else if (i == 37) {
            return ASSETPATH + "yoga_parivritta_sukasana_left_a.png";
        } else if (i == 38) {
            return ASSETPATH + "yoga_parivritta_sukasana_right_a.png";
        } else if (i == 39) {
            return ASSETPATH + "yoga_parsva_sukhasana_left.gif";
        } else if (i == 40) {
            return ASSETPATH + "yoga_parsva_sukhasana_right.gif";
        } else if (i == 41) {
            return ASSETPATH + "yoga_parsva_tadasana.gif";
        } else if (i == 42) {
            return ASSETPATH + "yoga_parsvottanasana_left.gif";
        } else if (i == 43) {
            return ASSETPATH + "yoga_parsvottanasana_right.gif";
        } else if (i == 44) {
            return ASSETPATH + "yoga_parvatasana_a.png";
        } else if (i == 45) {
            return ASSETPATH + "yoga_paschimottanasana_left.gif";
        } else if (i == 46) {
            return ASSETPATH + "yoga_paschimottanasana_right.gif";
        } else if (i == 47) {
            return ASSETPATH + "yoga_pavanamuktasana.gif";
        } else if (i == 48) {
            return ASSETPATH + "yoga_pranayama_a.png";
        } else if (i == 49) {
            return ASSETPATH + "yoga_prasarita_padottanasana_left_a.png";
        } else if (i == 50) {
            return ASSETPATH + "yoga_prasarita_padottanasana_right_a.png";
        } else if (i == 51) {
            return ASSETPATH + "yoga_purvottanasana_left.gif";
        } else if (i == 52) {
            return ASSETPATH + "yoga_purvottanasana_right.gif";
        } else if (i == 53) {
            return ASSETPATH + "yoga_salamba_bhujangasana_a.png";
        } else if (i == 54) {
            return ASSETPATH + "yoga_sasangasana.gif";
        } else if (i == 55) {
            return ASSETPATH + "yoga_setu_bandha_sarvangasana.gif";
        } else if (i == 56) {
            return ASSETPATH + "yoga_siddhasana_a.png";
        } else if (i == 57) {
            return ASSETPATH + "yoga_skandha_chakra.gif";
        } else if (i == 58) {
            return ASSETPATH + "yoga_skandha_chakrasana.gif";
        } else if (i == 59) {
            return ASSETPATH + "yoga_supta_baddha_konasana_a.png";
        } else if (i == 60) {
            return ASSETPATH + "yoga_supta_padangusthasana_left.gif";
        } else if (i == 61) {
            return ASSETPATH + "yoga_supta_padangusthasana_right.gif";
        } else if (i == 62) {
            return ASSETPATH + "yoga_surya_namaskara.gif";
        } else if (i == 63) {
            return ASSETPATH + "yoga_tadasana.gif";
        } else if (i == 64) {
            return ASSETPATH + "yoga_tiryaka_tadasana.gif";
        } else if (i == 65) {
            return ASSETPATH + "yoga_trikonasana.gif";
        } else if (i == 66) {
            return ASSETPATH + "yoga_upavistha_konasana_left.gif";
        } else if (i == 67) {
            return ASSETPATH + "yoga_upavistha_konasana_right.gif";
        } else if (i == 68) {
            return ASSETPATH + "yoga_urdhva_hastasana.gif";
        } else if (i == 69) {
            return ASSETPATH + "yoga_utkatasana.gif";
        } else if (i == 70) {
            return ASSETPATH + "yoga_uttanasana_a.png";
        } else if (i == 71) {
            return ASSETPATH + "yoga_uttanpadasana.gif";
        } else if (i == 72) {
            return ASSETPATH + "yoga_utthita_ashwa_sanchalanasana_left_a.png";
        } else if (i == 73) {
            return ASSETPATH + "yoga_utthita_ashwa_sanchalanasana_right_a.png";
        } else if (i == 74) {
            return ASSETPATH + "yoga_utthita_parsvakonasana_left_a.png";
        } else if (i == 75) {
            return ASSETPATH + "yoga_utthita_parsvakonasana_right_a.png";
        } else if (i == 76) {
            return ASSETPATH + "yoga_utthita_trikonasana.gif";
        } else if (i == 77) {
            return ASSETPATH + "yoga_vajrasana.gif";
        } else if (i == 78) {
            return ASSETPATH + "yoga_vasisthasana_left_a.png";
        } else if (i == 79) {
            return ASSETPATH + "yoga_vasisthasana_right_a.png";
        } else if (i == 80) {
            return ASSETPATH + "yoga_viparita_salabhasana_a.png";
        } else if (i == 81) {
            return ASSETPATH + "yoga_virabhadrasana_left.gif";
        } else if (i == 82) {
            return ASSETPATH + "yoga_virabhadrasana_right.gif";
        } else if (i == 83) {
            return ASSETPATH + "yoga_vrikshasana_left.gif";
        } else if (i == 84) {
            return ASSETPATH + "yoga_vrikshasana_right.gif";
        } else if (i == 85) {
            return ASSETPATH + "yoga_vyaghrasana_left.gif";
        } else if (i == 86) {
            return ASSETPATH + "yoga_vyaghrasana_right.gif";
        } else {
            return ASSETPATH + "yoga_shavasana_a.png";
        }
    }

    public static String getRootPath(Context context) {
        return context.getDatabasePath(BaseAppDB.DB_NAME).getParent();
    }

    public static String getMediaDir(Context context) {
        File file = new File(getRootPath(context), "media");
        if (!file.exists()) {
            file.mkdirs();
        }
        return file.getAbsolutePath();
    }


    public static List<Integer> getDay() {
        List<Integer> arrayList = new ArrayList<>();
        for (int i = 1; i <= 30; i++) {
            arrayList.add(Integer.valueOf(i));
        }
        return arrayList;
    }

    public static String getFormattedDate(long j, DateFormat dateFormat) {
        return dateFormat.format(new Date(j));
    }

    public static void remind24(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(CATEGORY_ALARM);
        Intent intent = new Intent(context, DailyAlarmReceiver.class);
        intent.putExtra("alarmRequestCode", 111);
        PendingIntent broadcast = PendingIntent.getBroadcast(context, 111, intent, 134217728);
        Calendar instance = Calendar.getInstance();
        instance.set(11, 24);
        instance.set(12, 0);
        instance.set(13, 0);
        instance.set(14, 0);
        if (Build.VERSION.SDK_INT < 23) {
            Log.i("`ww", "onReceive: 11111!");
            alarmManager.set(AlarmManager.RTC_WAKEUP, instance.getTimeInMillis(), broadcast);
        } else if (Build.VERSION.SDK_INT >= 19 && Build.VERSION.SDK_INT < 23) {
            Log.i("`ww", "onReceive: 22222!");
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, instance.getTimeInMillis(), broadcast);
        } else if (Build.VERSION.SDK_INT >= 23) {
            Log.i("`ww", "onReceive: 33333!");
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, instance.getTimeInMillis(), broadcast);
        }
    }

    public static void remind3hour(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(CATEGORY_ALARM);
        Intent intent = new Intent(context, DailyAlarmReceiver.class);
        intent.putExtra("alarmRequestCode", 112);
        PendingIntent broadcast = PendingIntent.getBroadcast(context, 112, intent, 134217728);
        Calendar instance = Calendar.getInstance();
        instance.setTimeInMillis(AppPref.getReminderTime(context));
        Calendar instance2 = Calendar.getInstance();
        instance2.set(11, instance.get(11));
        instance2.set(12, instance.get(12));
        instance2.set(13, 0);
        instance2.set(14, 0);
        if (System.currentTimeMillis() > instance2.getTimeInMillis()) {
            return;
        }
        if (Build.VERSION.SDK_INT < 19) {
            alarmManager.set(AlarmManager.RTC_WAKEUP, instance2.getTimeInMillis(), broadcast);
        } else if (Build.VERSION.SDK_INT >= 19 && Build.VERSION.SDK_INT < 23) {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, instance2.getTimeInMillis(), broadcast);
        } else if (Build.VERSION.SDK_INT >= 23) {
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, instance2.getTimeInMillis(), broadcast);
        }
    }

    public static String getMediaDirctory(Context context) {
        File file = new File(getRootPath(context), PICTURE_STORE_DIR_NAME);
        if (!file.exists()) {
            file.mkdirs();
        }
        return file.getAbsolutePath();
    }


    public static String getPathOfImage(String str) {
        if (str.isEmpty()) {
            return "";
        }
        return getMediaDirctory(BaseApp.getInstance()) + "/" + str;
    }

    public static long getTimeinMilliWithoutTime(long j) {
        Calendar instance = Calendar.getInstance();
        instance.setTimeInMillis(j);
        instance.set(10, 0);
        instance.set(12, 0);
        instance.set(13, 0);
        instance.set(14, 0);
        return instance.getTimeInMillis();
    }

    public static String decimalFormatNew(double d) {
        if (d % 1.0d == Utils.DOUBLE_EPSILON) {
            return String.valueOf((long) d);
        }
        return new DecimalFormat("#.##").format(d);
    }
}

package com.BestofallHealthYoga.TamarinYoga.utils;

import android.app.Application;
import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.util.Log;

import androidx.multidex.MultiDex;
import com.facebook.drawee.backends.pipeline.Fresco;
import java.util.HashMap;
import java.util.Locale;

public class BaseApp extends Application {
     static Context mInstance;
     static TextToSpeech textToSpeech;

    @Override
    public void onCreate() {
        super.onCreate();
        intializeTexttoSpeech();
        Fresco.initialize(this);
        mInstance = this;
    }

    private void intializeTexttoSpeech() {
        new Thread(new TextInit()).start();
    }

    public static void speeakSound(String str) {
        TextToSpeech textToSpeech2 = textToSpeech;
        if (textToSpeech2 != null) {
            textToSpeech2.setSpeechRate(1.0f);
            textToSpeech.speak(str, 1, (HashMap) null);
        }
    }

    public class TextInit implements Runnable {
        public TextInit() {
            Log.d("","dss");
        }

        public void run() {
            BaseApp.textToSpeech = new TextToSpeech(BaseApp.this.getApplicationContext(), new TextToSpeech.OnInitListener() {
                public void onInit(int i) {
                    if (i == 0) {
                        try {
                            BaseApp.textToSpeech.setLanguage(Locale.US);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        }
    }


    @Override
    public void attachBaseContext(Context context) {
        super.attachBaseContext(context);
        MultiDex.install(this);
    }

    public static synchronized Context getInstance() {
        Context context;
        synchronized (BaseApp.class) {
            context = mInstance;
        }
        return context;
    }
}

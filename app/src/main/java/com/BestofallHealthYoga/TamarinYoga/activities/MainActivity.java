package com.BestofallHealthYoga.TamarinYoga.activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.codemybrainsout.ratingdialog.RatingDialog;
import com.google.ads.mediation.admob.AdMobAdapter;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.VideoOptions;
import com.google.android.gms.ads.formats.NativeAdOptions;
import com.google.android.gms.ads.formats.UnifiedNativeAd;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.BestofallHealthYoga.TamarinYoga.BuildConfig;
import com.BestofallHealthYoga.TamarinYoga.R;
import com.BestofallHealthYoga.TamarinYoga.babyphotos.BabyPhotos;
import com.BestofallHealthYoga.TamarinYoga.dbhelper.BaseAppDB;
import com.BestofallHealthYoga.TamarinYoga.dbhelper.DemoDB;
import com.BestofallHealthYoga.TamarinYoga.fragments.BmiCalculator_Fragment;
import com.BestofallHealthYoga.TamarinYoga.fragments.HomeFragment;
import com.BestofallHealthYoga.TamarinYoga.fragments.Me_Fragment;
import com.BestofallHealthYoga.TamarinYoga.models.Drawer;
import com.BestofallHealthYoga.TamarinYoga.on_click;
import com.BestofallHealthYoga.TamarinYoga.utils.AdGlobal;
import com.BestofallHealthYoga.TamarinYoga.utils.AdsScreen;
import com.BestofallHealthYoga.TamarinYoga.utils.AppPref;
import com.BestofallHealthYoga.TamarinYoga.utils.Constant;

import java.util.ArrayList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    static InterstitialAd goglefullad;
    static AdRequest intadrequest;
    static AdsScreen mAdBackScreenInterface;
    static Context mainContext;
    static UnifiedNativeAd nativeAd;

    BabyPhotos babyPhotos;
    DemoDB demoDB;
    ArrayList<Drawer> drawerArrayList;
    DrawerLayout drawerLayout;
    BaseAppDB myDB;
    String playStoreUrl = "";
    String title = "كيف كانت تجربتك معنا؟";
    BottomNavigationView l;
    BottomNavigationMenuView meuvie;
    BottomNavigationItemView meuviebottom;

    InterstitialAd interstitial;
    AdRequest adRequest;

    public void googlefull_ad()
    {
        interstitial = new InterstitialAd(this);
        interstitial.setAdUnitId(AdGlobal.ADFULL);
        adRequest = new AdRequest.Builder().build();
        interstitial.setAdListener(new AdListener() {

            @Override
            public void onAdFailedToLoad(int errorCode) {
                // Code to be executed when an ad request fails.
            }

            @Override
            public void onAdLeftApplication() {
                // Code to be executed when the user has left the app.
            }

            @Override
            public void onAdClosed() {
                // Code to be executed when the interstitial ad is closed.
            }
        });
        interstitial.loadAd(this.adRequest);
    }


    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_main);

        googlefull_ad();

        AdView mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


        this.playStoreUrl = "https://play.google.com/store/apps/details?id=" + getPackageName();
        loadad();
        refreshAd();
        init();


        this.l = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        loadFragment(new HomeFragment());
        this.meuvie = (BottomNavigationMenuView) this.l.getChildAt(0);
        this.meuviebottom = (BottomNavigationItemView) this.meuvie.getChildAt(2);
        this.l.setOnNavigationItemSelectedListener(new on_click(this));
    }

    public boolean a(MenuItem menuItem) {
        Fragment fragment;
        switch (menuItem.getItemId()) {
            case R.id.training:
                fragment = new HomeFragment();
                break;
            case R.id.bmi:
                googlefull_ad();
                fragment = new BmiCalculator_Fragment();
                break;
            case R.id.tips_main:
                googlefull_ad();
                fragment = new Advancedtip();
                break;
            case R.id.reports:
                googlefull_ad();
                fragment = new ReportsActivity();
                break;
            case R.id.profile:
                googlefull_ad();
                fragment = new Me_Fragment();
                break;
            default:
                fragment = null;
                break;
        }
        return loadFragment(fragment);
    }

    private void init() {
        this.demoDB = new DemoDB(this);
        this.drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        this.myDB = new BaseAppDB(this);
        if (!AppPref.isSave(this)) {
            storeData();
        }


    }

    public void refreshAd() {
        AdRequest adRequest;
        AdLoader.Builder builder = new AdLoader.Builder(this, AdGlobal.NATIVE_ID);
        builder.forUnifiedNativeAd(new UnifiedNativeAd.OnUnifiedNativeAdLoadedListener() {
            @RequiresApi(api = 17)
            public void onUnifiedNativeAdLoaded(UnifiedNativeAd unifiedNativeAd) {
                if (MainActivity.this.isDestroyed()) {
                    unifiedNativeAd.destroy();
                    return;
                }
                if (nativeAd != null) {
                    nativeAd.destroy();
                }
                nativeAd = unifiedNativeAd;
            }
        });
        builder.withNativeAdOptions(new NativeAdOptions.Builder().setVideoOptions(new VideoOptions.Builder().setStartMuted(true).build()).setAdChoicesPlacement(1).build());
        AdLoader build = builder.withAdListener(new AdListener() {
            @Override
            public void onAdFailedToLoad(int i) {
                Log.d("", "sd");
            }
        }).build();
        if (AdGlobal.NPAFLAG) {
            Log.d("NPA", "" + AdGlobal.NPAFLAG);
            Bundle bundle = new Bundle();
            bundle.putString("npa", "1");
            adRequest = new AdRequest.Builder().addNetworkExtrasBundle(AdMobAdapter.class, bundle).build();
        } else {
            Log.d("NPA", "" + AdGlobal.NPAFLAG);
            adRequest = new AdRequest.Builder().build();
        }
        build.loadAd(adRequest);
    }


    public void addExerciseData() {
        this.babyPhotos = new BabyPhotos(Constant.getUniqueId(), getResources().getString(R.string.onemonth), "");
        this.myDB.addExerciseData(this.babyPhotos);
        this.babyPhotos = new BabyPhotos(Constant.getUniqueId(), getResources().getString(R.string.twomonth), "");
        this.myDB.addExerciseData(this.babyPhotos);
        this.babyPhotos = new BabyPhotos(Constant.getUniqueId(), getResources().getString(R.string.threemonth), "");
        this.myDB.addExerciseData(this.babyPhotos);
        this.babyPhotos = new BabyPhotos(Constant.getUniqueId(), getResources().getString(R.string.fourthmonth), "");
        this.myDB.addExerciseData(this.babyPhotos);
        this.babyPhotos = new BabyPhotos(Constant.getUniqueId(), getResources().getString(R.string.fivemonth), "");
        this.myDB.addExerciseData(this.babyPhotos);
        this.babyPhotos = new BabyPhotos(Constant.getUniqueId(), getResources().getString(R.string.sixmonth), "");
        this.myDB.addExerciseData(this.babyPhotos);
        this.babyPhotos = new BabyPhotos(Constant.getUniqueId(), getResources().getString(R.string.sevenmonth), "");
        this.myDB.addExerciseData(this.babyPhotos);
        this.babyPhotos = new BabyPhotos(Constant.getUniqueId(), getResources().getString(R.string.eightmonth), "");
        this.myDB.addExerciseData(this.babyPhotos);
        this.babyPhotos = new BabyPhotos(Constant.getUniqueId(), getResources().getString(R.string.ninemonth), "");
        this.myDB.addExerciseData(this.babyPhotos);
        this.babyPhotos = new BabyPhotos(Constant.getUniqueId(), getResources().getString(R.string.tenmonth), "");
        this.myDB.addExerciseData(this.babyPhotos);
        this.babyPhotos = new BabyPhotos(Constant.getUniqueId(), getResources().getString(R.string.elevenmonth), "");
        this.myDB.addExerciseData(this.babyPhotos);
        this.babyPhotos = new BabyPhotos(Constant.getUniqueId(), getResources().getString(R.string.twelvemonth), "");
        this.myDB.addExerciseData(this.babyPhotos);
        this.babyPhotos = new BabyPhotos(Constant.getUniqueId(), getResources().getString(R.string.thirteenmonth), "");
        this.myDB.addExerciseData(this.babyPhotos);
        this.babyPhotos = new BabyPhotos(Constant.getUniqueId(), getResources().getString(R.string.fourteenmonth), "");
        this.myDB.addExerciseData(this.babyPhotos);
        this.babyPhotos = new BabyPhotos(Constant.getUniqueId(), getResources().getString(R.string.fifteenmonth), "");
        this.myDB.addExerciseData(this.babyPhotos);
        this.babyPhotos = new BabyPhotos(Constant.getUniqueId(), getResources().getString(R.string.sixteenmonth), "");
        this.myDB.addExerciseData(this.babyPhotos);
        this.babyPhotos = new BabyPhotos(Constant.getUniqueId(), getResources().getString(R.string.seventeenmonth), "");
        this.myDB.addExerciseData(this.babyPhotos);
        this.babyPhotos = new BabyPhotos(Constant.getUniqueId(), getResources().getString(R.string.eighteenmonth), "");
        this.myDB.addExerciseData(this.babyPhotos);
        this.babyPhotos = new BabyPhotos(Constant.getUniqueId(), getResources().getString(R.string.nineteenmonth), "");
        this.myDB.addExerciseData(this.babyPhotos);
        this.babyPhotos = new BabyPhotos(Constant.getUniqueId(), getResources().getString(R.string.twentymonth), "");
        this.myDB.addExerciseData(this.babyPhotos);
        this.babyPhotos = new BabyPhotos(Constant.getUniqueId(), getResources().getString(R.string.twentyonemonth), "");
        this.myDB.addExerciseData(this.babyPhotos);
        this.babyPhotos = new BabyPhotos(Constant.getUniqueId(), getResources().getString(R.string.twentytwomonth), "");
        this.myDB.addExerciseData(this.babyPhotos);
        this.babyPhotos = new BabyPhotos(Constant.getUniqueId(), getResources().getString(R.string.twentythreemonth), "");
        this.myDB.addExerciseData(this.babyPhotos);
        this.babyPhotos = new BabyPhotos(Constant.getUniqueId(), getResources().getString(R.string.twentyfourmonth), "");
        this.myDB.addExerciseData(this.babyPhotos);
    }


    public void storeExerciseType() {
        this.demoDB.addExerciseTypeDetailsFirsTime(Constant.MEDIUM, "Medium");
    }


    public void storeActivityInformation() {

        this.demoDB.addActivityInformationDetails(1, "تحت الوجه جاف", "لتبدأ مع هذا أسانا ، اجلس على الأرض مع القرفصاء. ضع يديك بجانبك. عند الشهيق ، ارفع ذراعيك نحو السقف. انحنى للأمام عن طريق مد يديك للأمام ثم اقفل ذراعيك. استمر في هذا الوضع لبضع ثوان. عند الزفير ، عد إلى وضعية الجلوس وكرر.\n\n", 0, "", "");
        this.demoDB.addActivityInformationDetails(2, "نصف مواجهة الحكم الذاتي", "ابدأ أسانا بالانحناء للأمام بمقدار 45 درجة على ذراعيك وساقيك ، في مواجهة مؤخرتك للسقف. ابق على راحتي اليدين والقدمين. ابق في هذا الوضع لعدة أنفاس. ثم عد إلى الموضع الأولي. ومارس الأسانا من 5 إلى 6 مرات. \n\n", 0, "", "");
        this.demoDB.addActivityInformationDetails(3, "مشمئز", "يُطلق على Anahatasana أيضًا وضع ذوبان القلب. ابدأ أسانا في Adho Mukha Svanasana. أرِح يديك وركبتيك وجبينك على الأرض. ارفع الوركين لأعلى. أرِح صدرك على بساط اليوجا مع إبقاء المعدة مرفوعة. اثبت على هذا الوضع لمدة 30 إلى 60 ثانية. للإفراج ، قم بتخفيض الوركين ، والوصول إلى بالاسانا. ابق هنا لفترة وكرر مرة أخرى. هذا أسانا يعالج مشاكل الجهاز التنفسي. \n\n", 0, "", "");
        this.demoDB.addActivityInformationDetails(4, "أنجانياسانا اليسار", "ابدأ هذا الوضع بالركوع على الركبة اليسرى. قم بإطالة الساق اليمنى للخلف عن طريق لمس الركبة اليمنى للأرض ، مقابل الساق اليسرى. حافظ على يديك حرة من خلال مواجهة الأعلى ابق في هذا الوضع لبعض الأنفاس. حرر الساق وكرر الأمر مرة أخرى. إذا كنت تمارس هذا الأسانا بانتظام ، فسيتم تنشيط جسمك وتنشيطه.\n\n", 0, "", "");
        this.demoDB.addActivityInformationDetails(5, "أنجانياسانا حق", "ابدأ هذا الوضع بالركوع على الركبة اليمنى. قم بمد الساق اليسرى للخلف عن طريق لمس الركبة اليسرى للأرض ، مقابل الساق اليمنى. حافظ على يديك حرة من خلال مواجهة الأعلى ابق في هذا الوضع لعدة أنفاس ثم حرر ساقك وكرر الأمر مرة أخرى. أنجانياسانا يساعد في تخفيف عرق النسا.\n\n", 0, "", "");
        this.demoDB.addActivityInformationDetails(6, "أباناسانا", "ابدأ في وضع الأسانا بالاستلقاء على ظهرك مع وضع عمودك الفقري في وضع محايد. اثنِ ركبتك اليسرى بالقرب من صدرك وأمسك بها عن طريق تشبيك اليدين حول الركبة. افرد رجلك اليمنى. حافظ على راحة رأسك على الأرض. ابق في الوضع لمدة دقيقة إلى دقيقتين. استمر في الشهيق والزفير بهذه الوضعية. ثم حرر الركبة اليسرى وكرر الأمر مع الرجل اليمنى. يقلل الأسانا من الغضب المفرط والإثارة والقلق وارتفاع ضغط الدم. تخفف هذه الوضعية من آلام أسفل الظهر والانتفاخ والإمساك. كما أنه مفيد لمتلازمة القولون العصبي.\n\n", 0, "", "");
        this.demoDB.addActivityInformationDetails(7, "أردا ماتسيندراسانا اليسار", "اجلس على الأرض. حافظ على ساقك اليمنى مستقيمة ، وأصابع القدم تواجه السقف خذ صليب الساق اليسرى خارج الساق اليمنى واحتفظ به بجانب الركبة اليمنى. ضع ذراعك الأيسر على الأرض خلف ظهرك. ارفع يدك اليمنى وجلب كوعك الأيمن بجانب الركبة اليسرى. ابق في هذا الوضع لمدة تصل إلى 30 ثانية. هذا الأسانا يجعل العمود الفقري أكثر مرونة. كما أنه مفيد لاضطرابات الدورة الشهرية.\n\n", 0, "", "");
        this.demoDB.addActivityInformationDetails(8, "أردا ماتسيندراسانا حق", "اجلس على الأرض. حافظ على ساقك اليسرى مستقيمة ، وأصابع القدم تواجه السقف خذ صليب الساق اليمنى خارج الساق اليسرى واحتفظ به بجانب الركبة اليسرى. ضع ذراعك الأيمن على الأرض خلف ظهرك. ارفع يدك اليسرى وجلب مرفقك الأيسر بجانب الركبة اليمنى. ابق في هذا الوضع لمدة تصل إلى 30 ثانية. تساعد هذه الوضعية على إرخاء المفاصل الموجودة في الورك وتخفيف التصلب أيضًا. يساعد في علاج التهابات المسالك البولية.\n\n", 0, "", "");
        this.demoDB.addActivityInformationDetails(9, "أردا نافاسانا", "ابدأ بالجلوس على الأرض. ارفع كلا الساقين في وقت يصل إلى 90 درجة. افرد ذراعيك أمامك تجاه أصابع قدميك. ابدأ في الاستنشاق. اشعر بالتوتر في منطقة معدتك حيث تنقبض عضلات البطن. دع وزن جسمك كله يرتاح بالكامل على الأرداف. احبس أنفاسك وابق في هذا الوضع لأطول فترة ممكنة. عند الزفير ، أنزل ذراعيك وساقيك ببطء وكرر ذلك.\n\n", 0, "", "");
        this.demoDB.addActivityInformationDetails(10, "أردا بورفوتاناسانا", "ابدأ أسانا بالجلوس على الأرض. إثن ركبتيك. شد ذراعيك إلى الخلف. عند الشهيق ، ارفع وركيك لأعلى نحو السقف ، وقم بعمل ثني عكسي. ضع وزن جسمك على راحة يديك وقدميك. امسك هذا الوضع لبعض الوقت. الزفير. تعال إلى وضع البداية وكررها 10 مرات.\n\n", 0, "", "");
        this.demoDB.addActivityInformationDetails(11, "أردا سالاباسانا اليسار", "ابدأ أسانا بالاستلقاء على الأرض باتجاه الأرض. ابق يديك بالقرب من معدتك ، المس ذقنك على الأرض. عند الشهيق ، ارفع ساقك اليسرى ببطء لأعلى مع الضغط على المعدة. ارفع ساقك لأعلى لبضع ثوان. عند الزفير ، ارفعه لأسفل وكرر ذلك من 10 إلى 12 مرة.\n\n", 0, "", "");
        this.demoDB.addActivityInformationDetails(12, "أردا سالاباسانا حق", "ابدأ أسانا بالاستلقاء على الأرض باتجاه الأرض. ابق يديك بالقرب من معدتك ، المس ذقنك على الأرض. عند الشهيق ، ارفع ساقك اليمنى ببطء لأعلى مع الضغط على المعدة. ارفع ساقك لأعلى لبضع ثوان. عند الزفير ، ارفعه لأسفل وكرر ذلك من 10 إلى 12 مرة.\n\n", 0, "", "");
        this.demoDB.addActivityInformationDetails(13, "أردا أوتاناسانا", "ابدأ الوضع بالوقوف بشكل مستقيم. قدم معا. ذراعان بجانبك. ما عليك سوى ثني خصرك للأمام وثني ذراعيك على رأسك. من الجذع يجب أن ننظر 90 درجة. ويجب أن يكون جزء من جسمك حتى الخصر موازيًا للأرض. امسك هذا الوضع لعدة أنفاس ثم افرد خصرك وكرر ذلك 5 مرات.\n\n", 0, "", "");
        this.demoDB.addActivityInformationDetails(14, "بادها كوناسانا", "لبدء الوضع ، اجلس مع ساقيك أمامك مباشرة. ارفعي حوضك على بطانية إذا كان الوركين أو الفخذين مشدودتين. اثنِ ركبتيك واسحب كعبيك نحو حوضك. ثم أسقط ركبتيك على الجانبين. واضغط على باطن قدميك معًا. عند الشهيق ، انحنى للأمام. المس رأسك بقدميك وإذا أمكن المس الأرض. ابق في هذا الوضع لبضع ثوان. عند الزفير ، عد إلى الوضع الأولي. كرر الأسانا 10 مرات في الأيام الأولى. هذا أسانا يعزز أداء الجهاز التناسلي عند النساء. هذا أسانا هو مسكن كبير للتوتر. كما أنه يساعد في تقليل التعب. يقال إن الممارسة المنتظمة لهذا الأسانا يمكن أن تحافظ على خلوك من أي نوع من الأمراض.\n\n", 0, "", "");
        this.demoDB.addActivityInformationDetails(15, "بالاسانا", "بالاسانا هو وضع تصالحي ومهدئ يريح الجسم ويجدد شبابه. لذا ، ابدأ بركب ركبتيك وضع يديك على فخذيك. حرر أصابع قدميك على الأرض وافصل بين ركبتيك بمسافة عرض الوركين. الآن استنشق وانحني للأمام عن طريق مد يديك إلى المستقيم ولمس رأسك على الأرض. قم بالزفير والعودة إلى الوضع الأولي وكرر ذلك. يساعد هذا الأسانا على التخلص من التوتر في الصدر والظهر والكتفين. كما أنه يساعد على تمدد وإطالة العمود الفقري. يعزز الدورة الدموية في جميع أنحاء الجسم. جيد.\n\n", 0, "", "");
        this.demoDB.addActivityInformationDetails(16, "شكي شالاناسانا", "ابدأ أسانا بالجلوس بشكل مستقيم على الأرض. افرد ساقيك للأمام. افرد ذراعيك أمامك. ويجب أن يكون مستقيمًا إلى الصدر عن طريق تشابك أصابع اليدين. الآن قم بثني خصرك للأمام مع الحفاظ على استقامة اليدين المتشابكة وقم بتدوير يديك في اتجاه عقارب الساعة وعكس اتجاه عقارب الساعة ، مثل وضع الطاحونة. الآن يتحرك خصرك للخلف والرابع. يساعد هذا الأسانا في تنغيم أعصاب وأعضاء البطن والحوض. يفيد في تنظيم الدورة الشهرية. كما أنه مفيد لعضلات الخصر وأسفل الظهر. إنه وضع سهل للغاية. لذا استمر في فعل ذلك 10 مرات في كلا الاتجاهين. يطلق شد في عمودك الفقري.\n\n", 0, "", "");
        this.demoDB.addActivityInformationDetails(17, "داندايمن برماناسانا اليسار", "ابدأ الوضع بيديك وركبتيك على الأرض. مواجهة نحو الأرض. عند الشهيق ، ارفع ساقك اليمنى وذراعك الأيسر للأعلى ومددها بشكل مستقيم. ابق في هذا الوضع لأطول فترة ممكنة. في الزفير ، ارفع الساق اليمنى والذراع اليسرى. استرخ لبضع ثوان. ثم كرر ذلك 10 مرات على نفس الساق.\n\n", 0, "", "");
        this.demoDB.addActivityInformationDetails(18, "داندايمن برماناسانا حق", "ابدأ الوضع بيديك وركبتيك على الأرض. مواجهة نحو الأرض. عند الشهيق ، ارفع ساقك اليسرى وذراعك الأيمن لأعلى ومددها بشكل مستقيم. ابق في هذا الوضع لأطول فترة ممكنة. في الزفير ، ارفع الساق اليسرى والذراع اليمنى. استرخ لبضع ثوان. ثم كرر ذلك 10 مرات على نفس الساق.\n\n", 0, "", "");
        this.demoDB.addActivityInformationDetails(19, "إيكا بادا راجاكابوتاسانا اليسار", "ابدأ الوضع بيديك وركبتيك على الأرض. مواجهة نحو الأرض. عند الشهيق ، ارفع ساقك اليسرى وذراعك الأيمن لأعلى ومددها بشكل مستقيم. ابق في هذا الوضع لأطول فترة ممكنة. في الزفير ، ارفع الساق اليسرى والذراع اليمنى. استرخ لبضع ثوان. ثم كرر ذلك 10 مرات على نفس الساق.\n\n", 0, "", "");
        this.demoDB.addActivityInformationDetails(20, "إيكا بادا راجاكابوتاسانا حق", "ابدأ أسانا بالجلوس على الأرض. اثن ركبتك اليمنى وانحني للأمام وجلب الركبة اليمنى بالقرب من الصدر. شد الساق اليسرى للخلف. امسك هذا الوضع لعدة أنفاس يمكنك لمس رأسك للأرض أو يمكنك الحصول على دعم مثل الوسادة. عد وكرر ذلك من 5 إلى 6 مرات. هذا الأسانا يطلق التوتر في الوركين من الجلوس طوال اليوم\n\n", 0, "", "");
        this.demoDB.addActivityInformationDetails(21, "جوموخاسانا اليسار", "ابدأ الوضع بجثو ركبتيك ووضع يديك على فخذيك. حرر أصابع قدميك على الأرض وافصل بين ركبتيك بمسافة عرض الوركين. اجلب ذراعيك للخلف ، واليد اليسرى من الأعلى واليد اليمنى من الأسفل وحاول تعشيق الأصابع في الخلف. امسك الوضع لعدة أنفاس وكرر.\n\n", 0, "", "");
        this.demoDB.addActivityInformationDetails(22, "جوموخاسانا حق", "ابدأ الوضع بجثو ركبتيك ووضع يديك على فخذيك. حرر أصابع قدميك على الأرض وافصل بين ركبتيك بمسافة عرض الوركين. اجلب ذراعيك للخلف ، واليد اليمنى من الأعلى واليد اليسرى من الأسفل وحاول تشبيك الأصابع في الخلف. امسك الوضع لعدة أنفاس وكرر.\n\n", 0, "", "");
        this.demoDB.addActivityInformationDetails(23, "جانو سيرساسانا اليسار", "ابدأ الأسانا بالجلوس على الأرض ، مع وضع القدمين معًا ، ومددها للأمام. اثنِ ركبتك اليمنى. وتقريب القدم اليمنى من حوضك. ازفر وأنت تنحني عند الوركين. اخفض جذعك ببطء وامسك بقدمك اليسرى. حافظ على الوضع لمدة 30 ثانية إلى دقيقة واحدة. نعود إلى الموضع الأولي. وكرر الأسانا 10 مرات في البداية.\n\n", 0, "", "");
        this.demoDB.addActivityInformationDetails(24, "جانو سيرساسانا حق", "ابدأ الأسانا بالجلوس على الأرض ، مع وضع القدمين معًا ، ومددها للأمام. اثنِ ركبتك اليسرى. واجعلي قدمك اليسرى قريبة من حوضك. ازفر وأنت تنحني عند الوركين. أنزل جذعك ببطء وامسك بقدمك اليمنى. حافظ على الوضع لمدة 30 ثانية إلى دقيقة واحدة. نعود إلى الموضع الأولي. وكرر الأسانا 10 مرات في البداية.\n\n", 0, "", "");
        this.demoDB.addActivityInformationDetails(25, "جاثارا باريفارتاناسانا", "ابدأ تمرين الأسانا بالاستلقاء على ظهرك مع سحب ركبتيك إلى صدرك. افرد ذراعيك إلى جانبيك على مستوى الكتف وارفع راحتي اليدين. في الزفير ، امسح ركبتيك على الجانب الأيمن واجذبهما نحو مرفقك الأيمن. امسك هذا الوضع لبعض الوقت. الآن امسح ركبتيك إلى الجانب الأيسر وارسمهما باتجاه مرفقك الأيسر. امسك هذا الوضع لبعض الوقت. وكررها من 15 إلى 20 مرة. الفائدة الأولى والأهم من هذا الأسانا هي التخفيف من أي ألم في أسفل الظهر. وتقوي معدتك. هذا أسانا ، يطلق سموم الجسم أثناء العمل على زيادة التمثيل الغذائي الخاص بك. جيد. استمر في فعل ذلك 10 مرات على كل جانب.\n\n", 0, "", "");
        this.demoDB.addActivityInformationDetails(26, "جولانا لورهاكاناسانا", "ابدأ أسانا بالجلوس على الأرض دون لمس ظهرك للأرض. ارفع ركبتيك واثن رأسك للأسفل. ضع يديك تحت ركبتيك. أبقِ عمودك الفقري مستديرًا ، وقم بالتأرجح برفق للخلف وللأمام ، لتقليد حركة تأرجح الكرسي الهزاز. لا تقم بتصويب عمودك الفقري أثناء التدحرج للأمام والخلف. كرر هذا المتداول لبعض الوقت. يقوم هذا أسانا بتدليك الظهر والأرداف والوركين. يكون مفيدًا للغاية إذا تم القيام به أول شيء في الصباح بعد الاستيقاظ. يتم شد العضلات الأساسية وتقويتها مع وضع اليوغا هذا. تطلق غازات غير مرغوب فيها. استمر في التدحرج لبضع ثوان. جيد.\n\n", 0, "", "");
        this.demoDB.addActivityInformationDetails(27, "كابالاباتي براناياما", "اجلس على الأرض عن طريق طي رجليك بشكل متقاطع. أبقِ ذراعًا على بطنك والأخرى على فخذك. اجلس معتدلا. الآن اسحب معدتك نحو ظهرك. اجعل السرة أقرب إلى العمود الفقري بقدر ما تستطيع. يمكنك الشعور بانقباض عضلات بطنك. أثناء الزفير ، سيصدر صوت هسهسة. في هذه المرحلة ، اشعر أن كل الأشياء السيئة في جسدك تخرج. عندما تحرر بطنك ، يمكنك أن تشعر بالهواء يملأ رئتيك. افعل ذلك من 20 إلى 30 مرة على الأقل. ويزيد العدد تدريجياً. يولد كابالاباتي حرارة في جسمك ويذيب السموم وغيرها من النفايات. يساعد كابالاباتي في علاج الربو والجيوب الأنفية وتساقط الشعر. الممارسة المنتظمة لـ كابالاباتي ستنشطك وتجعل وجهك يتألق. تستمر في فعل.\n\n", 0, "", "");
        this.demoDB.addActivityInformationDetails(28, "كاتشاكراسانا", "كاتشاكراسانا هي وضعية يوغا بسيطة لكنها فعالة وآمنة لتمرين جذعك بشكل أساسي. لذلك ، لتبدأ بهذه الأسانا ، قف مع ساقين متباعدتين قليلاً. تمتد الأيدي إلى الخارج على جانبيك والنخيل موازية للأرض. عند الشهيق ، ضع يدك اليمنى على الجانب الأيسر ، والمس راحة يدك اليمنى بكتفك الأيسر ، عن طريق إبقاء يدك اليسرى لأسفل على الجانبين. عند الزفير ، عد إلى وضع الوقوف الأولي. الآن افعلها بيد أخرى. عند الشهيق ، ضع يدك اليسرى على الجانب الأيمن ، والمس راحة يدك اليسرى بكتفك الأيمن ، عن طريق إبقاء يدك اليمنى لأسفل على الجانبين. عند الزفير ، عد إلى وضع الوقوف الأولي. كرر هذا الأسانا من 10 إلى 12 مرة. يتضمن هذا الأسانا حركات دائرية وهو علاج جيد لآلام الظهر. وهي تمد عضلات الجذع. جيد. \n\n", 0, "", "");
        this.demoDB.addActivityInformationDetails(29, "ماكارا أدهو موكا سفاناسانا", "ابدأ على يديك وركبتيك. قم بمحاذاة ظهرك ، حتى يصبح كتفيك فوق المرفقين مباشرة ، ويكون جذعك موازيًا للأرض. انقل وزن الجسم إلى الأمام. افعل هذا ببطء. إلى أسفل رأسك تحدق في الأرض. يجب أن تكون الركبتان والظهر في خط مستقيم. الآن استنشق ببطء واسحب عضلات بطنك إلى الداخل. ابق في هذا الوضع لمدة 30 ثانية إلى دقيقة. أثناء الزفير ، أرخ عضلات البطن وكرر ذلك من 4 إلى 5 مرات.\n\n", 0, "", "");
        this.demoDB.addActivityInformationDetails(30, "مالاسانا", "ابدأ وضع الأسانا في وضعية الجبل مثل الجلوس على القدمين. ابق ذراعيك على الأرض. استنشق وانحني ببطء نحو الأرض. امسك هذا الوضع بقدر ما تستطيع. ثم الزفير. عد إلى الوضع الأولي وكرره مع الاحتفاظ بمزيد من الوقت. يعطي هذا الوضع أسفل الظهر. وينشط الجهاز الهضمي. \n\n", 0, "", "");
        this.demoDB.addActivityInformationDetails(31, "مانيباندا شاكراسانا", "تعتبر مانيباندا شاكراسانا وضعية يوغا دافئة. إنها وضعية يوغا على مستوى المبتدئين يتم إجراؤها في وضع الجلوس. يتضمن هذا الأسانا شد ولف معصميك. لذا ، ابدأ بالجلوس في Sukhasana. افرد ذراعيك أمامك واجعلهما على مسافة الكتفين. أخرج الزفير وأغلق الأصابع ، مع إدخال الإبهام أولاً. الآن قم بتدوير الرسغين في اتجاه عقارب الساعة وعكس اتجاه عقارب الساعة. يجب أن تظل الذراعين والأكواع مستقيمة وثابتة أثناء الدوران. اجعل دائرة كبيرة قدر الإمكان. كرر ذلك 10 مرات على الأقل. سيقلل هذا الأسانا من آلام مفصل الرسغ. كما أنه يخفف التوتر عن المعصم. عمل جيد.\n\n", 0, "", "");
        this.demoDB.addActivityInformationDetails(32, "مارجياسانا", "ابدأ بيديك وركبتيك على الأرض ، في وضع العمود الفقري المحايد. يجب أن تكون ركبتيك تحت وركيك ومعصميك تحت كتفيك. يجب أن يكون ظهرك مسطحًا وعضلات بطنك. خذ شهيقًا عميقًا كبيرًا. أثناء الزفير ، قم بتدوير عمودك الفقري لأعلى نحو السقف ، عن طريق سحب زر بطنك لأعلى باتجاه العمود الفقري ، مع إشراك عضلات البطن. المس ذقنك على صدرك ، واترك رقبتك تحرر. هذا هو موقفك مثل القط. مرة أخرى أثناء الاستنشاق ، ارخي بطنك ، وقوِّس ظهرك ، واسترخي. ارفع رأسك نحو السماء. هذا هو موقفك مثل البقر. كرر هذا الأسانا لمدة 10 جولات على الأقل. يزيد هذا الأسانا من المرونة ويقوي العمود الفقري ويخفف من آلام الدورة الشهرية.\n\n", 0, "", "");
        this.demoDB.addActivityInformationDetails(33, "ماتسياسانا", "ابدأ الوضع في شافاسانا. خذ شهيقًا وارفع صدرك لأعلى بحيث يتم رفع رأسك أيضًا. وتاجك يلامس الأرض. تأكد من أن وزن الجزء العلوي من جسمك على مرفقيك وليس على رأسك. شغل المنصب فقط حتى تشعر بالراحة. تنفس بشكل طبيعي. الزفير وتحرير الموقف. يحصل الحلق والجهاز الهضمي على تدليك جيد بهذه الوضعية.\n\n", 0, "", "");
        this.demoDB.addActivityInformationDetails(34, "ناماسكارا", "في هذه الوضعية ، يجب أن يكون جسمك منتصبًا والعمود الفقري طويلًا. تمتد الأذرع فوق الرأس مع تلامس راحة اليد مثل القيام ناماسكارا. يمكن أن تكون القدمان معًا أو مثبتة بعرض الكتفين. ابق في هذا الوضع عن طريق الاستنشاق والزفير ببطء.", 0, "", "");
        this.demoDB.addActivityInformationDetails(35, "ناتاراجاسانا اليسار", "ابدأ أسانا في Mountain Pose أو Tadasana. مع ضم قدميك وذراعيك إلى جانبيك. حول وزنك على قدمك اليسرى. اثنِ مؤخر الركبة اليمنى وجلب الكعب الأيمن نحو أردافك اليمنى. مد يدك اليمنى لأسفل وشبك الكاحل الداخلي لقدمك اليمنى. قم بمد اليد اليسرى بشكل مستقيم موازٍ للأرض انحنى قليلاً إلى الأمام عن طريق سحب الركبة اليمنى للخلف. امسك هذا الوضع لأطول فترة ممكنة. ثم حرر الرجل اليسرى وعد إلى وضع الوقوف. كرر ذلك من 5 إلى 6 مرات في البداية.\n\n", 0, "", "");
        this.demoDB.addActivityInformationDetails(36, "ناتاراجاسانا حق", "ابدأ أسانا في Mountain Pose أو Tadasana. مع ضم قدميك وذراعيك إلى جانبيك. انقل وزنك إلى قدمك اليمنى. ثني ركبتك اليسرى المؤخرة وجلب الكعب الأيسر نحو الأرداف اليسرى مد يدك اليسرى لأسفل وشبك الكاحل الداخلي لقدمك اليسرى. قم بمد اليد اليمنى بشكل مستقيم ، بالتوازي مع الأرض. انحنى قليلاً إلى الأمام عن طريق سحب الركبة اليسرى للخلف. امسك هذا الوضع لأطول فترة ممكنة. ثم حرر الساق اليمنى وعد إلى وضع الوقوف. كرر ذلك من 5 إلى 6 مرات في البداية.\n\n", 0, "", "");
        this.demoDB.addActivityInformationDetails(37, "بريفريتا سوكاسانا اليسار", "اجلس مستقيماً على الأرض مع وضع القرفصاء. أبقِ ذراعيك على فخذيك. عند الشهيق ، لف جذعك إلى الجانب الأيسر عن طريق وضع ذراعك اليمنى على ركبتك اليسرى والذراع الأيسر خلف ظهرك. امسك هذا الوضع لبضع ثوان. عند الزفير ، عد إلى الخلف وكرر مرة أخرى\n\n", 0, "", "");
        this.demoDB.addActivityInformationDetails(38, "بريفريتا سوكاسانا حق", "اجلس مستقيماً على الأرض مع وضع القرفصاء. أبقِ ذراعيك على فخذيك. عند الشهيق ، لف جذعك إلى الجانب الأيمن عن طريق وضع ذراعك اليسرى على الركبة اليمنى والذراع الأيمن إلى الخلف من ظهرك. امسك هذا الوضع لبضع ثوان. زفر ، عد إلى الوراء وكرر مرة أخرى.\n\n", 0, "", "");
        this.demoDB.addActivityInformationDetails(39, "بارسفا سوخاسانا اليسار", "ابدأ هذا الوضع بالجلوس مع القرفصاء. استرخاء الذراعين على جانبيك. الآن مد ذراعيك إلى جانبيك إلى مستقيم. اثنِ يدك اليسرى إلى الجانب الأيسر بثني خصرك إلى اليسار. إراحة اليد اليمنى على الأرض. امسك هذا الوضع لأطول فترة ممكنة. عد وكرر مرة أخرى.\n\n", 0, "", "");
        this.demoDB.addActivityInformationDetails(40, "بارسفا سوخاسانا حق", "ابدأ هذا الوضع بالجلوس مع القرفصاء. استرخاء الذراعين على جانبيك. الآن مد ذراعيك إلى جانبيك إلى مستقيم. ثني اليد اليمنى إلى الجانب الأيمن ، عن طريق ثني خصرك إلى اليمين. إراحة اليد اليسرى على الأرض. امسك هذا الوضع لأطول فترة ممكنة. عد وكرر مرة أخرى.\n\n", 0, "", "");
        this.demoDB.addActivityInformationDetails(41, "بارسفا تاداسانا", "قف بالقدمين معًا. رفع الأذرع نحو السقف وتعشيق الراحتين. عند الشهيق ، ثني خصرك إلى الجانب الأيمن ، عن طريق ثني الذراعين إلى اليمين. امسك هذا الوضع لبضع ثوان. الزفير والعودة إلى وضع الوقوف. الآن انحنى إلى الجانب الأيسر أثناء الاستنشاق. امسك الوضع لبعض الوقت. عد وكرر 10 مرات على كل جانب. تستمر في فعل.\n\n", 0, "", "");
        this.demoDB.addActivityInformationDetails(42, "بارسفوتاناسانا اليسار", "ابدأ أسانا بالوقوف مع رجلك اليسرى للأمام. افرد يديك نحو السقف. عند الشهيق ، ثني جذعك للأمام تجاه رجلك اليسرى. المس رأسك بساقك. حاول أن تريح راحتي يديك على الأرض. امسك هذا الوضع لأطول فترة ممكنة. في الزفير ، عد إلى الوضع الأولي وكرر.\n\n", 0, "", "");
        this.demoDB.addActivityInformationDetails(43, "بارسفوتاناسانا حق", "ابدأ أسانا بالوقوف مع رجلك اليمنى إلى الأمام. افرد يديك نحو السقف. عند الشهيق ، ثني جذعك للأمام نحو ساقك اليمنى المس رأسك بساقك. حاول أن تريح راحتي يديك على الأرض. امسك هذا الوضع لأطول فترة ممكنة. في الزفير ، عد إلى الوضع الأولي وكرر.\n\n", 0, "", "");
        this.demoDB.addActivityInformationDetails(44, "بارفاتاسانا", "اجلس على الأرض مع تشبيك رجليك. ارفع كلتا اليدين. امسك هذا الوضع لأطول فترة ممكنة. استنشق وازفر بعمق في هذا الوضع. هذا الوضع يمد عمودك الفقري ويهدئ عقلك. ازفر ببطء. وانزل اليدين. كرر ذلك 5 مرات على الأقل.\n\n", 0, "", "");
        this.demoDB.addActivityInformationDetails(45, "باشيموتاناسانا اليسار", "ابدأ بالجلوس على الأرض مع استقامة الساقين. ثني الركبة اليمنى وإحضارها إلى الحوض. أبقِ الذراعين على خصرك في البداية. عند الشهيق ، ارفع كلا الذراعين نحو السقف. انحنى للأمام واسحب الرجل اليسرى الممتدة بمساعدة قطعة قماش أو أي مادة أخرى ثم انحنى بالكامل. حاول أن تلمس رأسك بركبتك اليسرى عن طريق سحب القدمين الممتدة. امسك هذا الوضع لبعض الوقت. الزفير. عد إلى وضع البداية وكرر. يهدئ هذا الأسانا العقل ويخفف أيضًا من الاكتئاب الخفيف والضغط. يتم تنشيط الكلى والكبد والرحم والمبيض. تساعد ممارسة هذا الأسانا بانتظام على تحسين الهضم. استمر في العمل لبضع ثوان. \n\n", 0, "", "");
        this.demoDB.addActivityInformationDetails(46, "باشيموتاناسانا حق", "ابدأ بالجلوس على الأرض مع استقامة الأرجل. ثني الركبة اليسرى وإحضارها إلى الحوض. أبقِ الذراعين على خصرك في البداية. عند الشهيق ، ارفع كلا الذراعين نحو السقف. انحنى للأمام واسحب الرجل اليمنى الممتدة بمساعدة قطعة قماش أو أي مادة أخرى ثم انحنى بالكامل. حاول أن تلمس رأسك بركبتك اليمنى بسحب القدمين الممتدة. امسك هذا الوضع لبعض الوقت. الزفير. عد إلى وضع البداية وكرر. يقال إن باشيموتاناسانا يعالج الأمراض ويزيد الشهية ويقلل من السمنة. يعمل هذا الأسانا بشكل جيد للنساء بعد إنجاب طفل. يمكن محاربة انقطاع الطمث وانزعاج الدورة الشهرية مع هذا الأسانا. جيد ..\n\n", 0, "", "");
        this.demoDB.addActivityInformationDetails(47, "بافاناموكتاسانا", "ابدأ الأسانا بالاستلقاء على الأرض في مواجهة السقوط. الآن ثني ركبتيك وجذبهما نحو صدرك. الآن قم بتدوير ساقيك وركبتيك عن طريق تحريك الورك ، في اتجاه عقارب الساعة وعكس اتجاه عقارب الساعة. استنشق وازفر أثناء الدوران. قم بالدوران على الأقل 5 مرات في كل اتجاه. أنزلي ساقيك ، استرخي وكرر ذلك من 4 إلى 5 مرات. يساعد بافاناموكتاسانا بشكل خاص مرضى السكري. واحدة من الفوائد الرئيسية لهذا الأسانا هو أنه يساعد على تقوية الكبد والطحال والبنكرياس والبطن إلى جانب عضلات البطن. جيد.\n\n", 0, "", "");
        this.demoDB.addActivityInformationDetails(48, "براناياما", "ابدأ البراناياما بالوقوف مع القدمين معًا وشبك أصابعك أمام معدتك. اغلق عينيك. الآن ببطء خذ نفسًا عميقًا وازفر ببطء. كرر دورات الشهيق والزفير لمدة 2 إلى 3 دقائق.\n\n", 0, "", "");
        this.demoDB.addActivityInformationDetails(49, "براساريتا بادوتاناسانا اليسار", "قف في البداية مع وضع القدمين على مسافة ضعف عرض الكتفين. تمد الذراعين مباشرة إلى جانبيك. عند الشهيق ، المس راحة اليد اليسرى على الأرض بالانحناء للأمام. ومد الذراع اليمنى مباشرة نحو السقف. امسك الوضع لأطول فترة ممكنة. الزفير والعودة. كرر أسانا على أتليت 10 مرات. يساعد براساريتا بادوتاناسانا على فتح الوركين.\n\n", 0, "", "");
        this.demoDB.addActivityInformationDetails(50, "براساريتا بادوتاناسانا حق", "قف في البداية مع وضع القدمين على مسافة ضعف عرض الكتفين. تمد الذراعين مباشرة إلى جانبيك. عند الشهيق ، المس راحة اليد اليمنى على الأرض عن طريق الانحناء للأمام. ومد الذراع اليسرى مباشرة نحو السقف. امسك الوضع لأطول فترة ممكنة. الزفير والعودة. كرر أسانا على أتليت 10 مرات. يمتد هذا الأسانا ويفتح أوتار الركبة وعضلة الفخذ ، وبالتالي يزيد من قوة العضلات ومرونتها.\n\n", 0, "", "");
        this.demoDB.addActivityInformationDetails(51, "بورفوتاناسانا اليسار", "ابدأ أسانا بالجلوس على الأرض. مع الوركين قليلا من الأرض. ثني الركبة اليسرى والحفاظ على الساق اليمنى مستقيمة. ضع يديك من 6 إلى 8 بوصات خلف الأرداف. ضع وزنك بالكامل على راحتي اليدين والقدمين. عند الشهيق ، ارفع حوضك ببطء عن الأرض باتجاه السقف عن طريق شد الأرداف. والحفاظ على الذراعين مستقيمين ، مع توجيه أصابع القدم إلى الأرض. الآن يتم دعم الجسم على اليدين والقدمين. عند الزفير ، ارفعي حوضك ببطء وكرر ذلك من 10 إلى 15 مرة.\n\n", 0, "", "");
        this.demoDB.addActivityInformationDetails(52, "بورفوتاناسانا حق", "ابدأ أسانا بالجلوس على الأرض. مع ارتفاع الورك قليلاً عن الأرض. ثني الركبة اليمنى والحفاظ على الساق اليسرى مستقيمة. ضع يديك من 6 إلى 8 بوصات خلف الأرداف. ضع وزنك بالكامل على راحتي اليدين والقدمين. عند الشهيق ، ارفع حوضك ببطء عن الأرض باتجاه السقف عن طريق شد الأرداف. والحفاظ على الذراعين مستقيمين ، مع توجيه أصابع القدم إلى الأرض. الآن يتم دعم الجسم على اليدين والقدمين. عند الزفير ، ارفعي حوضك ببطء وكرر ذلك من 10 إلى 15 مرة.\n\n", 0, "", "");
        this.demoDB.addActivityInformationDetails(53, "سالامبا بوجانجاسانا", "ابدأ بالتمدد على بطنك مع وضع أصابع قدمك على الأرض والجبهة على الأرض. حافظ على ساقيك قريبين من بعضهما البعض. أبقِ مرفقيك على الأرض ، ومد يديك أمامك ، وراحتا الراحتان تواجهان الأرض. عند الشهيق ، ارفع رأسك لأعلى ، واضعًا كل القوة على المعدة. ابق في هذا الموقف لبعض الوقت. ازفر واخفض رأسك وكرر ذلك. \n\n", 0, "", "");
        this.demoDB.addActivityInformationDetails(54, "ساسانجاسانا", "ابدأ في وضع ساسانجاسانا في وضع الطفل. تمسك بالكعب باليدين. واسحب الجبهة للداخل باتجاه الركبتين ، بحيث يكون الجزء العلوي من الرأس على الأرض. امسك الكعبين بإحكام ، واستنشق وارفع الوركين نحو السقف. لفة على تاج الرأس. واضغط على الجبهة أقرب ما يمكن من الركبتين. امسك الوضع لعدة أنفاس الزفير ببطء. ونخفض الوركين إلى الكعبين. حرك الجبهة مرة أخرى إلى الأرض في وضع الطفل. ساسانجاسانا هو وضع الانحناء إلى الأمام. يهدئ العقل ويخفف التوترات حول الرقبة.\n\n", 0, "", "");
        this.demoDB.addActivityInformationDetails(55, "سيتو باندا سارفانجاسانا", "يعتبر سيتو باندا سارفانجاسانا مفيدًا جدًا لآلام الظهر وفقدان الوزن. لتبدأ ، استلق على ظهرك وذراعيك لأسفل. اثنِ ركبتيك واجعل قدميك مسطحة على الأرض. ارفع وركيك نحو السقف. شغل هذا المنصب لبضع ثوان وكرر ذلك. السبب الرئيسي للسمنة هو الدهون في البطن والخصر. إذا كنت تمارس هذا الأسانا بانتظام ، فهذا لا يمنحك الدهون في معدتك وخصرك ، لذا فهو يجعلك لائقًا.\n\n", 0, "", "");
        this.demoDB.addActivityInformationDetails(56, "سيدهاسانا", "ابدأ بالجلوس بشكل مريح. ضع يديك على ركبتيك بحيث تكون راحة اليد متجهة للخارج. اجلس معتدلا. الآن أغلق عينيك وخذ نفسًا عميقًا ببطء ، واجلس لفترة من الوقت. ازفر ببطء واسترخ. كرر ذلك من 10 إلى 15 مرة. هذا يهدئ عقلك.\n\n", 0, "", "");
        this.demoDB.addActivityInformationDetails(57, "سكاندا شقرا", "سكاندا شقرا هي وضعية يوغا على مستوى المبتدئين يتم إجراؤها في وضع بلا ، لذا قف بشكل طبيعي. عند الشهيق ، اسحب كتفك فقط ، قم بتدويرها ، واسحبها للأسفل ثم قم بالزفير. قم بتدوير الكتف في اتجاه عقارب الساعة وعكس اتجاه عقارب الساعة. كرر هذا الدوران لأتت 10 إلى 15 مرة. يخفف هذا الأسانا من إجهاد القيادة والعمل المكتبي ويساعد أيضًا في تخفيف التهاب الفقار العنقي والكتفين المتجمدة. استمر في العمل لبضع ثوان.\n\n", 0, "", "");
        this.demoDB.addActivityInformationDetails(58, "سكاندا شاكراسانا", "ابدأ بركب ركبتيك بوضع يديك على فخذيك. حرر أصابع قدميك على الأرض. وافصل بين ركبتيك حول عرض الورك. ارفع كلا الذراعين وأبق أصابعك على كتفك. ارفع مرفقيك. قم بتدوير مرفقيك عن طريق تدوير يدك. مثل هذا قم بتدوير ذراعيك بمساعدة المرفقين. سوف يريح كتفيك. كرر الدوران في اتجاه عقارب الساعة وعكس اتجاه عقارب الساعة.\n\n", 0, "", "");
        this.demoDB.addActivityInformationDetails(59, "سوبتا بادها كوناسانا", "ابدأ الأسانا في شافاسانا. اثنِ ركبتيك واسحب كعبيك نحو حوضك. أسقط ركبتيك على الجانبين. حافظ على الوضع لمدة تصل إلى دقيقة واحدة. ممارسة هذا الأسانا ينشط المبايض وغدة البروستاتا والكلى والمثانة. \n\n", 0, "", "");
        this.demoDB.addActivityInformationDetails(60, "سوبتا بادانجوستاسانا اليسار", "ابدأ هذا أسانا في شافاسانا. عند الشهيق ، وبمساعدة الداعم ، ارفع الساق اليسرى لأعلى ويجب أن تكون الركبة مستقيمة. ثم حاول سحب الساق اليسرى نحوك عن طريق إبقاء الساق اليمنى مستقيمة على الأرض. امسك هذا الوضع لبعض الوقت. عند الزفير ، حرر الساق عن طريق إنزالها ببطء ثم كرر. يساعد هذا الأسانا في علاج القدم المسطحة وضغط الدم والعقم.\n\n", 0, "", "");
        this.demoDB.addActivityInformationDetails(61, "سوبتا بادانجوستاسانا حق", "ابدأ هذا أسانا في شافاسانا. عند الشهيق ، وبمساعدة الداعم ، ارفع الرجل اليمنى لأعلى ويجب أن تكون الركبة مستقيمة. ثم حاول سحب الساق اليمنى نحوك عن طريق إبقاء الساق اليسرى مستقيمة على الأرض. امسك هذا الوضع لبعض الوقت. عند الزفير ، حرر الساق عن طريق إنزالها ببطء ثم كرر. يخفف هذا الأسانا من عدم الراحة في آلام الظهر وعرق النسا ومشاكل الدورة الشهرية. وتصبح الركبتان قويتين.\n\n", 0, "", "");
        this.demoDB.addActivityInformationDetails(62, "سوريا ناماسكارا", "يتضمن سوريا namaskara 12 خطوة. لذا ، دعونا نبدأ صوريا ناماسكارا في الصلاة. قف بشكل مستقيم وانضم إلى يديك على صدرك. تنفس ورفع ذراعيك لأعلى وانحني قليلاً للخلف. أخرج الزفير وانحني للأمام من خصرك. لمس اليدين على الأرض. الآن تنفس وادفع الساق اليسرى للخلف. ثني الساق اليمنى للأمام وانظر ببطء. بعد ذلك تنفس وقم بعمل وضعية adho mukha svanasana. تعال الآن. اخفض جسدك نحو الأرض. ارفع الوركين قدر المستطاع ، عن طريق الضغط على راحة اليد على الأرض. ثم اخفض وركك. انحني للخلف من خصرك بقدر ما تستطيع ، عن طريق الضغط على راحة اليد على الأرض. الآن تنفس وقم بوضعية أذهو موكا سفاناسانا. ثم اجلب ساقك اليمنى للأمام وانظر لأعلى. الآن انحنى للأمام من الخصر مع القدمين معًا. ثم تنفس. ارفع يديك. ينحني قليلاً. وأخيراً عد إلى وضع الصلاة. هنا تكتمل جولة واحدة من سوريا ناماسكارا. استرخ لبضع ثوان. وكرر الأسانا بتبادل الأرجل في الخطوة 4 و 9. في البداية قم بإجراء 5 جولات فقط ، إذا استطعت. ثم قم بزيادة الجولات تدريجياً. \n\n", 0, "", "");
        this.demoDB.addActivityInformationDetails(63, "تاداسانا", "قف بشكل طبيعي مع وضع القدمين معًا والذراعين بجانبك. عند الشهيق ، ارفع ذراعيك ببطء ، في مواجهة السقف. ثم قرب ذراعيك ببطء من بعضهما البعض وانضم إلى كل من راحتي اليد في الأعلى. امسك هذا الوضع لبضع ثوان. في الزفير ، أنزل ذراعيك ببطء وكرر. \n\n", 0, "", "");
        this.demoDB.addActivityInformationDetails(64, "تيريا تاداسانا", "تيريا تاداسانا يشبه امتداد شجرة متأرجح. ربما تكون قد رأيت هذا الوضع على الأشجار عندما تهب الرياح. للقيام بذلك ، قف مع القدمين متباعدتين قليلاً. افرد يديك لأعلى وشبك أصابع كلتا يديك. الآن انحنى من الخصر إلى الجانب الأيمن. امسك الوضع لبضع ثوان. ثم عد إلى وضع الوقوف. افعل ذلك الآن بالجانب الآخر ، بالانحناء من الخصر إلى الجانب الأيسر. امسك الوضع لبضع ثوان. ثم عد إلى الموضع الأولي. كرر ما يصل إلى 10 جولات لكل جانب. يساعد هذا الأسانا في التخفيف من عسر الهضم ، والإمساك ، والحموضة ، وتقلصات الدورة الشهرية ، والربو ، وحب الشباب ، والدمامل وما إلى ذلك ، كما أنه ينظف ويقوي الجهاز الهضمي بأكمله. جيد. استمر في العمل لبضع ثوان.\n\n", 0, "", "");
        this.demoDB.addActivityInformationDetails(65, "تريكوناسانا", "قف في البداية مع مباعدة رجليك. عند الشهيق ، ثني يدك اليمنى نحو الجانب الأيسر ، عن طريق ثني خصرك بصريًا. وحافظ على اليد الأخرى مستقيمة. امسك الوضع لبعض الوقت. عند الزفير ، ارفع اليد. الآن استنشق مرة أخرى ، وانحني يدك اليسرى إلى جانبك الأيمن ، عن طريق ثني الخصر قليلاً. إبقاء اليد الأخرى مستقيمة. امسك الوضع لبضع ثوان. ازفر ، وارفع اليد. كرر الأسانا من 10 إلى 15 مرة لكل جانب. في الأيام الأولى ، قد تشعر بإجهاد في خصرك. جيد.\n\n", 0, "", "");
        this.demoDB.addActivityInformationDetails(66, "أوفافيستا كوناسانا اليسار", "اجلس على الأرض مع وضع القدمين معًا وممدودًا للأمام. اثنِ ركبتك اليسرى واجعل قدمك اليسرى قريبة من حوضك. قم بالزفير وأنت تنحني عند الوركين ، وأنزل جذعك ببطء ومد كلا الذراعين للأمام. حافظ على الوضع لمدة 30 ثانية إلى دقيقة ثم كرر.\n\n", 0, "", "");
        this.demoDB.addActivityInformationDetails(67, "أوفافيستا كوناسانا حق", "اجلس على الأرض مع وضع القدمين معًا وممدودًا للأمام. اثنِ ركبتك اليمنى واجعل قدمك اليمنى قريبة من حوضك. قم بالزفير وأنت تنحني عند الوركين ، وأنزل جذعك ببطء ومد كلا الذراعين للأمام. حافظ على الوضع لمدة 30 ثانية إلى دقيقة ثم كرر.\n\n", 0, "", "");
        this.demoDB.addActivityInformationDetails(68, "أوردفا هاستاسانا", "قف في البداية مع مباعدة الساقين قليلاً. أدر ذراعيك للخارج. استنشق الآن وأخرج ذراعيك إلى الجانبين ببطء وصعود إلى السقف. ارفع يديك لأعلى لبضع ثوان. ستعمل هذه الوضعية على شد عضلات البطن وبالتالي تقوية هذه العضلات لتحسين عملية الهضم. مرر ذراعيك مرة أخرى للأسفل والجوانب أثناء الزفير كرر ذلك من 10 إلى 15 مرة. تستمر في فعل.\n\n", 0, "", "");
        this.demoDB.addActivityInformationDetails(69, "أوتكاتاسانا", "في هذا الوضع ، قف مع قدميك معًا. افرد ذراعك أمامك بحيث تكون راحتا اليد في مواجهة الأرض. عند الشهيق ، اثني ركبتيك. يجب أن يكون العمود الفقري طويلًا ، اسحب لوحي كتفك لأسفل. أعتقد أنك تجلس على كرسي. ابق في هذا الوضع لبضع ثوان. قم بالزفير والعودة إلى الوضع الأولي وكرر ذلك. يساعد هذا الأسانا في تقوية الكاحلين والفخذين. ويحفز أعضاء البطن\n\n", 0, "", "");
        this.demoDB.addActivityInformationDetails(70, "أوتاناسانا", "ابدأ الوضع بالوقوف مع القدمين معًا. الأيدي بجانبك. عند الشهيق ، ارفع يديك. انحن ببطء للأمام وحاول أن تلمس راحتي يديك على الأرض. يمكنك ثني ركبتيك قليلاً. امسك هذا الوضع لأطول فترة ممكنة. يخلق قوة جر للعمود الفقري. هذا يزيد من المرونة في العمود الفقري والوركين وظهر الساقين. هذا أسانا له تأثير مهدئ ويقلل من القلق.\n\n", 0, "", "");
        this.demoDB.addActivityInformationDetails(71, "أوتان باداسانا", "ابدأ الوضع بالاستلقاء على ظهرك مع فرد ساقيك وذراعيك. يجب أن تكون قدميك معًا. عند الشهيق ، ارفع ساقيك ببطء دون ثني الركبتين ، حوالي 30 درجة. استمر لمدة 5 ثوان. ثم ارفع إلى 60 درجة. امسك هنا أيضًا لمدة 5 ثوانٍ. ثم ارفع حتى 90 درجة. استمر في هذا الوضع لأكثر من 5 ثوانٍ إذا استطعت. عند الزفير ، أنزل ساقيك ببطء دون ثني الركبتين مرة أخرى ، بالضغط على 60 درجة و 30 درجة. وأقل من 0 درجة. خذ قسطًا من الراحة لبضع ثوان. ثم كرر الوضع من 5 إلى 6 مرات. يقوي عضلات البطن ويقوم أيضًا بتدليك أعضاء الأمعاء. أوتان باداسانا مفيد للغاية في علاج الحموضة والإمساك والعديد من أمراض المعدة الأخرى. يقوي عضلات أسفل الظهر ويساعد على تخفيف آلام أسفل الظهر. كما أنه يقوي عضلات المأبض. وأخيرًا هذه الوضعية ممتازة للرياضيين لأنها تمنح الجسم التوازن والقوة.\n\n", 0, "", "");
        this.demoDB.addActivityInformationDetails(72, "أوثيتا أشوا سانشالاناسانا اليسار", "ابدأ الأسانا في وضع الوقوف. حافظ على ذراعيك على خصرك. الآن خذ رجلك اليسرى إلى الأمام والساق اليمنى للخلف. شد الساقين بقدر ما تستطيع. عند الشهيق ، ثني جذعك للأمام. ابق في هذا الوضع لبضع ثوان. عند الزفير ، عد وكرر من 8 إلى 10 مرات.\n\n", 0, "", "");
        this.demoDB.addActivityInformationDetails(73, "أوثيتا أشوا سانشالاناسانا حق", "ابدأ الأسانا في وضع الوقوف. حافظ على ذراعيك على خصرك. الآن خذ رجلك اليمنى إلى الأمام والساق اليسرى للخلف. شد الساقين بقدر ما تستطيع. عند الشهيق ، ثني جذعك للأمام. ابق في هذا الوضع لبضع ثوان. عند الزفير ، عد وكرر من 8 إلى 10 مرات.\n\n", 0, "", "");
        this.demoDB.addActivityInformationDetails(74, "أوثيتا بارسفاكوناسانا اليسار", "ابدأ الوضع بالوقوف مع المباعدة بين الأرجل وعرض الكتفين. اثنِ ركبتك اليسرى عن طريق مد رجلك اليمنى للخارج. ثني خصرك إلى الجانب الأيسر. ارفع اليد اليمنى إلى السقف وثنيها إلى الجانب الأيسر ، مع إبقاء الكوع الأيسر على الركبة اليسرى. ابق في هذا الوضع لبعض الوقت. أنزل الذراع واسترخي وكرر ذلك على الأقل 5 مرات في البداية.\n\n", 0, "", "");
        this.demoDB.addActivityInformationDetails(75, "أوثيتا بارسفاكوناسانا حق", "ابدأ الوضع بالوقوف مع المباعدة بين الأرجل وعرض الكتفين. اثن ركبتك اليمنى عن طريق مد رجلك اليسرى للخارج. ثني خصرك إلى الجانب الأيمن. ارفع اليد اليسرى إلى السقف وثنيها إلى الجانب الأيمن عن طريق إبقاء الكوع الأيمن على الركبة اليمنى. ابق في هذا الوضع لبعض الوقت. أنزل الذراع واسترخي وكرر ذلك على الأقل 5 مرات في البداية.\n\n", 0, "", "");
        this.demoDB.addActivityInformationDetails(76, "أوثيتا تريكوناسانا", "الوقوف في البداية مع الساقين ضعف عرض الكتفين. أدر رجلك اليسرى إلى الجانب الأيسر والساق اليمنى قليلاً إلى الجانب الأيمن. مواجهة الجانب الأيسر ، شد ذراعيك للخارج ، يجب أن تكون مساوية لكتفك. اثني خصرك على الجانب الأيسر ، المس ذراعك اليسرى رجلك اليسرى ومدد ذراعك اليمنى لأعلى لتلمس السقف. ابق في هذا الوضع لبضع ثوان وكرر مع الجانب الآخر. يقوي هذا الأسانا ساقيك وقدميك وظهرك ورقبتك وبطنك وكاحليك. هذه الوضعية تمد الوركين والأربية وأوتار الركبة والعجول والعمود الفقري. استمر في العمل لبعض الوقت على كل جانب.\n\n", 0, "", "");
        this.demoDB.addActivityInformationDetails(77, "فاجراسانا", "ابدأ أسانا بوضعية جلوس القرفصاء. حافظ على راحة يدك على فخذيك. ارفع صدرك وإطالة عمودك الفقري. ادفع وركيك إلى كعبيك. الآن قم بتدوير رأسك إلى الجانب الأيسر ، بجانب الجانب الخلفي ، ثم إلى الجانب الأيمن وأخيرًا إلى الخط. عد إلى وضع البداية وكررها لبضع ثوان. هذا أسانا سوف يخفف آلام الرقبة وتيبس الرقبة استمر في القيام بذلك أسانا لمدة 10 دورات على الأقل يوميًا. يمكنك زيادة عدد الدورات للحصول على النتيجة بسرعة.\n\n", 0, "", "");
        this.demoDB.addActivityInformationDetails(78, "فاسيستاسانا اليسار", "ابدأ هذا أسانا ، مع Plank Pose. اجلب قدمك اليمنى في منتصف الطريق لأعلى السجادة واثني الركبة اليمنى وأدر أصابع القدم اليمنى للخارج. ارفع الذراع اليمنى نحو السقف. اثن قدمك اليسرى واضغط على كلا القدمين لرفع الوركين. ارفع نظرك إلى اليد اليمنى. خذ عدة أنفاس ثم حرر يدك اليمنى على الأرض. تراجع إلى أسفل الكلب وكرر.\n\n", 0, "", "");
        this.demoDB.addActivityInformationDetails(79, "فاسيستاسانا حق", "ابدأ هذا أسانا ، مع بلانك بوز. حرك وزنك برفق على الجانب الأيمن من ذراعك إلى قدمك. ضع اليد اليمنى أمام الكتف قليلاً ويجب أن تكون مستقيمة. والراحة اليمنى مضغوطة على الأرض. اجلب قدمك اليسرى في منتصف الطريق تقريبًا وضعها على القدم اليمنى. خذ شهيقًا وارفع ذراعك اليسرى نحو السقف. ارفع نظرك إلى اليد اليسرى. خذ عدة أنفاس. عند الزفير ، حرر ذراعك اليسرى لتستقر على وركك. عد إلى الوراء إلى وضع اللوح الخشبي وكرر.\n\n", 0, "", "");
        this.demoDB.addActivityInformationDetails(80, "فيباريتا سالاباسانا", "ابدأ الأسانا ، من خلال الاستلقاء على الأرض باتجاه الأرض. ضع يديك أسفل المعدة مباشرة. المس رأسك بالأرض. في الشهيق ، ارفع كلا الساقين ببطء لأعلى مع الضغط على المعدة. ابق في هذا الوضع بقدر ما تستطيع. عند الزفير ، اجلب كلا الساقين لأسفل وكرر ذلك من 5 إلى 10 مرات في البداية.\n\n", 0, "", "");
        this.demoDB.addActivityInformationDetails(81, "فيرابادراسانا اليسار", "قف مع الساقين ، ضاعف عرض الكتفين. حافظ على راحة اليد على خصرك. أدر القدم اليسرى إلى 90 درجة ، والقدم اليسرى واليمنى إلى 15 درجة ، والجانب الأيسر. الآن ثني الركبة اليسرى. مواجهة الجانب الأيسر ، مد ذراعيك مباشرة إلى جانبيك. امسك هذا الوضع لبضع ثوان. عد إلى وضع الوقوف الطبيعي وكرر الأسانا 10 مرات. من المعروف أن هذا الأسانا يقوي ويقوي أسفل الظهر والذراعين والساقين. يساعد على استقرار الجسم وتوازنه لأنه يزيد من القدرة على التحمل. تستمر في فعل.\n\n", 0, "", "");
        this.demoDB.addActivityInformationDetails(82, "فيرابادراسانا حق", "قف مع الساقين ، ضاعف عرض الكتفين. حافظ على راحة اليد على خصرك. أدر القدم اليمنى إلى 90 درجة ، إلى الجانب الأيمن والقدم اليسرى إلى 15 درجة ، إلى الجانب الأيمن. الآن ثني الركبة اليمنى. مواجهة الجانب الأيمن ، مد ذراعيك مباشرة إلى جانبيك امسك هذا الوضع لبضع ثوان. عد إلى وضع الوقوف الطبيعي وكرر الأسانا 10 مرات. هذا الأسانا يريح العقل والجسد ، وينشر فكرة السلام والشجاعة والنعمة والشعور بالخير. تستمر في فعل.\n\n", 0, "", "");
        this.demoDB.addActivityInformationDetails(83, "فريكشاسانا اليسار", "ابدأ أسانا بالوقوف في وضع محايد. افرد ذراعيك بحيث تكون راحتي اليدين في مواجهة الأرض ، ويجب أن تكون مساوية للكتف. اثنِ ركبتك اليسرى وحاول أن تلمس قدمك اليسرى الركبة اليمنى. امسك هذا الوضع لبعض الوقت. ارجع وكرر ذلك 5 مرات.\n\n", 0, "", "");
        this.demoDB.addActivityInformationDetails(84, "فريكشاسانا حق", "ابدأ أسانا بالوقوف في وضع محايد. افرد ذراعيك بحيث تكون راحتي اليدين في مواجهة الأرض ، ويجب أن تكون مساوية للكتف. اثنِ الركبة اليمنى وحاول أن تلمس قدمك اليمنى بالركبة اليسرى. امسك هذا الوضع لبعض الوقت. ارجع وكرر ذلك 5 مرات.\n\n", 0, "", "");
        this.demoDB.addActivityInformationDetails(85, "فياغراسانا اليسار", "ابدأ براحة يديك وركبتيك على الأرض. قم بتدوير عمودك الفقري لأعلى باتجاه السقف من خلال مواجهة الأرض. عند الشهيق ، ضع الركبة اليسرى باتجاه صدرك. وعند الزفير ، قم بمدها إلى الخلف وتحرك نحو السقف. مرة أخرى ، اجلب الركبة اليسرى نحو الصدر ثم شدها. كرر ذلك 10 مرات على الأقل.\n\n", 0, "", "");
        this.demoDB.addActivityInformationDetails(86, "فياغراسانا حق", "ابدأ براحة يديك وركبتيك على الأرض. قم بتدوير عمودك الفقري لأعلى باتجاه السقف من خلال مواجهة الأرض. عند الشهيق ، ضع الركبة اليسرى باتجاه صدرك. وعند الزفير ، قم بمدها إلى الخلف وتحرك نحو السقف. مرة أخرى ، اجلب الركبة اليسرى نحو الصدر ثم شدها. كرر ذلك 10 مرات على الأقل.\n\n", 0, "", "");
        this.demoDB.addActivityInformationDetails(87, "شافاسانا", "استلق على الأرض عن طريق وضع ساقيك وذراعيك بعيدًا عن بعضهما البعض. يجب أن تواجه أصابع قدميك الجوانب وأن تكون راحة اليد متجهة لأعلى. أغمض عينيك وكن مرتاحًا. الآن تنفس ببطء ، ولكن بعمق ، واجعل جسمك في حالة استرخاء. قم بالشهيق والزفير لمدة 5 دقائق للاسترخاء التام.\n\n", 0, "", "");
    }


    public void storeDayWiseActivityInformation() {
        this.demoDB.addDayWiseActivityDetails(1, 34, 2, 16, 1, 0);
        this.demoDB.addDayWiseActivityDetails(1, 65, 2, 10, 2, 0);
        this.demoDB.addDayWiseActivityDetails(1, 68, 2, 6, 3, 0);
        this.demoDB.addDayWiseActivityDetails(1, 57, 2, 6, 4, 0);
        this.demoDB.addDayWiseActivityDetails(1, 32, 2, 18, 5, 0);
        this.demoDB.addDayWiseActivityDetails(1, 29, 2, 18, 6, 0);
        this.demoDB.addDayWiseActivityDetails(1, 77, 2, 6, 7, 0);
        this.demoDB.addDayWiseActivityDetails(1, 31, 2, 6, 8, 0);
        this.demoDB.addDayWiseActivityDetails(1, 87, 2, 20, 9, 0);
        this.demoDB.addDayWiseActivityDetails(1, 25, 2, 10, 10, 0);
        this.demoDB.addDayWiseActivityDetails(2, 29, 2, 25, 1, 0);
        this.demoDB.addDayWiseActivityDetails(2, 28, 2, 15, 2, 0);
        this.demoDB.addDayWiseActivityDetails(2, 64, 2, 14, 3, 0);
        this.demoDB.addDayWiseActivityDetails(2, 57, 2, 6, 4, 0);
        this.demoDB.addDayWiseActivityDetails(2, 69, 2, 24, 5, 0);
        this.demoDB.addDayWiseActivityDetails(2, 15, 2, 15, 6, 0);
        this.demoDB.addDayWiseActivityDetails(2, 53, 2, 22, 7, 0);
        this.demoDB.addDayWiseActivityDetails(2, 7, 2, 22, 8, 0);
        this.demoDB.addDayWiseActivityDetails(2, 8, 2, 25, 9, 0);
        this.demoDB.addDayWiseActivityDetails(2, 55, 2, 28, 10, 0);
        this.demoDB.addDayWiseActivityDetails(3, 63, 2, 18, 1, 0);
        this.demoDB.addDayWiseActivityDetails(3, 70, 2, 22, 2, 0);
        this.demoDB.addDayWiseActivityDetails(3, 76, 2, 10, 3, 0);
        this.demoDB.addDayWiseActivityDetails(3, 30, 2, 22, 4, 0);
        this.demoDB.addDayWiseActivityDetails(3, 80, 2, 20, 5, 0);
        this.demoDB.addDayWiseActivityDetails(3, 85, 2, 20, 6, 0);
        this.demoDB.addDayWiseActivityDetails(3, 86, 2, 20, 7, 0);
        this.demoDB.addDayWiseActivityDetails(3, 58, 2, 6, 8, 0);
        this.demoDB.addDayWiseActivityDetails(3, 26, 2, 10, 9, 0);
        this.demoDB.addDayWiseActivityDetails(3, 47, 2, 10, 10, 0);
        this.demoDB.addDayWiseActivityDetails(4, 72, 2, 20, 1, 0);
        this.demoDB.addDayWiseActivityDetails(4, 73, 2, 20, 2, 0);
        this.demoDB.addDayWiseActivityDetails(4, 63, 2, 20, 3, 0);
        this.demoDB.addDayWiseActivityDetails(4, 44, 2, 20, 4, 0);
        this.demoDB.addDayWiseActivityDetails(4, 17, 2, 20, 5, 0);
        this.demoDB.addDayWiseActivityDetails(4, 18, 2, 20, 6, 0);
        this.demoDB.addDayWiseActivityDetails(4, 16, 2, 8, 7, 0);
        this.demoDB.addDayWiseActivityDetails(4, 9, 2, 25, 8, 0);
        this.demoDB.addDayWiseActivityDetails(4, 51, 2, 25, 9, 0);
        this.demoDB.addDayWiseActivityDetails(4, 52, 2, 25, 10, 0);
        this.demoDB.addDayWiseActivityDetails(5, 0, 2, 0, 0, 0);
        this.demoDB.addDayWiseActivityDetails(6, 76, 2, 10, 1, 0);
        this.demoDB.addDayWiseActivityDetails(6, 4, 2, 22, 2, 0);
        this.demoDB.addDayWiseActivityDetails(6, 5, 2, 22, 3, 0);
        this.demoDB.addDayWiseActivityDetails(6, 2, 2, 20, 4, 0);
        this.demoDB.addDayWiseActivityDetails(6, 19, 2, 25, 5, 0);
        this.demoDB.addDayWiseActivityDetails(6, 20, 2, 25, 6, 0);
        this.demoDB.addDayWiseActivityDetails(6, 21, 2, 20, 7, 0);
        this.demoDB.addDayWiseActivityDetails(6, 22, 2, 20, 8, 0);
        this.demoDB.addDayWiseActivityDetails(6, 47, 2, 15, 9, 0);
        this.demoDB.addDayWiseActivityDetails(6, 6, 2, 12, 10, 0);
        this.demoDB.addDayWiseActivityDetails(7, 83, 2, 20, 1, 0);
        this.demoDB.addDayWiseActivityDetails(7, 84, 2, 20, 2, 0);
        this.demoDB.addDayWiseActivityDetails(7, 30, 2, 22, 3, 0);
        this.demoDB.addDayWiseActivityDetails(7, 74, 2, 25, 4, 0);
        this.demoDB.addDayWiseActivityDetails(7, 75, 2, 25, 5, 0);
        this.demoDB.addDayWiseActivityDetails(7, 3, 2, 30, 6, 0);
        this.demoDB.addDayWiseActivityDetails(7, 39, 2, 25, 7, 0);
        this.demoDB.addDayWiseActivityDetails(7, 40, 2, 25, 8, 0);
        this.demoDB.addDayWiseActivityDetails(7, 59, 2, 25, 9, 0);
        this.demoDB.addDayWiseActivityDetails(7, 33, 2, 20, 10, 0);
        this.demoDB.addDayWiseActivityDetails(8, 65, 2, 12, 1, 0);
        this.demoDB.addDayWiseActivityDetails(8, 49, 2, 22, 2, 0);
        this.demoDB.addDayWiseActivityDetails(8, 50, 2, 22, 3, 0);
        this.demoDB.addDayWiseActivityDetails(8, 13, 2, 20, 4, 0);
        this.demoDB.addDayWiseActivityDetails(8, 11, 2, 20, 5, 0);
        this.demoDB.addDayWiseActivityDetails(8, 12, 2, 20, 6, 0);
        this.demoDB.addDayWiseActivityDetails(8, 66, 2, 18, 7, 0);
        this.demoDB.addDayWiseActivityDetails(8, 67, 2, 18, 8, 0);
        this.demoDB.addDayWiseActivityDetails(8, 71, 2, 10, 9, 0);
        this.demoDB.addDayWiseActivityDetails(8, 26, 2, 8, 10, 0);
        this.demoDB.addDayWiseActivityDetails(9, 42, 2, 20, 1, 0);
        this.demoDB.addDayWiseActivityDetails(9, 43, 2, 20, 2, 0);
        this.demoDB.addDayWiseActivityDetails(9, 81, 2, 15, 3, 0);
        this.demoDB.addDayWiseActivityDetails(9, 82, 2, 15, 4, 0);
        this.demoDB.addDayWiseActivityDetails(9, 27, 2, 45, 5, 0);
        this.demoDB.addDayWiseActivityDetails(9, 54, 2, 12, 6, 0);
        this.demoDB.addDayWiseActivityDetails(9, 23, 2, 20, 7, 0);
        this.demoDB.addDayWiseActivityDetails(9, 24, 2, 20, 8, 0);
        this.demoDB.addDayWiseActivityDetails(9, 60, 2, 10, 9, 0);
        this.demoDB.addDayWiseActivityDetails(9, 61, 2, 10, 10, 0);
        this.demoDB.addDayWiseActivityDetails(10, 0, 2, 0, 0, 0);
        this.demoDB.addDayWiseActivityDetails(11, 35, 2, 30, 1, 0);
        this.demoDB.addDayWiseActivityDetails(11, 36, 2, 30, 2, 0);
        this.demoDB.addDayWiseActivityDetails(11, 65, 2, 12, 3, 0);
        this.demoDB.addDayWiseActivityDetails(11, 68, 2, 10, 4, 0);
        this.demoDB.addDayWiseActivityDetails(11, 70, 2, 20, 5, 0);
        this.demoDB.addDayWiseActivityDetails(11, 78, 2, 28, 6, 0);
        this.demoDB.addDayWiseActivityDetails(11, 79, 2, 28, 7, 0);
        this.demoDB.addDayWiseActivityDetails(11, 27, 2, 45, 8, 0);
        this.demoDB.addDayWiseActivityDetails(11, 87, 2, 25, 9, 0);
        this.demoDB.addDayWiseActivityDetails(11, 25, 2, 15, 10, 0);
        this.demoDB.addDayWiseActivityDetails(12, 69, 2, 24, 1, 0);
        this.demoDB.addDayWiseActivityDetails(12, 57, 2, 10, 2, 0);
        this.demoDB.addDayWiseActivityDetails(12, 28, 2, 16, 3, 0);
        this.demoDB.addDayWiseActivityDetails(12, 64, 2, 20, 4, 0);
        this.demoDB.addDayWiseActivityDetails(12, 32, 2, 15, 5, 0);
        this.demoDB.addDayWiseActivityDetails(12, 29, 2, 25, 6, 0);
        this.demoDB.addDayWiseActivityDetails(12, 1, 2, 15, 7, 0);
        this.demoDB.addDayWiseActivityDetails(12, 14, 2, 15, 8, 0);
        this.demoDB.addDayWiseActivityDetails(12, 55, 2, 30, 9, 0);
        this.demoDB.addDayWiseActivityDetails(12, 26, 2, 15, 10, 0);
        this.demoDB.addDayWiseActivityDetails(13, 69, 2, 25, 1, 0);
        this.demoDB.addDayWiseActivityDetails(13, 70, 2, 25, 2, 0);
        this.demoDB.addDayWiseActivityDetails(13, 64, 2, 16, 3, 0);
        this.demoDB.addDayWiseActivityDetails(13, 76, 2, 15, 4, 0);
        this.demoDB.addDayWiseActivityDetails(13, 15, 2, 15, 5, 0);
        this.demoDB.addDayWiseActivityDetails(13, 53, 2, 22, 6, 0);
        this.demoDB.addDayWiseActivityDetails(13, 45, 2, 15, 7, 0);
        this.demoDB.addDayWiseActivityDetails(13, 46, 2, 15, 8, 0);
        this.demoDB.addDayWiseActivityDetails(13, 51, 2, 30, 9, 0);
        this.demoDB.addDayWiseActivityDetails(13, 52, 2, 30, 10, 0);
        this.demoDB.addDayWiseActivityDetails(14, 69, 2, 25, 1, 0);
        this.demoDB.addDayWiseActivityDetails(14, 70, 2, 25, 2, 0);
        this.demoDB.addDayWiseActivityDetails(14, 28, 2, 16, 3, 0);
        this.demoDB.addDayWiseActivityDetails(14, 76, 2, 12, 4, 0);
        this.demoDB.addDayWiseActivityDetails(14, 15, 2, 15, 5, 0);
        this.demoDB.addDayWiseActivityDetails(14, 53, 2, 22, 6, 0);
        this.demoDB.addDayWiseActivityDetails(14, 45, 2, 15, 7, 0);
        this.demoDB.addDayWiseActivityDetails(14, 46, 2, 15, 8, 0);
        this.demoDB.addDayWiseActivityDetails(14, 51, 2, 30, 9, 0);
        this.demoDB.addDayWiseActivityDetails(14, 52, 2, 30, 10, 0);
        this.demoDB.addDayWiseActivityDetails(15, 0, 2, 0, 0, 0);
        this.demoDB.addDayWiseActivityDetails(16, 30, 2, 20, 1, 0);
        this.demoDB.addDayWiseActivityDetails(16, 72, 2, 20, 2, 0);
        this.demoDB.addDayWiseActivityDetails(16, 73, 2, 20, 3, 0);
        this.demoDB.addDayWiseActivityDetails(16, 80, 2, 20, 4, 0);
        this.demoDB.addDayWiseActivityDetails(16, 16, 2, 10, 5, 0);
        this.demoDB.addDayWiseActivityDetails(16, 44, 2, 18, 6, 0);
        this.demoDB.addDayWiseActivityDetails(16, 37, 2, 18, 7, 0);
        this.demoDB.addDayWiseActivityDetails(16, 38, 2, 18, 8, 0);
        this.demoDB.addDayWiseActivityDetails(16, 10, 2, 20, 9, 0);
        this.demoDB.addDayWiseActivityDetails(16, 47, 2, 15, 10, 0);
        this.demoDB.addDayWiseActivityDetails(17, 5, 2, 20, 1, 0);
        this.demoDB.addDayWiseActivityDetails(17, 4, 2, 20, 2, 0);
        this.demoDB.addDayWiseActivityDetails(17, 84, 2, 20, 3, 0);
        this.demoDB.addDayWiseActivityDetails(17, 83, 2, 20, 4, 0);
        this.demoDB.addDayWiseActivityDetails(17, 32, 2, 15, 5, 0);
        this.demoDB.addDayWiseActivityDetails(17, 86, 2, 20, 6, 0);
        this.demoDB.addDayWiseActivityDetails(17, 85, 2, 20, 7, 0);
        this.demoDB.addDayWiseActivityDetails(17, 77, 2, 10, 8, 0);
        this.demoDB.addDayWiseActivityDetails(17, 31, 2, 8, 9, 0);
        this.demoDB.addDayWiseActivityDetails(17, 87, 2, 20, 10, 0);
        this.demoDB.addDayWiseActivityDetails(18, 75, 2, 22, 1, 0);
        this.demoDB.addDayWiseActivityDetails(18, 74, 2, 22, 2, 0);
        this.demoDB.addDayWiseActivityDetails(18, 43, 2, 22, 3, 0);
        this.demoDB.addDayWiseActivityDetails(18, 42, 2, 22, 4, 0);
        this.demoDB.addDayWiseActivityDetails(18, 18, 2, 25, 5, 0);
        this.demoDB.addDayWiseActivityDetails(18, 17, 2, 25, 6, 0);
        this.demoDB.addDayWiseActivityDetails(18, 8, 2, 28, 7, 0);
        this.demoDB.addDayWiseActivityDetails(18, 7, 2, 28, 8, 0);
        this.demoDB.addDayWiseActivityDetails(18, 25, 2, 14, 9, 0);
        this.demoDB.addDayWiseActivityDetails(18, 55, 2, 28, 10, 0);
        this.demoDB.addDayWiseActivityDetails(19, 50, 2, 25, 1, 0);
        this.demoDB.addDayWiseActivityDetails(19, 49, 2, 25, 2, 0);
        this.demoDB.addDayWiseActivityDetails(19, 13, 2, 20, 3, 0);
        this.demoDB.addDayWiseActivityDetails(19, 2, 2, 18, 4, 0);
        this.demoDB.addDayWiseActivityDetails(19, 20, 2, 22, 5, 0);
        this.demoDB.addDayWiseActivityDetails(19, 19, 2, 22, 6, 0);
        this.demoDB.addDayWiseActivityDetails(19, 62, 2, 8, 7, 0);
        this.demoDB.addDayWiseActivityDetails(19, 7, 2, 25, 8, 0);
        this.demoDB.addDayWiseActivityDetails(19, 8, 2, 25, 9, 0);
        this.demoDB.addDayWiseActivityDetails(19, 26, 2, 10, 10, 0);
        this.demoDB.addDayWiseActivityDetails(20, 0, 2, 0, 0, 0);
        this.demoDB.addDayWiseActivityDetails(21, 48, 2, 15, 1, 0);
        this.demoDB.addDayWiseActivityDetails(21, 63, 2, 20, 2, 0);
        this.demoDB.addDayWiseActivityDetails(21, 41, 2, 10, 3, 0);
        this.demoDB.addDayWiseActivityDetails(21, 12, 2, 20, 4, 0);
        this.demoDB.addDayWiseActivityDetails(21, 11, 2, 20, 5, 0);
        this.demoDB.addDayWiseActivityDetails(21, 56, 2, 20, 6, 0);
        this.demoDB.addDayWiseActivityDetails(21, 16, 2, 10, 7, 0);
        this.demoDB.addDayWiseActivityDetails(21, 22, 2, 20, 8, 0);
        this.demoDB.addDayWiseActivityDetails(21, 21, 2, 20, 9, 0);
        this.demoDB.addDayWiseActivityDetails(21, 33, 2, 22, 10, 0);
        this.demoDB.addDayWiseActivityDetails(22, 82, 2, 14, 1, 0);
        this.demoDB.addDayWiseActivityDetails(22, 81, 2, 14, 2, 0);
        this.demoDB.addDayWiseActivityDetails(22, 36, 2, 30, 3, 0);
        this.demoDB.addDayWiseActivityDetails(22, 35, 2, 30, 4, 0);
        this.demoDB.addDayWiseActivityDetails(22, 3, 2, 28, 5, 0);
        this.demoDB.addDayWiseActivityDetails(22, 54, 2, 12, 6, 0);
        this.demoDB.addDayWiseActivityDetails(22, 27, 2, 50, 7, 0);
        this.demoDB.addDayWiseActivityDetails(22, 44, 2, 20, 8, 0);
        this.demoDB.addDayWiseActivityDetails(22, 61, 2, 12, 9, 0);
        this.demoDB.addDayWiseActivityDetails(22, 60, 2, 12, 10, 0);
        this.demoDB.addDayWiseActivityDetails(23, 62, 2, 8, 1, 0);
        this.demoDB.addDayWiseActivityDetails(23, 65, 2, 15, 2, 0);
        this.demoDB.addDayWiseActivityDetails(23, 30, 2, 20, 3, 0);
        this.demoDB.addDayWiseActivityDetails(23, 53, 2, 22, 4, 0);
        this.demoDB.addDayWiseActivityDetails(23, 80, 2, 26, 5, 0);
        this.demoDB.addDayWiseActivityDetails(23, 14, 2, 15, 6, 0);
        this.demoDB.addDayWiseActivityDetails(23, 24, 2, 25, 7, 0);
        this.demoDB.addDayWiseActivityDetails(23, 23, 2, 25, 8, 0);
        this.demoDB.addDayWiseActivityDetails(23, 47, 2, 15, 9, 0);
        this.demoDB.addDayWiseActivityDetails(23, 25, 2, 15, 10, 0);
        this.demoDB.addDayWiseActivityDetails(24, 0, 2, 0, 0, 0);
        this.demoDB.addDayWiseActivityDetails(25, 50, 2, 25, 1, 0);
        this.demoDB.addDayWiseActivityDetails(25, 49, 2, 25, 2, 0);
        this.demoDB.addDayWiseActivityDetails(25, 5, 2, 20, 3, 0);
        this.demoDB.addDayWiseActivityDetails(25, 4, 2, 20, 4, 0);
        this.demoDB.addDayWiseActivityDetails(25, 46, 2, 15, 5, 0);
        this.demoDB.addDayWiseActivityDetails(25, 45, 2, 15, 6, 0);
        this.demoDB.addDayWiseActivityDetails(25, 3, 2, 28, 7, 0);
        this.demoDB.addDayWiseActivityDetails(25, 32, 2, 18, 8, 0);
        this.demoDB.addDayWiseActivityDetails(25, 71, 2, 10, 9, 0);
        this.demoDB.addDayWiseActivityDetails(25, 59, 2, 20, 10, 0);
        this.demoDB.addDayWiseActivityDetails(26, 36, 2, 32, 1, 0);
        this.demoDB.addDayWiseActivityDetails(26, 35, 2, 32, 2, 0);
        this.demoDB.addDayWiseActivityDetails(26, 82, 2, 15, 3, 0);
        this.demoDB.addDayWiseActivityDetails(26, 81, 2, 15, 4, 0);
        this.demoDB.addDayWiseActivityDetails(26, 27, 2, 50, 5, 0);
        this.demoDB.addDayWiseActivityDetails(26, 31, 2, 8, 6, 0);
        this.demoDB.addDayWiseActivityDetails(26, 62, 2, 8, 7, 0);
        this.demoDB.addDayWiseActivityDetails(26, 86, 2, 20, 8, 0);
        this.demoDB.addDayWiseActivityDetails(26, 85, 2, 20, 9, 0);
        this.demoDB.addDayWiseActivityDetails(26, 33, 2, 22, 10, 0);
        this.demoDB.addDayWiseActivityDetails(27, 30, 2, 20, 1, 0);
        this.demoDB.addDayWiseActivityDetails(27, 72, 2, 20, 2, 0);
        this.demoDB.addDayWiseActivityDetails(27, 73, 2, 20, 3, 0);
        this.demoDB.addDayWiseActivityDetails(27, 80, 2, 20, 4, 0);
        this.demoDB.addDayWiseActivityDetails(27, 16, 2, 10, 5, 0);
        this.demoDB.addDayWiseActivityDetails(27, 44, 2, 18, 6, 0);
        this.demoDB.addDayWiseActivityDetails(27, 37, 2, 18, 7, 0);
        this.demoDB.addDayWiseActivityDetails(27, 38, 2, 18, 8, 0);
        this.demoDB.addDayWiseActivityDetails(27, 10, 2, 20, 9, 0);
        this.demoDB.addDayWiseActivityDetails(27, 47, 2, 15, 10, 0);
        this.demoDB.addDayWiseActivityDetails(28, 0, 2, 0, 0, 0);
        this.demoDB.addDayWiseActivityDetails(29, 63, 2, 20, 1, 0);
        this.demoDB.addDayWiseActivityDetails(29, 13, 2, 22, 2, 0);
        this.demoDB.addDayWiseActivityDetails(29, 65, 2, 12, 3, 0);
        this.demoDB.addDayWiseActivityDetails(29, 28, 2, 16, 4, 0);
        this.demoDB.addDayWiseActivityDetails(29, 15, 2, 15, 5, 0);
        this.demoDB.addDayWiseActivityDetails(29, 32, 2, 18, 6, 0);
        this.demoDB.addDayWiseActivityDetails(29, 14, 2, 15, 7, 0);
        this.demoDB.addDayWiseActivityDetails(29, 16, 2, 10, 8, 0);
        this.demoDB.addDayWiseActivityDetails(29, 6, 2, 12, 9, 0);
        this.demoDB.addDayWiseActivityDetails(29, 25, 2, 15, 10, 0);
        this.demoDB.addDayWiseActivityDetails(30, 62, 2, 8, 1, 0);
        this.demoDB.addDayWiseActivityDetails(30, 64, 2, 15, 2, 0);
        this.demoDB.addDayWiseActivityDetails(30, 84, 2, 20, 3, 0);
        this.demoDB.addDayWiseActivityDetails(30, 83, 2, 20, 4, 0);
        this.demoDB.addDayWiseActivityDetails(30, 53, 2, 30, 5, 0);
        this.demoDB.addDayWiseActivityDetails(30, 12, 2, 30, 6, 0);
        this.demoDB.addDayWiseActivityDetails(30, 11, 2, 30, 7, 0);
        this.demoDB.addDayWiseActivityDetails(30, 16, 2, 10, 8, 0);
        this.demoDB.addDayWiseActivityDetails(30, 44, 2, 25, 9, 0);
        this.demoDB.addDayWiseActivityDetails(30, 87, 2, 25, 10, 0);
    }

    private void showDialog() {
        final SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(BuildConfig.APPLICATION_ID, 0);
        RatingDialog build = new RatingDialog.Builder(this).session(1).threshold(4.0f).title(this.title)
                .icon(getResources().getDrawable(R.mipmap.logo))
                .titleTextColor(R.color.colorPrimary)
                .negativeButtonText("أبدا")
                .positiveButtonTextColor(R.color.colorPrimary)
                .negativeButtonTextColor(R.color.colorPrimary)
                .feedbackTextColor(R.color.colorPrimary)
                .formTitle("إرسال التعليقات")
                .formHint("أخبرنا أين يمكننا أن نتطور")
                .formSubmitText("إرسال")
                .formCancelText("إلغاء")
                .ratingBarColor(R.color.ratingBarColor)
                .ratingBarBackgroundColor(R.color.ratingBarBackgroundColor)
                .playstoreUrl(this.playStoreUrl)
                .onRatingBarFormSumbit(new RatingDialog.Builder.RatingDialogFormListener() {
            public void onFormSubmitted(String str) {
                MainActivity.this.emailus(str);
                SharedPreferences.Editor edit = sharedPreferences.edit();
                edit.putBoolean("shownever", true);
                edit.commit();
            }
        }).build();
        if (sharedPreferences.getBoolean("shownever", false)) {
            Toast.makeText(this, "أرسلت بالفعل", Toast.LENGTH_SHORT).show();
        } else {
            build.show();
        }
    }

    public void emailus(String str) {
        try {
            String str2 = Build.MODEL;
            String str3 = Build.MANUFACTURER;
            Intent intent = new Intent("android.intent.action.VIEW", Uri.parse("Abc@gmail.com"));
            intent.putExtra("android.intent.extra.SUBJECT", "سيكون اقتراحك ممتنًا - تمارين اليوغا اليومية - خطة تجريب اليوجا )" + getPackageName() + ")");
            intent.putExtra("android.intent.extra.TEXT", str + "\n\n : الشركة المصنعة للجهاز " + str3 + "\nطراز الجهاز : " + str2 + "\nنسخة أندرويد : " + Build.VERSION.RELEASE);
            startActivityForResult(intent, 9);
        } catch (Exception e) {
            Log.d("", e.toString());
        }
    }


    @SuppressLint({"StaticFieldLeak"})
    private void storeData() {
        new AsyncTask<Void, Void, Void>() {

            public Void doInBackground(Void... voidArr) {
                MainActivity.this.storeExerciseType();
                MainActivity.this.addExerciseData();
                MainActivity.this.storeDayWiseActivityInformation();
                MainActivity.this.storeActivityInformation();
                return null;
            }


            @Override
            public void onPostExecute(Void voidR) {
                super.onPostExecute(voidR);
                AppPref.setSave(MainActivity.this, true);
            }
        }.execute();
    }

    private boolean loadFragment(Fragment fragment) {
        if (fragment == null) {
            return false;
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, fragment).commit();
        return true;
    }

    public static void setHomeItem(Activity activity) {
        ((BottomNavigationView) activity.findViewById(R.id.bottom_navigation)).setSelectedItemId(R.id.training);
    }

    @Override
    public void onBackPressed() {

        if (!AppPref.rateus(this)) {
            showDialog();
            AppPref.setRateUs(this, true);

            return;
        }

        int selectedItemId = this.l.getSelectedItemId();
        Log.i("selectedId: " + selectedItemId, "onBackPressed: " + selectedItemId);
        int backStackEntryCount = getSupportFragmentManager().getBackStackEntryCount();
        if (R.id.training == selectedItemId) {


            exitConfirmDialog();


        } else if (backStackEntryCount == 0) {
            setHomeItem(this);
        } else {

            getSupportFragmentManager().popBackStack();
        }

    }

    public static void loadad() {
        try {
            Log.d("loadad", "called");
            goglefullad = new InterstitialAd(mainContext);
            goglefullad.setAdUnitId(AdGlobal.ADFULL);
            if (AdGlobal.NPAFLAG) {
                Log.d("NPA", "" + AdGlobal.NPAFLAG);
                Bundle bundle = new Bundle();
                bundle.putString("npa", "1");
                intadrequest = new AdRequest.Builder().addNetworkExtrasBundle(AdMobAdapter.class, bundle).build();
            } else {
                Log.d("NPA", "" + AdGlobal.NPAFLAG);
                intadrequest = new AdRequest.Builder().build();
            }
            goglefullad.loadAd(intadrequest);
            goglefullad.setAdListener(new AdListener() {
                @Override
                public void onAdClosed() {
                    super.onAdClosed();
                    MainActivity.backscreen();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void backpressad(AdsScreen adbackscreeninterface) {
        mAdBackScreenInterface = adbackscreeninterface;
        InterstitialAd interstitialAd = goglefullad;
        if (interstitialAd == null) {
            backscreen();
        } else if (interstitialAd.isLoaded()) {
            try {
                AdGlobal.adcount++;
                goglefullad.show();
            } catch (Exception unused) {
                backscreen();
            }
        } else {
            backscreen();
        }
    }

    public static void backscreen() {
        AdsScreen adbackscreeninterface = mAdBackScreenInterface;
        if (adbackscreeninterface != null) {
            try {
                adbackscreeninterface.backscreen();
            } catch (Exception e) {
                e.printStackTrace();

            }
        }
        if (AdGlobal.adcount < AdGlobal.ADLIMIT) {
            loadad();
        }
    }

    private void exitConfirmDialog() {
        Dialog dialog = new Dialog(this, R.style.Theme_Dialog);
        dialog.requestWindowFeature(1);
        dialog.getWindow().setFlags(1024, 1024);
        dialog.setContentView(R.layout.exit_confirm_addialog_layout);


        ((Window) Objects.requireNonNull(dialog.getWindow())).setLayout(-1, -1);
        ((TextView) dialog.findViewById(R.id.btnYes)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                MainActivity.super.onBackPressed();
            }
        });
        ((TextView) dialog.findViewById(R.id.btnNo)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}

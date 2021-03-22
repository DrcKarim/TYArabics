package com.BestofallHealthYoga.TamarinYoga.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.BestofallHealthYoga.TamarinYoga.R;
import com.BestofallHealthYoga.TamarinYoga.adapters.AddExerciseAdapter;
import com.BestofallHealthYoga.TamarinYoga.dbhelper.DemoDB;
import com.BestofallHealthYoga.TamarinYoga.models.MyTrainingModel;
import com.BestofallHealthYoga.TamarinYoga.models.UserExercise;
import com.BestofallHealthYoga.TamarinYoga.utils.Constant;
import com.BestofallHealthYoga.TamarinYoga.utils.MyTraining;
import com.BestofallHealthYoga.TamarinYoga.utils.SwipeAndDragHelper;
import java.util.ArrayList;

public class AddTraining extends AppCompatActivity implements MyTraining {
    String trainingname;
    RecyclerView activityDetails;
    Button add;
    AddExerciseAdapter addExerciseAdapter;
    DemoDB demoDB;
    boolean isForEdit;
    MenuItem menuItem;
    RelativeLayout nolistlayout;
    ImageView toolImage;
    Toolbar toolbar;
    TextView toolbarText;
    int typeId;
    ArrayList<UserExercise> userExercisesArrayList;
    ImageView back_arrow;


    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_add_training);

        AdView mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        init();
        setupView();
    }

    private void setupView() {
        setAdapter();
    }

    private void init() {
        this.toolbar = (Toolbar) findViewById(R.id.toolbar);
        this.back_arrow = (ImageView) findViewById(R.id.back_arrow);
        setSupportActionBar(this.toolbar);

        this.back_arrow.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                AddTraining.this.onSupportNavigateUp();
            }
        });
        this.add = (Button) findViewById(R.id.add);
        this.add.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(AddTraining.this, AllActivityForTraining.class);
                intent.setFlags(67108864);
                AddTraining.this.startActivityForResult(intent, Constant.ALL_ACTIVITY_CODE);
            }
        });
        this.demoDB = new DemoDB(this);
        this.isForEdit = getIntent().getBooleanExtra("isEdit", false);
        this.typeId = getIntent().getIntExtra("typeId", 0);
        this.activityDetails = (RecyclerView) findViewById(R.id.activityDetails);
        this.toolbarText = (TextView) findViewById(R.id.toolbarText);
        this.nolistlayout = (RelativeLayout) findViewById(R.id.nolistlayout);
        this.userExercisesArrayList = getIntent().getParcelableArrayListExtra(getString(R.string.UserExercises));
        if (this.userExercisesArrayList == null) {
            this.userExercisesArrayList = new ArrayList<>();
        }
        if (this.isForEdit) {
            this.toolbarText.setText(R.string.edit_exercise);
        } else {
            this.toolbarText.setText(R.string.add_exercise);
        }
        this.trainingname = getIntent().getStringExtra(getString(R.string.TrainingName));
    }

    @Override
    public void onActivityResult(int i, int i2, @Nullable Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i == Constant.ALL_ACTIVITY_CODE && intent != null) {
            this.menuItem.setVisible(true);
            this.userExercisesArrayList.add((UserExercise) intent.getParcelableExtra(getString(R.string.UserExercises)));
            this.addExerciseAdapter.notifyDataSetChanged();
            listEmptyCheck();
        }
        if (i == Constant.TOTAL_SET_FOR_ACTIVITY_CODE && intent != null) {
            int intExtra = intent.getIntExtra("position", -1);
            this.userExercisesArrayList.set(intExtra, (UserExercise) intent.getParcelableExtra(getString(R.string.UserExercises)));
            this.addExerciseAdapter.notifyDataSetChanged();
        }
    }

    private void setAdapter() {
        this.addExerciseAdapter = new AddExerciseAdapter(this, this.userExercisesArrayList, this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        this.activityDetails.setLayoutManager(linearLayoutManager);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new SwipeAndDragHelper(this.addExerciseAdapter));
        this.addExerciseAdapter.setTouchHelper(itemTouchHelper);
        this.activityDetails.setAdapter(this.addExerciseAdapter);
        itemTouchHelper.attachToRecyclerView(this.activityDetails);
        listEmptyCheck();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.floatmenu, menu);
        this.menuItem = menu.findItem(R.id.addActivity);
        this.menuItem.setVisible(false);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem2) {
        if (menuItem2.getItemId() == R.id.addActivity) {
            addTrainingDialog();
        }
        return super.onOptionsItemSelected(menuItem2);
    }

    private void addTrainingDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View inflate = getLayoutInflater().inflate(R.layout.dialog_addexrcisetitle, (ViewGroup) null);
        builder.setView(inflate);
        final EditText editText = (EditText) inflate.findViewById(R.id.exercisename);
        String str = this.trainingname;
        if (str != null) {
            editText.setText(str);
        }
        final AlertDialog create = builder.create();
        create.getWindow().setBackgroundDrawableResource(17170445);
        create.show();
        ((LinearLayout) inflate.findViewById(R.id.submit)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (!editText.getText().toString().trim().isEmpty()) {
                    if (!AddTraining.this.isForEdit) {
                        AddTraining.this.insetExercise(editText.getText().toString());
                    } else if (AddTraining.this.typeId != 0) {
                        AddTraining.this.updateExercise(editText.getText().toString());
                    }
                    create.dismiss();
                }
            }
        });
        ((LinearLayout) inflate.findViewById(R.id.cancel)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                create.dismiss();
            }
        });
    }


    public void insetExercise(String str) {
        long addExercieDetails = this.demoDB.addExercieDetails(str);
        if (addExercieDetails != 0) {
            for (int i = 0; i < this.userExercisesArrayList.size(); i++) {
                this.demoDB.addDayWiseActivityDetails(1, this.userExercisesArrayList.get(i).getActivityId(), (int) addExercieDetails, this.userExercisesArrayList.get(i).getTotalTime(), i, Constant.ACTIVIY_NOT_FINISH);
            }
            MyTrainingModel myTrainingModel = new MyTrainingModel();
            myTrainingModel.setTypeid((int) addExercieDetails);
            myTrainingModel.setTypename(str);
            myTrainingModel.setTotalExercise(this.userExercisesArrayList.size());
            Intent intent = new Intent();
            intent.putExtra(getString(R.string.TrainingData), myTrainingModel);
            setResult(Constant.ADD_TRAINING_CODE, intent);
            finish();
        }
    }


    public void updateExercise(String str) {
        this.demoDB.updateTitleForExerciseTypeDetails(str, this.typeId);
        this.demoDB.deleteMyTrainingFromADayWiseActivityDetails((long) this.typeId);
        for (int i = 0; i < this.userExercisesArrayList.size(); i++) {
            this.demoDB.addDayWiseActivityDetails(1, this.userExercisesArrayList.get(i).getActivityId(), this.typeId, this.userExercisesArrayList.get(i).getTotalTime(), i, Constant.ACTIVIY_NOT_FINISH);
        }
        MyTrainingModel myTrainingModel = new MyTrainingModel();
        myTrainingModel.setTypeid(this.typeId);
        myTrainingModel.setTypename(str);
        myTrainingModel.setTotalExercise(this.userExercisesArrayList.size());
        Intent intent = new Intent();
        intent.putExtra("isAllDelete", false);
        intent.putExtra(getString(R.string.TrainingData), myTrainingModel);
        setResult(Constant.FROM_DAY_INFORMATION, intent);
        finish();
    }

    public void onClick(int i) {
        Intent intent = new Intent(this, Activity_totalSet.class);
        intent.putExtra(getString(R.string.UserExercises), this.userExercisesArrayList.get(i));
        intent.putExtra("position", i);
        startActivityForResult(intent, Constant.TOTAL_SET_FOR_ACTIVITY_CODE);
    }

    public void onAllDelete(boolean z) {
        if (z) {
            this.demoDB.deleteMyTrainingFromADayWiseActivityDetails((long) this.typeId);
            Intent intent = new Intent();
            intent.putExtra("isAllDelete", true);
            setResult(Constant.FROM_DAY_INFORMATION, intent);
            finish();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    private void listEmptyCheck() {
        ArrayList<UserExercise> arrayList = this.userExercisesArrayList;
        if (arrayList == null || arrayList.isEmpty()) {
            this.nolistlayout.setVisibility(View.VISIBLE);
        } else {
            this.nolistlayout.setVisibility(View.GONE);
        }
    }
}

package com.BestofallHealthYoga.TamarinYoga.activities;

import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.mikephil.charting.utils.Utils;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.BestofallHealthYoga.TamarinYoga.R;
import com.BestofallHealthYoga.TamarinYoga.dbhelper.BaseAppDB;
import com.BestofallHealthYoga.TamarinYoga.models.Report;
import com.BestofallHealthYoga.TamarinYoga.utils.AppPref;
import com.BestofallHealthYoga.TamarinYoga.utils.BaseApp;
import com.BestofallHealthYoga.TamarinYoga.utils.Constant;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Objects;

public class SummaryActivity extends AppCompatActivity {
    boolean isDelete = false;
    boolean istypeheight = false;
    BaseAppDB myDb;
    RecyclerView recyclerView;
    ArrayList<Report> reportArrayList = new ArrayList<>();
    ImageView back_arrow;


    @Override
    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_summery);


        AdView mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        this.myDb = new BaseAppDB(this);
        this.istypeheight = getIntent().getBooleanExtra("TYPE_WEIGHT", false);
        if (!this.istypeheight) {
            ((TextView) findViewById(R.id.toolbarText)).setText(getString(R.string.weight));
        } else {
            ((TextView) findViewById(R.id.toolbarText)).setText(getString(R.string.waistline));
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        back_arrow = (ImageView) findViewById(R.id.back_arrow);


        back_arrow.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                SummaryActivity.this.onSupportNavigateUp();
            }
        });
        new GetData().execute();
        this.recyclerView = (RecyclerView) findViewById(R.id.listAll);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(this));

        this.recyclerView.setAdapter(new RecyclerView.Adapter() {
            @NonNull
            public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                return new SummaryHoldder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_summary, viewGroup, false));
            }

            public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                if (viewHolder instanceof SummaryHoldder) {
                    SummaryHoldder summaryHoldder = (SummaryHoldder) viewHolder;
                    Report report = SummaryActivity.this.reportArrayList.get(i);
                    if (!SummaryActivity.this.istypeheight) {
                        TextView textView = summaryHoldder.weight;
                        StringBuilder sb = new StringBuilder();
                        sb.append(report.getWeight());
                        sb.append("");
                        sb.append(AppPref.isMatrixWeight(BaseApp.getInstance()) ? " kg" : " lb");
                        textView.setText(sb.toString());
                    } else {
                        TextView textView2 = summaryHoldder.weight;
                        StringBuilder sb2 = new StringBuilder();
                        sb2.append(report.getWeight());
                        sb2.append("");
                        sb2.append(AppPref.isMatrixWaist(BaseApp.getInstance()) ? " cm" : " in");
                        textView2.setText(sb2.toString());
                    }
                    summaryHoldder.date.setText(Constant.getFormattedDate(report.getDate(), new SimpleDateFormat( "MMMM dd,yyyy")));
                }
            }

            public int getItemCount() {
                if (SummaryActivity.this.reportArrayList != null) {
                    return SummaryActivity.this.reportArrayList.size();
                }
                return 0;
            }


            class SummaryHoldder extends RecyclerView.ViewHolder {
                TextView date;
                ImageView delete;
                TextView weight;

                public SummaryHoldder(@NonNull View view) {
                    super(view);
                    this.date = (TextView) view.findViewById(R.id.date);
                    this.weight = (TextView) view.findViewById(R.id.weight);
                    this.delete = (ImageView) view.findViewById(R.id.delete);
                    this.delete.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View view) {
                            SummaryHoldder.this.deleteMyTraining();
                        }

                    });


                }

                public void deleteMyTraining() {
                    final Dialog dialog = new Dialog(SummaryActivity.this);
                    dialog.setContentView(R.layout.delete_training_dialog);
                    dialog.setCancelable(true);
                    dialog.getWindow().setLayout(-1, -2);
                    dialog.getWindow().setBackgroundDrawableResource(17170445);
                    dialog.show();
                    ((TextView) dialog.findViewById(R.id.message)).setText(SummaryActivity.this.getString(R.string.deleteMsg));
                    ((LinearLayout) dialog.findViewById(R.id.lin_cancel)).setOnClickListener(new View.OnClickListener() {
                        public void onClick(View view) {
                            dialog.dismiss();
                        }
                    });
                    ((LinearLayout) dialog.findViewById(R.id.lin_ok)).setOnClickListener(new View.OnClickListener() {
                        public void onClick(View view) {
                            dialog.dismiss();
                            SummaryActivity.this.myDb.updateReportData(!SummaryActivity.this.istypeheight ? 1 : 2, SummaryActivity.this.reportArrayList.get(SummaryHoldder.this.getAdapterPosition()).getDate(), Utils.DOUBLE_EPSILON);
                            SummaryActivity.this.reportArrayList.remove(SummaryHoldder.this.getAdapterPosition());
                            notifyItemRemoved(SummaryHoldder.this.getAdapterPosition());
                            SummaryActivity.this.isDelete = true;
                        }
                    });
                }
            }
        });
    }

    public class GetData extends AsyncTask<Object, Object, Object> {
        public GetData() {
            Log.d("", "Hello");
        }


        public Object doInBackground(Object[] objArr) {
            SummaryActivity.this.reportArrayList.addAll(SummaryActivity.this.myDb.getReportData(SummaryActivity.this.istypeheight));
            return null;
        }


        @Override
        public void onPostExecute(Object obj) {
            super.onPostExecute(obj);
            ((RecyclerView.Adapter) Objects.requireNonNull(SummaryActivity.this.recyclerView.getAdapter())).notifyDataSetChanged();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {
        if (this.isDelete) {
            Intent intent = getIntent();
            intent.putExtra("isDelete", this.isDelete);
            setResult(-1, intent);
            finish();
        }
        super.onBackPressed();
    }
}

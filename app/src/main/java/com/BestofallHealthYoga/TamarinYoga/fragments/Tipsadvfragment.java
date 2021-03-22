package com.BestofallHealthYoga.TamarinYoga.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.BestofallHealthYoga.TamarinYoga.R;
import com.squareup.picasso.Picasso;

import java.util.Random;

public class Tipsadvfragment extends Fragment {


     Random f833a;
     ImageView b;
     TextView c;
     TextView d;
     String[] e;
     String[] f;
     int[] g = {R.drawable.avoid_foods_with_added_sugar, R.drawable.do_cardioexercises, R.drawable.do_notconsumejunkfoods, R.drawable.walk_regularlytoloseweight, R.drawable.avoid_foods_with_added_sugar, R.drawable.consume_foodsrichinprotein, R.drawable.get_sufficientsleep, R.drawable.eat_foodsrichinfiber, R.drawable.reduce_carbohydratesinyourdiet, R.drawable.eat_healthy, R.drawable.consume_blackcoffee, R.drawable.eat_wholeeggs, R.drawable.drink_adequatequantityofwater, R.drawable.drink_greentea, R.drawable.eat_datesasasnack, R.drawable.consume_fruitsduringbrunch, R.drawable.consume_carbohydratesintheiroriginalform, R.drawable.include_vegetablesaladsinyourdiet, R.drawable.manage_stresstoloseweight, R.drawable.choose_healthyoils, R.drawable.avoid_processedfoods, R.drawable.do_noteatfriedfoods, R.drawable.eat_healthysnacks, R.drawable.eat_darkchocolate, R.drawable.chew_slowlywhileeating, R.drawable.spices_forweightloss, R.drawable.consume_flaxseeds, R.drawable.yoga_forweightlose, R.drawable.keep_trackonthecaloriecount, R.drawable.exercise_withyourfriends};
     int h;
     int[] i;

    public int a() {
        this.f833a = new Random();
        Log.d("RandomNumber", "" + this.f833a.nextInt(this.g.length));
        return this.f833a.nextInt(this.g.length);
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_tipsadvfragment, viewGroup, false);
        this.c = (TextView) inflate.findViewById(R.id.tipsdescription);
        this.b = (ImageView) inflate.findViewById(R.id.tipsimage);
        this.d = (TextView) inflate.findViewById(R.id.tipsshortdescription);
        this.f = getResources().getStringArray(R.array.advbellyfattipstitle);
        this.e = getResources().getStringArray(R.array.advbellyfattips);
        this.h = a();
        Bundle arguments = getArguments();
        if (arguments != null) {
            arguments.getInt("pos");
        }
        this.i = this.g;
        this.c.setText(this.e[this.h]);
        this.d.setText(this.f[this.h]);
        this.b.setImageResource(this.g[this.h]);
        Picasso.get().load(this.i[this.h]).placeholder((int) R.drawable.progress_animation).error((int) R.drawable.error).into(this.b);
        return inflate;
    }
}

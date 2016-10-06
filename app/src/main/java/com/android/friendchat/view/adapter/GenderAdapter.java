package com.android.friendchat.view.adapter;

import com.android.friendchat.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by hp 400 on 10/6/2016.
 */
public class GenderAdapter extends BaseAdapter {
    Context context;
    int icons[];
    String[] genders;
    LayoutInflater inflter;
    @Bind(R.id.icon)
    ImageView icon;
    @Bind(R.id.gender)
    TextView gender;

    public GenderAdapter(Context applicationContext, int[] flags, String[] genders) {
        this.context = applicationContext;
        this.icons = flags;
        this.genders = genders;
        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return icons.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.gender_spinner_item, null);
        ButterKnife.bind(this,view);
        icon.setImageResource(icons[i]);
        gender.setText(genders[i]);
        return view;
    }
}
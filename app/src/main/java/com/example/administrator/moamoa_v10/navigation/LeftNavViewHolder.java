package com.example.administrator.moamoa_v10.navigation;

import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;

import com.example.administrator.moamoa_v10.R;

/**
 * Created by Administrator on 2016-11-20.
 */

public class LeftNavViewHolder extends RecyclerView.ViewHolder {

    public MenuItem nav_facebook;

    public LeftNavViewHolder(View itemView) {
        super(itemView);

        nav_facebook = (MenuItem) itemView.findViewById(R.id.nav_facebook);

    }
}

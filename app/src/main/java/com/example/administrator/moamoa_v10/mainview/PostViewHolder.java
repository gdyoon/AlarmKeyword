package com.example.administrator.moamoa_v10.mainview;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.moamoa_v10.R;

/**
 * Created by Administrator on 2016-11-20.
 */

public class PostViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    public ImageView iv_post_pic, iv_url;
    public TextView tv_post_title, tv_post_content, tv_post_user_name;
    private PostAdapter mAdapter;

    public PostViewHolder(View itemView, PostAdapter postAdapter) {
        super(itemView);

        mAdapter = postAdapter;
        iv_url = (ImageView) itemView.findViewById(R.id.iv_url);
        iv_post_pic = (ImageView) itemView.findViewById(R.id.iv_post_pic);
        tv_post_user_name = (TextView) itemView.findViewById(R.id.tv_post_user_name);
        tv_post_title = (TextView) itemView.findViewById(R.id.tv_post_title);
        tv_post_content = (TextView) itemView.findViewById(R.id.tv_post_text);

        iv_url.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {

        int position = getAdapterPosition();

        switch (v.getId())
        {
            case R.id.iv_url:
                mAdapter.onURLClicked(position);
                break;


        }
    }


}

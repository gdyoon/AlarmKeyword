package com.example.administrator.moamoa_v10.mainview;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.administrator.moamoa_v10.R;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016-11-20.
 */

public class PostAdapter extends RecyclerView.Adapter<PostViewHolder> {

    private Context mContext;
    private ArrayList<Postitem> postItems;

    public PostAdapter(Context _context, ArrayList<Postitem> _postitem)
    {
        this.mContext = _context;
        this.postItems = _postitem;
    }


    @Override
    public PostViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(mContext, R.layout.post_item, null);
        PostViewHolder postViewHolder = new PostViewHolder(view, this);

        return postViewHolder;
    }

    @Override
    public void onBindViewHolder(PostViewHolder holder, int position) {

        Postitem item = postItems.get(position);

        //Log.i("POSTITEM", "content : " + item.getM_postContent());

        holder.tv_post_user_name.setText("["+item.getM_userName()+"] " + item.getM_postContent()  );
       // holder.tv_post_content.setText("외않되는걷이역");
        holder.tv_post_title.setText(item.getM_postTitle());

        // 이미지가 없는 게시글의 경우
        if(item.getM_postPic().equals("EMPTY") != false)
        {
            holder.iv_post_pic.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            Glide.with(mContext).load(R.drawable.no_image).into(holder.iv_post_pic);
        }
        else
        {
            Glide.with(mContext).load(item.getM_postPic()).into(holder.iv_post_pic);
        }

    }

    @Override
    public int getItemCount() {
        return postItems.size();
    }

    public void onURLClicked(int position) {

        Postitem item = postItems.get(position);

        if(item.getM_postUrl().equals("EMPTY") != false)
        {
            Toast.makeText(mContext, "원문이 없는 게시글 입니다", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(item.getM_postUrl()));
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mContext.startActivity(intent);
        }
       // Toast.makeText(mContext, position + "번째 글 URL 로 연결", Toast.LENGTH_SHORT).show();

    }
}

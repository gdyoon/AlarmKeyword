package com.example.administrator.moamoa_v10;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.administrator.moamoa_v10.Config.DataConfig;
import com.example.administrator.moamoa_v10.mainview.Postitem;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static android.util.JsonToken.NULL;

/**
 * Created by Administrator on 2016-11-20.
 */

public class Facebook {

    private Context mContext;
    private CallbackManager callback;



    ArrayList<Postitem> listItem = new ArrayList<>();



    public Facebook(Context mContext)
    {
        this.mContext = mContext;
        InitalizeSDK();
    }

    private void InitalizeSDK()
    {
        FacebookSdk.sdkInitialize(mContext);
        callback = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(callback, loginCallback);
        Log.i("FACEBOOK" , "Init Success. .");
    }

    FacebookCallback<LoginResult> loginCallback = new FacebookCallback<LoginResult>() {

        @Override
        public void onSuccess(final LoginResult loginResult) {
            GraphRequest request = new GraphRequest(
                    loginResult.getAccessToken(),
                    "/me/feed",
                    null,
                    HttpMethod.GET,
                    new GraphRequest.Callback() {
                        public void onCompleted(GraphResponse response) {
                            //Log.i("FACEBOOK", response.getJSONObject().toString());

                            String post_name, post_link, post_story, post_msg, post_pic;
                            //DataConfig.accessToken = loginResult.getAccessToken();
                            try
                            {
                                JSONArray jarray = response.getJSONObject().getJSONArray("data");
                                //Log.i("FACEBOOK" ,"ItemCount: " + String.valueOf(jarray.length()));

                                for(int i=0; i<jarray.length(); i++) {

                                    JSONObject responseObject = jarray.getJSONObject(i);

                                    if(!responseObject.isNull("name"))
                                        post_name = responseObject.getString("name");
                                    else
                                        post_name = "불러올 수 없습니다.";


                                    if(!responseObject.isNull("link"))
                                        post_link = responseObject.getString("link");
                                    else
                                        post_link = "EMPTY";

                                    if(!responseObject.isNull("story"))
                                        post_story = responseObject.getString("story");
                                    else
                                        post_story = "불러올 수 없습니다.";


                                    if(!responseObject.isNull("message"))
                                        post_msg = responseObject.getString("message");
                                    else
                                        post_msg = "불러올 수 없습니다.";


                                    if(!responseObject.isNull("full_picture"))
                                        post_pic = responseObject.getString("full_picture");
                                    else
                                        post_pic = "EMPTY";

                                    /*
                                    post_name = responseObject.getString("name");
                                    post_link = responseObject.getString("link");
                                    post_story = responseObject.getString("story");
                                    post_msg = responseObject.getString("message");
                                    post_pic = responseObject.getString("picture");


                                    if(post_name == null) post_name = "불러올 수 없습니다.";
                                    if(post_link == null) post_link = "불러올 수 없습니다.";
                                    if(post_story == null) post_story = "불러올 수 없습니다.";
                                    if(post_msg == null) post_msg = "불러올 수 없습니다.";
                                    if(post_pic == null) post_pic = "불러올 수 없습니다.";
*/

                                   //Log.i("FACEBOOK",post_name + ", " + post_link + ", " + post_story + ", " + post_msg + ", " + post_pic );

                                    Postitem item = new Postitem(post_name// 글쓴이
                                            , post_link // 해당 글의 링크
                                            , post_story// 제목
                                            , post_msg // 내용
                                            , post_pic); // 사진 URL

                                    listItem.add(item);


                                }
                            }
                            catch (JSONException e)
                            {
                                e.printStackTrace();
                            }



                        }
                    });

            Bundle parameters = new Bundle();
            parameters.putString("fields", "name, link, story, full_picture, created_time, message");
            request.setParameters(parameters);
            request.executeAsync();
        }
        @Override
        public void onCancel() {
            Toast.makeText(mContext, "로그인 실패..", Toast.LENGTH_SHORT).show();
        }
        @Override
        public void onError(FacebookException exception) {
            Toast.makeText(mContext,"로그인 실패..", Toast.LENGTH_SHORT).show();
        }};

    public CallbackManager getCallback() {
        return callback;
    }

    public ArrayList<Postitem> getListItem() {
        return listItem;
    }
}

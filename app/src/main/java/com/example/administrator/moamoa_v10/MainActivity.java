package com.example.administrator.moamoa_v10;

import android.app.ActionBar;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.hardware.camera2.params.Face;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.administrator.moamoa_v10.Config.DataConfig;
import com.example.administrator.moamoa_v10.mainview.PostAdapter;
import com.example.administrator.moamoa_v10.service.AlarmService;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;

import java.util.ArrayList;
import java.util.Arrays;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    final String LOG_TAG = MainActivity.class.getSimpleName();
    Facebook facebook               = null;
    RecyclerView rv_post_list       = null;
    ArrayList<String> keywordList   = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        // 페이스북 api
        facebook = new Facebook(this);

        keywordList = new ArrayList<String>();

        // Recycler View 적용


        // 추후에 애니메이션 적용
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        toolbar.setTitle("모아모아");
        toolbar.setTitleTextColor(Color.BLACK);

/*
        Drawable dr = getResources().getDrawable(R.drawable.ic_app_title);
        Bitmap bitmap = ((BitmapDrawable) dr).getBitmap();
        Drawable d = new BitmapDrawable(getResources(),
                Bitmap.createScaledBitmap(bitmap, 300, 300, true));

        toolbar.setLogo(R.drawable.ic_app_title);
      */
        setSupportActionBar(toolbar);



        // 우측 하단 버튼
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final EditText et = new EditText(getApplicationContext());
                et.setTextColor(Color.BLACK);
                final View _view = view;
                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                dialog.setTitle("키워드를 입력해주세요");
                dialog.setView(et);

                dialog.setPositiveButton("추가", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String inputValue = et.getText().toString();

                        for(int i=0; i<DataConfig.KeywordList.size(); i++)
                        {
                            if(DataConfig.KeywordList.get(i).equals(inputValue) == true)
                            {
                                Snackbar.make(_view, "이미 존재하는 키워드 입니다.", Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();

                                return;
                            }
                        }

                        DataConfig.KeywordList.add(inputValue);

                        // SharedPreference 공간에 저장
                        DataConfig.setKeywordArrayPref(getApplicationContext(), "KeywordList", keywordList);
                        DataConfig.getKeywordArrayPref(getApplicationContext(), "KeywordList");

                        Snackbar.make(_view, "키워드 추가 완료", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                });

                dialog.setNegativeButton("취소",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                dialog.show();

            }
        });


        // Drawer 기능을 이용하기 위한 레이아웃
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        // 왼쪽 네비게이션 객체를 부를 때 토글되는 버튼
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();




        // 왼쪽 상단 액션바 토글시 불러오는 메뉴
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }





    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        facebook.getCallback().onActivityResult(requestCode, resultCode, data);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        try {
            switch (item.getItemId()) {
                case R.id.nav_facebook:

                    // Toast.makeText(getApplicationContext(),"facebook", Toast.LENGTH_LONG).show();
                    LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile", "user_posts", "email"));
                    // RecyclerView 적용
                    rv_post_list = (RecyclerView) findViewById(R.id.rv_post_list);


                    new Handler().postDelayed(new Runnable()
                    {
                        @Override
                        public void run() {
                            PostAdapter adapter = new PostAdapter(getApplicationContext(), facebook.getListItem());
                            rv_post_list.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayout.VERTICAL, false));
                            rv_post_list.setAdapter(adapter);

                            Log.i("KEYWORD", "알람 서비스 시작 전");
                            Intent intent = new Intent(MainActivity.this, AlarmService.class);
                            startService(intent);
                        }},2000);

                    break;

                case R.id.nav_instagram:
                    break;

                case R.id.nav_twitter:
                    break;

                case R.id.nav_keyword:
                    Log.i(LOG_TAG,"Data Size : " + DataConfig.KeywordList.size());

                    final CharSequence[] items = DataConfig.KeywordList.toArray(new CharSequence[DataConfig.KeywordList.size()]);
                    final boolean[] keywordsChecked = new boolean[items.length];


                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("키워드 관리(1개 선택가능)");
                    builder.setMultiChoiceItems( items, keywordsChecked, new DialogInterface.OnMultiChoiceClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                            Log.i(LOG_TAG, "index : " + which);
                            keywordsChecked[which] = isChecked;
                        }
                    });

                    DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            if(which == DialogInterface.BUTTON_POSITIVE)
                            {
                                for(int i=0; i< keywordsChecked.length; i++)
                                {
                                    if(keywordsChecked[i] == true)
                                    {
                                        try {
                                            DataConfig.KeywordList.remove(i);
                                        }
                                        catch(IndexOutOfBoundsException ex)
                                        {
                                            Toast.makeText(MainActivity.this, "삭제되지 않은 것도 있습니다.", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }
                            }
                            else if(which == DialogInterface.BUTTON_NEUTRAL)
                            {
                                dialog.dismiss();
                            }

                            //DataConfig.setKeywordArrayPref(getApplicationContext(), "KeywordList",  DataConfig.KeywordList);
                            //DataConfig.getKeywordArrayPref(getApplicationContext(), "KeywordList");
                        }
                    };

                    builder.setPositiveButton("삭제", listener);
                    builder.setNegativeButton("취소", listener);
                    builder.create().show();



                    break;

                case R.id.nav_share:
                    break;
            }
        }
        catch (Exception ex)
        {
            Toast.makeText(this, ex.toString(), Toast.LENGTH_SHORT).show();
        }

      /*  int id = item.getItemId();

        if (id == R.id.nav_facebook) {

        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}

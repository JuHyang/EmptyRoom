package com.juhyang.android.empty_room;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

public class RoomActivity extends AppCompatActivity  implements RoomPresenter.View{
    private TextView textViewDay;
    private RoomAdapter roomAdapter;
    private RoomPresenter roomPresenter;
    private ArrayList<RoomData> roomDatas = null;
    private ListView listView;
    private Button btn_all, btn_eb, btn_sb, btn_nb;
    private ImageButton btn_menu, btn_setting, btn_back;
    private RadioGroup radioGroup_d;

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);
        setCustomActionBar();

        roomPresenter = new RoomPresenterImp(RoomActivity.this);
        roomPresenter.setView(this);

        initView();

        SharedPreferences mPref = getSharedPreferences("isFirst", Activity.MODE_PRIVATE);
        Boolean bFirst = mPref.getBoolean("isFirst", false);
        if (bFirst == false) {
            Log.d("version", "first");
            SharedPreferences.Editor editor = mPref.edit();
            editor.putBoolean("isFirst", true);
            editor.commit();
            BuildingNum temp = new BuildingNum(99);
            temp.save();
            openDialog();
        }
        if (bFirst == true) {
            Log.d("version", "not first");
        }

        getOpenData();

        initModel();
        makeList();
        aboutView();




    }

    public void initView () {
        listView = (ListView) findViewById(R.id.listView);
        btn_all = (Button) findViewById(R.id.btn_all);
        btn_eb = (Button) findViewById(R.id.btn_eb);
        btn_sb = (Button) findViewById(R.id.btn_sb);
        btn_nb = (Button) findViewById(R.id.btn_nb);
    }

    public void aboutView() {

        ArrayList<Integer> presentTime;
        presentTime = getPresentTime();
        int time = presentTime.get(1);
        int index = (time - 9) * 2;
        if (presentTime.get(2) >= 30) {
            index += 1;
        }

        BuildingNum temp = BuildingNum.findById(BuildingNum.class, 1);
        ArrayList<RoomData> roomData_temp;
        roomData_temp = roomPresenter.onGetRoom(temp.buildingnum, index);
        roomDatas.addAll(roomData_temp);
        roomAdapter.notifyDataSetChanged();

        final int finalIndex = index;
        btn_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<RoomData> roomData_temp;
                roomData_temp = roomPresenter.onGetRoom(0, finalIndex);
                roomDatas.clear();
                roomDatas.addAll(roomData_temp);
                roomAdapter.notifyDataSetChanged();
            }
        });
        btn_eb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<RoomData> roomData_temp;
                roomData_temp = roomPresenter.onGetRoom(1, finalIndex);
                roomDatas.clear();
                roomDatas.addAll(roomData_temp);
                Log.v("태그", roomDatas.get(0).room);
                roomAdapter.notifyDataSetChanged();
            }
        });
        btn_sb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<RoomData> roomData_temp;
                roomData_temp = roomPresenter.onGetRoom(2, finalIndex);
                roomDatas.clear();
                roomDatas.addAll(roomData_temp);
                roomAdapter.notifyDataSetChanged();
            }
        });
        btn_nb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<RoomData> roomData_temp;
                roomData_temp = roomPresenter.onGetRoom(3, finalIndex);
                roomDatas.clear();
                roomDatas.addAll(roomData_temp);
                roomAdapter.notifyDataSetChanged();
            }
        });

    }

    public ArrayList<Integer> getPresentTime () {
        ArrayList<Integer> presentTime;
        presentTime = new ArrayList<Integer>();

        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        int time = calendar.get(Calendar.HOUR_OF_DAY);
        int min = calendar.get(Calendar.MINUTE);

        if (time < 9) {
            time = 9;
            min = 0;
        } else if ( time > 18) {
            day += 1;
            time = 9;
            min = 0;
        }
        if (day < 2 || day > 6) {
            day = 2;
        }
        day = day - 1;
        presentTime.add(day);
        presentTime.add(time);
        presentTime.add(min);

        return presentTime;
    }

    public void getOpenData () {

        ArrayList<Integer> presentTime;
        presentTime = getPresentTime();
        int day = presentTime.get(0);
        int time = presentTime.get(1);
        int index = (time - 9) * 2;
        if (presentTime.get(2) >= 30) {
            index += 1;
        }
        int success = roomPresenter.getRoomTotal(day, index);

        changeDayText(day);

        if (success == 0) {
            Toast.makeText(this, "서버에 문제가 있습니다. 잠시 후 다시 시도해 주세요", Toast.LENGTH_SHORT).show();
        }
    }

    public void openDialog () {
        final int[] temp = new int[1];
        LayoutInflater layoutInflater = (LayoutInflater)getLayoutInflater();
        View dialogLayout = layoutInflater.inflate(R.layout.first_dialog, null);

        radioGroup_d = (RadioGroup) dialogLayout.findViewById(R.id.radio_building);

        radioGroup_d.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId) {
                    case R.id.radio_eb_d :
                        temp[0] = 1;
                        break;
                    case R.id.radio_sb_d :
                        temp[0] = 2;
                        break;
                    case R.id.radio_nb_d :
                        temp[0] = 3;
                        break;
                }
            }
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(RoomActivity.this);
        builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                roomPresenter.changeBuilding(temp[0]);
            }
        });

        builder.setTitle("주로 가는 건물");
        builder.setView(dialogLayout);


        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void initModel () {
        roomDatas = new ArrayList<RoomData> ();
    }

    private void makeList() {
        roomAdapter = new RoomAdapter(roomDatas,getApplicationContext());
        listView.setAdapter(roomAdapter);
    }

    private void setCustomActionBar () {

        drawerLayout = (DrawerLayout) findViewById(R.id.dl_room_activity);
        navigationView = (NavigationView) findViewById(R.id.navigation);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                String day = (String) item.getTitle();
                char temp = day.charAt(4);
                switch (temp) {
                    case '월' :
                        changeDay(0);
                        break;
                    case '화' :
                        changeDay(1);
                        break;
                    case '수' :
                        changeDay(2);
                        break;
                    case '목' :
                        changeDay(3);
                        break;
                    case '금' :
                        changeDay(4);
                        break;
                }
                btn_menu.setVisibility(View.VISIBLE);
                btn_back.setVisibility(View.GONE);
                drawerLayout.closeDrawer(Gravity.LEFT);
                return false;
            }

        });




        ActionBar actionBar = getSupportActionBar();

        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);

        View actionBarView = LayoutInflater.from(this).inflate(R.layout.layout_actionbar, null);

        btn_menu = (ImageButton) actionBarView.findViewById(R.id.btn_menu);
        btn_setting = (ImageButton) actionBarView.findViewById(R.id.btn_setting);
        btn_back = (ImageButton) actionBarView.findViewById(R.id.btn_back);
        textViewDay = (TextView) actionBarView.findViewById(R.id.textView_day);

        btn_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(Gravity.LEFT);
                btn_menu.setVisibility(View.GONE);
                btn_back.setVisibility(View.VISIBLE);
            }
        });
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.closeDrawer(Gravity.LEFT);
                btn_menu.setVisibility(View.VISIBLE);
                btn_back.setVisibility(View.GONE);
            }
        });

        btn_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });


        actionBar.setCustomView(actionBarView);

        toolbar = (Toolbar) actionBarView.getParent();
        toolbar.setContentInsetsAbsolute(0,0);

        ActionBar.LayoutParams params = new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT);
        actionBar.setCustomView(actionBarView, params);
    }

    public void changeDay(int order) {
        roomPresenter.getRoomTotal(order,0);
        BuildingNum temp = BuildingNum.findById(BuildingNum.class, 1);
        ArrayList<RoomData> roomData_temp;
        roomData_temp = roomPresenter.onGetRoom(temp.buildingnum, 0);
        roomDatas.clear();
        roomDatas.addAll(roomData_temp);
        roomAdapter.notifyDataSetChanged();
        changeDayText(order);

    }

    public void changeDayText (int day) {
        switch (day) {
            case 0:
                textViewDay.setText("월요일");
                break;
            case 1:
                textViewDay.setText("화요일");
                break;
            case 2:
                textViewDay.setText("수요일");
                break;
            case 3:
                textViewDay.setText("목요일");
                break;
            case 4:
                textViewDay.setText("금요일");
                break;

        }
    }



}

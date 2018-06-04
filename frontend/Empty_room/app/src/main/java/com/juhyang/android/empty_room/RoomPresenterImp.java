package com.juhyang.android.empty_room;

import android.app.Activity;

import java.util.ArrayList;

/**
 * Created by kkss2 on 2017-04-16.
 */

public class RoomPresenterImp implements RoomPresenter {
    private RoomTotalData roomTotalData = new RoomTotalData();
    private Activity activity;
    private RoomPresenter.View view;
    private RoomModel roomModel;

    public RoomPresenterImp (Activity activity) {
        this.activity = activity;
        this.roomModel = new RoomModel();
    }

    public void setView(RoomPresenter.View view) {
        this.view = view;
    }

    @Override
    public int getRoomTotal(int day, int time) {
        RoomTotalData temp = roomModel.getRoomTotal(day, time);
        roomTotalData.roomTotalData = new ArrayList<>();
        roomTotalData.roomTotalData.addAll(temp.roomTotalData);
        roomTotalData.success = temp.success;
        return roomTotalData.success;
    }

    @Override
    public ArrayList<RoomData> onGetRoom(int buildingNum, int time) {
        int realtime = (time / 2) + 9;
        int status;
        if (time % 2 == 0 ) {
            status = 0;
        } else {
            status = 1;
        }
        roomModel.makeText(realtime, status);
        if (buildingNum == 0) {
            return roomModel.makeArray(0, realtime, status, roomTotalData);
        } else if (buildingNum == 1) {
            return roomModel.makeArray(1, realtime, status, roomTotalData);
        } else if (buildingNum == 2) {
            return roomModel.makeArray(2, realtime, status, roomTotalData);
        } else if (buildingNum == 3) {
            return roomModel.makeArray(3, realtime, status, roomTotalData);
        }
        return null;
    }



    @Override
    public void changeBuilding(int changeNum) {
        BuildingNum buildingNum = BuildingNum.findById(BuildingNum.class, 1);
        buildingNum.buildingnum = changeNum;
        buildingNum.save();
    }
}

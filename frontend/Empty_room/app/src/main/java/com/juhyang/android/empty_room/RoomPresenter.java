package com.juhyang.android.empty_room;

import java.util.ArrayList;

/**
 * Created by kkss2 on 2017-04-16.
 */

public interface RoomPresenter {
    void setView (RoomPresenter.View view);

    int getRoomTotal (int day, int index);
    ArrayList<RoomData> onGetRoom (int buildingNum, int time);
    void changeBuilding(int buildingNum);

    public interface View {
    }

}

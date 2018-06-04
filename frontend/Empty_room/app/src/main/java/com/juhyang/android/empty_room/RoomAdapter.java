package com.juhyang.android.empty_room;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by kkss2 on 2017-04-15.
 */

public class RoomAdapter extends BaseAdapter{
    private ArrayList<RoomData> roomDatas = null;
    private LayoutInflater layoutInflater = null;

    public RoomAdapter(ArrayList<RoomData> roomDatas, Context ctx){

        this.roomDatas = roomDatas;
        layoutInflater = (LayoutInflater)ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setRoomDatas(ArrayList<RoomData> roomDatas){
        this.roomDatas = roomDatas;
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return roomDatas !=null ? roomDatas.size():0;
    }

    @Override
    public Object getItem(int position) {
        return (roomDatas !=null && (position >= 0 && position < roomDatas.size()) ? roomDatas.get(position) : null );
    }

    @Override
    public long getItemId(int position) {
        return (roomDatas !=null && (position >= 0 && position < roomDatas.size()) ? position : 0 );
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        RoomViewHolder roomViewHolder = new RoomViewHolder();
        if(convertView ==null){
            convertView = layoutInflater.inflate(R.layout.list_item,parent,false);

            roomViewHolder.textView_time = (TextView) convertView.findViewById(R.id.textView_time);
            roomViewHolder.textView_room = (TextView) convertView.findViewById(R.id.textView_room);


            convertView.setTag(roomViewHolder);
        }

        else{
            roomViewHolder = (RoomViewHolder)convertView.getTag();
        }

        RoomData roomData_temp = roomDatas.get(position);
        roomViewHolder.textView_time.setText(roomData_temp.time);
        roomViewHolder.textView_room.setText(roomData_temp.room);

        return convertView;
    }
}

package com.example.demo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.LinkedList;

public class ListViewAdapter extends BaseAdapter {
    private LinkedList<RecordBean> list=new LinkedList<>();
    private LayoutInflater layoutInflater;
    private Context context;

    public ListViewAdapter(Context context){
        this.context=context;
        layoutInflater=LayoutInflater.from(context);
    }
    public void setData(LinkedList<RecordBean> records){
        this.list = records;
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null){
            convertView = layoutInflater.inflate(R.layout.cell_list_view,null);

            RecordBean recordBean = (RecordBean) getItem(position);
            holder = new ViewHolder(convertView, recordBean);

            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        return convertView;
    }

class ViewHolder{
    TextView remarkTV;
    TextView amountTV;
    TextView timeTV;
    ImageView categoryIcon;

    public ViewHolder(View view, RecordBean record){
        remarkTV = view.findViewById(R.id.textView_remark);
        amountTV = view.findViewById(R.id.textView_amount);
        timeTV = view.findViewById(R.id.textView_time);
        categoryIcon = view.findViewById(R.id.imageView_category);

        remarkTV.setText(record.getRemark());
        if (record.getType() == 1){
            amountTV.setText("- "+record.getAmount());
        } else {
            amountTV.setText("+ "+record.getAmount());
        }

        timeTV.setText(DateUtil.getFormattedTime(record.getTimeStamp()));
        categoryIcon.setImageResource(GlobalUtil.getInstance().getResourceIcon(record.getCategory()));
    }
}
}

package com.example.demo;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.LinkedList;
import java.util.Objects;

public class MainFragment extends Fragment implements AdapterView.OnItemClickListener {
    private View view;
    private TextView textView;
    private ListView listView;
    private ListViewAdapter listViewAdapter;
    private LinkedList<RecordBean> recordBeans=new LinkedList<>();
    private String date="";

    public MainFragment(String date){
        this.date=date;
        recordBeans=GlobalUtil.getInstance().databaseHelper.readRecords(date);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_main,container,false);
        initView();
        return view;
    }

    public void reload(){

        recordBeans = GlobalUtil.getInstance().databaseHelper.readRecords(date);
        if (listViewAdapter==null){
            listViewAdapter = new ListViewAdapter(getActivity().getApplicationContext());
        }

        listViewAdapter.setData(recordBeans);
        listView.setAdapter(listViewAdapter);

        if (listViewAdapter.getCount()>0){
            view.findViewById(R.id.no_record_layout).setVisibility(View.INVISIBLE);
        }
    }

    private void initView(){
        textView = (TextView) view.findViewById(R.id.day_text);
        listView = (ListView) view.findViewById(R.id.listView);
        textView.setText(date);
        listViewAdapter = new ListViewAdapter(getContext());
        listViewAdapter.setData(recordBeans);
        listView.setAdapter(listViewAdapter);

        if (listViewAdapter.getCount()>0){
            view.findViewById(R.id.no_record_layout).setVisibility(View.INVISIBLE);
        }

        textView.setText(DateUtil.getDateTitle(date));

        listView.setOnItemClickListener(this);
    }

    public int getTotalCost(){
        double totalCost = 0;
        for (RecordBean record: recordBeans){
            if (record.getType()==1){
                totalCost+= record.getAmount();
            }
        }
        return (int)totalCost;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        showDialog(position);
    }

    private void showDialog(int index){
        final String[] options={"Remove","Edit"};
        final RecordBean selectedRecord = recordBeans.get(index);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.create();
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which==0){
                    String uuid = selectedRecord.getUuid();
                    GlobalUtil.getInstance().databaseHelper.removeRecord(uuid);
                    reload();
                    GlobalUtil.getInstance().mainActivity.updateHeader();
                }else if (which==1){
                    Intent intent = new Intent(getActivity(),AddActivity.class);
                    Bundle extra = new Bundle();
                    extra.putSerializable("record",selectedRecord);
                    intent.putExtras(extra);
                    startActivityForResult(intent,1);
                }
            }
        });
        builder.setNegativeButton("Button",null);
        builder.create().show();
    }
}

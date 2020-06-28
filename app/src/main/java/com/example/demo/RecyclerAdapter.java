package com.example.demo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.LinkedList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.CViewHolder> {
    private LayoutInflater layoutInflater;
    private Context context;
    private LinkedList<ResBean> list=GlobalUtil.getInstance().costRes;
    private String selected="";
    private OnCategoryClickListener onCategoryClickListener;
    public String getSelected(){
        return this.selected;
    }
    public void setOnCategoryClickListener(OnCategoryClickListener onCategoryClickListener){
        this.onCategoryClickListener=onCategoryClickListener;
    }

    public RecyclerAdapter(Context context){
        GlobalUtil.getInstance().setContext(context);
        this.context=context;
        layoutInflater=LayoutInflater.from(context);
        selected=list.get(0).title;
    }
    @NonNull
    @Override
    public CViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=layoutInflater.inflate(R.layout.cell_category,parent,false);
        CViewHolder holder=new CViewHolder(view);
        return holder;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onBindViewHolder(@NonNull CViewHolder holder, int position) {
        final ResBean bean=list.get(position);
        holder.imageView.setImageResource(bean.resWhite);
        holder.textView.setText(bean.title);
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selected=bean.title;
                notifyDataSetChanged();
                if(onCategoryClickListener!=null){
                    onCategoryClickListener.Click(bean.title);
                }

            }
        });
        if(holder.textView.getText().toString().equals(selected))
        {
            holder.background.setBackgroundResource(R.drawable.bg_edit_text);
        }
        else {
            holder.background.setBackgroundResource(R.color.colorPrimary);
        }
    }

    public void ChangeType(RecordBean.RecordType type){
        if (type == RecordBean.RecordType.RECORD_TYPE_EXPENSE){
            list = GlobalUtil.getInstance().costRes;
        }else {
            list = GlobalUtil.getInstance().earnRes;
        }

        selected = list.get(0).title;
        notifyDataSetChanged();
    }

    public interface OnCategoryClickListener{
        void Click(String category);
    }

    class CViewHolder extends RecyclerView.ViewHolder{
        RelativeLayout background;
        ImageView imageView;
        TextView textView;
        public CViewHolder(@NonNull View itemView) {
            super(itemView);
            background=(RelativeLayout) itemView.findViewById(R.id.cell_background);
            imageView=(ImageView) itemView.findViewById(R.id.imageView_category);
            textView=(TextView)itemView.findViewById(R.id.textView_category);
        }
    }
}

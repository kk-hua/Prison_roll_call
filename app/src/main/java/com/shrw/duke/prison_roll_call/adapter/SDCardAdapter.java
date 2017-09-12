package com.shrw.duke.prison_roll_call.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shrw.duke.prison_roll_call.R;
import com.shrw.duke.prison_roll_call.entity.FileInfo;
import com.shrw.duke.prison_roll_call.listener.OnRecyclerViewItemClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rw-duke on 2017/9/11.
 */

public class SDCardAdapter extends RecyclerView.Adapter implements View.OnClickListener {
    private Context context;
    private List<FileInfo> datas = new ArrayList<>();
    private OnRecyclerViewItemClickListener mOnItemClickListener;

    public SDCardAdapter(Context context, List<FileInfo> datas) {
        this.context = context;
        this.datas = datas;
    }

    public SDCardAdapter(Context context) {
        this.context = context;
    }

    public void addData(List<FileInfo> datas) {
        this.datas = datas;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //加载holder
        View view = LayoutInflater.from(context).inflate(R.layout.file_item, parent, false);
        MyViewHolder holder = new MyViewHolder(view);

        //给item设置监听
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);
        view.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyViewHolder viewHolder = (MyViewHolder) holder;
        viewHolder.tv_name.setText(datas.get(position).getFileName());
        viewHolder.tv_path.setText(datas.get(position).getFilePath());
        viewHolder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取数据
            mOnItemClickListener.onItemClick(v, (int) v.getTag(), datas.get((int) v.getTag()));
        }
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_name,tv_path;
        private LinearLayout item;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv_name = (TextView) itemView.findViewById(R.id.tv_file_name);
            tv_path = (TextView) itemView.findViewById(R.id.tv_file_path);
            item = (LinearLayout) itemView.findViewById(R.id.linear_file_item);
        }

    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }


}

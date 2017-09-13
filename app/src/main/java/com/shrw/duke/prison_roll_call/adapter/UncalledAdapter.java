package com.shrw.duke.prison_roll_call.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shrw.duke.prison_roll_call.R;
import com.shrw.duke.prison_roll_call.entity.PeopleRoll;
import com.shrw.duke.prison_roll_call.listener.OnRecyclerViewItemClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rw-duke on 2017/9/12.
 */

public class UncalledAdapter extends RecyclerView.Adapter implements View.OnClickListener {

    private Context mContext;
    private List<PeopleRoll> mNames;
    private OnRecyclerViewItemClickListener mOnItemClickListener;
    private int size;

    public UncalledAdapter(Context mContext, List<PeopleRoll> mNames) {
        this.mContext = mContext;
        this.mNames = mNames;
    }

    public UncalledAdapter(Context context) {
        this.mContext = context;
        mNames = new ArrayList<>();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //加载holder
        View view = LayoutInflater.from(mContext).inflate(R.layout.uncalled_list_item, parent, false);
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
        viewHolder.tv_name.setText(mNames.get(position).getName());
        viewHolder.itemView.setTag(position);
    }


    @Override
    public int getItemCount() {
        return mNames.size();
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取数据
            mOnItemClickListener.onItemClick(v, (int) v.getTag(), mNames.get((int) v.getTag()));
        }
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_name;
        private LinearLayout item;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv_name = (TextView) itemView.findViewById(R.id.personName);
//            item = (LinearLayout) itemView.findViewById(R.id.linear_file_item);
        }

    }

    /**
     * 设置item点击监听
     *
     * @param listener
     */
    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    public boolean add(PeopleRoll peopleRoll){
        if (!contains(peopleRoll.getRfid())){
            return mNames.add(peopleRoll);
        }
        return false;
    }
    /**
     *
     * @param s
     * @return
     */
    public boolean remove(String s) {
        size = getItemCount();
        PeopleRoll peopleRoll = null;
        String rfid;
        if (s == null) {
            for (int index = 0; index < size; index++) {
                peopleRoll = mNames.get(index);
                rfid = peopleRoll.getRfid();
                if (rfid == null) {
                    mNames.remove(index);
                    return true;
                }
            }

        } else {
            for (int index = 0; index < size; index++){
                peopleRoll = mNames.get(index);
                rfid = peopleRoll.getRfid();
                if (rfid.equals(s)){
                    mNames.remove(index);
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 是否包含这个id的对象
     * @param id    标签rfid编号
     * @return  true：已经存在   false:不存在
     */
    public boolean contains(String id){
        return indexOf(id)>=0;
    }

    private int indexOf(String id) {
        size = getItemCount();
        PeopleRoll peopleRoll;
        String rfid;
        if (id == null) {
            for (int i = 0; i < size; i++){
                peopleRoll = mNames.get(i);
                rfid = peopleRoll.getRfid();
                if (rfid == null)
                    return i;
            }
        } else {
            for (int i = 0; i < size; i++){
                peopleRoll = mNames.get(i);
                rfid = peopleRoll.getRfid();
                if (rfid.equals(id))
                    return i;
            }
        }
        return -1;
    }

}

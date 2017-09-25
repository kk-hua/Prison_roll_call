package com.shrw.duke.prison_roll_call.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.shrw.duke.portlibrary.common.bean.type18.TagBase18;
import com.shrw.duke.prison_roll_call.R;
import com.shrw.duke.prison_roll_call.activity.MainActivity;
import com.shrw.duke.prison_roll_call.adapter.UncalledAdapter;
import com.shrw.duke.prison_roll_call.common.Constant;
import com.shrw.duke.prison_roll_call.entity.FileInfo;
import com.shrw.duke.prison_roll_call.entity.PeopleRoll;
import com.shrw.duke.prison_roll_call.listener.OnActivityOrFragmentArgListener;
import com.shrw.duke.prison_roll_call.listener.OnRecyclerViewItemClickListener;
import com.shrw.duke.prison_roll_call.listener.OnRecyclerViewItemLongClickListener;
import com.shrw.duke.prison_roll_call.utils.ListUtils;
import com.shrw.duke.prison_roll_call.utils.ToastUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 */
public class UncalledFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, OnRecyclerViewItemClickListener<PeopleRoll>, OnRecyclerViewItemLongClickListener<PeopleRoll>, OnActivityOrFragmentArgListener<TagBase18.Tag18>, PopupMenu.OnMenuItemClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String PEOPLE_ROLL_LIST = "people_roll_list";
    private static final String NAME_LIST = "PEOPLE_NAME_LIST";

    private OnActivityOrFragmentArgListener mMainActivityArgListener = null;
    private MainActivity mMainActivity = null;

    private List<String> mRfidList; //rfid列表
    private List<String> mNameList; //名字列表
    private List<PeopleRoll> mPeopleRollList = new ArrayList<>(); //名字列表
    private Map<String, PeopleRoll> mPeopleRollMap;
    private PeopleRoll mEditPeopleFlag = null;//备注未到人员


    //适配器
    private UncalledAdapter mUncalledAdapter;

    @BindView(R.id.unCalledFragment_recycler_list)
    RecyclerView mUncalledRecycle;

    @BindView(R.id.uncalled_swipeRefresh)
    SwipeRefreshLayout mRefreshLayout;

    @BindView(R.id.uncalledFragment_Tv_null_hint)
    TextView mHint;
    @BindView(R.id.tv_uncalled_peopleNum)
    TextView mPeopleNum;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param peopleRollList Parameter 1.
     * @return A new instance of fragment UncalledFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UncalledFragment newInstance(List<PeopleRoll> peopleRollList) {
        UncalledFragment fragment = new UncalledFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(NAME_LIST, (ArrayList<? extends Parcelable>) peopleRollList);
//        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v(NAME_LIST, "onCreate()");
        if (getArguments() != null) {
            mPeopleRollList = getArguments().getParcelableArrayList(NAME_LIST);
            mRfidList = new ArrayList<>();
            mNameList = new ArrayList<>();
            mPeopleRollMap = new HashMap<>();
            int len = mPeopleRollList.size();
            for (int i = 0; i < len; i++) {
                PeopleRoll preson = mPeopleRollList.get(i);
                String rfid = preson.getRfid();
                String name = preson.getName();
                if (!TextUtils.isEmpty(rfid) && !TextUtils.isEmpty(name)) {
                    mRfidList.add(rfid);
                    mNameList.add(name);
                    mPeopleRollMap.put(preson.getRfid(), preson);
                }

            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_uncalled, container, false);
        ButterKnife.bind(this, view);
        Log.v(NAME_LIST, "onCreateView()");
        init();
        return view;
    }

    /**
     * 初始化
     */
    private void init() {
        mPeopleNum.setText(String.valueOf(mPeopleRollList.size()));
        mUncalledAdapter = new UncalledAdapter(getContext().getApplicationContext(), mPeopleRollList);
        mUncalledRecycle.setLayoutManager(new LinearLayoutManager(getContext()));
        mUncalledRecycle.setAdapter(mUncalledAdapter);
        mUncalledAdapter.setOnItemClickListener(this);
        mUncalledAdapter.setOnItemLongClickListener(this);
        mRefreshLayout.setOnRefreshListener(this);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (mMainActivity == null)
            mMainActivity = (MainActivity) context;

        if (context instanceof OnActivityOrFragmentArgListener) {
            if (mMainActivityArgListener == null)
                mMainActivityArgListener = (OnActivityOrFragmentArgListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnActivityOrFragmentArgListener");
        }


    }

    @Override
    public void onResume() {
        super.onResume();
        Log.v(NAME_LIST, "onResume()");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.v(NAME_LIST, "onDetach()");
    }


    /**
     * 接收activity传递过来的参数
     *
     * @param context
     * @param data    标签数据
     */
    @Override
    public void onData(Context context, final TagBase18.Tag18 data, int type) {

        if (mUncalledAdapter != null &&
                mUncalledAdapter.contains(data.getTagId())) {
            Log.e(NAME_LIST + "\t", data.getTagId());

            switch (type) {
                case Constant.PORT_DATA_TYPE:
                    if (mMainActivityArgListener != null) {
                        mMainActivityArgListener.onData(getContext(), data.getTagId(), Constant.HAS_TO_TYPE);
                    }
                    mUncalledAdapter.remove(data.getTagId());
                    mUncalledAdapter.notifyDataSetChanged();
                    mPeopleNum.setText(String.valueOf(mPeopleRollList.size()));

                    Log.d("mmmmmmmmmmm", mPeopleRollList.size() + "");
                    break;
                case Constant.UNCALLED_TYPE:
                    break;

                case Constant.REFRESH:

                    break;

                default:
                    break;
            }


        }

    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_details:
                if (mEditPeopleFlag != null) {

                    final TextView name, carid, rfid, room, type, level;
                    final EditText note;
                    ToastUtil.showToast(mEditPeopleFlag.getRfid());
                    View view = getLayoutInflater(null).inflate(R.layout.dialog_edit_info, null);
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setTitle(R.string.details);//设置title
//                    builder.setView(R.layout.dialog_edit_info);//添加布局
                    builder.setView(view);//添加布局
                    builder.setCancelable(true);//true：点击其他地方关闭

                    //初始化dialog
                    name = (TextView) view.findViewById(R.id.tv_dialog_name);
                    carid = (TextView) view.findViewById(R.id.tv_dialog_carid);
                    rfid = (TextView) view.findViewById(R.id.tv_dialog_rfid);
                    room = (TextView) view.findViewById(R.id.tv_dialog_room);
                    type = (TextView) view.findViewById(R.id.tv_dialog_type);
                    level = (TextView) view.findViewById(R.id.tv_dialog_level);
                    note = (EditText) view.findViewById(R.id.edit_dialog_note);

                    name.setText(getString(R.string.summary_name, mEditPeopleFlag.getName()));
                    carid.setText(getString(R.string.summary_carid, mEditPeopleFlag.getCarid()));
                    rfid.setText(getString(R.string.summary_rfid, mEditPeopleFlag.getRfid()));
                    room.setText(getString(R.string.summary_room, mEditPeopleFlag.getRoom()));
                    type.setText(getString(R.string.summary_type, "0".equals(mEditPeopleFlag.getType()) ? "罪犯" : "警员"));
                    level.setText(getString(R.string.summary_level, mEditPeopleFlag.getLevel()));

                    builder.setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //确定
                            ToastUtil.showToast(room.getText().toString());
                            String strNote = note.getText().toString();
                            if (!TextUtils.isEmpty(strNote)) {
                                mEditPeopleFlag.setNote(strNote);
                                int index = ListUtils.indexOf(mPeopleRollList, mEditPeopleFlag.getRfid());
                                mPeopleRollList.set(index, mEditPeopleFlag);
//                                mMainActivityArgListener.onData(getContext(), mPeopleRollList, Constant.NOTE_TYPE);

                            } else {
                                ToastUtil.showToast(getString(R.string.not_is_null));
                            }
                        }
                    });

                    builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    builder.show(); //显示
                }

                break;
            default:
                break;
        }
        return false;
    }

    @Override
    public void onItemClick(View view, int position, PeopleRoll data) {

    }

    @Override
    public void onItemLongClick(View view, int position, PeopleRoll data) {
        Log.e(NAME_LIST, data.getName() + "\t" + position);
        //创建弹出式菜单对象（最低版本11）
        PopupMenu popup = new PopupMenu(getContext(), view);//第二个参数是绑定的那个view
        //获取菜单填充器
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.item_popup_menu, popup.getMenu());
        popup.setOnMenuItemClickListener(this);
        popup.show();
        if (data != null) {
            mEditPeopleFlag = data;
        }
    }

    public List<PeopleRoll> getUnCalledPeopleRool() {
        if (mPeopleRollList != null) {
            return mPeopleRollList;
        }
        return null;
    }


    @Override
    public void onRefresh() {
        if (mMainActivity != null) {
            mPeopleRollList = mMainActivity.getPeopleList();
        }
        mUncalledAdapter.add(mPeopleRollList);
        mPeopleNum.setText(String.valueOf(mPeopleRollList.size()));
//        mUncalledAdapter.notifyDataSetChanged();
        mRefreshLayout.setRefreshing(false);
    }
}

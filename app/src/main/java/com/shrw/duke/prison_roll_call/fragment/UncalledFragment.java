package com.shrw.duke.prison_roll_call.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shrw.duke.prison_roll_call.R;
import com.shrw.duke.prison_roll_call.adapter.UncalledAdapter;
import com.shrw.duke.prison_roll_call.entity.FileInfo;
import com.shrw.duke.prison_roll_call.entity.PeopleRoll;
import com.shrw.duke.prison_roll_call.listener.OnRecyclerViewItemClickListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 */
public class UncalledFragment extends Fragment implements OnRecyclerViewItemClickListener<String>{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String PEOPLE_ROLL_LIST = "people_roll_list";
    private static final String NAME_LIST = "PEOPLE_NAME_LIST";

    private List<String> mRfidList; //rfid列表
    private List<String> mNameList; //名字列表
    private List<PeopleRoll> mPeopleRollList; //名字列表
    private Map<String,PeopleRoll> mPeopleRollMap;
    //适配器
    private UncalledAdapter mUncalledAdapter;

    @BindView(R.id.unCalledFragment_recycler_list)
    RecyclerView mUncalledRecycle;
    @BindView(R.id.uncalledFragment_Tv_null_hint)
    TextView mHint;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param peopleRollList Parameter 1.
     * @return A new instance of fragment UncalledFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UncalledFragment newInstance(List<PeopleRoll> peopleRollList ) {
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
        Log.v(NAME_LIST,"onCreate()");
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
                if (!TextUtils.isEmpty(rfid)&&!TextUtils.isEmpty(name)){
                    mRfidList.add(rfid);
                    mNameList.add(name);
                    mPeopleRollMap.put(preson.getRfid(),preson);
                }

            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_uncalled, container, false);
        ButterKnife.bind(this,view);
        Log.v(NAME_LIST,"onCreateView()");
        init();
        return view;
    }

    /**
     * 初始化
     */
    private void init() {
        mUncalledAdapter = new UncalledAdapter(getContext().getApplicationContext(),mPeopleRollList);
        mUncalledRecycle.setLayoutManager(new LinearLayoutManager(getContext()));
        mUncalledRecycle.setAdapter(mUncalledAdapter);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.v(NAME_LIST,"onAttach()");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.v(NAME_LIST,"onResume()");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.v(NAME_LIST,"onDetach()");
    }


    @Override
    public void onItemClick(View view, int position, String data) {
        Log.e(NAME_LIST,data+"\t"+position);
    }
}

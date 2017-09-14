package com.shrw.duke.prison_roll_call.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shrw.duke.prison_roll_call.R;
import com.shrw.duke.prison_roll_call.adapter.UncalledAdapter;
import com.shrw.duke.prison_roll_call.common.Constant;
import com.shrw.duke.prison_roll_call.entity.PeopleRoll;
import com.shrw.duke.prison_roll_call.listener.OnActivityOrFragmentArgListener;
import com.shrw.duke.prison_roll_call.utils.ListUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.ContentValues.TAG;

/**
 */
public class HasToFragment extends Fragment implements OnActivityOrFragmentArgListener<PeopleRoll> {
    @BindView(R.id.hasToFragment_recycler_list)
    RecyclerView mHasToRecycler;
    @BindView(R.id.tv_hasTo_peopleNum)
    TextView mPeopleNum;

    private UncalledAdapter mAdapter;

    private OnActivityOrFragmentArgListener mListener;

    public static List<PeopleRoll> mPeopleRollList = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_has_to, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mPeopleNum.setText(String.valueOf(mPeopleRollList.size()));
        mAdapter = new UncalledAdapter(getContext(), mPeopleRollList);
        mHasToRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        mHasToRecycler.setAdapter(mAdapter);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnActivityOrFragmentArgListener) {
            mListener = (OnActivityOrFragmentArgListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnActivityOrFragmentArgListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    @Override
    public void onData(Context context, PeopleRoll data, int type) {

        switch (type){
            case Constant.HAS_TO_TYPE:
                if (!ListUtils.contains(mPeopleRollList, data.getRfid()))
                    mPeopleRollList.add(data);
                if (mAdapter != null) {
                    mPeopleNum.setText(String.valueOf(mPeopleRollList.size()));
                    mAdapter.notifyDataSetChanged();
                }
                break;
            default:
                break;
        }

//        if (!ListUtils.contains(mPeopleRollList, data.getRfid()))
//            mPeopleRollList.add(data);
//        if (mAdapter != null) {
//            mPeopleNum.setText(String.valueOf(mPeopleRollList.size()));
//            mAdapter.notifyDataSetChanged();
//        }
        Log.i("HasToFragment:\t", data.toString());
    }

}

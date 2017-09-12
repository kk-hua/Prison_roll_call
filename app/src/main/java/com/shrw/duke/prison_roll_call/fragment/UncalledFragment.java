package com.shrw.duke.prison_roll_call.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
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
import com.shrw.duke.prison_roll_call.entity.PeopleRoll;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link UncalledFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link UncalledFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UncalledFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String PEOPLE_ROLL_LIST = "people_roll_list";
    private static final String NAME_LIST = "PEOPLE_NAME_LIST";

    private List<PeopleRoll> mPeopleRollList;
    private List<String> mNameList;
    //适配器
    private UncalledAdapter mUncalledAdapter;

    @BindView(R.id.unCalledFragment_recycler_list)
    RecyclerView mUncalledRecycle;
    @BindView(R.id.uncalledFragment_Tv_null_hint)
    TextView mHint;

    // TODO: Rename and change types of parameters

    private OnFragmentInteractionListener mListener;

    public UncalledFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param peopleRollList Parameter 1.
     * @return A new instance of fragment UncalledFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UncalledFragment newInstance(List<String> peopleRollList ) {
        UncalledFragment fragment = new UncalledFragment();
        Bundle args = new Bundle();
        args.putStringArrayList(NAME_LIST, (ArrayList<String>) peopleRollList);
//        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mNameList = getArguments().getStringArrayList(NAME_LIST);
        }
        Log.v(NAME_LIST,"onCreate()");
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

    private void init() {
        mUncalledAdapter = new UncalledAdapter(getContext().getApplicationContext(),mNameList);
        mUncalledRecycle.setLayoutManager(new LinearLayoutManager(getContext()));
        mUncalledRecycle.setAdapter(mUncalledAdapter);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.v(NAME_LIST,"onAttach()");
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
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
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}

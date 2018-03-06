package com.dreamwalker.knu2018.dteacher.Fragment;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;

import com.dreamwalker.knu2018.dteacher.Adapter.DiaryDangAdapter;
import com.dreamwalker.knu2018.dteacher.DBHelper.BSDBHelper;
import com.dreamwalker.knu2018.dteacher.Model.BloodSugar;
import com.dreamwalker.knu2018.dteacher.R;

import org.angmarch.views.NiceSpinner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;

import static android.content.ContentValues.TAG;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DiaryDangFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DiaryDangFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DiaryDangFragment extends Fragment {

    @BindView(R.id.button_graph_show)
    Button buttonGraphShow;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.nice_spinner)
    NiceSpinner niceSpinner;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private BSDBHelper bsdbHelper;
    private SQLiteDatabase db;

//    private TextView dbResult;

    LinearLayoutManager layoutManager;
    DiaryDangAdapter adapter;
    ArrayList<BloodSugar> bloodSugarArrayList;


    public DiaryDangFragment() {
        // Required empty public constructor
    }

    public static DiaryDangFragment newInstance(String param1, String param2) {
        DiaryDangFragment fragment = new DiaryDangFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_diary_dang, container, false);
        ButterKnife.bind(this, view);

        List<String> dataset = new LinkedList<>(Arrays.asList("오름차순", "내림차순", "최대값", "최소값"));
        niceSpinner.attachDataSource(dataset);
        //dbResult = (TextView) view.findViewById(R.id.label);
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    // TODO: 2018-02-07 이곳에 코드 처리
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        bloodSugarArrayList = new ArrayList<>();
        bsdbHelper = new BSDBHelper(getActivity(), "bs.db", null, 1);
        bloodSugarArrayList = bsdbHelper.selectDiaryAll();

        adapter = new DiaryDangAdapter(getActivity(), bloodSugarArrayList);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),layoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setAdapter(new AlphaInAnimationAdapter(adapter));

        niceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.e(TAG, "onItemSelected: " + parent.toString() + "- " + position + "- "+id );
                switch (position){
                    case 0:
                        bloodSugarArrayList.clear();
                        adapter.notifyDataSetChanged();
                        bloodSugarArrayList = bsdbHelper.selectDiaryAll();
                        adapter = new DiaryDangAdapter(getActivity(), bloodSugarArrayList);
                        recyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                        break;
                    case 1:
                        bloodSugarArrayList.clear();
                        adapter.notifyDataSetChanged();
                        bloodSugarArrayList = bsdbHelper.selectDiaryDESC();
                        adapter = new DiaryDangAdapter(getActivity(), bloodSugarArrayList);
                        recyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                        break;
                    case 2:
                        bloodSugarArrayList.clear();
                        adapter.notifyDataSetChanged();
                        bloodSugarArrayList = bsdbHelper.selectDiaryMax();
                        adapter = new DiaryDangAdapter(getActivity(), bloodSugarArrayList);
                        recyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                        break;
                    case 3:
                        bloodSugarArrayList.clear();
                        adapter.notifyDataSetChanged();
                        bloodSugarArrayList = bsdbHelper.selectDiaryMin();
                        adapter = new DiaryDangAdapter(getActivity(), bloodSugarArrayList);
                        recyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        //String dbText = bsdbHelper.selectAllData();
        //dbResult.setText(dbText);
    }

    @OnClick(R.id.button_graph_show)
    public void onButtonGraphShowClicked(){

        

    }

    /**
     * 다른 곳으로 무엇가 요청하고 리턴받을때 이걸 사용하..
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }
    //    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }

    @Override
    public void onDetach() {
        super.onDetach();
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

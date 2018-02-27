package com.dreamwalker.knu2018.dteacher.BSMSync;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import com.dreamwalker.knu2018.dteacher.Activity.DetailListActivity;
import com.dreamwalker.knu2018.dteacher.Activity.GraphAnalysisActivity;
import com.dreamwalker.knu2018.dteacher.Adapter.BSMSyncAdapter;
import com.dreamwalker.knu2018.dteacher.DBHelper.BSDBHelper;
import com.dreamwalker.knu2018.dteacher.Model.BloodSugar;
import com.dreamwalker.knu2018.dteacher.R;
import com.michaldrabik.tapbarmenulib.TapBarMenu;
import com.mingle.entity.MenuEntity;
import com.mingle.sweetpick.BlurEffect;
import com.mingle.sweetpick.RecyclerViewDelegate;
import com.mingle.sweetpick.SweetSheet;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.paperdb.Paper;

public class SyncBMSResultActivity extends AppCompatActivity {

    private static final String TAG = "SyncBMSResultActivity";

    @BindView(R.id.bsm_recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.fab)
    FloatingActionButton fab;

//    @BindView(R.id.tapBarMenu)
//    TapBarMenu tapBarMenu;

    ArrayList<BloodSugar> mBSList;
    RecyclerView.LayoutManager layoutManager;
    BSMSyncAdapter adapter;

    @BindView(R.id.coordinator)
    RelativeLayout coordinator;

    @BindView(R.id.toolbar)
    Toolbar myToolbar;

    //Bottom Sheet
    private SweetSheet mSweetSheet;
    Workbook workbook;
    Sheet sheet; // 새로운 시트 생성

    private BSDBHelper bsdbHelper;
    private SQLiteDatabase db;
    String dbName = "bs.db";
    int dbVersion = 1; // 데이터베이스 버전

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sync_bmsresult);
        setSupportActionBar(myToolbar);
        setTitle("동기화 결과");
        Paper.init(this);
        ButterKnife.bind(this);
        init();
        setupRecyclerView();
        bsdbHelper = new BSDBHelper(this, dbName, null, dbVersion);
        try {
//         // 데이터베이스 객체를 얻어오는 다른 간단한 방법
//         db = openOrCreateDatabase(dbName,  // 데이터베이스파일 이름
//                          Context.MODE_PRIVATE, // 파일 모드
//                          null);    // 커서 팩토리
//
//         String sql = "create table mytable(id integer primary key autoincrement, name text);";
//        db.execSQL(sql);
            db = bsdbHelper.getWritableDatabase(); // 읽고 쓸수 있는 DB
            //db = helper.getReadableDatabase(); // 읽기 전용 DB select문
        } catch (SQLiteException e) {
            e.printStackTrace();
            Log.e(TAG, "데이터베이스를 얻어올 수 없음");
            finish(); // 액티비티 종료
        }
    }

//    @OnClick(R.id.tapBarMenu)
//    public void onMenuButtonClick() {
//        tapBarMenu.toggle();
//    }

    @OnClick(R.id.fab)
    public void onFabClicked() {
        mSweetSheet.toggle();
    }


//    @OnClick({ R.id.item1, R.id.item2, R.id.item3, R.id.item4 })
//    public void onMenuItemClick(View view) {
//        tapBarMenu.close();
//        switch (view.getId()) {
//            case R.id.item1:
//                Log.e("TAG", "Item 1 selected");
//                break;
//            case R.id.item2:
//                Log.e("TAG", "Item 2 selected");
//                break;
//            case R.id.item3:
//                Log.e("TAG", "Item 3 selected");
//                break;
//            case R.id.item4:
//                Log.e("TAG", "Item 4 selected");
//                break;
//        }
//    }

    public void init() {

        mBSList = new ArrayList<>();
        if (Paper.book("syncBms").read("data") == null) {
            Snackbar.make(getWindow().getDecorView().getRootView(), "데이터가 없어요", Snackbar.LENGTH_SHORT).show();
        } else {
            // TODO: 2018-02-27 동기화된 데이터를 가져옴.
            mBSList = Paper.book("syncBms").read("data");
        }

        int newIndex = mBSList.size(); //받아온 최신 데이터 사이즈
        Log.e(TAG, "init: newIndex -  " + newIndex);
        int oldIndex = 0;
        // TODO: 2018-02-27 기존의 인덱스와 최신 인덱스 비교 저장이 가능해야함.
        if (Paper.book("syncBms").read("index") == null) {
            Paper.book("syncBms").write("index", newIndex);
        } else {
            oldIndex = Paper.book("syncBms").read("index"); // 기존의 데이터 사이즈
            Log.e(TAG, "init: oldIndex -  " + oldIndex);
        }

        int subIndex = newIndex - oldIndex;
        Log.e(TAG, "init: subIndex -  " + subIndex);
        if (subIndex == 0) {
            // TODO: 2018-02-27 같은 데이터 양이라면
            Log.e(TAG, "init: 같은 데이터 양이라면");
            //추가할 데이터가 없어요
        } else if (subIndex > 0) {
            // TODO: 2018-02-27 새로운 데이터가 더 많다면
            Log.e(TAG, "새로운 데이터가 더 많다면");
            //List<BloodSugar> subList =  mBSList.subList(oldIndex, newIndex);
            ArrayList<BloodSugar> subList = new ArrayList<>(mBSList.subList(oldIndex, newIndex));
            subList.add(mBSList.get(mBSList.size() - 1));
            for (int i = 0; i < subList.size(); i++) {
                Log.e(TAG, "subList - " + subList.get(i).getBsValue());
            }

        } else if (subIndex < 0) {
            // TODO: 2018-02-27 기존 데이터가 더 많다면 ( 경우가 없을 듯)
        }

        String[] date = new String[mBSList.size()];
        String[] time = new String[mBSList.size()];

        for (int i = 0; i < mBSList.size(); i++) {
            date[i] = mBSList.get(i).getBsTime().split(",")[0];
            time[i] = mBSList.get(i).getBsTime().split(",")[1];
            Log.e(TAG, "init: mBSList - " + mBSList.get(i).getBsTime());
        }

        for (int i = 0; i < date.length; i++) {
            Log.e(TAG, "init: date - " + date[i] + ", " + time[i]);
        }

        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new BSMSyncAdapter(this, mBSList);
        recyclerView.setAdapter(adapter);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    //tapBarMenu.setVisibility(View.VISIBLE);
                    fab.show();
                }
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0 || dy < 0 && fab.isShown()) {
                    fab.hide();
                    //tapBarMenu.setVisibility(View.GONE);
                }
            }
        });
    }

    /**
     * 리사이클러 Bottom Sheet 생성
     * 공유하기
     * 저장하기
     * 등 그래프를 보고 해야할 일을 처리해야함.
     *
     * @author : JAICHANGPARK(DREAMWALKER)
     */
    private void setupRecyclerView() {

        ArrayList<MenuEntity> list = new ArrayList<>();
        //添加假数据
        MenuEntity menuEntity1 = new MenuEntity();
        menuEntity1.iconId = R.drawable.ic_save_black_24dp;
        menuEntity1.title = "SAVE";
        MenuEntity menuEntity2 = new MenuEntity();
        menuEntity2.iconId = R.drawable.ic_menu_share;
        menuEntity2.title = "SHARE";

        list.add(menuEntity1);
        list.add(menuEntity2);

        // SweetSheet 控件,根据 rl 确认位置
        mSweetSheet = new SweetSheet(coordinator);
        //设置数据源 (数据源支持设置 list 数组,也支持从菜单中获取)
        mSweetSheet.setMenuList(list);
        //根据设置不同的 Delegate 来显示不同的风格.
        mSweetSheet.setDelegate(new RecyclerViewDelegate(true));
        //根据设置不同Effect 来显示背景效果BlurEffect:模糊效果.DimEffect 变暗效果
        mSweetSheet.setBackgroundEffect(new BlurEffect(14));
        //设置点击事件
        mSweetSheet.setOnMenuItemClickListener((position, menuEntity11) ->
        {
            //即时改变当前项的颜色
//                list.get(position).titleColor = 0xff5823ff;
//                ((RecyclerViewDelegate) mSweetSheet.getDelegate()).notifyDataSetChanged();
            //根据返回值, true 会关闭 SweetSheet ,false 则不会.
            switch (position) {
                case 0:
                    break;
                case 1:
            }
            //Toast.makeText(GraphAnalysisActivity.this, menuEntity1.title + "  " + position, Toast.LENGTH_SHORT).show();
            return false;
        });
    }

    @Override
    public void onBackPressed() {
        if (mSweetSheet.isShow()) {
            mSweetSheet.dismiss();
        } else {
            super.onBackPressed();
        }
    }

    // TODO: 2018-02-27 저장했을 경우와 저장 안했을 경우를 생각해야함
}

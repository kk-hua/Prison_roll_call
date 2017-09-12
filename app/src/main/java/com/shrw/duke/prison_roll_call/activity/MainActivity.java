package com.shrw.duke.prison_roll_call.activity;

import android.app.Application;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.shrw.duke.portlibrary.common.Constant;
import com.shrw.duke.prison_roll_call.R;
import com.shrw.duke.prison_roll_call.RollCallApplication;
import com.shrw.duke.prison_roll_call.entity.PeopleRoll;
import com.shrw.duke.prison_roll_call.fragment.HasToFragment;
import com.shrw.duke.prison_roll_call.fragment.UncalledFragment;
import com.shrw.duke.prison_roll_call.utils.FileUtil;
import com.shrw.duke.prison_roll_call.utils.PreferencesUtils;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

import static com.shrw.duke.prison_roll_call.common.Constant.SDCARD_RESULT_CODE;
import static com.shrw.duke.prison_roll_call.common.Constant.SDCard_REQUEST_CODE;

public class MainActivity extends AppCompatActivity implements CompoundButton.OnClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    @BindView(R.id.tv_is_null)
    TextView mTv_null;
    @BindView(R.id.linear_content)
    LinearLayout mLinear;
    //tab
    @BindView(R.id.tab_uncalled)
    RadioButton mRbUncalled;
    @BindView(R.id.tab_has_to)
    RadioButton mRbHasTo;

    private int mCurrentPage;    //当前页面
    private String mFilePath;   //文件路径

    private List<PeopleRoll> mPeoples;
    private List<String> mRfidNumberList;
    private List<String> mNameList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        init();


    }


    @Override
    protected void onResume() {
        super.onResume();
        selectHomePage();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.main_search:  //扫描

                break;
            case R.id.main_import:  //导入
                startActivityForResult(new Intent(MainActivity.this, SDCardActivity.class), SDCard_REQUEST_CODE);
                break;
            case R.id.main_export:  //导出
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bundle bundle;
        switch (resultCode) {
            case SDCARD_RESULT_CODE:
                //选择名单
                if (data != null) {
                    bundle = data.getExtras();
                    String file_path = bundle.getString(SDCardActivity.FILE_PATH);
                    if (!TextUtils.isEmpty(file_path)) {
                        Log.d(TAG, file_path);
                        mFilePath = file_path;
                        String fileContent = FileUtil.readFileContent(file_path, "utf-8", "\n", 1024);
                        PreferencesUtils.putString(this, Constant.FILE_PATH, file_path);
                        if (!TextUtils.isEmpty(fileContent))
                            splitFileContent(fileContent);
                        Log.d(TAG, fileContent);
                    }
                }
                break;
            default:
                break;
        }
    }


    /**
     * 初始化
     */
    private void init() {
        //文件路径
        mFilePath = RollCallApplication.mFilePath;

        //设置监听
        mRbUncalled.setOnClickListener(this);
        mRbHasTo.setOnClickListener(this);

    }

    /**
     * 判空
     */
    private void selectHomePage() {
        //判断是否有选择文件
        if (TextUtils.isEmpty(mFilePath)) {
            //没有选择文件
            mTv_null.setVisibility(View.VISIBLE);
            mLinear.setVisibility(View.GONE);
        } else {
            //选择了文件
            mTv_null.setVisibility(View.GONE);
            mLinear.setVisibility(View.VISIBLE);
            String fileContent = FileUtil.readFileContent(mFilePath, "utf-8", "\n", 1024);
            if (!TextUtils.isEmpty(fileContent)){
                splitFileContent(fileContent);
            }
            //第一次自动选择未到页面
            if (mNameList!=null&&mNameList.size()>0){
                getSupportFragmentManager().beginTransaction().add(R.id.fragment, UncalledFragment.newInstance(mNameList)).commit();
            }else {
                getSupportFragmentManager().beginTransaction().add(R.id.fragment, new UncalledFragment()).commit();
            }
            mCurrentPage = mRbUncalled.getId();
            mRbUncalled.setChecked(true);
        }
    }

    //修剪文件内容
    private List<String> splitFileContent(String fileContent) {
        mPeoples = new ArrayList<>();
        mRfidNumberList= new ArrayList<>();
        mNameList = new ArrayList<>();
        String[] files = fileContent.replace(" ", "\\t").split("\\n");
        int len = files.length;
        Log.w(TAG, Arrays.toString(files));
        for (int i = 1; i < len; i++) {
            //行
            Log.w(TAG + "rows:", files[i]);
            String[] rows = files[i].split("\\t");
            if (rows.length==6) {
                PeopleRoll peopleRoll = new PeopleRoll(rows[0], rows[1], rows[2], rows[3], rows[4], rows[5]);
                mPeoples.add(peopleRoll);
                mRfidNumberList.add(peopleRoll.getRfid());
                mNameList.add(peopleRoll.getName());
            }
            //字段
//            Log.w(TAG + "arg:", arg);

        }
        return null;
    }

    /**
     * Tab点击处理事件
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tab_has_to:
                if (mCurrentPage != v.getId()) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment, new HasToFragment()).commit();
                    mCurrentPage = v.getId();
                }
                break;

            case R.id.tab_uncalled:
                if (mCurrentPage != v.getId()) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment, UncalledFragment.newInstance(mNameList)).commit();
                    mCurrentPage = v.getId();
                }
                break;
        }
    }

    // TODO: 2017/9/11 页面布局
    // TODO: 2017/9/11 读取txt文件
    // TODO: 2017/9/11 显示列表名单
    // TODO: 2017/9/11 设置rssi读取范围
    // TODO: 2017/9/11 搜索
    // TODO: 2017/9/11 保存txt报告


}

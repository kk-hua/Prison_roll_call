package com.shrw.duke.prison_roll_call.activity;

import android.app.Application;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.shrw.duke.prison_roll_call.R;
import com.shrw.duke.prison_roll_call.RollCallApplication;
import com.shrw.duke.prison_roll_call.utils.FileUtil;

import org.w3c.dom.Text;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

import static com.shrw.duke.prison_roll_call.common.Constant.SDCARD_RESULT_CODE;
import static com.shrw.duke.prison_roll_call.common.Constant.SDCard_REQUEST_CODE;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @BindView(R.id.tv_is_null)
    TextView mTv_null;
    @BindView(R.id.recycle_list)
    RecyclerView mReyclerList;

    private String mFilePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        init();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.main_search:  //扫描

                break;
            case R.id.main_import:  //导入
                startActivityForResult(new Intent(MainActivity.this,SDCardActivity.class),SDCard_REQUEST_CODE);
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
        switch (resultCode){
            case SDCARD_RESULT_CODE:
                if (data!=null){
                    bundle = data.getExtras();
                    String file_path = bundle.getString(SDCardActivity.FILE_PATH);
                    if (!TextUtils.isEmpty(file_path)){
                        Log.d(TAG,file_path);
                        String fileContent = FileUtil.readFileContent(file_path,"utf-8","\n",1024);
                        Log.d(TAG,fileContent);

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

        //判断是否有选择文件
        mFilePath = RollCallApplication.mFilePath;
        if (TextUtils.isEmpty(mFilePath)){
            mReyclerList.setVisibility(View.VISIBLE);
            mTv_null.setVisibility(View.GONE);
        }else {
            mReyclerList.setVisibility(View.GONE);
            mTv_null.setVisibility(View.VISIBLE);
        }


    }

    // TODO: 2017/9/11 读取txt文件
    // TODO: 2017/9/11 显示列表名单
    // TODO: 2017/9/11 设置rssi读取范围
    // TODO: 2017/9/11 搜索
    // TODO: 2017/9/11 保存txt报告


}

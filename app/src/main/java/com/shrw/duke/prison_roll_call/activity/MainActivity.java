package com.shrw.duke.prison_roll_call.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.shrw.duke.portlibrary.common.Constant;
import com.shrw.duke.portlibrary.common.ReadCallback;
import com.shrw.duke.portlibrary.common.bean.type18.TagBase18;
import com.shrw.duke.portlibrary.common.interfaces.IGenerator;
import com.shrw.duke.portlibrary.common.interfaces.ITag;
import com.shrw.duke.portlibrary.common.utils.Parse;
import com.shrw.duke.portlibrary.common.utils.TagGenerator;
import com.shrw.duke.portlibrary.control.port.PortManager;
import com.shrw.duke.prison_roll_call.R;
import com.shrw.duke.prison_roll_call.RollCallApplication;
import com.shrw.duke.prison_roll_call.common.MessageType;
import com.shrw.duke.prison_roll_call.entity.Note;
import com.shrw.duke.prison_roll_call.entity.PeopleRoll;
import com.shrw.duke.prison_roll_call.fragment.HasToFragment;
import com.shrw.duke.prison_roll_call.fragment.UncalledFragment;
import com.shrw.duke.prison_roll_call.listener.OnActivityOrFragmentArgListener;
import com.shrw.duke.prison_roll_call.utils.FileUtil;
import com.shrw.duke.prison_roll_call.utils.ListUtils;
import com.shrw.duke.prison_roll_call.utils.PreferencesUtils;
import com.shrw.duke.prison_roll_call.utils.ToastUtil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.shrw.duke.prison_roll_call.common.Constant.SDCARD_RESULT_CODE;
import static com.shrw.duke.prison_roll_call.common.Constant.SDCard_REQUEST_CODE;

public class MainActivity extends AppCompatActivity implements CompoundButton.OnClickListener, ReadCallback, OnActivityOrFragmentArgListener<Object>, DialogInterface.OnDismissListener {

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

    EditText mEditText;
    Button mBtnRead;
    Button mBtnSend;

    private int mCurrentPage;    //当前页面
    private String mFilePath;   //文件路径

    private List<PeopleRoll> mPeoples;
    private List<String> mRfidNumberList;
    private List<String> mNameList;
    private Map<String, PeopleRoll> mPeopleRollMap;
    private Note mNote;//考勤报告
    private List<PeopleRoll> mUncalledHint;//未到人员备注
    private int mHasTo = 0;
    private int mCount = 0;


    private PortManager mPortManager;//串口管理工具
    private IGenerator mGenerator;
    private ITag mTag;


    private String cmd = "";
    private String mSendCMD = "";

    private OnActivityOrFragmentArgListener mUncalledFragmentArgListener = null;
    private OnActivityOrFragmentArgListener mHasToFragmentArgListener = null;
    private UncalledFragment mUncalledFragment;
    private HasToFragment mHasToFragment;
    private AlertDialog builder = null;

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

        //选择首页
        selectHomePage();

        //串口上电
        mPortManager.powerUp();
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
                if (item.getTitle().equals(getString(R.string.main_open_search)) && !mPortManager.isIsSearch()) {
                    if (open()) {
                        item.setTitle(R.string.main_close_search);
                        item.setIcon(R.drawable.ic_hdr_strong_white_24dp);
                        cmd = "41";
                    }


                } else {
                    close();
                    item.setTitle(R.string.main_open_search);
                    item.setIcon(R.drawable.ic_hdr_weak_white_24dp);
                }
                break;

            case R.id.main_setting:  //设置
                if (mPortManager.isIsSearch()) {
                    ToastUtil.showToast(getString(R.string.searching));
                    return super.onOptionsItemSelected(item);
                }
                builder = new AlertDialog.Builder(MainActivity.this).create();
                builder.show();
                builder.getWindow().setContentView(R.layout.dialog_setting_rssi);//设置弹出框加载的布局

                mEditText = (EditText) builder.findViewById(R.id.textInputLayout);
                mBtnRead = (Button) builder.findViewById(R.id.btn_read);
                mBtnSend = (Button) builder.findViewById(R.id.btn_send);
                mBtnRead.setOnClickListener(this);
                mBtnSend.setOnClickListener(this);
                builder.getWindow();
                builder.setOnDismissListener(this);

                open();
                cmd = "44";
                break;

            case R.id.main_import:  //导入
                if (mPortManager.isIsSearch()) {
                    ToastUtil.showToast(getString(R.string.searching));
                    return super.onOptionsItemSelected(item);
                }
                startActivityForResult(new Intent(MainActivity.this, SDCardActivity.class), SDCard_REQUEST_CODE);
                break;
            case R.id.main_export:  //导出
                if (mPortManager.isIsSearch()) {
                    ToastUtil.showToast(getString(R.string.searching));
                    return super.onOptionsItemSelected(item);

                }
                if (mUncalledHint == null && mUncalledFragment != null) {
                    mUncalledHint = mUncalledFragment.getUnCalledPeopleRool();
                }

                final View view = getLayoutInflater().inflate(R.layout.dialog_save_file, null);
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setView(view);
                builder.setTitle(R.string.save_file);
                builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d("bbbbbbbbfe",new File(com.shrw.duke.prison_roll_call.common.Constant.saveNoteFilePath).exists()+"");
//                        if (){
//
//                        }
                        EditText editRename = (EditText) view.findViewById(R.id.dialog_rename_file);
                        TextView editFilePath = (TextView) view.findViewById(R.id.dialog_file_path);
                        String fileName = editRename.getText().toString();
                        editFilePath.setText(com.shrw.duke.prison_roll_call.common.Constant.saveNoteFilePath);
                        final StringBuilder filePath = new StringBuilder(com.shrw.duke.prison_roll_call.common.Constant.saveNoteFilePath);
                        filePath.append(fileName);
                        filePath.append(".txt");
                        final StringBuilder content = new StringBuilder();
                        content.append("应到" + mCount + "人\n");
                        content.append("实到" + mHasTo + "人\n");
                        content.append("未到" + mUncalledHint.size() + "人\n");
                        content.append("备注：");
                        for (PeopleRoll p : mUncalledHint) {
                            content.append("\n\t姓名：" + p.getName() + "\trfid：" + p.getRfid() + "\t类别："
                                    + p.getType() + "\n未到原因：" + p.getNote());

                        }
                        final String path = filePath.toString();
                        final String text = content.toString();
                        if (FileUtil.isFileExists(filePath.toString())){
                            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                            builder.setTitle("提示");
                            builder.setMessage(R.string.isFileExists);
                            builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                            builder.setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if (FileUtil.writeFileFromString(path, text)){
                                        ToastUtil.showToast(getString(R.string.save_success));
                                    }else {
                                        ToastUtil.showToast(getString(R.string.save_fail));
                                    }
                                }
                            });
                            builder.show();
                            return;
                        }

                        Log.e("mUncalledHint:", "path:" + filePath + "\t" + content.toString());
                        if (FileUtil.writeFileFromString(filePath.toString(), content.toString())){
                            ToastUtil.showToast(getString(R.string.save_success));
                        }else {
                            ToastUtil.showToast(getString(R.string.save_fail));
                        }

                    }
                });
                builder.show();

//                ToastUtil.showToast("总：" + mCount + "\t已：" + mHasTo + "\t未：" + mUncalledHint.size());
//                Log.e("mUncalledHint:", mUncalledHint.toString());
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUncalledFragmentArgListener = null;
        mHasToFragmentArgListener = null;
        if (mPortManager.isIsSearch())
            close();
    }

    /**
     * 打开串口
     */
    private boolean open() {
        if (mPortManager != null) {
            mPortManager.powerUp();
            try {
                mPortManager.openSerialPort();
                mPortManager.startRead(this);
                return true;
            } catch (IOException e) {
                ToastUtil.showToast(getString(R.string.open_port_fail));
                return false;
            }
        }
        return false;
    }

    /**
     * 关闭串口
     */
    private void close() {
        mPortManager.stopRead();
        mPortManager.closeSerialPort();
        mPortManager.powerDown();
    }

    /**
     * 初始化
     */
    private void init() {
        RollCallApplication.getContext().addActivity(this);

        //文件路径
        mFilePath = RollCallApplication.mFilePath;
        //构建已到人员页面
        mHasToFragment = new HasToFragment();
        mHasToFragmentArgListener = mHasToFragment;


        //设置监听
        mRbUncalled.setOnClickListener(this);
        mRbHasTo.setOnClickListener(this);
        //初始化串口工具
        mPortManager = PortManager.getInstance();

        mGenerator = TagGenerator.getInstance();
        mTag = mGenerator.createTagParse(18);

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

            if (mPeoples == null || mPeoples.size() == 0) {
                String fileContent = FileUtil.readFileContent(mFilePath, "utf-8", "\n", 1024);
                if (!TextUtils.isEmpty(fileContent)) {
                    splitFileContent(fileContent);
                }
            }
            //第一次自动选择未到页面
            if (mNameList != null && mNameList.size() > 0) {
                mUncalledFragment = UncalledFragment.newInstance(mPeoples);
                mUncalledFragmentArgListener = mUncalledFragment;
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment, mUncalledFragment).commit();
            } else {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment, new UncalledFragment()).commit();
            }
            mCurrentPage = mRbUncalled.getId();
            mRbUncalled.setChecked(true);
        }
    }

    //修剪文件内容
    private List<String> splitFileContent(String fileContent) {
        HasToFragment.mPeopleRollList.clear();
        mPeoples = new ArrayList<>();
        mRfidNumberList = new ArrayList<>();
        mNameList = new ArrayList<>();
        mPeopleRollMap = new HashMap<>();

        mHasTo = 0;

        String[] files = fileContent.replace(" ", "\\t").split("\\n");
        int len = files.length;
        Log.w(TAG, Arrays.toString(files));
        for (int i = 1; i < len; i++) {
            //行
            Log.w(TAG + "rows:", files[i]);
            String[] rows = files[i].split("\\t");
            if (rows.length == 6 && "0".equals(rows[4])) {
                PeopleRoll peopleRoll = new PeopleRoll(rows[0], rows[1], rows[2], rows[3], rows[4], rows[5]);
                mPeopleRollMap.put(peopleRoll.getRfid(), peopleRoll);
                mPeoples.add(peopleRoll);
                mRfidNumberList.add(peopleRoll.getRfid());
                mNameList.add(peopleRoll.getName());
            }
            //字段
//            Log.w(TAG + "arg:", arg);

        }
        mCount = mPeoples.size();
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
                //已到人员页面
                if (mCurrentPage != v.getId()) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment, mHasToFragment).commit();
                    mCurrentPage = v.getId();
                }
                break;

            case R.id.tab_uncalled:
                //未到人员页面
                if (mCurrentPage != v.getId()) {
                    mUncalledFragment = UncalledFragment.newInstance(mPeoples);
                    mUncalledFragmentArgListener = mUncalledFragment;
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment, mUncalledFragment).commit();
                    mCurrentPage = v.getId();
                }
                break;

            case R.id.btn_read:
                if (mPortManager.isIsSearch()) {
                    mPortManager.write(Constant.READ_ARG_CMD);
                }
                break;
            case R.id.btn_send:
                if (mPortManager.isIsSearch()) {
                    String rssi = mEditText.getText().toString();
                    if (TextUtils.isEmpty(rssi))
                        ToastUtil.showToast(getString(R.string.not_is_null));
                    if (TextUtils.isEmpty(mSendCMD)) {
                        mSendCMD = com.shrw.duke.prison_roll_call.common.Constant.SEND_CMD;
                    }
                    StringBuilder sbRssi = new StringBuilder(mSendCMD);
                    sbRssi.replace(16, 18, "43");
                    sbRssi.replace(mSendCMD.length() - 4, mSendCMD.length() - 2, rssi);
                    cmd = "43";
                    mPortManager.write(sbRssi.toString());
                }
                break;
        }
    }

    /**
     * 接收串口数据
     *
     * @param buffer
     * @param size
     */
    @Override
    public void exec(byte[] buffer, int size) {
        final String data = Parse.toHexString(buffer, size);
        if (data.length() < 18)
            return;
        Log.d(TAG + "\t数据1", data);

        switch (cmd) {
            case "41":
                //读取标签
                TagBase18 people = (TagBase18) mTag.parseTag(data);
                if (people != null) {
                    List<TagBase18.Tag18> tags = people.getTags();
                    int len = tags.size();
                    for (int i = 0; i < len; i++) {
                        final TagBase18.Tag18 tag = tags.get(i);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (mUncalledFragmentArgListener != null && tag != null)
                                    mUncalledFragmentArgListener.onData(MainActivity.this, tag,
                                            com.shrw.duke.prison_roll_call.common.Constant.PORT_DATA_TYPE);
                            }
                        });

//                tag.getTagId();
                    }
                    Log.d(TAG + "\t数据2", people.toString());
                }
                break;
            case "44":
                //读取参数
                String cmd = data.substring(16, 18);
                if (this.cmd.equals(cmd)) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (mEditText != null) {
                                mEditText.setText(data.substring(data.length() - 4, data.length() - 2));
                            }
                        }
                    });
                    Log.d("读取数据：", data);
                }
                break;
            case "43":
                //更改参数
                String cmd1 = data.substring(16, 18);
                if (cmd1.equals(this.cmd)) {
                    final String confirmBit = data.substring(data.length() - 6, data.length() - 2).toUpperCase();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if ("4F4B".equals(confirmBit)) {
                                ToastUtil.showToast(getString(R.string.write_suecces));
                            } else {
                                ToastUtil.showToast(getString(R.string.write_fail));
                            }
                        }
                    });
//                    Log.d("读取数据：",data);
                }
                break;
        }
    }

    /**
     * 已到人员
     *
     * @param context
     * @param o       data
     */
    @Override
    public void onData(Context context, Object o, int type) {
        switch (type) {
            case com.shrw.duke.prison_roll_call.common.Constant.HAS_TO_TYPE:
                //UncalledFragment-->this
                if (mHasToFragmentArgListener != null) {
                    int index = ListUtils.indexOf(mPeoples, (String) o);
                    if (index >= 0) {
                        mHasTo++;
                        PeopleRoll people = mPeoples.get(index);
                        mHasToFragmentArgListener.onData(this, people, com.shrw.duke.prison_roll_call.common.Constant.HAS_TO_TYPE);
//                        Log.e(TAG + "\t", people.toString());
                    }
                    Log.e(TAG + "\t", (String) o);
                }
                break;

            case com.shrw.duke.prison_roll_call.common.Constant.NOTE_TYPE:
                //UncalledFragment-->this
                if (mNote == null || mUncalledHint == null && mUncalledHint.size() == 0) {
                    //考勤报告
                    mNote = new Note();
                    mUncalledHint = new ArrayList<>();//未到人员备注
                }
                if (mHasToFragmentArgListener != null) {
                    mUncalledHint = (List<PeopleRoll>) o;
                }
                break;

            case com.shrw.duke.prison_roll_call.common.Constant.UNCALLED_TYPE:
                //HasToFragment-->this
                break;

            default:
                break;
        }


    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        close();
        cmd = "41";
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        Log.e("bvnv","onBackPressed()");
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("退出");
        builder.setMessage("是否退出应用？");
        builder.setCancelable(false);
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                RollCallApplication.getContext().exit();
            }
        });
        builder.show();
    }


    // TODO: 2017/9/11 页面布局
    // TODO: 2017/9/11 读取txt文件
    // TODO: 2017/9/11 显示列表名单
    // TODO: 2017/9/11 设置rssi读取范围
    // TODO: 2017/9/11 搜索
    // TODO: 2017/9/11 保存txt报告


}

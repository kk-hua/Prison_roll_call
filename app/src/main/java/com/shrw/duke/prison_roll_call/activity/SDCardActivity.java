package com.shrw.duke.prison_roll_call.activity;

import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.shrw.duke.prison_roll_call.R;
import com.shrw.duke.prison_roll_call.adapter.SDCardAdapter;
import com.shrw.duke.prison_roll_call.common.Constant;
import com.shrw.duke.prison_roll_call.entity.FileInfo;
import com.shrw.duke.prison_roll_call.listener.OnRecyclerViewItemClickListener;
import com.shrw.duke.prison_roll_call.utils.FileUtil;

import org.w3c.dom.Text;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

import static com.shrw.duke.prison_roll_call.utils.FileUtil.listFiles;

public class SDCardActivity extends AppCompatActivity implements OnRecyclerViewItemClickListener {
    private static final String TAG = SDCardActivity.class.getSimpleName();
    public static final String FILE_PATH = "file_path";


    @BindView(R.id.sdcard_recycler_file_list)
    RecyclerView mRecycleFile;

    private List<FileInfo> files = new ArrayList<>();

    private SDCardAdapter mSDCardAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sdcard);
        ButterKnife.bind(this);

        //列表初始化
        mSDCardAdapter = new SDCardAdapter(this, files);
        mSDCardAdapter.setOnItemClickListener(this);
        mRecycleFile.setLayoutManager(new LinearLayoutManager(this));
        mRecycleFile.setAdapter(mSDCardAdapter);


    }

    @Override
    protected void onResume() {
        super.onResume();
        readDOCFile();
    }

    private void readDOCFile() {
        List<File> m = new ArrayList<>();
        m.add(new File(Environment.getExternalStorageDirectory().toString()));
//        m.add(new File(Environment.getExternalStorageDirectory() + "/dzsh/"));
        Observable.from(m)
                .flatMap(new Func1<File, Observable<File>>() {
                    @Override
                    public Observable<File> call(File file) {
                        Log.d(TAG, "flatMap");
                        return listFiles(file);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        new Subscriber<File>() {
                            @Override
                            public void onCompleted() {
//                                progressDialog.dismiss();
                                if (files.size() > 0) {
//                                    adapter.addData(files);
//                                    rlv_sd_card.setAdapter(adapter);
//                                    adapter.notifyDataSetChanged();
                                } else {
                                    Toast.makeText(getApplicationContext(), "sorry,没有读取到文件!", Toast.LENGTH_LONG).show();
                                }
                            }

                            @Override
                            public void onError(Throwable e) {
//                                progressDialog.dismiss();
                            }

                            @Override
                            public void onNext(File file) {
                                FileInfo fileInfo = FileUtil.getFileInfoFromFile(file);
                                Log.e("文件路径", "文件路径：：：" + file.getAbsolutePath());
                                Log.e("文件名", "文件名：：：" + file.getName());
                                files.add(fileInfo);
                                mSDCardAdapter.notifyDataSetChanged();

                            }
                        }
                );
    }

    /**
     * 选择文件
     *
     * @param view
     * @param position
     * @param data
     */
    @Override
    public void onItemClick(View view, int position, FileInfo data) {
        Log.d(TAG, position + "");
        if (!TextUtils.isEmpty(data.getFilePath())){
            Intent intent = new Intent(SDCardActivity.this,MainActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString(FILE_PATH,data.getFilePath());
            intent.putExtras(bundle);
            setResult(Constant.SDCARD_RESULT_CODE,intent);
            finish();
        }

    }
}
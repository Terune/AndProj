package com.fd.sonbongjin.filedownloader;

/**
 * Created by sonbongjin on 15. 9. 15..
 */

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.ExecutionException;

public class FileActivity extends Activity implements View.OnClickListener {

    private Button mBtnFileDown;
    private LinearLayout mLinearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file);

        // ��ư ��ü ����
        mBtnFileDown = (Button)findViewById(R.id.button1);
        mLinearLayout = (LinearLayout)findViewById(R.id.main_linear);

        // Ŭ���̺�Ʈ ����
        mBtnFileDown.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button1:
                try {
                    startDownload();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;

            default:
                break;
        }
    }
    private void startDownload() throws IOException {
        String url = "http://nmsl.kaist.ac.kr/2015fall/cs492c/hw1/input.txt";
        String text = null;
        int filenumber=0;

        try {
            text = new Downsync(this).execute(url, "list.txt", "1").get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        Log.d("text value", text);
        FileInputStream fis = new FileInputStream(text); // loadPath는 txt파일의 경로
        BufferedReader bufferReader = new BufferedReader(new InputStreamReader(fis));

        String str, str1="";
        String map[];

        while( (str = bufferReader.readLine()) != null )	// str에 txt파일의 한 라인을 읽어온다
        {
            str1 += str+"\n";// 읽어온 라인을 str1에 추가한다

            map = str.split("\\t");
            LinearLayout File_layout= new LinearLayout(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            File_layout.setLayoutParams(params);
            File_layout.setBackgroundColor(Color.WHITE);

            ProgressBar down_progress = new ProgressBar(this);

            mLinearLayout.addView(File_layout);
            filenumber++;
            new Downsync(this).execute(map[1], map[0], "data");
        }

    }




}

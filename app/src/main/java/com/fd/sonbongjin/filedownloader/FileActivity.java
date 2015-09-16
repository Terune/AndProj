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
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.ExecutionException;

public class FileActivity extends Activity implements View.OnClickListener {

    private Button mBtnFileDown;
    public LinearLayout mLinearLayout;


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

        int filenumber=0; //현재 파일 숫자
        int whole_size=0; //전체 파일 갯수

        try {
            text = new Downsync(this).execute(url, "list.txt", "text").get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        //Log.d("text value", text);
        FileInputStream fis = new FileInputStream(text); // loadPath는 txt파일의 경로
        BufferedReader bufferReader = new BufferedReader(new InputStreamReader(fis));

        String str, str1="";
        String map[];


        while( (str = bufferReader.readLine()) != null )	// str에 txt파일의 한 라인을 읽어온다
        {
            str1 += str+"\n";
            whole_size++;
        }
        fis.close();

        String whole_sentence[];
        final String file_names[] = new String[whole_size];
        final String file_urls[] = new String[whole_size];


        whole_sentence=str1.split("\\n");

        while( filenumber<whole_size )	// str에 txt파일의 한 라인을 읽어온다
        {
            //str1 += str+"\n";// 읽어온 라인을 str1에 추가한다

            map = whole_sentence[filenumber].split("\\t");

            file_names[filenumber]=map[0];
            file_urls[filenumber]=map[1];

            LinearLayout File_layout= new LinearLayout(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.topMargin=10;
            File_layout.setOrientation(LinearLayout.VERTICAL);

            File_layout.setLayoutParams(params);
            File_layout.setBackgroundColor(Color.YELLOW);
            File_layout.setAlpha((float) 0.8);

            TextView down_filename = new TextView(this);
            down_filename.setText(file_names[filenumber]);

            File_layout.addView(down_filename);

            TextView percent = new TextView(this);
            ViewGroup.LayoutParams percent_params=
                    new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT);
            percent.setLayoutParams(percent_params);
            percent.setId(filenumber+2000);
            percent.setText("0%");
            percent.setGravity(Gravity.RIGHT);

            final ProgressBar down_progress = new ProgressBar(this,null,android.R.attr.progressBarStyleHorizontal);
            ViewGroup.LayoutParams edittext_layout_params =
                    new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT);
            down_progress.setLayoutParams(edittext_layout_params);
            down_progress.setId(filenumber);
           // down_progress.setIndeterminate(true);
            //down_progress.setMax(100);

            File_layout.addView(down_progress);

            Button down_button = new Button(this);
            ViewGroup.LayoutParams button_layout_params =
                    new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT);
            down_button.setBackgroundColor(Color.WHITE);
            down_button.setAlpha(1);
            down_button.setLayoutParams(button_layout_params);
            down_button.setId(filenumber + 1000);


            down_button.setText("Start Download");
            File_layout.addView(down_button);


            mLinearLayout.addView(File_layout);



            final int finalFilenumber = filenumber;
            down_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Downsync downtask= new Downsync(FileActivity.this);
                   // ProgressBar temp = (ProgressBar)findViewById(finalFilenumber+1000);
                    downtask.setProgressBar(down_progress);
                    downtask.execute(file_urls[finalFilenumber], file_urls[finalFilenumber], String.valueOf(finalFilenumber));
                    //new Downsync(FileActivity.this).execute(file_urls[finalFilenumber], file_urls[finalFilenumber], String.valueOf(finalFilenumber));

                }
            });
            filenumber++;
        }

    }




}

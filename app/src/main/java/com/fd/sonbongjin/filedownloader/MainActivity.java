package com.fd.sonbongjin.filedownloader;

import android.app.Activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;


public class MainActivity extends Activity implements View.OnClickListener {

    /** ���̾�α� ��� ���α׷��ú�ٸ� �����ϱ� ���� ���� */
	/*private static final int PROGRESSBAR_DLG_LARGE = 1;
	private static final int PROGRESSBAR_DLG_MID = 2;
	private static final int PROGRESSBAR_DLG_SMALL = 3;
	private static final int PROGRESSBAR_DLG_STATIC = 4;
	private static final int PROGRESSBAR_DLG_SPINNER = 5;*/

    private Button mBtnProgressDlg;
    private Button mBtnSpinner;
    private Button mBtnLarge;
    private Button mBtnMid;
    private Button mBtnSmall;
    private Button mBtnStick;

    private AsyncTask<Integer, String, Integer> mProgressDlg;
    private ProgressBar mProgressLarge;
    private ProgressBar mProgressMid;
    private ProgressBar mProgressSmall;
    private ProgressBar mProgressStick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // ��ư ��ü ����
        mBtnProgressDlg = (Button) findViewById(R.id.btnProgressDialog);
        mBtnLarge = (Button)findViewById(R.id.btnProgressLarge);
        mBtnMid = (Button)findViewById(R.id.btnProgressMid);
        mBtnSmall = (Button)findViewById(R.id.btnProgressSmall);
        mBtnStick = (Button)findViewById(R.id.btnProgressStick);

        // ���α׷��ú�� ����
        mProgressLarge = (ProgressBar) findViewById(R.id.progressBar1);
        mProgressMid = (ProgressBar) findViewById(R.id.progressBar2);
        mProgressSmall = (ProgressBar) findViewById(R.id.progressBar3);
        mProgressStick = (ProgressBar) findViewById(R.id.progressBar4);

        // Ŭ���̺�Ʈ ����
        mBtnProgressDlg.setOnClickListener(this);
        mBtnLarge.setOnClickListener(this); // ����� ū��
        mBtnMid.setOnClickListener(this); // ����� �߰�
        mBtnSmall.setOnClickListener(this);// ����� ������
        mBtnStick.setOnClickListener(this); // ������ �����

        // ����ٸ� ����
        mProgressLarge.setVisibility(ProgressBar.GONE);
        mProgressMid.setVisibility(ProgressBar.GONE); // ����� �߰�
        mProgressSmall.setVisibility(ProgressBar.GONE);// ����� ������
        mProgressStick.setVisibility(ProgressBar.GONE); // ������ �����

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnProgressDialog:
                mProgressDlg = new Progress(MainActivity.this).execute(100);
                break;

            case R.id.btnProgressLarge:
                mProgressLarge.setVisibility(ProgressBar.VISIBLE);
                mProgressLarge.setIndeterminate(true);
                mProgressLarge.setMax(100);

                break;

            case R.id.btnProgressMid:
                mProgressMid.setVisibility(ProgressBar.VISIBLE);
                mProgressMid.setIndeterminate(true);
                mProgressMid.setMax(100);
                break;

            case R.id.btnProgressSmall:
                mProgressSmall.setVisibility(ProgressBar.VISIBLE);
                mProgressSmall.setIndeterminate(true);
                mProgressSmall.setMax(100);
                break;

            case R.id.btnProgressStick:
                mProgressStick.setVisibility(ProgressBar.VISIBLE);
                mProgressStick.setIndeterminate(true);
                mProgressStick.setMax(100);
                break;

            default:
                break;
        }
    }
}

package com.fd.sonbongjin.filedownloader;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by sonbongjin on 15. 9. 15..
 */
public class Downsync extends AsyncTask<String, String, String> {

    private ProgressDialog mDlg;
    private Context mContext;
    private ProgressBar update_progress;

    public Downsync(Context context) {
        mContext = context;
    }




    @Override
    protected void onPreExecute() {
        mDlg = new ProgressDialog(mContext);
        mDlg.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        mDlg.setMessage("Start");
        mDlg.show();
        //update_progress.findViewById()

        super.onPreExecute();
    }

    private File getStorageDir(String Name) {
        // Get the directory for the user's public pictures directory.
        File file = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), Name);
        if (!file.mkdirs()) {
            file.mkdir();
            //Log.e("Error", "Directory not created");
        }
        return file;
    }

    @Override
    public String doInBackground(String... params) {

        int count = 0;
        File file = null;

        try {


                Thread.sleep(100);

                URL url = new URL(params[0].toString());
                URLConnection conexion = url.openConnection();
                conexion.connect();

                int lenghtOfFile = conexion.getContentLength();
                Log.d("ANDRO_ASYNC", "Lenght of file: " + lenghtOfFile);

            if(!params[2].equals("text")) {
                InputStream input = new BufferedInputStream(url.openStream());
                file=new File(getStorageDir("Down") + params[1]);
                OutputStream output = new FileOutputStream(file);

                byte data[] = new byte[1024];

                long total = 0;

                while ((count = input.read(data)) != -1) {
                    total += count;
                    publishProgress("progress","" + (int) ((total * 100) / lenghtOfFile), params[2]);
                    output.write(data, 0, count);
                }

                output.flush();
                output.close();
                input.close();
            } else {
                InputStream input = new BufferedInputStream(url.openStream());
                file = getTempFile(mContext, url.toString());
                OutputStream output = new FileOutputStream(file);

                byte data[] = new byte[1024];

                while ((count = input.read(data)) != -1) {
                    output.write(data, 0, count);
                }

                output.flush();
                output.close();
                input.close();
            }

            // �۾��� ����Ǹ鼭 ȣ���ϸ� ȭ���� ���׷��̵带 ����ϰ� �ȴ�
           // publishProgress("progress", 1, "Task " + 1 + " number");

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // ������ ������ �����ϴ� ���� ������ ����� onProgressUpdate �� �Ķ���Ͱ� �ȴ�
        return file.toString();
    }

    private File getTempFile(Context context,String url){
        File file = null;
        try{
            String fileName = Uri.parse(url).getLastPathSegment();
            file = File.createTempFile( fileName, null, context.getCacheDir());
        }catch( IOException e){
            e.printStackTrace();
        }
        return file;
    }

    @Override
    protected void onProgressUpdate(String... progress) {
        if (progress[0].equals("progress")) {


            mDlg.setProgress(Integer.parseInt(progress[1]));
            mDlg.setMessage(progress[2]);
        } else if (progress[0].equals("max")) {
            mDlg.setMax(Integer.parseInt(progress[1]));
        }
    }

    @SuppressWarnings("deprecation")
    @Override
    protected void onPostExecute(String unused) {
        mDlg.dismiss();
        //Toast.makeText(mContext, Integer.toString(result) + " total sum",
        //Toast.LENGTH_SHORT).show();
    }
}

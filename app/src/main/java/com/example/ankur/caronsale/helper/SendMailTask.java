package com.example.ankur.caronsale.helper;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

public class SendMailTask extends AsyncTask {

    private Activity mainActivity;

    public SendMailTask(Activity activity) {
        mainActivity = activity;
    }

    @Override
    protected Object doInBackground(Object... args) {
        // TODO Auto-generated method stub
        try {


            Log.i("SendMailTask", "About to instantiate GMail...");

            //publishProgress("Processing input....");
            GMail androidEmail = new GMail(args[0].toString(),
                    args[1].toString(),  args[2].toString(), args[3].toString(),
                    args[4].toString());
            //	publishProgress("Preparing mail message....");
            androidEmail.createEmailMessage();
            //publishProgress("Sending email....");
            androidEmail.sendEmail();
            //publishProgress("Email Sent.");
            Log.i("SendMailTask", "Mail Sent.");
        } catch (Exception e) {
            //publishProgress(e.getMessage());

            //session.setDebug(true);
            Log.e("SendMailTask", e.getMessage(), e);
        }

        return null;
    }
}

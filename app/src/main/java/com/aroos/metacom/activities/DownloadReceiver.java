package com.aroos.metacom.activities;

import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;

/**
 * Created by murilo on 16/10/2016.
 */
public class DownloadReceiver extends ResultReceiver {

    public DownloadReceiver(Handler handler) {
        super(handler);
    }

    @Override
    protected void onReceiveResult(int resultCode, Bundle resultData) {
        super.onReceiveResult(resultCode, resultData);
        if (resultCode == DownloadService.UPDATE_PROGRESS) {
            int progress = resultData.getInt("progress");

            if (progress == 100) {
                // Download Complete
            }
        }
    }
}
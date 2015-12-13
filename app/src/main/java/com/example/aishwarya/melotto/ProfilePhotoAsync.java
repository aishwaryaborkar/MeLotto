package com.example.aishwarya.melotto;

/**
 * Created by Aishwarya on 12/12/2015.
 */

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import com.facebook.Profile;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

class ProfilePhotoAsync extends AsyncTask<String, String, String> {

    Profile profile;
    public Bitmap bitmap;

    public ProfilePhotoAsync(Profile profile) {
        this.profile = profile;
    }

    @Override
    protected String doInBackground(String... params) {
        // Fetching data from URI and storing in bitmap
        bitmap = DownloadImageBitmap(profile.getProfilePictureUri(200, 200).toString());
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        //mProfileImage.setImageBitmap(bitmap);
    }

    public static Bitmap DownloadImageBitmap(String url) {
        Bitmap bm = null;
        try {
            URL aURL = new URL(url);
            URLConnection conn = aURL.openConnection();
            conn.connect();
            InputStream is = conn.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(is);
            bm = BitmapFactory.decodeStream(bis);
            bis.close();
            is.close();
        } catch (IOException e) {
            Log.e("IMAGE", "Error getting bitmap", e);
        }
        return bm;
    }
}
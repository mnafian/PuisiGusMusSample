package net.mnafian.puisigusmus.Utilities;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.util.Log;

import java.io.File;

/**
 * Created by mnafian on 8/28/15.
 */
public class StaticClass {

    //link url
    public static final String URL_PUISI_LIST = "http://private-bb8ba-puisigusmus.apiary-mock.com/apigusmus";

    //property data
    public static final String GET_IMAGE_URL = "image_url";
    public static final String GET_PENGARANG = "pengarang";
    public static final String GET_JUDUL = "judul_puisi";
    public static final String GET_AUDIO = "link_audio";
    public static final String GET_POST = "data_puisi";
    public static final String GET_PUISI = "isi";


    //connection
    public static boolean checkConnection(Context context) {

        final ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        final NetworkInfo netInfo = mConnectivityManager.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        } else
            return false;
    }

    public static boolean createDirIfNotExists(String path) {
        boolean ret = true;

        File file = new File(Environment.getExternalStorageDirectory(), path);
        if (!file.exists()) {
            if (!file.mkdirs()) {
                Log.e("Image Folder :: ", "Problem creating Image folder");
                ret = false;
            }
        }
        return ret;
    }
}

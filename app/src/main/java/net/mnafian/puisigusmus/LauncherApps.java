package net.mnafian.puisigusmus;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

/**
 * Created by mnafian on 8/29/15.
 */
public class LauncherApps extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.puisi_splashscreen);

//        Typeface blockFonts = Typeface.createFromAsset(getAssets(), "CarterOne.ttf");
//        TextView txtSampleTxt = (TextView) findViewById(R.id.gus_mustx);
////      txtSampleTxt.setTypeface(blockFonts);
//
//        TextView txtSampleTxt1 = (TextView) findViewById(R.id.gus_mustx_1);
////      txtSampleTxt1.setTypeface(blockFonts);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(LauncherApps.this, MainActivity.class);
                startActivity(intent);
            }
        }, 2000);
    }
}

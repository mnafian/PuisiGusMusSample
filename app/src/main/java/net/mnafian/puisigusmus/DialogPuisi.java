package net.mnafian.puisigusmus;

import android.app.Dialog;
import android.content.Context;
import android.media.Image;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.Snackbar;
import android.text.Html;
import android.view.View;
import android.view.Window;
import android.webkit.MimeTypeMap;
import android.webkit.URLUtil;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.koushikdutta.async.future.Future;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.ProgressCallback;

import net.mnafian.puisigusmus.Utilities.StaticClass;

import java.io.File;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by mnafian on 8/28/15.
 */

public class DialogPuisi extends Dialog {

    private String mPuisi, mUrlDownload, mNameFile, mFileExtension, mPengarang, mJudul, mLinkUrl;
    private Context mContext;
    private TextView mPuisiText, mPuisiPengarang, mPuisiJudul;
    private Future<File> downloading;
    private ProgressBar progressBar;
    private MediaPlayer mp;
    private ImageButton playAudio, pauseAudio;
    private CircleImageView mImageView;

    public DialogPuisi(Context context, String puisi, String urlDownload, String pengarang, String judul, String linkUrlImage) {
        super(context);
        this.mContext = context;
        this.mPuisi = puisi;
        this.mUrlDownload = urlDownload;
        this.mPengarang = pengarang;
        this.mJudul = judul;
        this.mLinkUrl = linkUrlImage;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.puisi_layout_textpuisi);
        mPuisiText = (TextView) findViewById(R.id.puisi_text);
        mPuisiPengarang = (TextView) findViewById(R.id.puisi_pengarang);
        mPuisiJudul = (TextView) findViewById(R.id.puisi_judul);
        mImageView = (CircleImageView) findViewById(R.id.puisi_imagedetail);

        Glide.with(mContext)
                .load(mLinkUrl)
                .into(mImageView);

        mPuisiPengarang.setText(mPengarang);
        mPuisiJudul.setText(mJudul);

        playAudio = (ImageButton) findViewById(R.id.play_audio);
        pauseAudio = (ImageButton) findViewById(R.id.pause_audio);

        mPuisiText.setText(Html.fromHtml(mPuisi));

        progressBar = (ProgressBar)findViewById(R.id.progress);

        mFileExtension = MimeTypeMap.getFileExtensionFromUrl(mUrlDownload);
        mNameFile = URLUtil.guessFileName(mUrlDownload, null, mFileExtension);

        StaticClass.createDirIfNotExists("/PuisiGusMus/media/");

        File check = new File(Environment.getExternalStorageDirectory().getPath() + "/PuisiGusMus/media/" + mNameFile);

        if (check.exists()) {
            progressBar.setVisibility(View.GONE);
            mp = MediaPlayer.create(mContext, Uri.parse(Environment.getExternalStorageDirectory().getPath() + "/PuisiGusMus/media/" + mNameFile));
            mp.start();

        } else {

            downloading = Ion.with(mContext)
                    .load(mUrlDownload)
                            // attach the percentage report to a progress bar.
                            // can also attach to a ProgressDialog with progressDialog.
                    .progressBar(progressBar)
                            // callbacks on progress can happen on the UI thread
                            // via progressHandler. This is useful if you need to update a TextView.
                            // Updates to TextViews MUST happen on the UI thread.
                    .progressHandler(new ProgressCallback() {
                        @Override
                        public void onProgress(long downloaded, long total) {

                        }
                    })
                    .write(new File(Environment.getExternalStorageDirectory().getPath() + "/PuisiGusMus/media/" + mNameFile))
                            // run a callback on completion
                    .setCallback(new FutureCallback<File>() {
                        @Override
                        public void onCompleted(Exception e, File result) {
                            progressBar.setVisibility(View.GONE);
                            if (e != null) {
                                Snackbar.make(getWindow().getDecorView(), "Error downloading file", Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();
                                return;
                            }
                            Snackbar.make(getWindow().getDecorView(), "Complete", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();

                            mp = MediaPlayer.create(mContext, Uri.parse(Environment.getExternalStorageDirectory().getPath() + "/PuisiGusMus/media/" + mNameFile));
                            mp.start();
                        }
                    });

        }

        playAudio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mp.start();
            }
        });

        pauseAudio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mp.pause();
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mp.stop();
    }
}

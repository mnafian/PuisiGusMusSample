package net.mnafian.puisigusmus;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import net.mnafian.puisigusmus.Adapter.PuisiAdapter;
import net.mnafian.puisigusmus.ItemClass.PuisiItem;
import net.mnafian.puisigusmus.Utilities.StaticClass;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.recyclerview.animators.adapters.SlideInBottomAnimationAdapter;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mPlayListPuisi;

    private List<PuisiItem> puisiList = new ArrayList<PuisiItem>();

    private OkHttpClient clientRest = new OkHttpClient();
    private LoadDataPuisiFeed newsTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.puisi_list_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mPlayListPuisi = (RecyclerView) findViewById(R.id.list_puisi);
        mPlayListPuisi.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        mPlayListPuisi.setLayoutManager(llm);

        if (!StaticClass.checkConnection(this)) {
            Snackbar.make(mPlayListPuisi, "No Connection Found", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        } else {
            newsTask = (LoadDataPuisiFeed) new LoadDataPuisiFeed().execute();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_about) {
            Intent intent = new Intent(MainActivity.this, About.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    public class LoadDataPuisiFeed extends AsyncTask<String, Void, List<PuisiItem>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected List<PuisiItem> doInBackground(String... params) {
            try {

                Request request = new Request.Builder().url(StaticClass.URL_PUISI_LIST)
                        .build();
                Response response = clientRest.newCall(request).execute();
                String responsedata = response.body().string();
                Log.e("String json", responsedata);
                JSONArray rootArr = new JSONArray(responsedata);
                JSONObject rootObj = rootArr.getJSONObject(0);

                JSONArray postData = rootObj.getJSONArray(StaticClass.GET_POST);
                if (postData != null) {
                    for (int i = 0; i < postData.length(); i++) {
                        JSONObject postTotal = postData.getJSONObject(i);
                        String mUrlImage = postTotal.getString(StaticClass.GET_IMAGE_URL);
                        String mPengarang = postTotal.getString(StaticClass.GET_PENGARANG);
                        String mJudul = postTotal.getString(StaticClass.GET_JUDUL);
                        String mAudio = postTotal.getString(StaticClass.GET_AUDIO);
                        String mPuisi = postTotal.getString(StaticClass.GET_PUISI);

                        PuisiItem puisiItemFeed = new PuisiItem();
                        puisiItemFeed.setmPengarang(mPengarang);
                        puisiItemFeed.setmJudulPuisi(mJudul);
                        puisiItemFeed.setmLinkImage(mUrlImage);
                        puisiItemFeed.setLinkPuisiDownload(mAudio);
                        puisiItemFeed.setmPuisi(mPuisi);

                        puisiList.add(puisiItemFeed);
                    }
                }

            } catch (JSONException e) {
                Log.e("errJSON", e.getMessage());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return puisiList;
        }

        @Override
        protected void onPostExecute(List<PuisiItem> result) {
            super.onPostExecute(result);

            PuisiAdapter puisiAdapter = new PuisiAdapter(MainActivity.this, puisiList);
            mPlayListPuisi.setAdapter(new SlideInBottomAnimationAdapter(puisiAdapter));
        }
    }
}

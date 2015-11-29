package com.example.username.myweather;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.username.myweather.entity.City;
import com.example.username.myweather.entity.Pref;
import com.example.username.myweather.entity.Rss;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SettingActivity extends AppCompatActivity {

    private List<Map<String, String>> mCities = new ArrayList<Map<String, String>>();
    private ListView mListView;
    private SimpleAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        mListView = (ListView) findViewById(R.id.listView);
        mAdapter = new SimpleAdapter(this,
                mCities,
                android.R.layout.simple_list_item_1,
                new String[]{"title"},
                new int[]{android.R.id.text1}
        );
        mListView.setAdapter(mAdapter);

        String url = "http://weather.livedoor.com/forecast/rss/primary_area.xml";
        StringRequest request = new StringRequest(url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Serializer serializer = new Persister();
                        try {
                            Rss rss = serializer.read(Rss.class, response);
                            List<Pref> prefs = rss.getChannel().getSource().getPref();
                            for (Pref pref : prefs) {
                                for (City city : pref.getCity()) {
                                    HashMap<String, String> map = new HashMap<String, String>();
                                    map.put("title", city.getTitle());
                                    map.put("id", city.getId());
                                    mCities.add(map);
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        mAdapter.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        MySingleton.getInstance(this).addToRequestQueue(request);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent,
                                    View view, int position, long id) {
                Map<String, String> map
                        = (Map<String, String>) parent.getItemAtPosition(position);
                Intent intent = new Intent();
                intent.putExtra("id", map.get("id"));
                setResult(RESULT_OK, intent);
                finish();
            }
        });

    }
}

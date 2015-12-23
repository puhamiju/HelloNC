package com.ncsoft.mobile.hellonc;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.ncsoft.mobile.hellonc.adapter.IconTextListAdapter;
import com.ncsoft.mobile.hellonc.beans.SearchItem;
import com.ncsoft.mobile.hellonc.beans.SearchSet;
import com.ncsoft.mobile.hellonc.views.IconTextItem;

import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.http.converter.xml.SourceHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.xml.transform.Source;

public class MainActivity extends Activity implements OnScrollListener {

    private static final String TAG = "com.ncsoft";
    ListView listView1;
    IconTextListAdapter adapter;
    RestTemplate restTemplate;

    List<String> keywordList;
    int keywordIdx = 0;
    List<SearchItem> searchItemList = new ArrayList<SearchItem>();

    boolean LockListView;

    private LayoutInflater mInflater;

    String tagName = "";
    String title = "";
    String content = "";
    List<String> titleList = new ArrayList<String>();
    List<String> contentList = new ArrayList<String>();

    public static String defaultUrl = "http://m.naver.com";

    Handler handler = new Handler();
	String  keywordUrlStr[] = {"http://static.plaync.co.kr/search/popkwd/live_keyword_lineage1.js"};
    String searchUrl[] = {"http://112.175.202.179/openapi/_searchgroup_json.jsp?query=%s&collection=lineage1,powerbook&site=lineage1&userip=127.0.0.1"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LockListView = true;
        keywordList = new ArrayList<String>();

        // 리스트뷰 객체 참조
        listView1 = (ListView) findViewById(R.id.listView1);

        // 어댑터 객체 생성
        adapter = new IconTextListAdapter(this);
        // 아이템 데이터 만들기
        Resources res = getResources();
        



        // Create a new RestTemplate instance
        restTemplate = new RestTemplate();

        // Add the String message converter
        restTemplate.getMessageConverters().add(new GsonHttpMessageConverter());
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
        restTemplate.getMessageConverters().add(new SourceHttpMessageConverter<Source>());

        new KeywordTask().execute(keywordUrlStr);



        mInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

//        listView1.addFooterView(mInflater.inflate(R.layout.footer, null));

        // 리스트뷰에 어댑터 설정
        listView1.setOnScrollListener(this);
        listView1.setAdapter(adapter);

        // 새로 정의한 리스너로 객체를 만들어 설정
        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                IconTextItem curItem = (IconTextItem) adapter.getItem(position);
                String[] curData = curItem.getData();

                Toast.makeText(getApplicationContext(), "Selected : " + curData[0], Toast.LENGTH_LONG).show();

            }

        });
    }

    public void refresh(View view) {
        Log.d(TAG, "refresh");

//        adapter = new IconTextListAdapter(this);

//        adapter.notifyDataSetChanged();
        adapter.clearData();
        keywordList.clear();
        keywordIdx = 0;
        adapter.notifyDataSetInvalidated();

        new KeywordTask().execute(keywordUrlStr);

    }

    public void openList(View view) {
        Intent intent = new Intent(getApplicationContext(), ListActivity.class);
        startActivity(intent);

    }

    private void drawView (SearchSet[] searchSetList)  {

        Log.d(TAG, "drawView");
        LockListView = true;

        for(SearchSet searchSet : searchSetList) {

            String collection = searchSet.getCollection();


            int searchCount = searchSet.getSearchCount();

            SearchItem searchItem= searchSet.getResultSet();
            for(int i = 0 ; i < searchCount; i++) {
                String thumbnail = searchItem.getThumbnail()[i];
                adapter.addItem(new IconTextItem(thumbnail, replaceHighlight(searchItem.getTitle()[i]),searchItem.getCategoryname()[i], searchItem.getDate()[i], replaceHighlight(searchItem.getContents()[i])));
            }
        }
        adapter.notifyDataSetChanged();
        LockListView = false;
    }

    private String replaceHighlight(String str) {
        if(str == null)
            return "";

        return str.replace("<!HS>", "" ).replace("<!HE>", "");
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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {



    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {



        int count = totalItemCount - visibleItemCount;
        if(firstVisibleItem >= count && totalItemCount !=0 && LockListView == false) {

//            Log.d(TAG, "onScroll , keywordIdx:" + keywordIdx + " : " + keywordList.size());

            if(keywordList != null && keywordList.size() > 0 && keywordIdx < keywordList.size()) {
                String[] urlStr = {String.format(searchUrl[0], keywordList.get(keywordIdx))};
                keywordIdx ++;

                new Search().execute(urlStr);
//                Log.d(TAG, " onScroll start new Search " + urlStr[0]);
            }


        }

    }


    private class KeywordTask extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... urls) {
            // 키워드 목록



//            HttpURLConnection conn = null;
            try {
                URL url = new URL(urls[0]);

                String keywordStr = restTemplate.getForObject(urls[0], String.class);
                InputStream is = new ByteArrayInputStream(new String(keywordStr.getBytes("8859_1"), "utf-8").getBytes());
                String keyword = "";
                BufferedReader reader = new BufferedReader(new InputStreamReader(is)) ;
                String line = null;
                while(true) {
                    line = reader.readLine();
                    if (line == null) {
                        break;
                    }
                    if(line.startsWith("live_keywords.add")) {
                        keyword = line.substring(line.indexOf("'")+1, line.indexOf("'", line.indexOf("'")+1));
                        keywordList.add(keyword);
                        Log.d(TAG, "keyword :" + keyword);
                    }


                }
/*
                conn = (HttpURLConnection)url.openConnection();
                if (conn != null) {
                    conn.setConnectTimeout(10000);
                    conn.setRequestMethod("GET");
                    conn.setDoInput(false);
                    conn.setDoOutput(true);


                    int resCode = conn.getResponseCode();
                    Log.d(TAG, "resCode : " + resCode);
                    if (resCode == HttpURLConnection.HTTP_OK) {
                        String keyword = "";

                        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream())) ;
                        String line = null;
                        while(true) {
                            line = reader.readLine();
                            if (line == null) {
                                break;
                            }
                            if(line.startsWith("live_keywords.add")) {
                                keyword = line.substring(line.indexOf("'")+1, line.indexOf("'", line.indexOf("'")+1));
                                keywordList.add(keyword);
                            }

                            Log.d(TAG, "keyword :" +keyword );
                        }

                        reader.close();

                    }

                }
 */
            } catch(Exception e) {
                e.getStackTrace();
                Log.e(TAG, "passing error " + e.getMessage());
            } finally {
                //if (conn != null) {
                //   conn.disconnect();
//                }
            }

            for(int i = 0; i < keywordList.size(); i ++)
                Log.d(TAG, "keyword + " +i +" : " + keywordList.get(i));
            return null;

        }
        @Override
        protected void onPostExecute(Void v) {
            super.onPostExecute(v);

            Log.d(TAG, "keyword onPostExecute");
            if(keywordList != null && keywordList.size() > 0) {
                String[] urlStr = {String.format(searchUrl[0], keywordList.get(0))};
                keywordIdx ++;

                new Search().execute(urlStr);
                Log.d(TAG, "start new Search ");
            }
        }



    }

    /**
     *
     */
    private class Search extends AsyncTask<String, Void, SearchSet[]>{


        @Override
        protected SearchSet[] doInBackground(String... urls) {
            SearchSet[] searchSetList = null;
            try {
                String urlStr = urls[0];


                // Make the HTTP GET request, marshaling the response to a String
                searchSetList = restTemplate.getForObject(urlStr, SearchSet[].class, "Android");
//                StreamSource result = (StreamSource)restTemplate.getForObject(urlStr, Source.class, "Android");

                  Log.d(TAG, "result " + Arrays.toString(searchSetList));

            } catch(Exception e) {
                e.getStackTrace();
                Log.e(TAG, "passing error " + e.getMessage());
            }

            return searchSetList;
//            return 1L;
        }

        @Override
        protected void onPostExecute(SearchSet[] searchSetList) {
            super.onPostExecute(searchSetList);
            Log.d(TAG, "onPostExcute searchSetList length: " + searchSetList.length);

            drawView(searchSetList);
        }
    }

//    @Override
//    protected void onPostExecute(Long result) {
//
////       Log.d(TAG, "onPostExcute" + searchSetList.length);
////       Log.d(TAG, searchSetList.size()+"");
//         Log.d(TAG, result+"");
//
////        for( SearchSet searchSet : searchSetList ) {
////            Log.d(TAG, Arrays.toString(searchSet.getResultSet().getTitle()));
////        }
//    }


}



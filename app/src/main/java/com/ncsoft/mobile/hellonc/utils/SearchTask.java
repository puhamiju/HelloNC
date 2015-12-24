package com.ncsoft.mobile.hellonc.utils;

import android.os.AsyncTask;
import android.util.Log;

import com.ncsoft.mobile.hellonc.adapter.IconTextListAdapter;
import com.ncsoft.mobile.hellonc.beans.SearchItem;
import com.ncsoft.mobile.hellonc.beans.SearchSet;
import com.ncsoft.mobile.hellonc.views.IconTextItem;

import org.springframework.web.client.RestTemplate;

import java.lang.ref.WeakReference;
import java.util.Arrays;



/**
 * Created by puhamiju on 2015-12-16.
 */
public class SearchTask extends AsyncTask<String, Void, SearchSet[]> {
    private static final String TAG = "com.ncsoft";

    private final WeakReference<IconTextListAdapter> adapterReference;
    private final RestTemplate restTemplate;

    public SearchTask(IconTextListAdapter adapter, RestTemplate restTemplate) {
        adapterReference = new WeakReference<IconTextListAdapter>(adapter);
        this.restTemplate = restTemplate;
    }

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
        Log.d(TAG, "onPostExcute" + searchSetList.length);

        drawView(searchSetList);
    }

    private void drawView (SearchSet[] searchSetList) {

        if (adapterReference != null) {
            IconTextListAdapter adapter = adapterReference.get();

            Log.d(TAG, "drawView");
            for (SearchSet searchSet : searchSetList) {

                String collection = searchSet.getCollection();

                int searchCount = searchSet.getSearchCount();
                SearchItem searchItem = searchSet.getResultSet();
                for (int i = 0; i < searchCount; i++) {
                    String thumbnail = searchItem.getThumbnail()[i];
                    adapter.addItem(new IconTextItem(thumbnail, searchItem.getTitle()[i],searchItem.getCategoryname()[i], searchItem.getDate()[i], searchItem.getContents()[i], searchItem.getUrl()[i] ));
                }
            }


//            // 리스트뷰에 어댑터 설정
//            listView1.setAdapter(adapter);
//
//            // 새로 정의한 리스너로 객체를 만들어 설정
//            listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//
//                @Override
//                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                    IconTextItem curItem = (IconTextItem) adapter.getItem(position);
//                    String[] curData = curItem.getData();
//
//                    Toast.makeText(getApplicationContext(), "Selected : " + curData[0], Toast.LENGTH_LONG).show();
//
//                }
//
//            });

        }
    }
}

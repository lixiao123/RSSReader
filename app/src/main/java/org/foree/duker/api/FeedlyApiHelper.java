package org.foree.duker.api;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.foree.duker.base.BaseApplication;
import org.foree.duker.net.NetCallback;
import org.foree.duker.rssinfo.RssCategory;
import org.foree.duker.rssinfo.RssFeedInfo;
import org.foree.duker.rssinfo.RssItemInfo;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by foree on 16-7-15.
 */
public class FeedlyApiHelper extends AbsApiHelper {
    private static final String TAG = FeedlyApiHelper.class.getSimpleName();
    private static final String API_HOST_URL = "http://cloud.feedly.com";
    private static final String API_TOKEN_TEST = "A3wMXqyNgMOZwCqIoBC5OZoKdSyKemk1IYWp12rk86Kb7KIBHlUBER2Pe2PWaro4Ur_0Rq1h8MiqQBFE_uly7A6GNbjtT5wWbIF5rf6haQetytQcjZj6_FSDSTrkmF3y5CclNtH3q_6UlK1kPPY0i4_CXXIkhIrT7aTJRUTry3b-HGvq_rwWK7JFewguG4PvV7EMozQuosYKOcMrcd3cGwmYsToq8hc:feedlydev";
    private static final String API_TOKEN = "A3wMXqyNgMOZwCqIoBC5OZoKdSyKemk1IYWp12rk86Kb7KIBHlUBER2Pe2PWaro4Ur_0Rq1h8MiqQBFE_uly7A6GNbjtT5wWbIF5rf6haQetytQcjZj6_FSDSTrkmF3y5CclNtH3q_6UlK1kPPY0i4_CXXIkhIrT7aTJRUTry3b-HGvq_rwWK7JFewguG4PvV7EMozQuosYKOcMrcd3cGwmYsToq8hc:feedlydev";
    private static final String API_CATEGORIES_URL = "/v3/categories";
    private static final String API_SUBSCRIPTIONS_URL = "/v3/subscriptions";
    private static final String API_STREAM_IDS_URL = " /v3/streams/ids?streamId=:streamId";
    private static final String API_STREAM_CONTENTS_URL = "GET /v3/streams/contents?streamId=:streamId";

    private static class NetHolder {
        private static final Context mContext = BaseApplication.getInstance().getApplicationContext();
    }
    @Override
    public void getCategoriesList(String token, NetCallback<RssCategory> netCallback) {
        final String token1 = API_TOKEN_TEST;
        RequestQueue queue = Volley.newRequestQueue(NetHolder.mContext);

        String url = API_HOST_URL + API_CATEGORIES_URL;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i(TAG, response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, error.getMessage());
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params =  super.getHeaders();
                if(params==null)params = new HashMap<>();
                params.put("Authorization","OAuth " + token1);
                //..add other headers
                return params;
            }
            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {

                return super.parseNetworkResponse(response);
            }
        };


        queue.add(stringRequest);
    }

    @Override
    public void getSubscriptions(String token, NetCallback<RssFeedInfo> netCallback) {

    }

    @Override
    public void getStream(String token, NetCallback<RssItemInfo> netCallback) {

    }
}

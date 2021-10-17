package com.example.we_can.tests.base_tools;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Parcel;
import android.util.Log;


import androidx.core.app.ActivityCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.we_can.ServerConnection;
import com.example.we_can.TestActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

//import okhttp3.FormBody;
//import okhttp3.OkHttpClient;
//import okhttp3.Request;
//import okhttp3.RequestBody;
//import okhttp3.Response;


public class HTTPHandler extends AsyncTask<Void, Void, String> {
    private static String url;
    public static int server_state;
    private static Context context;
    //    private static RequestQueue mRequestQueue;

//    OkHttpClient client = new OkHttpClient();
    public static HttpsURLConnection myConnection;
    public static int copnn_status_code;
    public HTTPHandler(String url, Context context){
        this.url = url;
        this.context = context;


        try {

            URL httpbinEndpoint = new URL(url);

        } catch (IOException e) {
            this.copnn_status_code = 0;
            e.printStackTrace();
        }

    }
    public static void set_server_state(int state){
        server_state = state;
    }
    public static int get_server_state(){
        return server_state;
    }
    protected HTTPHandler(Parcel in) {
        url = in.readString();
    }

    public static int server_state_get(){
        String base_url = url;
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

        }
        String alive_state_url = base_url + "/?request=is_alive_req";
        RequestQueue mRequestQueue = Volley.newRequestQueue(context);

        StringRequest sr = new StringRequest(Request.Method.GET, alive_state_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

//                        Intent intent = new Intent(context, TestActivity.class);
//                        context.startActivity(intent);
                        try {
                            JSONObject jsonObject = new JSONObject(response.toString());
                            String status = jsonObject.getJSONObject("payload").get("status").toString();
                            if (status.toString().equals("running")){
                                set_server_state(1);
                            }
//                            Log.e("hhh", (String) jsonObject.get("payload"));
                        } catch (Exception e) {
                            set_server_state(0);
                            e.printStackTrace();
                        }
                        Log.e("HttpClient", "success! response: " + response.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        set_server_state(0);
                        Log.e("HttpClient", "error: " + error.toString());
                    }
                });
        mRequestQueue.add(sr);
        return 0;
    }

    public static int setup_connection(String urli, String request_type, Context context) throws IOException, JSONException {
        // Instantiate the RequestQueue.
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

        }
//        RequestBody formBody = new FormBody.Builder()
//                .add("email", "Jurassic@Park.com")
//                .add("tel", "90301171XX")
//                .build();
//        String url = "http://192.168.52.100:8038/";
//        OkHttpClient client = new OkHttpClient();
//        Thread thread = new Thread(new Runnable() {
//            //
//            @Override
//            public void run() {
//                try  {
//                    Request request = new Request.Builder()
//                            .method("GET", null).url(url).build();
//
//                    System.out.println(request.method());
////                    Response response = client.newCall(request).execute();
//                    System.out.println("iron dome");
////                    String a = response.body().string();
////                    JSONObject Jobject = new JSONObject(a);
////                    System.out.println(Jobject.toString());
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//        thread.start();
//        URL httpbinEndpoint = new URL("http://192.168.52.100:8038/");
//        HttpsURLConnection myConnection
//                = (HttpsURLConnection) httpbinEndpoint.openConnection();
//
//        myConnection.setRequestMethod("GET");
//        // Create the data
//        String myData = "message=Hello";
//
//// Enable writing
//        myConnection.setDoOutput(true);
//
//// Write the data
//        myConnection.getOutputStream().write(myData.getBytes());
//        mRequestQueue = Volley.newRequestQueue(context);
//        String url = "http://192.168.52.100:8038/ ";
//        JSONObject object = new JSONObject();
//        URL Y = new URL(url);
//        try {
//            //input your API parameters
//            object.put("parameter","value");
//            object.put("parameter2","value");
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        StringRequest sr = new StringRequest(Request.Method.GET, url,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        Log.e("HttpClient", "success! response: " + response.toString());
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Log.e("HttpClient", "error: " + error.toString());
//                    }
//                })
//        {
//            @Override
//            protected Map<String,String> getParams(){
//                Map<String,String> params = new HashMap<String, String>();
//                params.put("mac","YOUR USERNAME");
//                params.put("request","is_alive_req");
//                return params;
//            }
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                Map<String,String> params = new HashMap<String, String>();
//                params.put("Content-Type","application/string");
//                return params;
//            }
//        };
//
//        mRequestQueue.add(sr);
        //        mRequestQueue = Volley.newRequestQueue(context);
//        Map<String, String> postParam= new HashMap<String, String>();
//        postParam.put("mac", "ab:cd:ef:gh:ij:kl");
//        postParam.put("request", "is_alive_req");
//        postParam.put("payload", "{}");
//        //String Request initialized
//        //display the response on screen
//        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET,
//                url, new JSONObject(postParam),
//                new Response.Listener<JSONObject>() {
//
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        System.out.println("gopi bahu");
//                        System.out.println(response.toString());
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//
//                    }
//                });
//        System.out.println("shivamrock");
//        System.out.println(req.getBody().toString());
//        mRequestQueue.add(req);



//        String url1 = "http://192.168.52.100:8038";
//        OkHttpClient client = new OkHttpClient();
//        HttpUrl.Builder urlBuilder = HttpUrl.parse("http://192.168.52.100:8038/?request=is_alive_req").newBuilder();
//
//        String url = urlBuilder.build().toString();
//        Thread thread = new Thread(new Runnable() {
//
//            @Override
//            public void run() {
//                try  {
//                    Request request = new Request.Builder()
//                            .url(url)
//                            .build();
//                    Response response = client.newCall(request).execute();
//                    System.out.println("iron dome");
//                    String a = response.body().string();
//                    JSONObject Jobject = new JSONObject(a);
//                    System.out.println(Jobject.toString());
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//
//        thread.start();


        return 0;
    }


    @Override
    protected String doInBackground(Void... voids) {
        return null;
    }
}
/*



 */




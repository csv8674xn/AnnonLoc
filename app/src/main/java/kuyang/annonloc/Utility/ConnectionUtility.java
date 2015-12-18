package kuyang.annonloc.Utility;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

import kuyang.annonloc.R;

/**
 * Created by Yao-Jung on 2015/9/4.
 */
public class ConnectionUtility  {

    private static String TAG = "ConnectionUtility";

    public interface onTaskListener {
        void onPostExecute(String result);
    }

    private onTaskListener mTaskListener;
    private Context context;

    private static final String LAT_KEY = "latitude";
    private static final String LON_KEY = "longitude";

    public ConnectionUtility (Context context,onTaskListener listener){
        this.context = context;
        mTaskListener = listener;
    }

    public static String getLocation(Context context, LatLng location){
        String SERVER_ADDRESS = context.getString(R.string.SERVER_ADDRESS);
        String requestURL = SERVER_ADDRESS + "nearLocations"+"?"+LAT_KEY+"="+location.latitude+"&"+LON_KEY+"="+location.longitude;
        return requestResult(requestURL);
    }

    public static String getLocationDetail(Context context, String locationID){
        String SERVER_ADDRESS = context.getString(R.string.SERVER_ADDRESS);
        String requestURL = SERVER_ADDRESS + "locations/"+locationID+"/comments";
        return requestResult(requestURL);
    }

    public static boolean postTalk(Context context, String locationID, String talk, String icon_url, String name){
        String SERVER_ADDRESS = context.getString(R.string.SERVER_ADDRESS);
        String requestURL = SERVER_ADDRESS + "locations/"+locationID;
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost(requestURL);

        try {
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
            nameValuePairs.add(new BasicNameValuePair("text", talk));
            nameValuePairs.add(new BasicNameValuePair("name", name));
            nameValuePairs.add(new BasicNameValuePair("icon_id", icon_url));
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            HttpResponse response = httpclient.execute(httppost);
            Log.d(TAG, "POST " + httppost.getURI());
            return true;

        } catch (ClientProtocolException e) {
            return false;
        } catch (IOException e) {
            return false;
        }
    }

    public static Bitmap getBitmapFromURL(String src) {
        try {
            java.net.URL url = new java.net.URL(src);
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    private static String requestResult(String requestURL){
        HttpClient client = new DefaultHttpClient();
        String result ="";
        try{
            Log.i(TAG, requestURL);
            HttpGet get = new HttpGet(requestURL);
            HttpResponse response = client.execute(get);
            HttpEntity resEntity = response.getEntity();
            if (resEntity != null) {
                result = EntityUtils.toString(resEntity);
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally{
            client.getConnectionManager().shutdown();
        }
        return result;
    }
}

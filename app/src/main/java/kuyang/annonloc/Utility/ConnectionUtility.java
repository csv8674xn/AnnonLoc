package kuyang.annonloc.Utility;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import kuyang.annonloc.R;

/**
 * Created by Yao-Jung on 2015/9/4.
 */
public class ConnectionUtility  {

    public interface onTaskListener {
        void onPostExecute(String result);
    }

    private onTaskListener mTaskListener;
    private Context context;

    private static final String LAT_KEY = "latitude";
    private static final String LON_KEY = "longtitude";

    public ConnectionUtility (Context context,onTaskListener listener){
        this.context = context;
        mTaskListener = listener;
    }

    public static String getLocation(Context context, LatLng location){
        String SERVER_ADDRESS = context.getString(R.string.SERVER_ADDRESS);
        String requestURL = SERVER_ADDRESS + "locations"+"?"+LAT_KEY+"="+location.latitude+"&"+LON_KEY+"="+location.longitude;
        return requestResult(requestURL);
    }

    public static String getLocationDetail(Context context, String locationID){
        String SERVER_ADDRESS = context.getString(R.string.SERVER_ADDRESS);
        String requestURL = SERVER_ADDRESS + "locations/"+locationID;
        return requestResult(requestURL);
    }

    private static String requestResult(String requestURL){
        HttpClient client = new DefaultHttpClient();
        String result ="";
        try{
            Log.i("SERVER TEST", requestURL);
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

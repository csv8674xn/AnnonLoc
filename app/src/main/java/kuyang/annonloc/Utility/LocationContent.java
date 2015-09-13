package kuyang.annonloc.Utility;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Yao-Jung on 2015/9/9.
 */
public class LocationContent {

    private final static String ID_KEY ="_id";
    private final static String LOCATION_KEY = "loc_info";
    private final static String LOCATION_NAME_KEY ="name";
    private final static String LONGITUDE_KEY = "longitude";
    private final static String LATITUDE_KEY = "latitude";
    private final static String VALUE_KEY = "value";
    private final static String COMMENT_ARRAY_KEY = "comment";
    private final static String COMMENT_ITEM_KEY = "text";
    private final static String LOCATION_IMG = "img";

    private String locationName;
    private double locationLatitude;
    private double locationLongitude;
    private String locationID ;
    private ArrayList<String> commentArray = new ArrayList<>();
    private JSONObject locationJSON;
    private String imgURL;
//    private Bitmap coverPhoto;

    public LocationContent(JSONObject contentJSON){
        try{
            this.locationID = contentJSON.getString(ID_KEY);
            JSONObject valueJSON = contentJSON.getJSONObject(VALUE_KEY);
            this.locationJSON = valueJSON.getJSONObject(LOCATION_KEY);
            this.locationName = locationJSON.getString(LOCATION_NAME_KEY);
            this.locationLatitude = locationJSON.getDouble(LATITUDE_KEY);
            this.locationLongitude = locationJSON.getDouble(LONGITUDE_KEY);
            this.imgURL = locationJSON.getString(LOCATION_IMG);
            JSONArray jsonArray= valueJSON.getJSONArray(COMMENT_ARRAY_KEY);
            for(int i=0; i < jsonArray.length(); i++){
                JSONObject currComment = (JSONObject) jsonArray.get(i);
                commentArray.add(currComment.getString(COMMENT_ITEM_KEY));
            }
        } catch(Exception e){
            e.getStackTrace();
        }
    }

    public String getLocationName(){
        return this.locationName;
    }

    public String getLocationID(){
        return this.locationID;
    }

    public String getImgURL(){
        return this.imgURL;
    }

//    public Bitmap getCoverPhoto(){
//        return coverPhoto;
//    }

    public ArrayList<String> getComments(){
        return new ArrayList<String>(commentArray);
    }
}

package kuyang.annonloc.Utility;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Yao-Jung on 2015/9/9.
 */
public class LocationContent {

    private final static String ID_KEY ="_id";
    private final static String LOCATION_COORDINATE_KEY ="loc";
    private final static String LOCATION_NAME_KEY ="name";
    private final static String COMMENT_ARRAY_KEY = "comments";
    private final static String COMMENT_ITEM_KEY = "text";
    private final static String LOCATION_IMG = "img";

    private String locationName;
    private double locationLatitude;
    private double locationLongitude;
    private String locationID ;
    private ArrayList<String> commentArray = new ArrayList<>();
    private String imgURL;
//    private Bitmap coverPhoto;

    public LocationContent(JSONObject contentJSON){
        try{
            this.locationID = contentJSON.getString(ID_KEY);
            this.locationName = contentJSON.getString(LOCATION_NAME_KEY);
            JSONArray coordinate = contentJSON.getJSONArray(LOCATION_COORDINATE_KEY);
            this.locationLongitude = Double.parseDouble(coordinate.getString(0));
            this.locationLatitude = Double.parseDouble(coordinate.getString(1));

            this.imgURL = contentJSON.getString(LOCATION_IMG);
            JSONArray jsonArray= contentJSON.getJSONArray(COMMENT_ARRAY_KEY);
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

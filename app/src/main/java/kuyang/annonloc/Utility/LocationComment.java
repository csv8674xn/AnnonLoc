package kuyang.annonloc.Utility;

import org.json.JSONObject;

/**
 * Created by Yao-Jung on 2015/9/13.
 */
public class LocationComment {

    private static final String COMMENT_ID_KEY = "name";
    private static final String COMMENT_TEXT_KEY = "text";
    private static final String COMMENT_TIME_KEY = "time";
    private static final String COMMENT_LOC_KEY = "locId";
    private static final String COMMENT_ICON_KEY = "icon_id";

    private String comment_id;
    private String comment_text;
    private String comment_time;
    private String comment_locId;
    private String comment_iconId;

    public LocationComment(JSONObject commentJSON){
        try{
            comment_id = commentJSON.getString(COMMENT_ID_KEY);
            comment_text = commentJSON.getString(COMMENT_TEXT_KEY);
            comment_time = commentJSON.getString(COMMENT_TIME_KEY);
            comment_locId = commentJSON.getString(COMMENT_LOC_KEY);
            comment_iconId = commentJSON.getString(COMMENT_ICON_KEY);
        } catch (Exception e){
            e.getStackTrace();
        }
    }

    public String getCommentText(){
        return comment_text;
    }
    public String getCommentUserId(){
        return comment_id;
    }
    public String getCommentIconId(){
        return comment_iconId;
    }
}

package com.anvesh.saranamayyappa.app;


public class AyyappaConstants {

    public static final int PERMISSIONS_REQUEST_READ_CONTACTS = 1001;
    public static final int PERMISSIONS_REQUEST_RC_RS_RP = 9131;
    public static final int PERMISSIONS_REQUEST_RCAM_RSTORE = 9141;
    //shared preference constants
    public static final String AYYAPPA_PREF = "ayyappapref";
    public static final String MOBILE_NUM = "mobileNumber";
    public static final String USER_NAME = "username";
    public static final String LOGIN_STATUS = "LOGIN_STATUS";
    public static final String COUNTRY_CODE = "COUNTRY_CODE";
    public static final String AYYAPPA_CONTACTS_READ = "AYYAPPA_CONTACTS_READ";
    public final static String SELECTED_CONTACTS = "SELECTED_CONTACTS";
    public final static String ALREADY_SELECTED_CONTACTS = "ALREADY_SELECTED_CONTACTS";
    public final static String FROM_MEMBERS = "FROM_MEMBERS";
    public static boolean CONTACTS_CHANGED = false;
    public final static String MEMBERS_JOINED = "membersjoined";

    public static final String LAT = "latitude" ;

    public static final String LANG = "longitude" ;


    public static final String USER_EMAIL = "email";
    public static final String MOBILE_Type = "m";
    public static final String FB_TYPE = "fb";

    public static final String PROFILE_FIRST = "PROFILE_FIRST";


    public static final String GROUP_NAME = "groupName";
    public static final String DESCRIPTION = "description";
    public static final String CREATED_USERNAME = "createdUserName";
    public static final String HASHTAG = "hashTag";
    public static final String PHONE_NUMBER = "phoneNumbers";
    public static final String inviteMember = "inviteMember";
    public static final String LOCATION_NAME = "locationName";


    public static final String STATUS_CODE = "statusCode";

    public static final String PROFILE_IMAGE_PATH = "PROFILE_IMAGE_PATH";
    public static final String GENERATE_SAS_TOKEN_WS_EP = "generateSASToken";
    public static final String SAS_TOKEN_EXPIRY_TIME = "SAS_TOKEN_EXPIRY_TIME";
    public static final String SAS_ACCESS_TOKEN = "SAS_ACCESS_TOKEN";

    //service constants
    public static final String USER_ID = "_id";
    public static final String EVENT_ID = "eventId";
    public static final String STORE_ID = "storeId";
    public static final String GROUP_ID = "groupId";
    public static final String likeStatus_PARAM = "likeStatus";

    public static final String COMM_CATEGORY = "category";

/*    //Services Names
    public static final String GET_NEARBY_PUJAS = "getNearByPoojas";
    public static final String GET_NEARBY_GURUSWAMY = "getListOfGroups";
    public static final String GET_NEARBY_TEMPLES = "getNearByTemples";*/


    public final static int FEED_EDIT = 1;
    public final static int FEED_DELETE = 2;


    public static final String AYYAPPA_USER_LOGIN = "http://ayyappadev.fankick.io/rest/userLogin";
    public static final String AYYAPPA_USER_LOGINMFB = "http://ayyappadev.fankick.io/rest/userLoginMFB";
    public static final String AYYAPPA_GMAIL_LOGIN = "http://ayyappadev.fankick.io/rest/gmailLogin/auth/google";
    public static final String AYYAPPA_UPDATE_USERLOGIN = "http://ayyappadev.fankick.io/rest/updateUser";
    public static final String AYYAPPA_UPDATE_USERLOGINMFB = "http://ayyappadev.fankick.io/rest/updateuserLoginMFB";
    public static final String AYYAPPA_CREATE_OR_UPDATEPUJA = "http://ayyappadev.fankick.io/rest/createOrUpdatePooja";
    public static final String AYYAPPA_CREATE_NEW_GROUP = "http://ayyappadev.fankick.io/rest/createOrUpdateGuruSwamyGroups";
    public static final String AYYAPPA_NEARBY_PUJAS = "http://ayyappadev.fankick.io/rest/getNearByPoojas?";
    public static final String AYYAPPA_JOIN_UNJOIN_PUJA = "http://ayyappadev.fankick.io/rest/joinOrUnjoinPuja?";
    public static final String AYYAPPA_NEARBY_GURUSWAMYGROUPS = "http://ayyappadev.fankick.io/rest/getNearByGuruSwamy?";
    public static final String AYYAPPA_GROUPSEARCH = "http://ayyappadev.fankick.io/rest/gSearch?search=";
    public static final String AYYAPPA_JOIN_UNJOIN_GROUPS = "http://ayyappadev.fankick.io/rest/joinOrunjoinGroup?";
    public static final String AYYAPPA_MATERIALS = "http://ayyappadev.fankick.io/rest/materials?";
    public static final String AYYAPPA_GROUP_MEMBERS = "http://ayyappadev.fankick.io/rest/getGroupMembersDetails?";
    public static final String AYYAPPA_CREATE_SHOP="http://ayyappadev.fankick.io/rest/createOrUpdateStore";
    public static final String AYYAPPA_NEARBY_STORES="http://ayyappadev.fankick.io/rest/getNearByStore?";
    public static final String AYYAPPA_POST_FEED="http://ayyappadev.fankick.io/rest/postFeed";
    public static final String AYYAPPA_FEED="http://ayyappadev.fankick.io/rest/getFeedDetails";
    public static final String AYYAPPA_ABOUT_GROUP = "http://ayyappadev.fankick.io/rest/getGroupById?";
    public static final String AYYAPPA_ABOUT_STORE = "http://ayyappadev.fankick.io/rest/getStoreById?";
    public static final String AYYAPPA_POST_GURUSWAMY_WALL = "http://ayyappadev.fankick.io/rest/postFeedByGuruSwamy";
    public static final String AYYAPPA_POST_GURUSWAMY_FEED = "http://ayyappadev.fankick.io/rest/postFeedByGuruSwamyUsers";
    public static final String AYYAPPA_POST_STORE_FEED = "http://ayyappadev.fankick.io/rest/postFeedByStoreOwner";
    public static final String AYYAPPA_GURUSWAMY_WALL = "http://ayyappadev.fankick.io/rest/getFeedFromGuruSwamy?";
    public static final String AYYAPPA_GURUSWAMY_FEED = "http://ayyappadev.fankick.io/rest/getFeedFromGuruSwamyUsers?";
    public static final String AYYAPPA_STORE_FEED = "http://ayyappadev.fankick.io/rest/getFeedFromStoreOwner?";
    public static final String AYYAPPA_LIKEORUNLIKE_GROUP_WALL = "http://ayyappadev.fankick.io/rest/likeOrUnlikeGroupFeed?";
    public static final String AYYAPPA_LIKEORUNLIKE_GROUP_FEED = "http://ayyappadev.fankick.io/rest/likeOrUnlikeGroupFeed?";
    public static final String AYYAPPA_LIKEORUNLIKE_STORE_WALL = "http://ayyappadev.fankick.io/rest/likeOrUnlikeStoreFeed?";
    public static final String AYYAPPA_COMMENT_ON_UNIVERSALFEEDS = "http://ayyappadev.fankick.io/rest/commentsOnUniversalFeeds";
    public static final String AYYAPPA_COMMENT_ON_GROUPFEED = "http://ayyappadev.fankick.io/rest/commentsOnGroupFeeds";
    public static final String AYYAPPA_COMMENT_ON_GROUPWALL = "http://ayyappadev.fankick.io/rest/commentsOnGroupFeeds";
    public static final String AYYAPPA_COMMENT_ON_STOREWALL = "http://ayyappadev.fankick.io/rest/commentsOnStoreFeeds";


}

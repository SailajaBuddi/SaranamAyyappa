package com.anvesh.saranamayyappa.app;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;


public class AyyappaPref {
    private static SharedPreferences getSharedPreferences() {
        return AyyappaApplication.getInstance().getSharedPreferences(AyyappaConstants.AYYAPPA_PREF, Context.MODE_PRIVATE);
    }

    private static SharedPreferences.Editor getEditor() {
        return getSharedPreferences().edit();
    }

    public static void setContactsRead(boolean isRead) {
        putBooleanToSharedPreferences(AyyappaConstants.AYYAPPA_CONTACTS_READ, isRead);
    }

    public static void saveUserEmailIntoPref(String name) {
        putStringIntoPref(AyyappaConstants.USER_EMAIL, name);

    }

    public static String getUserEmailFromPref() {
        return getStringFromPref(AyyappaConstants.USER_EMAIL);
    }

    public static void saveUserId(String userId) {
        putStringIntoPref(AyyappaConstants.USER_ID, userId);
    }

    public static String getUserId() {
        return getStringFromPref(AyyappaConstants.USER_ID);
    }

    public static void saveEventId(String eventId) {
        putStringIntoPref(AyyappaConstants.EVENT_ID, eventId);
    }

    public static String getEventId() {
        return getStringFromPref(AyyappaConstants.EVENT_ID);
    }


    public static void saveGroupId(String groupId) {
        putStringIntoPref(AyyappaConstants.GROUP_ID, groupId);
    }

    public static String getGroupId() {
        return getStringFromPref(AyyappaConstants.GROUP_ID);
    }

    public static boolean getLoginStatus() {
        return getBooleanValueFromSharedPrefrences(AyyappaConstants.LOGIN_STATUS);
    }

    public static void saveLoginStatus(boolean accessToken) {
        putBooleanToSharedPreferences(AyyappaConstants.LOGIN_STATUS, accessToken);
    }


    public static void saveCountryCode(String countryCode) {
        putStringIntoPref(AyyappaConstants.COUNTRY_CODE, countryCode);
    }

    public static String getCountryCode() {
        return getStringFromPref(AyyappaConstants.COUNTRY_CODE);
    }

    public static void saveUserMobileNum(String mobileNum) {
        putStringIntoPref(AyyappaConstants.MOBILE_NUM, mobileNum);
    }

    public static String getUserMobileNum() {
        return getStringFromPref(AyyappaConstants.MOBILE_NUM);
    }


    public static void saveUserName(String userName) {
        putStringIntoPref(AyyappaConstants.USER_NAME, userName);
    }

    public static String getUserName() {
        return getStringFromPref(AyyappaConstants.USER_NAME);
    }

    public static void saveCommCategory(String category){ putStringIntoPref(AyyappaConstants.COMM_CATEGORY,category);}

    public static String getCommCategory() { return getStringFromPref(AyyappaConstants.COMM_CATEGORY);}


    public static void saveLatitude(String latitude) {
        putStringIntoPref(AyyappaConstants.LAT, latitude);
    }

   public static void saveLongitude(String longitude) {
       putStringIntoPref(AyyappaConstants.LANG, longitude);
   }

    public static void saveStoreId(String storeId) {
        putStringIntoPref(AyyappaConstants.STORE_ID, storeId);
    }

    public static String getStoreId() {
        return getStringFromPref(AyyappaConstants.STORE_ID);
    }

    public static void saveLatLang(String lat,String lang){
        putStringsIntoPref(AyyappaConstants.LAT, AyyappaConstants.LANG, lat, lang );
    }

    public static String getLongitude() {
        return getStringFromPref(AyyappaConstants.LANG);
    }

    public static String getLatitude() {
        return getStringFromPref(AyyappaConstants.LAT);
    }


    public static void saveMembersJoinedCount(String membersJoined){
        putStringIntoPref(AyyappaConstants.MEMBERS_JOINED,membersJoined);
    }

    public static String getMembersJoined(){
        return getStringFromPref(AyyappaConstants.MEMBERS_JOINED);
    }


    /*  public static void saveUserRole(String role) {
        putStringIntoPref(WCMConstants.USER_ROLE, role);
    }

    public static String getUserRole() {
        return getStringFromPref(WCMConstants.USER_ROLE);
    }
*/
    private static void putStringIntoPref(String key, String value) {
        getEditor().putString(key, value).apply();
    }

    private static void putStringsIntoPref(String key, String key1, String value,String value1) {
        getEditor().putString(key, value).apply();
        getEditor().putString(key1, value1).apply();
    }

    private static String getStringFromPref(String key) {
        return getSharedPreferences().getString(key, "");
    }


    public static boolean getStatus() {
        return getBooleanValueFromSharedPrefrences(AyyappaConstants.STATUS_CODE);
    }

    public static void saveStatus(boolean accessToken) {
        putBooleanToSharedPreferences(AyyappaConstants.STATUS_CODE, accessToken);
    }

    private static boolean getBooleanValueFromSharedPrefrences(String key) {
        return getSharedPreferences().getBoolean(key, false);
    }

    private static void putBooleanToSharedPreferences(String key, boolean value) {
        getEditor().putBoolean(key, value).apply();
    }

    public static void saveProfileImagePath(String profileimagepath) {
        putStringIntoPref(AyyappaConstants.PROFILE_IMAGE_PATH, profileimagepath);
    }

    public static String getProfileImagePath() {
        return getStringFromPref(AyyappaConstants.PROFILE_IMAGE_PATH);
    }

    public static boolean isProfileFirst() {
        return getBooleanValueFromSharedPrefrences(AyyappaConstants.PROFILE_FIRST);
    }

    public static void setProfileFirst(boolean accessToken) {
        putBooleanToSharedPreferences(AyyappaConstants.PROFILE_FIRST, accessToken);
    }

    public static void saveSasAccessToken(String savesasaccesstoken) {
        putStringIntoPref(AyyappaConstants.SAS_ACCESS_TOKEN, savesasaccesstoken);
    }

    public static String getSasAccessToken() {
        return getStringFromPref(AyyappaConstants.SAS_ACCESS_TOKEN);
    }

    public static void saveSasTokenExpiryTime(String savesastokenexptime) {
        putStringIntoPref(AyyappaConstants.SAS_TOKEN_EXPIRY_TIME, savesastokenexptime);
    }

    public static String getSasTokenExpiryTime() {
        return getStringFromPref(AyyappaConstants.SAS_TOKEN_EXPIRY_TIME);
    }

}

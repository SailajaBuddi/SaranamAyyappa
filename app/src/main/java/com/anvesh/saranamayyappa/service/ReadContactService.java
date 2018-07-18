package com.anvesh.saranamayyappa.service;

import android.app.IntentService;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.util.Log;

import com.anvesh.saranamayyappa.app.AyyappaPref;
import com.anvesh.saranamayyappa.app.DataBaseHelper;
import com.anvesh.saranamayyappa.model.Contact;
import com.anvesh.saranamayyappa.utils.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ReadContactService extends IntentService {

    public ReadContactService() {
        super(ReadContactService.class.getName());
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        ArrayList<Contact> contactArrayList = getAllContacts();
        DataBaseHelper db = DataBaseHelper.getInstance(this);
        db.clearContacts();

        for (int i = 0; i < contactArrayList.size(); i++) { //get number with country code and save in the DB
            String numberWithCountryCode = util.validateUsing_libPhoneNumber(AyyappaPref.getCountryCode(), contactArrayList.get(i).getNumber());
            contactArrayList.get(i).setNumberWithCountryCode(numberWithCountryCode == null ? contactArrayList.get(i).getNumber() : numberWithCountryCode);//pass countryCode from account kit
        }

        if (db.addAllContacts(contactArrayList)) {
            AyyappaPref.setContactsRead(true);
        }

      /*  Intent broadcastIntent = new Intent();
        broadcastIntent.setAction(FKickConstants.CONTACTS_PROCESS);
        broadcastIntent.addCategory(Intent.CATEGORY_DEFAULT);
        sendBroadcast(broadcastIntent);*/
    }

    public ArrayList<Contact> getAllContacts() {
        Cursor cursor = null;
        ContentResolver cr = null;
        try {
            cr = getContentResolver();
            ContentProviderClient mCProviderClient = cr.acquireContentProviderClient(ContactsContract.Contacts.CONTENT_URI);
            cursor = mCProviderClient.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
            ArrayList<Contact> contactArrayList = new ArrayList<>();
            if (cursor.moveToFirst()) {
                do {
                    String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                    if (Integer.parseInt(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
                        Cursor pCur = mCProviderClient.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", new String[]{id}, null);

                        while (pCur.moveToNext()) {
                            String contactName = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                            int phoneIdx = 0;
                            phoneIdx = pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DATA);
                            pCur.moveToFirst();
                            while (!pCur.isAfterLast()) {
                                Contact contact = new Contact(contactName, pCur.getString(phoneIdx), getContactImagePath(mCProviderClient, Long.valueOf(id)));
                                contactArrayList.add(contact);
                                pCur.moveToNext();
                            }
                            break;
                        }
                        pCur.close();
                    }
                } while (cursor.moveToNext());
            } else Log.d("Name->", "no contacts read");
            Collections.sort(contactArrayList, new SortContactNames());
            return contactArrayList;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return null;
    }

    public static class SortContactNames implements Comparator<Contact> {
        public int compare(Contact o1, Contact o2) {
            return o1.getName().compareToIgnoreCase(o2.getName());
        }
    }

    public String getContactImagePath(ContentProviderClient mCProviderClient, long contactId) {
        Uri contactUri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, contactId);
        Uri photoUri = Uri.withAppendedPath(contactUri, ContactsContract.Contacts.Photo.CONTENT_DIRECTORY);
        return photoUri.getPath();
    }
}

package com.anvesh.saranamayyappa.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.anvesh.saranamayyappa.R;
import com.anvesh.saranamayyappa.adapters.ContactsAdapter;
import com.anvesh.saranamayyappa.adapters.ContactsGroupAdapter;
import com.anvesh.saranamayyappa.app.AyyappaConstants;
import com.anvesh.saranamayyappa.app.AyyappaPref;
import com.anvesh.saranamayyappa.app.DataBaseHelper;
import com.anvesh.saranamayyappa.model.Contact;
import com.anvesh.saranamayyappa.service.ReadContactService;
import com.anvesh.saranamayyappa.utils.util;


import java.util.ArrayList;
import java.util.Collections;

public class ContactsActivity extends AppCompatActivity implements View.OnClickListener {

    ListView contactsListView;
    ContactsAdapter adapter;
    ContactsGroupAdapter selectedContactsRecyclerView;
    ArrayList<com.anvesh.saranamayyappa.model.Contact> contactLists = new ArrayList<>();
    ArrayList<com.anvesh.saranamayyappa.model.Contact> selected_contacts_list = new ArrayList<>();

    ArrayList<com.anvesh.saranamayyappa.model.Contact> new_added_contacts = new ArrayList<>();

    RecyclerView mRecyclerSelectedContacts;
    SearchView searchView;

    private MyWebRequestReceiver receiver;

    TextView tv_selected_contacts;
    String fromMembers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
        init();

        ArrayList<com.anvesh.saranamayyappa.model.Contact> selectedContacts = (ArrayList<com.anvesh.saranamayyappa.model.Contact>) getIntent().getSerializableExtra(AyyappaConstants.SELECTED_CONTACTS);

        ArrayList<String> alreadySelectedContacts = (ArrayList<String>) getIntent().getSerializableExtra(AyyappaConstants.ALREADY_SELECTED_CONTACTS);
        fromMembers = getIntent().getStringExtra(AyyappaConstants.FROM_MEMBERS);
        try {
            DataBaseHelper db = DataBaseHelper.getInstance(ContactsActivity.this);
            contactLists = (ArrayList<com.anvesh.saranamayyappa.model.Contact>) db.getAllContacts();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (alreadySelectedContacts != null) {          //check for already selected contacts
            for (String alreadySelected : alreadySelectedContacts) {
                for (com.anvesh.saranamayyappa.model.Contact c : contactLists) {
                    if (alreadySelected.equalsIgnoreCase(c.getNumber())) {
                        c.isAlreadySelected = true;
                        c.setNumberWithCountryCode(alreadySelected);
                    }
                }
            }
        }
        if (selectedContacts != null && contactLists != null) {             //check for selected contacts
            for (com.anvesh.saranamayyappa.model.Contact selected : selectedContacts) {
                for (com.anvesh.saranamayyappa.model.Contact c : contactLists) {
                    if (selected.getNumberWithCountryCode().equals(c.getNumber())) {
                        c.isSelected = true;
                        c.setNumberWithCountryCode(selected.getNumberWithCountryCode());
                    }
                }
            }
        }
        adapter = new ContactsAdapter(ContactsActivity.this, contactLists);
        contactsListView.setAdapter(adapter);
        //Selected/Already selected contacts list
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ContactsActivity.this, LinearLayoutManager.HORIZONTAL, false);
        mRecyclerSelectedContacts.setLayoutManager(linearLayoutManager);
        selected_contacts_list = getSelectedContacts();
        selectedContactsRecyclerView = new ContactsGroupAdapter(selected_contacts_list);
        mRecyclerSelectedContacts.setAdapter(selectedContactsRecyclerView);
        showHideSelectedContacts();
        selectedContactsRecyclerView.setOnItemClickListener(new ContactsGroupAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                com.anvesh.saranamayyappa.model.Contact contact = selected_contacts_list.get(position);
                setSelected(contact, false);
            }
        });
    }

    private void showHideSelectedContacts() {
        if (selected_contacts_list.size() == 0) {
            if (mRecyclerSelectedContacts.getVisibility() == View.VISIBLE) {
                mRecyclerSelectedContacts.setVisibility(View.GONE);
            }
            tv_selected_contacts.setVisibility(View.GONE);
        } else if (selected_contacts_list.size() > 0) {
            if (mRecyclerSelectedContacts.getVisibility() == View.GONE) {
                mRecyclerSelectedContacts.setVisibility(View.VISIBLE);
            }
            tv_selected_contacts.setVisibility(View.VISIBLE);
        }
    }

    private void init() {
        Toolbar mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setTitle("Contacts");
        TextView toolbar_title = findViewById(R.id.toolbar_title);
        toolbar_title.setText("Contacts");
        contactsListView = findViewById(R.id.list_contacts);
        mRecyclerSelectedContacts = findViewById(R.id.selected_contacts);
        TextView toolbar_done = findViewById(R.id.toolbar_done);
        tv_selected_contacts = findViewById(R.id.tv_selected_contacts);
        toolbar_done.setOnClickListener(this);
        contactsListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                util.hideKeyboard(ContactsActivity.this);
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            }
        });
    }

    public void setSelected(Contact picContact, boolean fromGroup) {
        if (null == picContact)
            return;
        Log.d("isSelected", "picContact  " + picContact.getName() + " _ " + picContact.getNumber());
        String numberWithCountryCode = util.validateUsing_libPhoneNumber(AyyappaPref.getCountryCode(), picContact.getNumber());//pass countryCode from account kit
        if (numberWithCountryCode == null) {
            util.showMessage(this, "Contact " + picContact.getName() + " is not valid");
            return;
        } else {
            for (int i = 0; i < contactLists.size(); i++) {
                if (picContact.getNumber().equals(contactLists.get(i).getNumber()) && picContact.getName().equals(contactLists.get(i).getName())) {
                    contactLists.get(i).setNumberWithCountryCode(numberWithCountryCode);
                    if (contactLists.get(i).isAlreadySelected)       //if its already selected contact do nothing
                        return;
                    if (contactLists.get(i).isSelected && !fromGroup) {             // fromGroup flag-->when adding contacts from group list don't un select already selected contact
                        Log.d("isSelected++", "isSelected " + contactLists.get(i).isSelected);
                        contactLists.get(i).isSelected = false;
                        contactLists.get(i).newContact = false;
                        selected_contacts_list.remove(contactLists.get(i));
                        new_added_contacts.remove(contactLists.get(i));
                        showHideSelectedContacts();
                        //removeFromSelectedList(picContact);
                    } else {
                        Log.d("isSelected+", "isSelected " + contactLists.get(i).isSelected);
                        contactLists.get(i).isSelected = true;
                        contactLists.get(i).newContact = true;
                        addToSelectedList(contactLists.get(i));
                        showHideSelectedContacts();
                        mRecyclerSelectedContacts.scrollToPosition(selected_contacts_list.size() - 1);
                    }
                    break;
                }
            }
            contactsListView.invalidateViews();
            adapter.notifyDataSetChanged();
            selectedContactsRecyclerView.notifyDataSetChanged();
        }
        if (null != searchView)
            searchView.setQuery("", false);

    }

    private void addToSelectedList(com.anvesh.saranamayyappa.model.Contact contact) {
        boolean oldContact = false;
        for (int i = 0; i < selected_contacts_list.size(); i++) {
            if (contact.getNumber().equals(selected_contacts_list.get(i).getNumber()) && contact.getName().equals(selected_contacts_list.get(i).getName())) {
                oldContact = true;
            }
        }
        if (!oldContact) {
            selected_contacts_list.add(contact);
            new_added_contacts.add(contact);
        }

    }

    private void removeFromSelectedList(com.anvesh.saranamayyappa.model.Contact picContact) {
        for (int i = 0; i < selected_contacts_list.size(); i++) {
            if (picContact.getNumber().equals(selected_contacts_list.get(i).getNumber()) && picContact.getName().equals(selected_contacts_list.get(i).getName())) {
                selected_contacts_list.remove(selected_contacts_list.get(i));
                selectedContactsRecyclerView.notifyItemRemoved(i);
                break;
            }
        }
    }

  /*  @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.mRefreshContacts:
                refreshContacts();
                return true;
            case R.id.mDone:
                if (util.isNetworkAvailable()) {
                    Intent resultIntent = new Intent();
                    if ((fromMembers != null) && fromMembers.equalsIgnoreCase(FKickConstants.FROM_MEMBERS)) {
                        resultIntent.putExtra("data", new_added_contacts);
                    } else {
                        resultIntent.putExtra("data", getSelectedContacts());
                    }
                    setResult(Activity.RESULT_OK, resultIntent);
                    finish();
                } else {
                    Toast.makeText(ContactsActivity.this, "No internet Connection", Toast.LENGTH_SHORT).show();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }*/
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public class MyWebRequestReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            refreshTotalContacts();
        }
    }

    private void refreshTotalContacts() {
        try {
            DataBaseHelper db = DataBaseHelper.getInstance(this);
            //contactLists = new ArrayList<Contact>(db.getAllContacts());
            //if (contactLists_new.get(i).getNumber().contains(contactLists.get(j).getNumber())) {
            ArrayList<com.anvesh.saranamayyappa.model.Contact> contactLists_new = (ArrayList<com.anvesh.saranamayyappa.model.Contact>) db.getAllContacts();
            Log.d("before size", "" + contactLists.size());
            // check for added contacts
            for (int i = 0; i < contactLists_new.size(); i++) {
                boolean oldContact = false;
                for (int j = 0; j < contactLists.size(); j++) {
                    if (contactLists_new.get(i).getNumber().contains(contactLists.get(j).getNumber())) {
                        oldContact = true;
                        break;
                    }
                }
                if (!oldContact) {
                    contactLists.add(contactLists_new.get(i));
                    Log.d("new contact", "" + contactLists_new.get(i).getName() + " " + contactLists_new.get(i).getNumber());
                }
            }
            Log.d("after size", "" + contactLists.size());
            Collections.sort(contactLists, new ReadContactService.SortContactNames());
            adapter = new ContactsAdapter(ContactsActivity.this, contactLists);
            contactsListView.setAdapter(adapter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

   /* public void refreshContacts() {
        IntentFilter filter = new IntentFilter(FKickConstants.CONTACTS_PROCESS);
        filter.addCategory(Intent.CATEGORY_DEFAULT);
        receiver = new MyWebRequestReceiver();
        registerReceiver(receiver, filter);
        Intent msgIntent = new Intent(ContactsActivity.this, ReadContactService.class);
        startService(msgIntent);
    }*/

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.toolbar_done:

                if (util.isNetworkAvailable()) {
                    Intent resultIntent = new Intent();
                    if ((fromMembers != null) && fromMembers.equalsIgnoreCase(AyyappaConstants.FROM_MEMBERS)) {
                        resultIntent.putExtra("data", new_added_contacts);
                    } else {
                        resultIntent.putExtra("data", getSelectedContacts());
                    }
                    setResult(Activity.RESULT_OK, resultIntent);
                    finish();
                } else {
                    Toast.makeText(ContactsActivity.this, "No internet Connection", Toast.LENGTH_SHORT).show();
                }
              /*  Intent resultIntent = new Intent();
                resultIntent.putExtra("data", getSelectedContacts());
                setResult(Activity.RESULT_OK, resultIntent);
                finish();*/
//                Intent intent = new Intent(ContactsActivity.this, CreateGroupActivity.class);
//                startActivity(intent);
                break;
        }
    }

    private ArrayList<com.anvesh.saranamayyappa.model.Contact> getSelectedContacts() {
        ArrayList<com.anvesh.saranamayyappa.model.Contact> selectedContacts = new ArrayList<>();
        for (int i = 0; i < contactLists.size(); i++) {
            if (contactLists.get(i).isSelected || contactLists.get(i).isAlreadySelected) {
                selectedContacts.add(contactLists.get(i));
                Log.d("selected c name is ", contactLists.get(i).getName() + "");
            }
        }
        return selectedContacts;
    }


    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_contacts, menu);
        MenuItem searchItem = menu.findItem(R.id.mSearchMenuItem);
        searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String cs) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String cs) {
                Log.d("text changes", "changes" + cs);
                ContactsActivity.this.adapter.getFilter().filter(cs);
                return false;
            }
        });
        // you can get query
        searchView.getQuery();
        return true;
    }*/

    @Override
    protected void onResume() {
        super.onResume();
        if (AyyappaConstants.CONTACTS_CHANGED) {  //if contacts changed refresh the list
            refreshTotalContacts();
            AyyappaConstants.CONTACTS_CHANGED = false;
        }
    }
}

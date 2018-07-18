package com.anvesh.saranamayyappa.adapters;

import android.app.Activity;
import android.content.Context;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.SectionIndexer;

import com.anvesh.saranamayyappa.R;
import com.anvesh.saranamayyappa.activity.ContactsActivity;
import com.anvesh.saranamayyappa.adapters.Holders.ContactsViewHolder;
import com.anvesh.saranamayyappa.model.Contact;
import com.anvesh.saranamayyappa.utils.util;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Set;


public class ContactsAdapter extends BaseAdapter implements SectionIndexer, Filterable {
    Activity act;
    List<Contact> contactLists, contactLists_displayed;
    HashMap<String, Integer> mapIndex;

    HashSet<String> sectionAlreadyHad = new HashSet<>();
    List<Integer> positionsToSections = new ArrayList<Integer>();
    String[] sections;
    private LayoutInflater inflater;

    public ContactsAdapter(Activity act, ArrayList<Contact> contactLists) {
        this.act = act;
        this.contactLists = contactLists;
        this.contactLists_displayed = contactLists;
        this.inflater = LayoutInflater.from(act);

        List<String> nameList = new ArrayList<>();
        for (Contact list : contactLists) {
            Log.d("Name->", list.getName());
            Log.d("Number->", list.getNumber() + "");

            nameList.add(list.getName().substring(0, 1).toUpperCase(Locale.US));
        }

        mapIndex = new LinkedHashMap<String, Integer>();

        for (int x = 0; x < nameList.size(); x++) {
            String fruit = nameList.get(x);
            String ch = fruit.substring(0, 1);
            ch = ch.toUpperCase(Locale.US);

            if (sectionAlreadyHad.add(ch)) {
                mapIndex.put(ch, x);
                Log.d("sectionList-with index", mapIndex.toString());
            }
            positionsToSections.add(mapIndex.size() - 1);
            // HashMap will prevent duplicates
        }

        Set<String> sectionLetters = mapIndex.keySet();

        // create a list from the set to sort
        ArrayList<String> sectionList = new ArrayList<String>(sectionLetters);

        Log.d("sectionList", sectionList.toString());
        Collections.sort(sectionList);

        sections = new String[sectionList.size()];

        sectionList.toArray(sections);
    }

    @Override
    public int getCount() {
        return contactLists.size();
    }

    @Override
    public Object getItem(int position) {
        return contactLists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ContactsViewHolder holder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.contact_list_item, null, false);
            holder = new ContactsViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ContactsViewHolder) convertView.getTag();
        }

        if (contactLists.get(position).isAlreadySelected) {
            holder.unSelectedContact.setVisibility(View.GONE);
            holder.selectedContact.setVisibility(View.GONE);
            holder.tVAlreadySelectedContact.setVisibility(View.VISIBLE);
        } else if (contactLists.get(position).isSelected) {
            holder.unSelectedContact.setVisibility(View.GONE);
            holder.tVAlreadySelectedContact.setVisibility(View.GONE);
            holder.selectedContact.setVisibility(View.VISIBLE);
        } else {
            holder.selectedContact.setVisibility(View.GONE);
            holder.tVAlreadySelectedContact.setVisibility(View.GONE);
            holder.unSelectedContact.setVisibility(View.VISIBLE);
        }

        holder.contactName.setText(util.getContactName(act, contactLists.get(position).getNumber()));
        holder.contactNumber.setText(contactLists.get(position).getNumber());

        String ch = contactLists.get(position).getName().substring(0, 1);
        ch = ch.toUpperCase(Locale.US);

        if (mapIndex.get(ch) == position) {
            holder.tVAlphabet.setVisibility(View.VISIBLE);
            holder.tVAlphabet.setText(ch);
        } else
            holder.tVAlphabet.setVisibility(View.INVISIBLE);

        if (contactLists.get(position).getContactImagePath() != null) {
            Log.d("image path adapter", contactLists.get(position).getContactImagePath());
//            holder.iVContact.bind(contactLists.get(position).getContactImagePath(), ch, true);
            Picasso.with(act)
                    .load(contactLists.get(position).getContactImagePath())
                    .into(holder.iVContact);
        } /*else
            holder.iVContact.setBackground(act.getResources().getDrawable(R.drawable.bottom_profile_picture_empty));*/

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (act instanceof ContactsActivity) {
                    ((ContactsActivity) act).setSelected(contactLists.get(position), false);
                }
            }
        });

        return convertView;
    }

    private void checkCountry(String number) {

        TelephonyManager manager = (TelephonyManager) act.getSystemService(Context.TELEPHONY_SERVICE);

        String usersCountryISOCode = manager.getNetworkCountryIso();

        PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
        try {
            Phonenumber.PhoneNumber phonenUmber = phoneUtil.parse(number, usersCountryISOCode);
            if (phoneUtil.isValidNumber(phonenUmber)) {
                String temp = phoneUtil.getNationalSignificantNumber(phonenUmber);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean validateUsing_libphonenumber(String countryCode, String phNumber) {
        PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();
        String isoCode = phoneNumberUtil.getRegionCodeForCountryCode(Integer.parseInt(countryCode));
        Phonenumber.PhoneNumber phoneNumber = null;
        try {
            phoneNumber = phoneNumberUtil.parse(phNumber, isoCode);

            boolean isValid = phoneNumberUtil.isValidNumber(phoneNumber);
            return isValid;
        } catch (NumberParseException e) {
            System.err.println(e);
            e.printStackTrace();
        }
        return false;
    }

    public int getPositionForSection(int section) {
        return mapIndex.get(sections[section]);
    }

    public int getSectionForPosition(int position) {
        return positionsToSections.get(position);
    }

    public Object[] getSections() {
        return sections;
    }

    @Override
    public Filter getFilter() {

        Filter filter = new Filter() {

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                contactLists = (ArrayList<Contact>) results.values;
                notifyDataSetChanged();
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();
                if (contactLists_displayed == null) {
                    contactLists_displayed = new ArrayList<>(contactLists);
                }

                ArrayList<Contact> contactLists_filter = new ArrayList<>();
                for (Contact c : contactLists_displayed) {
                    if (c.getName().toLowerCase().contains(constraint.toString().toLowerCase())) {
                        contactLists_filter.add(c);
                    }
                }

                results.count = contactLists_filter.size();
                results.values = contactLists_filter;
                return results;
            }
        };
        return filter;
    }
}

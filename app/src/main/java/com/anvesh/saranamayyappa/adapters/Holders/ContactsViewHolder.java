package com.anvesh.saranamayyappa.adapters.Holders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.anvesh.saranamayyappa.R;


public class ContactsViewHolder {
    public TextView contactName, tVAlphabet, unSelectedContact, selectedContact,tVAlreadySelectedContact,contactNumber;
    public ImageView iVContact;

    public ContactsViewHolder(View view) {
        contactName = view.findViewById(R.id.tVcontactName);
        contactNumber = view.findViewById(R.id.tVcontactNumber);
        unSelectedContact = view.findViewById(R.id.tVUnSelectedContact);
        selectedContact = view.findViewById(R.id.tVSelectedContact);
        tVAlreadySelectedContact = view.findViewById(R.id.tVAlreadySelectedContact);
        tVAlphabet = view.findViewById(R.id.tVAlphabet);
        iVContact = view.findViewById(R.id.iVContact);
    }
}
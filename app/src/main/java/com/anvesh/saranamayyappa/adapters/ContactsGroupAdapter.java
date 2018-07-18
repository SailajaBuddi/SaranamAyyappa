package com.anvesh.saranamayyappa.adapters;

import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.anvesh.saranamayyappa.R;
import com.anvesh.saranamayyappa.model.Contact;

import java.util.List;

public class ContactsGroupAdapter extends RecyclerView.Adapter<ContactsGroupAdapter.MyViewHolder> {

    public List<Contact> selected_contacts;
    OnItemClickListener mItemClickListener;


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView iv_contact_image;
        public TextView contact_name;

        public MyViewHolder(View view) {
            super(view);
            iv_contact_image = view.findViewById(R.id.iv_contact_image);
            contact_name = view.findViewById(R.id.contact_name);
        }

        @Override
        public void onClick(View v) {

            mItemClickListener.onItemClick(v, getAdapterPosition());
        }
    }

    public ContactsGroupAdapter(List<Contact> selectedcontacts) {
        this.selected_contacts = selectedcontacts;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.contact_items, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.contact_name.setText(selected_contacts.get(position).getName());
        String firstLetter = selected_contacts.get(holder.getAdapterPosition()).getName().substring(0, 1);
        if (firstLetter != null) {
            holder.iv_contact_image.setText(firstLetter);
            holder.iv_contact_image.setAllCaps(true);

            GradientDrawable bgShape = (GradientDrawable) holder.iv_contact_image.getBackground();
            bgShape.setColor(selected_contacts.get(holder.getAdapterPosition()).getcolor());
        }

        holder.iv_contact_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mItemClickListener.onItemClick(v, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return selected_contacts.size();
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }
}

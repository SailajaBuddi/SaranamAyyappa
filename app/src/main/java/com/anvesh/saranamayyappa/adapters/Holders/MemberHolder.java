package com.anvesh.saranamayyappa.adapters.Holders;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.anvesh.saranamayyappa.R;
import com.anvesh.saranamayyappa.model.GroupMembersPojo;
import com.squareup.picasso.Picasso;

public class MemberHolder extends BaseViewHolder {
    TextView name;
    ImageView img;
    Context context;
    public MemberHolder(Context context, View itemView) {
        super(itemView);
        this.context = context;
        img=itemView.findViewById(R.id.mem_img);
        name=itemView.findViewById(R.id.text11);

    }

    public void setData(Context context, GroupMembersPojo groupmemberspojo) {
        if (groupmemberspojo != null) {

            name.setText(groupmemberspojo.getFullName());
           // Toast.makeText(context, ""+groupmemberspojo.getFullName(), Toast.LENGTH_SHORT).show();

            if (TextUtils.isEmpty(groupmemberspojo.getProfileImage())) {
                Picasso.with(context)
                        .load(R.drawable.god)
                        .into(img);
            } else {
                Picasso.with(context)
                        .load(groupmemberspojo.getProfileImage())
                        .placeholder(R.drawable.god)
                        .into(img);
            }


            /*cardView.setOnClickListener(listener);

            tvJoinNow.setTag(groupspojo);*/
        }
    }
}

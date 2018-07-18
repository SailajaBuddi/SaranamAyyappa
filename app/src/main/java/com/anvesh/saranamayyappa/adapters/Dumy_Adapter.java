package com.anvesh.saranamayyappa.adapters;

/**
 * Created by Anvesh on 18/5/2018.
 */

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.anvesh.saranamayyappa.R;

public class Dumy_Adapter extends RecyclerView.Adapter<Dumy_Adapter.holder> {
    @NonNull

    Context cx;
    int  LAYOUT=R.layout.card_event_h;
    View.OnClickListener listener;
    private String s1[];
    private  TypedArray imgs;
    private int i,size=10;


    public Dumy_Adapter(@NonNull Context context,int LAYOUT) {
        cx=context;
        this.LAYOUT=LAYOUT;

    }

    public Dumy_Adapter(Context context, int LAYOUT, View.OnClickListener listener, int i) {
        cx=context;
        this.LAYOUT=LAYOUT;
        this.listener =listener;
        this.i=i;
    }

    public Dumy_Adapter(Context context, int LAYOUT, String[] s1, TypedArray imgs, View.OnClickListener listener,int i) {
        cx=context;
        this.LAYOUT=LAYOUT;
        this.s1=s1;
        this.imgs=imgs;
        this.listener=listener;
        this.i=i;

    }

    public Dumy_Adapter(Context context, int LAYOUT, String[] s1, TypedArray imgs,View.OnClickListener listener, int i, int size) {
        cx=context;
        this.LAYOUT=LAYOUT;
        this.s1=s1;
        this.imgs=imgs;
        this.listener=listener;
        this.i=i;
        this.size=size;

    }


    @Override
    public holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(cx);
        View view=inflater.inflate(LAYOUT,parent,false);
        return  new holder(view,listener);
    }

    @Override
    public void onBindViewHolder(@NonNull holder holder, int position) {
        try {
            holder.c1.setOnClickListener(listener );
            //Toast.makeText(cx, ""+listener, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try{
           holder.t1.setImageResource(imgs.getResourceId(position, -1));
           holder.t2.setText(s1[position]);
       }
       catch(Exception e){}
    }

    @Override
    public int getItemCount() {
        return size;
    }

    public class holder extends RecyclerView.ViewHolder{
       CardView c1;
        ImageView t1;
        TextView t2;
        public holder(View itemView, View.OnClickListener listener) {
            super(itemView);
            switch (i){
                case 1:  c1=itemView.findViewById(R.id.event_cv);break;
                case 2:  c1=itemView.findViewById(R.id.group_cv); break;
                case 3:  c1=itemView.findViewById(R.id.story_cv); break;
                case 4:  c1=itemView.findViewById(R.id.comm_cv); break;
            }

                t1=itemView.findViewById(R.id.img);
                t2=itemView.findViewById(R.id.text);

        }
    }
}

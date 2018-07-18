package com.anvesh.saranamayyappa.adapters;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.anvesh.saranamayyappa.model.FamousTalkProvider;
import com.anvesh.saranamayyappa.R;
import com.anvesh.saranamayyappa.activity.CommentsActivity;
import com.anvesh.saranamayyappa.activity.QuestionsActivity;

import java.util.ArrayList;

/**
 * Created by Hi on 27-06-2018.
 */
public class FunAdapter extends RecyclerView.Adapter<FunAdapter.RecyclerViewHolderFun> {

    TextView take_challenge;
    ArrayList<FamousTalkProvider> fun = new ArrayList<>();

    public FunAdapter(ArrayList<FamousTalkProvider> fun) {
        this.fun = fun;
    }

    @Override
    public RecyclerViewHolderFun onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_fun2win, parent, false);
        RecyclerViewHolderFun r = new RecyclerViewHolderFun(view);
        return r;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolderFun holder, int position) {

        FamousTalkProvider dp1 = fun.get(position);
        holder.t1.setText(dp1.getText());
        holder.t2.setText(dp1.getName());
        holder.funImg.setImageResource(dp1.getImage());


    }
    @Override
    public int getItemCount() {
        return 4;
    }

    public class RecyclerViewHolderFun extends RecyclerView.ViewHolder {
        TextView t1, t2,likes;
        ImageView funImg,comments,share,img,img1;
        int counter =0;
        public RecyclerViewHolderFun(final View itemView) {
            super(itemView);
            t1 = itemView.findViewById(R.id.f_text1);
            t2 = itemView.findViewById(R.id.f_text2);
            funImg = itemView.findViewById(R.id.funImg);
            take_challenge=itemView.findViewById(R.id.take_challenge);

            take_challenge= itemView.findViewById(R.id.take_challenge);
            take_challenge.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent=new Intent(itemView.getContext(),QuestionsActivity.class);
                    itemView.getContext().startActivity(intent);
                }
            });
            share=itemView.findViewById(R.id.share_img);
            share.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(Intent.ACTION_SEND);
                    intent.setType("text/plain");
                    String shareBody = "Here is the share content body";
                    intent.putExtra(Intent.EXTRA_SUBJECT, "Subject Here");
                    intent.putExtra(Intent.EXTRA_TEXT, shareBody);
                    itemView.getContext().startActivity(Intent.createChooser(intent, "Share via"));
                }
            });
            comments=itemView.findViewById(R.id.comment_img);
            comments.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(itemView.getContext(), CommentsActivity.class);
                    itemView.getContext().startActivity(intent);
                }
            });
            likes=itemView.findViewById(R.id.likes);
            img1=itemView.findViewById(R.id.fav_img1);
            img=itemView.findViewById(R.id.fav_img);
            img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    img.setVisibility(View.INVISIBLE);
                    img1.setVisibility(View.VISIBLE);
                    Toast.makeText(view.getContext(), "Liked", Toast.LENGTH_SHORT).show();
                    counter++;
                    likes.setText(""+counter+" likes");
                }
            });
            img1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    img1.setVisibility(View.INVISIBLE);
                    img.setVisibility(View.VISIBLE);
                    Toast.makeText(view.getContext(), "UnLiked", Toast.LENGTH_SHORT).show();
                    counter--;
                    likes.setText(""+counter+" likes");
                }
            });

        }

    }
}
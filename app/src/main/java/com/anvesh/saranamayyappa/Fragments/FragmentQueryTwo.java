package com.anvesh.saranamayyappa.Fragments;

import android.content.ClipData;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.anvesh.saranamayyappa.R;


public class FragmentQueryTwo extends Fragment {
    ImageView drag1,drag2,drag3;
    ImageView target;
    ViewPager viewPager;
    Handler handler=new Handler();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.activity_query_two,null,false);
        drag1=view.findViewById(R.id.drag1);
        drag2=view.findViewById(R.id.drag2);
        drag3=view.findViewById(R.id.drag3);
        target=view.findViewById(R.id.target);

        viewPager= (ViewPager) getActivity().findViewById(R.id.Q_viewpager);

        drag1.setOnTouchListener(touch);
        drag2.setOnTouchListener(touch);
        drag3.setOnTouchListener(touch);
        target.setOnDragListener(dragListener);


        return view;
    }
    View.OnTouchListener touch=new View.OnTouchListener(){

        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if(motionEvent.getAction()==MotionEvent.ACTION_DOWN&&((ImageView)view).getDrawable()!=null) {
                ClipData clipData = ClipData.newPlainText("", "");
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
                view.startDrag(clipData, shadowBuilder, view, 0);
                return true;
            }else{
                return false;
            }
        }
    };
    View.OnDragListener dragListener=new View.OnDragListener() {
        @Override
        public boolean onDrag(View view, DragEvent dragEvent) {
            int dragevent=dragEvent.getAction();
            switch(dragevent){
                case DragEvent.ACTION_DRAG_ENTERED:
                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    break;
                case DragEvent.ACTION_DROP:
                    final View view1= (View) dragEvent.getLocalState();
                    if(view1.getId()==R.id.drag1){


                        target.setVisibility(View.VISIBLE);
                        target.setImageResource(R.drawable.img4);
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                viewPager.setCurrentItem(2);

                            }
                        },1000);

                    }
                    else if(view1.getId()==R.id.drag2){
                        target.setVisibility(View.VISIBLE);
                        target.setImageResource(R.drawable.img);
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                viewPager.setCurrentItem(2);

                            }
                        },1000);
                    }
                    else if(view1.getId()==R.id.drag3){
                        target.setVisibility(View.VISIBLE);
                        target.setImageResource(R.drawable.img3);
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                viewPager.setCurrentItem(2);

                            }
                        },1000);
                    }
                    break;
            }
            return true;
        }
    };
}

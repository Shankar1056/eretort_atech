package com.atechexcel.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.atechexcel.R;
import com.atechexcel.activity.SkipActivity;
import com.atechexcel.model.BannerImageModel;
import com.atechexcel.webservices.WebServices;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Shankar on 3/18/2018.
 */

public class SkipPagerAdapter extends PagerAdapter {

    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private ArrayList<BannerImageModel> models;
    private Timer timer;
    private int page = 1;
    private SkipActivity activity;
    private boolean isDynamic = false;
    private ViewPager viewPager;

    @Override
    public int getCount() {
        return models.size();
    }

    public void setData(ArrayList<BannerImageModel> res, Context context, SkipActivity activity) {
        this.models = res;
        this.mContext = context;
        this.activity = activity;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void isDynamic(boolean isAnimatable) {
        this.isDynamic = isAnimatable;
    }

    public void setVP(ViewPager viewPager) {
        this.viewPager = viewPager;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View itemView = mLayoutInflater.inflate(R.layout.pager_item, container, false);

        final ImageView imageView = (ImageView) itemView.findViewById(R.id.img_pager);
        try {
                Picasso.with(activity).load(WebServices.IMAGE_BASE_URL_ERETORT+models.get(position).getBanner()).into(imageView);
                //Picasso.with(activity).load("http://i.huffpost.com/gen/4233470/thumbs/o-CHILD-EXAMS-570.jpg?7").into(imageView);

        } catch (Exception e) {
            Log.e("image Exception", e.getMessage());
        }

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (models.get(position).getId().equalsIgnoreCase("5"))
                {
                    //   activity.sendos();
                }
            }
        });
        container.addView(itemView);
        if (isDynamic) {
            if (timer == null) {
                pageSwitcher(5);
            }
        }

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }

    public void pageSwitcher(int seconds) {
        timer = new Timer(); // At this line a new Thread will be created
        timer.scheduleAtFixedRate(new RemindTask(), 0, seconds * 1000); // delay
        // in
        // milliseconds
    }

    public void stopTimer()
    {
        if(timer!=null)
            timer.cancel();
    }

    // this is an inner class...
    class RemindTask extends TimerTask {

        @Override
        public void run() {

            // As the TimerTask run on a seprate thread from UI thread we have
            // to call runOnUiThread to do work on UI thread.
            activity.runOnUiThread(new Runnable() {
                public void run() {
                    viewPager.setCurrentItem(0);
                }
            });

        }
    }

}

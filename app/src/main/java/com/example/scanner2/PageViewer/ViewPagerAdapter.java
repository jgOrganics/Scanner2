package com.example.scanner2.PageViewer;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.toolbox.ImageLoader;
import com.example.scanner2.R;


import java.util.List;

public class ViewPagerAdapter extends PagerAdapter {
    /* access modifiers changed from: private */
    public Context context;
    private ImageLoader imageLoader;
    private LayoutInflater layoutInflater;
    private List<SliderUtils> sliderImg;

    public ViewPagerAdapter(List sliderImg2, Context context2) {
        this.sliderImg = sliderImg2;
        this.context = context2;
    }

    public int getCount() {
        return this.sliderImg.size();
    }

    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public Object instantiateItem(ViewGroup container, final int position) {
        LayoutInflater layoutInflater2 = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.layoutInflater = layoutInflater2;
        View view = layoutInflater2.inflate(R.layout.custom_layout, (ViewGroup) null);
        this.imageLoader = CustomVolleyRequest.getInstance(this.context).getImageLoader();
        String photo_url = "https://texiri.com/jg/images/" + this.sliderImg.get(position).getSliderImageUrl();
        System.out.println(photo_url);
        imageLoader.get(photo_url, ImageLoader.getImageListener((ImageView) view.findViewById(R.id.imageView), R.mipmap.ic_launcher, 17301543));
        view.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                int i = position;
                if (i == 0) {
                    Toast.makeText(ViewPagerAdapter.this.context, "Slide 1 Clicked", Toast.LENGTH_SHORT).show();
                } else if (i == 1) {
                    Toast.makeText(ViewPagerAdapter.this.context, "Slide 2 Clicked",Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ViewPagerAdapter.this.context, "Slide 3 Clicked",Toast.LENGTH_SHORT).show();
                }
            }
        });
        ((ViewPager) container).addView(view, 0);
        return view;
    }

    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((View) object);
    }
}

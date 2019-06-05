package com.tac.blaze.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.tac.blaze.R;


public class SplashActivity extends AppCompatActivity {

    android.support.v4.view.ViewPager indicator;
    AppCompatButton pixelbutton;
    ExtensiblePageIndicator ind;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        final Animation an = AnimationUtils.loadAnimation(getBaseContext(),R.anim.fadebutton);
        final Animation an1 = AnimationUtils.loadAnimation(getBaseContext(),R.anim.fadefirst);
        pixelbutton=findViewById(R.id.pixelbutton);
        indicator=findViewById(R.id.container);
        ind=findViewById(R.id.flexibleIndicator);
        pixelbutton.startAnimation(an);
        indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int arg0) {

                if (arg0 == 2) {
                    pixelbutton.setVisibility(View.VISIBLE);
                    ind.setVisibility(View.GONE);
                    pixelbutton.startAnimation(an);
                }else{
                    ind.setVisibility(View.VISIBLE);
                    pixelbutton.setVisibility(View.GONE);
                }

            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {

                System.out.println("onPageScrolled");
            }

            @Override
            public void onPageScrollStateChanged(int num) {
            }
        });
        ExtensiblePageIndicator extensiblePageIndicator = findViewById(R.id.flexibleIndicator);
        MainFragmentAdapter mSimpleFragmentAdapter = new MainFragmentAdapter(getSupportFragmentManager());
        mSimpleFragmentAdapter.addFragment(MainFragment.newInstance(R.color.lightGrey, R.drawable.cube,"Trail Blaze","Make the most of every day"));
        mSimpleFragmentAdapter.addFragment(MainFragment.newInstance(R.color.lightGrey, R.drawable.nav,"Easy to route & lovely to look at","Schedule route puts images and maps on your map"));
        mSimpleFragmentAdapter.addFragment(MainFragment.newInstance(R.color.lightGrey, R.drawable.road,"Push Notifications","Real time notifications of your frined's location without any hassels"));
        ViewPager mViewPager = findViewById(R.id.container);
        mViewPager.setAdapter(mSimpleFragmentAdapter);
        extensiblePageIndicator.initViewPager(mViewPager);

        pixelbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SplashActivity.this,CheckInfoActivity.class);
                startActivity(intent);
            }
        });

    }
}
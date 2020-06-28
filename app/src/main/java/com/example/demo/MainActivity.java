package com.example.demo;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.robinhood.ticker.TickerUtils;
import com.robinhood.ticker.TickerView;

public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {
    private static final String TAG="MainActivity";

    private ViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter;
    private TickerView tickerView;
    private TextView textView;
    private int curr=0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        GlobalUtil.getInstance().setContext(getApplicationContext());
        GlobalUtil.getInstance().mainActivity=this;
        getSupportActionBar().setElevation(0);
        tickerView = (TickerView)findViewById(R.id.amount_text);
        tickerView.setCharacterLists(TickerUtils.provideNumberList());
        textView=(TextView) findViewById(R.id.date_text);

        viewPager = (ViewPager) findViewById(R.id.view_pager);
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.notifyDataSetChanged();
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.setOnPageChangeListener(this);
        viewPager.setCurrentItem(viewPagerAdapter.getlastindex());

        findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,AddActivity.class);
                startActivityForResult(intent,1);
            }
        });
        updateHeader();


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("LHY","1111");
        viewPagerAdapter.reload();
        updateHeader();
        Log.d("LHY","2222");
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        curr=position;
        updateHeader();
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
    public void updateHeader(){
        String amount = String.valueOf(viewPagerAdapter.getTotalCost(curr));
        Log.d("LHY","curr "+curr);
        Log.d("LHY",amount);
        tickerView.setText(amount);
        String date = viewPagerAdapter.getDateStr(curr);
        Log.d("LHY",date);
        textView.setText(DateUtil.getWeekDay(date));
    }
}

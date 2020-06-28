package com.example.demo;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.LinkedList;

public class ViewPagerAdapter extends FragmentPagerAdapter {
    LinkedList<MainFragment> fragments=new LinkedList<>();
    LinkedList<String> dates=new LinkedList<>();

    public ViewPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
        initFragments();
    }

    public void initFragments(){
        dates=GlobalUtil.getInstance().databaseHelper.getAvaliableDate();
        if (!dates.contains(DateUtil.getFormattedDate())){
            dates.addLast(DateUtil.getFormattedDate());
        }

        for (String date:dates){
            MainFragment fragment = new MainFragment(date);
            fragments.add(fragment);
        }
    }

    public void reload(){
        for(MainFragment fragment:fragments){
            fragment.reload();
        }
    }

    public int getlastindex(){
        return fragments.size()-1;
    }
    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    public String getDateStr(int index){
        return dates.get(index);
    }

    public int getTotalCost(int index){
        return fragments.get(index).getTotalCost();
    }
}

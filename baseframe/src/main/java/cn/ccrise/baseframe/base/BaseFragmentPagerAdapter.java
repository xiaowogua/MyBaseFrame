package cn.ccrise.baseframe.base;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by wangxl on 2016/8/17.
 */
public class BaseFragmentPagerAdapter extends FragmentPagerAdapter {
    private List<BaseFragment> fragmentList;
    private String[] titles;

    public BaseFragmentPagerAdapter(FragmentManager fm, List<BaseFragment> fragmentList) {
        this(fm, fragmentList, null);
    }
    public BaseFragmentPagerAdapter(FragmentManager fm, List<BaseFragment> fragmentList, String[] titles) {
        super(fm);
        this.fragmentList = fragmentList;
        this.titles = titles;
    }

    @Override
    public BaseFragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList == null ? 0 : fragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles == null || titles.length == 0 ? "" : titles[position % titles.length];
    }
}

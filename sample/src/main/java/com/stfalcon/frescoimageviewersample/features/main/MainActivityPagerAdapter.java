package com.stfalcon.frescoimageviewersample.features.main;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.stfalcon.frescoimageviewersample.R;

/*
 * Created by troy379 on 06.03.17.
 */
class MainActivityPagerAdapter extends FragmentStatePagerAdapter {

    static final int ID_SIMPLE_USAGE = 0;
    static final int ID_STYLED_VIEW = 1;
    static final int ID_ROTATION_SUPPORT = 2;
    static final int ID_CUSTOM_OBJECTS = 3;

    private Context context;

    MainActivityPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        String title = null;
        String description = null;
        switch (position) {
            case MainActivityPagerAdapter.ID_SIMPLE_USAGE:
                title = context.getString(R.string.action_simple_usage);
                description = context.getString(R.string.action_description_simple_usage);
                break;
            case MainActivityPagerAdapter.ID_STYLED_VIEW:
                title = context.getString(R.string.action_styled_view);
                description = context.getString(R.string.action_description_styled_view);
                break;
            case MainActivityPagerAdapter.ID_ROTATION_SUPPORT:
                title = context.getString(R.string.action_rotation_support);
                description = context.getString(R.string.action_description_rotation_support);
                break;
            case MainActivityPagerAdapter.ID_CUSTOM_OBJECTS:
                title = context.getString(R.string.action_custom_objects);
                description = context.getString(R.string.action_description_custom_objects);
                break;
        }
        return DemoCardFragment.newInstance(position, title, description);
    }

    @Override
    public int getCount() {
        return 4;
    }
}

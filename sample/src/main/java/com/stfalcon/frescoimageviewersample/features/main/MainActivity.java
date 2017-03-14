package com.stfalcon.frescoimageviewersample.features.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.stfalcon.frescoimageviewersample.R;
import com.stfalcon.frescoimageviewersample.features.demo.DemoActivity;
import com.stfalcon.frescoimageviewersample.features.demo.formatter.CustomObjectsActivity;
import com.stfalcon.frescoimageviewersample.features.demo.rotation.DialogRotationExampleActivity;
import com.stfalcon.frescoimageviewersample.features.demo.simple.SimpleUsageActivity;
import com.stfalcon.frescoimageviewersample.features.demo.styled.StyledViewActivity;

import me.relex.circleindicator.CircleIndicator;

/*
 * Created by Alexander Krol (troy379) on 29.08.16.
 */
public class MainActivity extends DemoActivity
        implements DemoCardFragment.OnActionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

        ViewPager pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(new MainActivityPagerAdapter(this, getSupportFragmentManager()));
        pager.setPageMargin((int) getResources().getDimension(R.dimen.card_padding) / 4);
        pager.setOffscreenPageLimit(3);

        CircleIndicator indicator = (CircleIndicator) findViewById(R.id.indicator);
        indicator.setViewPager(pager);
    }

    @Override
    public void onAction(int id) {
        Class activity = null;
        switch (id) {
            case MainActivityPagerAdapter.ID_SIMPLE_USAGE:
                activity = SimpleUsageActivity.class;
                break;
            case MainActivityPagerAdapter.ID_STYLED_VIEW:
                activity = StyledViewActivity.class;
                break;
            case MainActivityPagerAdapter.ID_ROTATION_SUPPORT:
                activity = DialogRotationExampleActivity.class;
                break;
            case MainActivityPagerAdapter.ID_CUSTOM_OBJECTS:
                activity = CustomObjectsActivity.class;
                break;
        }
        startActivity(new Intent(this, activity));
    }
}

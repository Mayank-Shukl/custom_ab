
package easyapps.ms.com.config_annotation.ui;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import easyapps.ms.com.config_annotation.R;
import easyapps.ms.com.config_annotation.model.AbModel;


public class AbFragmentAdapter extends FragmentPagerAdapter {

    private final List<String> lobs;
    private final FragmentManager fm;

    public AbFragmentAdapter(FragmentManager manager, Map<String, List<AbModel>> abDataMap) {
        super(manager);
        fm = manager;
        lobs = new ArrayList<>(abDataMap.keySet());
    }

    @Override
    public Fragment getItem(int position) {
        AbFragment abFragment = getFragmentByPosition(position);
        if( abFragment == null){
            abFragment = AbFragment.getInstance(lobs.get(position));
        }
        return abFragment;
    }

    @Override
    public int getCount() {
        return lobs.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return lobs.get(position);
    }

    public AbFragment getFragmentByPosition(int position){
        return (AbFragment) fm.findFragmentByTag("android:switcher:" + R.id.viewpager_ab + ":" +position);
    }
}

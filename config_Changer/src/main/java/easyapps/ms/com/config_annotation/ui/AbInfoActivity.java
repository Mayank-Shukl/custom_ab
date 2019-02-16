
package easyapps.ms.com.config_annotation.ui;

/*How to add new variables
 *
 * 1 if return type for apptimize function is boolean then add the key in keys set 1
 * otherwise in keys set 2 for strings, key set 3 for integers, keys set 4 for lists,
 * keys set 5 for HashSets and keys set 6 for long
 * 2 in get value function return the value from apptimize
 *
 * and that is it */

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import java.util.List;
import java.util.Map;

import easyapps.ms.com.config_annotation.R;
import easyapps.ms.com.config_annotation.databinding.ActivtyAbInfoBinding;
import easyapps.ms.com.config_annotation.model.AbModel;
import easyapps.ms.com.config_annotation.ui.viewModel.SharedViewModel;

public class AbInfoActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ActivtyAbInfoBinding viewDataBinding;
    private AbFragmentAdapter adapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewDataBinding = DataBindingUtil.setContentView(this, R.layout.activty_ab_info);
        SharedViewModel viewModel = ViewModelProviders.of(this).get(SharedViewModel.class);
        viewDataBinding.setModel(viewModel);
        init();
        observeDataChange(viewModel);
    }


    private void observeDataChange(SharedViewModel viewModel) {
        Object config = getIntent().getParcelableExtra("CONFIG");
        if(config==null){
            return;
        }
        viewModel.getAbData(config).observe(this, lobListMap -> {
            if (lobListMap != null) {
                setupViewPager(viewPager, lobListMap);
                tabLayout.setupWithViewPager(viewPager);
                viewDataBinding.getRoot().findViewById(R.id.progress).setVisibility(View.GONE);
            }
        });

        viewModel.getToastString().observe(this, toastString -> {
            if (toastString == null) {
                return;
            }
            Toast.makeText(AbInfoActivity.this, toastString, Toast.LENGTH_SHORT).show();
            finish();
        });
    }

    private void init() {
        viewPager = viewDataBinding.getRoot().findViewById(R.id.viewpager_ab);
        tabLayout = viewDataBinding.getRoot().findViewById(R.id.tabs);
    }


    private void setupViewPager(ViewPager viewPager, Map<String, List<AbModel>> abDataMap) {
        adapter = new AbFragmentAdapter(getSupportFragmentManager(), abDataMap);
        viewPager.setAdapter(adapter);
    }

}

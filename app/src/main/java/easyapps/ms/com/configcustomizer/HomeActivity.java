package easyapps.ms.com.configcustomizer;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.easyapps.AbManager;
import com.easyapps.Navigator;

import easyapps.ms.com.config_annotation.ui.AbInfoActivity;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            Navigator.launchCustomizer(HomeActivity.this);
        });
        Toast.makeText(this,"value:"+ABManagerImpl.getInstance().getTextFlag(),Toast.LENGTH_SHORT).show();
    }


    @Override
    protected void onResume() {
        super.onResume();
        Toast.makeText(this,"value:"+ABManagerImpl.getInstance().getTextFlag(),Toast.LENGTH_SHORT).show();
    }
}

package com.yasar.collegeproject;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
Toolbar toolbar;
BottomNavigationView navigation;
    com.yasar.collegeproject.home home;
    LecturesFragment lectures;
    StudyFragment study;
    UserFragment user;
    Fragment fragment;
    CardView pyq_button;
    CardView lectures_button;
    CardView notice_button;
    CardView tt_button;
    CardView calendar_button;
    ChatFragment Chat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        home=new home();
        study=new StudyFragment();
        lectures=new LecturesFragment();
        user=new UserFragment();
        Chat=new ChatFragment();
        toolbar=findViewById(R.id.toolbar);
        navigation=findViewById(R.id.navigationbar);
        toolbar.setTitle("StuDeck");
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        toolbar.setTitleMarginStart(150);
        toolbar.setTitleMarginTop(45);




        getSupportFragmentManager().beginTransaction().replace(R.id.framelayout,new ChatFragment())
                        .addToBackStack(null)
                                .commit();
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                if (id == R.id.aibuddy) {
                    setFragment(Chat);

                    return true;
                } else if (id == R.id.study) {
                    setFragment(study);

                    return true;
                }  else if (id == R.id.user) {
                    setFragment(user);

                    return true;
                }
                return false;
            }

        });

//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//
//        });

    }
    public void setFragment(Fragment fragment){
    FragmentTransaction frag_trans = getSupportFragmentManager().beginTransaction();
    frag_trans.replace(R.id.framelayout, fragment);
    frag_trans.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
    frag_trans.commit();
}

    @Override
    public void onClick(View view) {

    }

}
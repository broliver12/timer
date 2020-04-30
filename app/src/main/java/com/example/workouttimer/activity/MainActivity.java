package com.example.workouttimer.activity;

import android.os.Bundle;

import com.example.workouttimer.R;
import com.example.workouttimer.fragment.TimerScreenFragmentInterface;
import com.example.workouttimer.viewmodel.CreateNewTimerViewModelInterface;
import com.example.workouttimer.viewmodel.HomeScreenViewModelInterface;
import com.example.workouttimer.viewmodel.TimerScreenViewModel;
import com.example.workouttimer.viewmodel.TimerScreenViewModelInterface;
import com.example.workouttimer.viewmodel.ViewModel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    private ViewModel viewModel ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModel();
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);

    }

    public ViewModel getViewModel(){
        return viewModel;
    }

    public TimerScreenViewModel getTimerScreenViewModel(){
        return viewModel.getTimerScreenViewModel();
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
}

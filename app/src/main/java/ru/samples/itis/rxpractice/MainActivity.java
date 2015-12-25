package ru.samples.itis.rxpractice;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import ru.samples.itis.rxpractice.samples.CombiningObservables;
import ru.samples.itis.rxpractice.samples.CreatingObservable;
import ru.samples.itis.rxpractice.samples.FilteringObservables;
import ru.samples.itis.rxpractice.samples.HelloWorld;
import ru.samples.itis.rxpractice.samples.Operators;
import ru.samples.itis.rxpractice.samples.TransformingObservables;

public class MainActivity extends AppCompatActivity {

    private static String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

}



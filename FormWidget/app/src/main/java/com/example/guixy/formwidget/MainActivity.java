package com.example.guixy.formwidget;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.guixy.formwidget.datePicker.DatePicker;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.form_widget)
    FormWidget mFormWidget;

    private Unbinder mUnbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mUnbinder = ButterKnife.bind(this);
        mFormWidget.setDatePicker(this, DatePicker.CARD_EXPIRY_TYPE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mFormWidget.setContent("36042619940709");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mUnbinder != null) {
            mUnbinder.unbind();
        }

    }
}

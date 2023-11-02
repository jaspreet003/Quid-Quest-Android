package com.androidclass2023.quidquest;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.LayoutTransition;
import android.os.Bundle;
import android.text.Layout;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.View;
import android.widget.LinearLayout;

public class Expense_Screen extends AppCompatActivity {

    LinearLayout  btnLayout, mLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense_screen);
        initialize();
    }

    private void initialize() {
        btnLayout = findViewById(R.id.btnLayout);
        mLayout = findViewById(R.id.mLayout);
        mLayout.getLayoutTransition().enableTransitionType(LayoutTransition.CHANGING);
    }

    public void expand(View view) {
        int v = (btnLayout.getVisibility() == View.GONE)? View.VISIBLE: View.GONE;

        TransitionManager.beginDelayedTransition(mLayout,new AutoTransition());
        btnLayout.setVisibility(v);
    }
}
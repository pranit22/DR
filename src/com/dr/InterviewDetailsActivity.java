package com.dr;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class InterviewDetailsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interview_details);

        startActivity(new Intent(this, EditCreateJobActivity.class));
    }

}

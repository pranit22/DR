package com.dr;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.dr.helpers.Utilities;
import com.dr.objects.Interview;

public class InterviewDetailsActivity extends Activity {

    Interview interview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interview_details);

        Intent intent = getIntent();
        interview = (Interview) intent.getSerializableExtra("interview");
        ((TextView) findViewById(R.id.date)).setText(Utilities.printDate(interview.getTime()));
        ((TextView) findViewById(R.id.time)).setText(Utilities.printTime(interview.getTime()));
        ((TextView)findViewById(R.id.interviewer)).setText(interview.getInterviewer());
        ((TextView)findViewById(R.id.location)).setText(interview.getLocation());
        String reminder = Utilities.printDateTime(interview.getReminder());
        ((TextView)findViewById(R.id.reminder)).setText(reminder);
    }

}

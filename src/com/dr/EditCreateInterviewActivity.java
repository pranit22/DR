package com.dr;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.dr.helpers.Utilities;
import com.dr.objects.Interview;
import com.dr.objects.dao.InterviewDAO;

import java.util.Calendar;

public class EditCreateInterviewActivity extends Activity {

    Interview interview;
    private InterviewDAO interviewDAO;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_create_interview);

        interviewDAO = new InterviewDAO(this);
        interviewDAO.open();

        Intent intent = getIntent();
        interview = (Interview) intent.getSerializableExtra("interview");
        Calendar time = interview.getTime();
        if (time != null) {
            DatePicker datePicker = (DatePicker) findViewById(R.id.date);
            TimePicker timePicker = (TimePicker) findViewById(R.id.time);
            Utilities.setDateTimePickerFromCalendar(datePicker, timePicker, time);
        }
        ((EditText) findViewById(R.id.location)).setText(interview.getLocation());
        ((EditText) findViewById(R.id.interviewer)).setText(interview.getInterviewer());

        Calendar reminder = interview.getReminder();
        if (reminder != null) {
            ((TextView) findViewById(R.id.reminder)).setText(Utilities.printDateTime(interview.getReminder()));
        }
    }

    public void navigateToAddEditReminder(View view) {
        updateInterviewFromForm();
        Intent intent = new Intent(this, EditCreateReminderActivity.class);
        intent.putExtra("interview", interview);
        startActivity(intent);
    }

    public void submitCreateNewInterview(View view) {
        updateInterviewFromForm();
        if (interview.getId() == 0) {
            interview = interviewDAO.addInterview(interview);
        } else {
            interview = interviewDAO.updateInterview(interview);
        }
        Intent intent = new Intent(this, InterviewDetailsActivity.class);
        intent.putExtra("landingActivity", InterviewsListActivity.class);
        intent.putExtra("interview", interview);
        startActivity(intent);
    }

    public void navigateToListInterviews(View view) {
        finish();
    }

    private void updateInterviewFromForm() {
        DatePicker datePicker = (DatePicker) findViewById(R.id.date);
        TimePicker timePicker = (TimePicker) findViewById(R.id.time);
        interview.setTime(Utilities.getCalendarFromDateTimePicker(datePicker, timePicker));
        interview.setInterviewer(((EditText) findViewById(R.id.interviewer)).getText().toString());
        interview.setLocation(((EditText) findViewById(R.id.location)).getText().toString());
    }

}

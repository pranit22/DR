package com.dr;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.dr.helpers.Utilities;
import com.dr.objects.Interview;
import com.dr.objects.JobApplication;
import com.dr.objects.dao.InterviewDAO;
import com.dr.objects.dao.JobApplicationDAO;

import java.util.Calendar;

public class EditCreateInterviewActivity extends Activity {

    Interview interview;
    private InterviewDAO interviewDAO;
private JobApplicationDAO jobApplicationDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_create_interview);

        interviewDAO = new InterviewDAO(this);
        interviewDAO.open();
        jobApplicationDAO = new JobApplicationDAO(this);
        jobApplicationDAO.open();

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

        if(interview.getReminder() != null) {
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

            Intent intent = new Intent(this, NotificationService.class);
            intent.putExtra("message", "Interview with "
                    +getJobApplicationFromInterview(interview).getJob().getCompany()
                    +" at " + Utilities.getStringFromCalendar(interview.getTime()));
            intent.putExtra("interview", interview);
            PendingIntent pendingIntent = PendingIntent.getService(this, 0, intent, 0);

            alarmManager.set(AlarmManager.RTC_WAKEUP, interview.getReminder().getTimeInMillis(), pendingIntent);
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

    private JobApplication getJobApplicationFromInterview(Interview interview) {
        return jobApplicationDAO.getJobApplicationByJobId(interview.getJobId());
    }

}

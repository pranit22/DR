package com.dr;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.dr.helpers.Utilities;
import com.dr.objects.Interview;

import java.util.Calendar;

public class EditCreateReminderActivity extends Activity {

    Interview interview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_create_reminder);

        Intent intent = getIntent();
        interview = (Interview) intent.getSerializableExtra("interview");
        if (interview == null) {
            interview = new Interview();
        }
        if (interview != null && interview.getReminder() != null) {
            Calendar calendar = interview.getReminder();
            DatePicker datePicker = (DatePicker) findViewById(R.id.date);
            TimePicker timePicker = (TimePicker) findViewById(R.id.time);
            Utilities.setDateTimePickerFromCalendar(datePicker, timePicker, calendar);
        }
    }

    public void navigateToEditCreateInterviewActivity(View view) {
        finish();
    }

    public void submitAddEditReminder(View view) {
        Intent intent = new Intent(this, EditCreateInterviewActivity.class);
        interview.setReminder(Utilities.getCalendarFromDateTimePicker((DatePicker) findViewById(R.id.date), (TimePicker) findViewById(R.id.time)));
        intent.putExtra("interview", interview);
        startActivity(intent);
    }

}

package com.dr;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.dr.helpers.Utilities;
import com.dr.objects.Interview;
import com.dr.objects.JobApplication;
import com.dr.objects.dao.InterviewDAO;

import java.util.ArrayList;
import java.util.List;

public class InterviewsListActivity extends Activity {

    JobApplication jobApplication;
    List<Interview> interviews;
    InterviewDAO interviewDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interviews_list);

        /*
        Intent intent = new Intent(this, NotificationService.class);
        PendingIntent pendingIntent = PendingIntent.getService(this, 0, intent, 0);

        AlarmManager manager=(AlarmManager)getSystemService(Activity.ALARM_SERVICE);

        Calendar cal=Calendar.getInstance();
        cal.add(Calendar.SECOND, 5);
        manager.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pendingIntent);

        Toast.makeText(this, "Start Alarm", Toast.LENGTH_SHORT).show();
        */

        Intent intent = getIntent();
        jobApplication = (JobApplication) intent.getSerializableExtra("jobApplication");

        interviewDAO = new InterviewDAO(this);
        interviewDAO.open();
        interviews = interviewDAO.getAllInterviewsByJobId(jobApplication.getJob().getJobId());

        ListView listView = (ListView) findViewById(R.id.listView);

    }

    public void navigateToNewInterview(View view) {
        Intent intent = new Intent(this, EditCreateInterviewActivity.class);
        Interview interview = new Interview();
        interview.setJobId(jobApplication.getJob().getJobId());
        intent.putExtra("interview", interview);
        startActivity(intent);
    }

    class InterviewListAdapter extends ArrayAdapter<Interview> {

        private ArrayList<Interview> items;

        public InterviewListAdapter(Context context, int textViewResourceId, ArrayList<Interview> items) {
            super(context, textViewResourceId, items);
            this.items = items;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;
            if (v == null) {
                LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = vi.inflate(R.layout.layout_job_application, null);
            }
            final Interview o = items.get(position);
            if (o != null) {
                TextView company = (TextView) v.findViewById(R.id.company);
                TextView location = (TextView) v.findViewById(R.id.location);
                TextView time = (TextView) v.findViewById(R.id.time);
                if (company != null) {
                    company.setText(jobApplication.getJob().getCompany());
                }
                if (location != null) {
                    location.setText(o.getLocation());
                }
                if (time != null) {
                    time.setText(Utilities.printDateTime(o.getTime()));
                }
                final Context context = getApplicationContext();
                v.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, InterviewDetailsActivity.class);
                        intent.putExtra("interview", o);
                        startActivity(intent);
                    }
                });
            }
            return v;
        }
    }

}
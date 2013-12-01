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
import com.dr.objects.dao.JobApplicationDAO;

import java.util.ArrayList;
import java.util.List;

public class RemindersListActivity extends Activity {
    InterviewDAO interviewDAO;
    JobApplicationDAO jobApplicationDAO;
    List<Interview> interviews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminders_list);

        interviewDAO = new InterviewDAO(this);
        interviewDAO.open();
        jobApplicationDAO = new JobApplicationDAO(this);
        jobApplicationDAO.open();

        interviews = interviewDAO.getAllInterviews();
        for(Interview interview: interviews) {
            if(interview.getReminder() == null) {
                interviews.remove(interview);
            }
        }

        ListView listView = (ListView) findViewById(R.id.listView);
        InterviewListAdapter adapter = new InterviewListAdapter(this,
                R.layout.layout_interview,
                (ArrayList<Interview>) interviews);
        listView.setAdapter(adapter);
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
                v = vi.inflate(R.layout.layout_interview, null);
            }
            final Interview o = items.get(position);
            if (o != null) {
                TextView company = (TextView) v.findViewById(R.id.company);
                TextView location = (TextView) v.findViewById(R.id.location);
                TextView time = (TextView) v.findViewById(R.id.time);
                if (company != null) {
                    company.setText(getJobApplicationFromInterview(o).getJob().getCompany());
                }
                if (location != null) {
                    location.setText(o.getLocation());
                }
                if (time != null) {
                    time.setText(Utilities.printDateTime(o.getReminder()));
                }
                final Context context = getApplicationContext();
                v.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, InterviewDetailsActivity.class);
                        intent.putExtra("landingActivity", RemindersListActivity.class);
                        intent.putExtra("allInterviews", true);
                        intent.putExtra("interview", o);
                        startActivity(intent);
                    }
                });
            }
            return v;
        }
    }

    private JobApplication getJobApplicationFromInterview(Interview interview) {
        return jobApplicationDAO.getJobApplicationByJobId(interview.getJobId());
    }

    public void navigateToMainMenu(View view) {
        startActivity(new Intent(this, MainActivity.class));
    }

}

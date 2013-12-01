package com.dr;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.dr.helpers.Utilities;
import com.dr.objects.Interview;
import com.dr.objects.JobApplication;
import com.dr.objects.dao.InterviewDAO;
import com.dr.objects.dao.JobApplicationDAO;

import java.util.ArrayList;
import java.util.List;

public class InterviewsListActivity extends Activity {

    JobApplication jobApplication;
    List<Interview> interviews;
    InterviewDAO interviewDAO;
    JobApplicationDAO jobApplicationDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interviews_list);

        Intent intent = getIntent();

        interviewDAO = new InterviewDAO(this);
        interviewDAO.open();
        jobApplicationDAO = new JobApplicationDAO(this);
        jobApplicationDAO.open();

        if (intent.hasExtra("jobApplication")) {
            jobApplication = (JobApplication) intent.getSerializableExtra("jobApplication");
            interviews = interviewDAO.getAllInterviewsByJobId(jobApplication.getJob().getJobId());
        } else {
            interviews = interviewDAO.getAllInterviews();
            ((Button) findViewById(R.id.backButton)).setText("BACK TO MAIN MENU");
        }

        ListView listView = (ListView) findViewById(R.id.listView);
        InterviewListAdapter adapter = new InterviewListAdapter(this,
                R.layout.layout_interview,
                (ArrayList<Interview>) interviews);
        listView.setAdapter(adapter);

    }

    public void navigateToNewInterview(View view) {
        Intent intent = new Intent(this, EditCreateInterviewActivity.class);
        Interview interview = new Interview();
        interview.setJobId(jobApplication.getJob().getJobId());
        intent.putExtra("interview", interview);
        startActivity(intent);
    }

    public void navigateToJobApplication(View view) {
        if (getIntent().hasExtra("jobApplication")) {
            Intent intent = new Intent(this, JobDetailsActivity.class);
            intent.putExtra("jobApplication", jobApplication);
            startActivity(intent);
        } else {
            startActivity(new Intent(this, MainActivity.class));
        }
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
                    if (jobApplication != null) {
                        company.setText(jobApplication.getJob().getCompany());
                    } else {
                        company.setText(getJobApplicationFromInterview(o).getJob().getCompany());
                    }
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
                        intent.putExtra("landingActivity", InterviewsListActivity.class);
                        intent.putExtra("allInterviews", jobApplication == null);
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

}
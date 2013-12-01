package com.dr;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.dr.objects.JobApplication;
import com.dr.objects.dao.JobApplicationDAO;

import java.util.ArrayList;
import java.util.List;

public class JobApplicationsListActivity extends Activity {

    List<JobApplication> jobs;
    private JobApplicationDAO jobApplicationDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_applications_list);

        jobApplicationDAO = new JobApplicationDAO(this);
        jobApplicationDAO.open();
        jobs = jobApplicationDAO.getAllJobApplications();

        ListView listView = (ListView) findViewById(R.id.listView);

        JobApplicationListAdapter adapter = new JobApplicationListAdapter(this,
                R.layout.layout_job_application,
                (ArrayList<JobApplication>) jobs);

        listView.setAdapter(adapter);
    }

    class JobApplicationListAdapter extends ArrayAdapter<JobApplication> {

        private ArrayList<JobApplication> items;

        public JobApplicationListAdapter(Context context, int textViewResourceId, ArrayList<JobApplication> items) {
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
            final JobApplication o = items.get(position);
            if (o != null) {
                TextView company = (TextView) v.findViewById(R.id.company);
                TextView title = (TextView) v.findViewById(R.id.title);
                TextView status = (TextView) v.findViewById(R.id.status);
                if (company != null) {
                    company.setText(o.getJob().getCompany());
                }
                if (title != null) {
                    title.setText(o.getJob().getTitle());
                }
                if (status != null) {
                    status.setText(o.getStatus().getValue());
                }
                final Context context = getApplicationContext();
                v.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, JobDetailsActivity.class);
                        intent.putExtra("jobApplication", o);
                        startActivity(intent);
                    }
                });
            }
            return v;
        }
    }

    public void navigateToNewApplication(View view) {
        startActivity(new Intent(this, EditCreateJobActivity.class));
    }

    public void navigateToMainMenu(View v) {
        startActivity(new Intent(this, MainActivity.class));
    }
}

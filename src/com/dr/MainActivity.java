package com.dr;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.dr.objects.dao.JobApplicationDAO;

public class MainActivity extends Activity {

    private JobApplicationDAO jobApplicationDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        jobApplicationDAO = new JobApplicationDAO(this);
        jobApplicationDAO.open();
    }

    public void navigateToListApplications(View view) {
        startActivity(new Intent(this, JobApplicationsListActivity.class));
    }

    public void navigateToListInterviews(View view) {
        startActivity(new Intent(this, InterviewsListActivity.class));
    }

    public void navigateToListReminders(View view) {
        startActivity(new Intent(this, RemindersListActivity.class));
    }

    public void navigateToListResumes(View view) {
        Intent intent = new Intent(this, DocumentsListActivity.class);
        intent.putExtra("resume", true);
        startActivity(intent);
    }

    public void navigateToListCoverLetters(View view) {
        startActivity(new Intent(this, DocumentsListActivity.class));
    }

    public void exit(View view) {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}



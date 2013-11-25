package com.dr.objects;

import java.io.Serializable;
import java.util.Calendar;

/**
 * Created by Pranit on 11/23/13.
 */
public class Interview implements Serializable {
    private int id;
    private int jobId;
    private Calendar time;
    private String location;
    private String interviewer;
    private Calendar reminder;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getJobId() {
        return jobId;
    }

    public void setJobId(int jobId) {
        this.jobId = jobId;
    }

    public Calendar getTime() {
        return time;
    }

    public void setTime(Calendar time) {
        this.time = time;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getInterviewer() {
        return interviewer;
    }

    public void setInterviewer(String interviewer) {
        this.interviewer = interviewer;
    }

    public Calendar getReminder() {
        return reminder;
    }

    public void setReminder(Calendar reminder) {
        this.reminder = reminder;
    }
}

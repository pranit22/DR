package com.dr.objects;

import java.io.Serializable;

/**
 * Created by Pranit on 11/18/13.
 */
public class JobApplication implements Serializable{
    Job job;
    Status status;
    int resume;
    int coverLetter;

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public int getResume() {
        return resume;
    }

    public void setResume(int resume) {
        this.resume = resume;
    }

    public int getCoverLetter() {
        return coverLetter;
    }

    public void setCoverLetter(int coverLetter) {
        this.coverLetter = coverLetter;
    }

    @Override
    public String toString() {
        return "JobApplication [job=" + job + ", status=" + status
                + ", resume=" + resume + ", coverLetter=" + coverLetter + "]";
    }


}

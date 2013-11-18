package com.dr.objects;

/**
 * Created by Pranit on 11/18/13.
 */
public class JobApplication {
    Job job;
    Status status;
    Resume resume;
    CoverLetter coverLetter;
    public Job getJob()
    {
        return job;
    }
    public void setJob(Job job)
    {
        this.job = job;
    }
    public Status getStatus()
    {
        return status;
    }
    public void setStatus(Status status)
    {
        this.status = status;
    }
    public Resume getResume()
    {
        return resume;
    }
    public void setResume(Resume resume)
    {
        this.resume = resume;
    }
    public CoverLetter getCoverLetter()
    {
        return coverLetter;
    }
    public void setCoverLetter(CoverLetter coverLetter)
    {
        this.coverLetter = coverLetter;
    }
    
    
}

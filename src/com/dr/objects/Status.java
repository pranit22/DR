package com.dr.objects;

/**
 * Created by Pranit on 11/18/13.
 */
public enum Status {
    APPLIED("Applied"),
    INTERVIEW_SCHEDULED("Interview Scheduled"),
    REJECTED("Rejected"),
    SELECTED("Selected"),
    ACCEPTED_OFFER("Accepted"),
    IGNORED("Ignored");

    private final String value;

    Status(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    public static Status toStatus(String value) {
        for (Status status : Status.values()) {
            if (status.value.equals(value)) return status;
        }

        return null;
    }
}

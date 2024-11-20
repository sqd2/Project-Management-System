package lecturermain;
//TP072764

import java.io.Serializable;

public class PresentationRequest implements Serializable {
    private String superviseeId;
    private String requestDetails;

    public PresentationRequest(String superviseeId, String requestDetails) {
        this.superviseeId = superviseeId;
        this.requestDetails = requestDetails;
    }

    public String getSuperviseeId() {
        return superviseeId;
    }

    public String getRequestDetails() {
        return requestDetails;
    }

    @Override
    public String toString() {
        return "Supervisee ID: " + superviseeId + ", Request Details: " + requestDetails;
    }
}

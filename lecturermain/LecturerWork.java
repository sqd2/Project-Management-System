package lecturermain;
//TP072764

import entities.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class LecturerWork extends Lecturer implements Serializable {

    private String lecturerID;
    private String name;
    private String email;
    private Department department;
    private LecturerRole lecturerRole;
    private List<Supervisee> supervisees;
    private List<PresentationRequest> presentationRequests;
    private List<PresentationSlot> availableSlots;
    private List<String> feedbacks;

    public LecturerWork(String ID, String name, String password, String email, Department department,
    LecturerRole lecturerRole) {
super(ID, name, password, email, department, lecturerRole);
        this.department = department;
        this.lecturerRole = lecturerRole;
        this.supervisees = new ArrayList<>();
        this.presentationRequests = new ArrayList<>();
        this.availableSlots = new ArrayList<>();
        this.feedbacks = new ArrayList<>();
    }

    public String getLecturerID() {
        return lecturerID;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public Department getDepartment() {
        return department;
    }

    public LecturerRole getLecturerRole() {
        return lecturerRole;
    }

    public List<Supervisee> getSupervisees() {
        return supervisees;
    }

    public List<PresentationRequest> getPresentationRequests() {
        return presentationRequests;
    }

    public List<PresentationSlot> getAvailableSlots() {
        return availableSlots;
    }

    public void addSupervisee(Supervisee supervisee) {
        supervisees.add(supervisee);
    }

    public void addPresentationRequest(PresentationRequest request) {
        presentationRequests.add(request);
    }

    public void addPresentationSlot(PresentationSlot slot) {
        availableSlots.add(slot);
    }

    public void addFeedback(String feedback) {
        feedbacks.add(feedback);
    }

    public void setPresentationDateAndSlot(String superviseeId, String date, String slot) {
        for (Supervisee s : supervisees) {
            if (s.getId().equals(superviseeId)) {
                availableSlots.add(new PresentationSlot(date, slot));
                break;
            }
        }
    }

    public void evaluateReport(String superviseeId, String feedback) {
        for (Supervisee s : supervisees) {
            if (s.getId().equals(superviseeId)) {
                addFeedback(feedback);
                break;
            }
        }
    }

    public List<Supervisee> getSuperviseesByStatus(String status) {
        List<Supervisee> result = new ArrayList<>();
        for (Supervisee s : supervisees) {
            if (s.getStatus().equalsIgnoreCase(status)) {
                result.add(s);
            }
        }
        return result;
    }

    @Override
    public String toString() {
        return "Lecturer ID: " + lecturerID + ", Name: " + name + ", Email: " + email + ", Department: " + department + ", Role: " + lecturerRole;
    }
}

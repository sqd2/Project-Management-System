package lecturermain;
//TP072764

import java.io.Serializable;

public class Supervisee implements Serializable {
    private String id;
    private String name;
    private String intake;
    private String assessmentType;
    private String status;

    public Supervisee(String id, String name, String intake, String assessmentType, String status) {
        this.id = id;
        this.name = name;
        this.intake = intake;
        this.assessmentType = assessmentType;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getIntake() {
        return intake;
    }

    public String getAssessmentType() {
        return assessmentType;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "ID: " + id + ", Name: " + name + ", Intake: " + intake + ", Assessment Type: " + assessmentType + ", Status: " + status;
    }
}

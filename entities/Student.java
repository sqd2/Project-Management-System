package entities;
//SALEM AHMED ABDULLAH BA SUHAI - TP073526

public class Student extends User {

    private Program program;
    private Intake intake;

    public Student(String ID, String name, String password, String email, Program program, Intake intake) {
        super(ID, name, password, email);
        this.program = program;
        this.intake = intake;
    }

    // Getters and setters
    public Program getProgram() { return program; }
    public Intake getIntake() { return intake; }

    public void setProgram(Program program) { this.program = program; }
    public void setIntake(Intake intake) { this.intake = intake; }

    // Method to get StudentID, using the getID method from User class
    public String getStudentID() {
        return getID();
    }

    
}

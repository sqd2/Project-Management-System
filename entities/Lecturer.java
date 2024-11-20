package entities;
//SALEM AHMED ABDULLAH BA SUHAI - TP073526

public class Lecturer extends User {

    private Department department;
    private LecturerRole lecturerRole;

    public Lecturer(String ID, String name, String password, String email, Department department, LecturerRole lecturerRole) {
        super(ID, name, password, email);
        this.department = department;
        this.lecturerRole = lecturerRole;
    }

    // Getters and setters
    public Department getDepartment() { return department; }
    public LecturerRole getLecturerRole() { return lecturerRole; }
    public String getLecturerID() { return getID(); }
    public void setDepartment(Department department) { this.department = department; }
    public void setLecturerRole(LecturerRole lecturerRole) { this.lecturerRole = lecturerRole; }
    public void setRole(LecturerRole role) { this.lecturerRole = role; }

    @Override
public String toString() {
    return "Lecturer ID: " + getID() + ", Name: " + getName() + ", Department: " + department;
 }
}


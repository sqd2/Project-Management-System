package entities;
//SALEM AHMED ABDULLAH BA SUHAI - TP073526

public enum Department {
    IT("IT"),
    Business("Business"),
    Engineering("Engineering"),
    Law("Law"),
    Psychology("Psychology");

    private final String displayDepartment;

    Department(String displayDepartment) {
        this.displayDepartment = displayDepartment;
    }
    @Override
    public String toString() {
        return displayDepartment;
    }

public static Department fromString(String displayName) {
    for (Department dept : Department.values()) {
        if (dept.displayDepartment.equalsIgnoreCase(displayName)) {
            return dept;
        }
    }
    return null; //returns null if we didnt find a match
}
}
package entities;
//SALEM AHMED ABDULLAH BA SUHAI - TP073526

public enum LecturerRole {
    LECTURER, PROJECT_MANAGER;

    public static LecturerRole fromString(String role) {
        for (LecturerRole r : LecturerRole.values()) {
            if (r.name().equalsIgnoreCase(role)) {
                return r;
            }
        }
        throw new IllegalArgumentException("No constant with text " + role + " found");
    }
}

package entities;
//SALEM AHMED ABDULLAH BA SUHAI - TP073526

public enum Intake {
    AUG2024("AUG 2024"),
    NOV2024("NOV 2024"),
    FEB2025("FEB 2025"),
    MAY2024("MAY 2024"), // Add this line if needed
    MAY2025("MAY 2025");

    private final String displayIntake;

    Intake(String displayIntake) {
        this.displayIntake = displayIntake;
    }

    @Override
    public String toString() {
        return displayIntake;
    }

    public static Intake fromString(String displayIntake) {
        for (Intake intake : Intake.values()) {
            if (intake.displayIntake.equalsIgnoreCase(displayIntake) || intake.name().equalsIgnoreCase(displayIntake)) {
                return intake;
            }
        }
        return null; // Return null if no match is found
    }
}

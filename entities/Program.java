package entities;
//SALEM AHMED ABDULLAH BA SUHAI - TP073526
public enum Program {
    COMPUTER_SCIENCE("Computer Science"),
    INFORMATION_TECHNOLOGY("Information Technology"),
    FINANCE("Finance"),
    ELECTRICAL_ENGINEERING("Electrical Engineering"),
    LAW("Law"),
    PSYCHOLOGY("Psychology");

    private final String displayProgram;

    Program(String displayProgram) {
        this.displayProgram = displayProgram;
    }

    @Override
    public String toString() {
        return displayProgram;
    }

    public static Program fromString(String displayProgram) {
        for (Program program : Program.values()) {
            if (program.displayProgram.equalsIgnoreCase(displayProgram)) {
                return program;
            }
        }
        throw new IllegalArgumentException("No enum constant " + Program.class.getCanonicalName() + "." + displayProgram);
    }
}

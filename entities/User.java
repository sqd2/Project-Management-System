package entities;
//SALEM AHMED ABDULLAH BA SUHAI - TP073526

public abstract class User {
    protected String ID;
    protected String name;
    protected String password; 
    protected String email;

    public User(String ID, String name, String password, String email) {
        this.ID = ID;
        this.name = name;
        this.password = password;
        this.email = email;
    }
    // Getters
    public String getID() { return ID; }
    public String getName() { return name; }
    public String getPassword() { return password; }
    public String getEmail() { return email; }
    // Setters
    public void setID(String ID) {this.ID = ID;}
    public void setName(String name) {this.name = name;}
    public void setPassword(String password) {this.password = password;}
    public void setEmail(String email) {this.email = email;}

    @Override
    public String toString() {
        return "ID: " + ID + ", Name: " + name;
    }
}

package sg.edu.nus.iss.springbay.models;

import javax.validation.constraints.NotNull;

public class User {

    private String userId;

    @NotNull(message="Username is a required field")
    private String username;

    @NotNull(message="Password is a required field") 
    private String password;

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password;}

    public User(){
    }

    public User(String userId, @NotNull(message = "Username is a required field") String username,
            @NotNull(message = "Password is a required field") String password) {
        this.userId = userId;
        this.username = username;
        this.password = password;
    }

    @Override
    public String toString() {
        return "User [userId=" + userId + ", username=" + username + ", password=" + password + "]";
    }

    
    
}

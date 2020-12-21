package toko;

public class User {
    private String username;
    private String password;
    private String last_login;
    private String email;

    public User(String username, String password, String last_login, String email) {
        this.username = username;
        this.password = password;
        this.last_login = last_login;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLast_login() {
        return last_login;
    }

    public void setLast_login(String last_login) {
        this.last_login = last_login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
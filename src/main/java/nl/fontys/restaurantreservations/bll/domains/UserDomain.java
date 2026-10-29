package nl.fontys.restaurantreservations.bll.domains;

import nl.fontys.restaurantreservations.bll.enums.UserRole;

public class UserDomain {

    private final Long id;
    private String email;
    private String name;
    private String password;
    private UserRole role;

    public UserDomain(Long id, String email, String name, String password, UserRole role) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.password = password;
        this.role = role;
    }

    public UserDomain(String email, String name, String password, UserRole role) {
        this.id = null;
        this.email = email;
        this.name = name;
        this.password = password;
        this.role = role;
    }

    //Getters
    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public UserRole getRole() {
        return role;
    }

    //Setters
    public void setEmail(String email) {
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }
}

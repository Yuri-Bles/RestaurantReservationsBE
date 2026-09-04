package nl.fontys.restaurantreservations.models;

import jakarta.persistence.*;

@Entity
@Table(name="users")
public class UserModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private String name;
    private String password;
    private Integer role;
}
package nl.fontys.restaurantreservations.dal.entities;

import jakarta.persistence.*;
import nl.fontys.restaurantreservations.bll.enums.UserRole;

@Entity
@Table(name = "tables")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String email;

    private String name;

    private String password;

    @Enumerated(EnumType.STRING)
    private UserRole role;
}

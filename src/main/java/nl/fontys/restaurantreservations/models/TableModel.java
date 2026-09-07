package nl.fontys.restaurantreservations.models;

import jakarta.persistence.*;

@Entity
@Table(name="tables")
public class TableModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(name = "table_number")
    public String tableNumber;

    public Integer capacity;

    public String status;
}
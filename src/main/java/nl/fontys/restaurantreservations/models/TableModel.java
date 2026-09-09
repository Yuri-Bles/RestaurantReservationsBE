package nl.fontys.restaurantreservations.models;

import jakarta.persistence.*;
import nl.fontys.restaurantreservations.enums.TableStatus;

@Entity
@Table(name="tables")
public class TableModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(name = "table_number")
    public String tableNumber;

    public Integer capacity;

    @Enumerated(EnumType.STRING)
    public TableStatus status;

    public TableModel(String tableNumber, Integer capacity, String status)
    {
        this.tableNumber = tableNumber;
        this.capacity = capacity;
        this.status = TableStatus.valueOf(status);
    }

    public TableModel(String tableNumber, Integer capacity, TableStatus status)
    {
        this.tableNumber = tableNumber;
        this.capacity = capacity;
        this.status = status;
    }
}
package nl.fontys.restaurantreservations.models;

import jakarta.persistence.*;
import nl.fontys.restaurantreservations.enums.TableStatus;

@Entity
@Table(name="tables")
public class TableModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "table_number")
    private String tableNumber;

    private Integer capacity;

    @Enumerated(EnumType.STRING)
    private TableStatus status;

    protected TableModel() {
    }

    public TableModel(String tableNumber, Integer capacity, TableStatus status)
    {
        this.tableNumber = tableNumber;
        this.capacity = capacity;
        this.status = status;
    }

    public TableModel(String tableNumber, Integer capacity, String status)
    {
        this.tableNumber = tableNumber;
        this.capacity = capacity;
        this.status = TableStatus.valueOf(status);
    }

    //Getters
    public Long getId() {
        return id;
    }

    public String getTableNumber() {
        return tableNumber;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public TableStatus getStatus() {
        return status;
    }

    //Setters
    public void setTableNumber(String tableNumber) {
        this.tableNumber = tableNumber;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public void setStatus(TableStatus status) {
        this.status = status;
    }
}
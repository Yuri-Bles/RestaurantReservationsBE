package nl.fontys.restaurantreservations.bll.domains;

import nl.fontys.restaurantreservations.bll.enums.TableStatus;

public class TableDomain {

    private final Long id;

    private String tableNumber;
    private Integer capacity;
    private TableStatus status;

    public TableDomain(Long id, String tableNumber, Integer capacity, TableStatus status) {
        this.id = id;
        this.tableNumber = tableNumber;
        this.capacity = capacity;
        this.status = status;
    }

    public TableDomain(String tableNumber, Integer capacity, TableStatus status) {
        this.id = null;
        this.tableNumber = tableNumber;
        this.capacity = capacity;
        this.status = status;
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
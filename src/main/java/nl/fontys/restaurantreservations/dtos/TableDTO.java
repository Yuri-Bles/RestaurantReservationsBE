package nl.fontys.restaurantreservations.dtos;

import nl.fontys.restaurantreservations.enums.TableStatus;
import nl.fontys.restaurantreservations.models.TableModel;

public class TableDTO
{
    public Long id;
    public String tableNumber;
    public Integer capacity;
    public TableStatus status;

    public TableDTO(TableModel model)
    {
        this.id = model.id;
        this.tableNumber = model.tableNumber;
        this.capacity = model.capacity;
        this.status = TableStatus.valueOf(model.status);
    }
}

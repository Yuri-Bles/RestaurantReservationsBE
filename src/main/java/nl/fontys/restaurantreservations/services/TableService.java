package nl.fontys.restaurantreservations.services;

import nl.fontys.restaurantreservations.dtos.*;
import nl.fontys.restaurantreservations.interfaces.ITableRepository;

import java.util.*;

public class TableService
{
    private ITableRepository repo;

    public TableService(ITableRepository repo)
    {
        this.repo = repo;
    }

    public List<TableDTO> getAllActiveTables()
    {
        return repo.getAllActiveTables();
    }
}

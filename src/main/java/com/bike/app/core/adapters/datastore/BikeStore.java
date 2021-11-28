package com.bike.app.core.adapters.datastore;

import com.bike.app.core.Bike;

import java.util.List;

public interface BikeStore {

    List<Bike> findAll();
}

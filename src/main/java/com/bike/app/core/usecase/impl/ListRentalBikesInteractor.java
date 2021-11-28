package com.bike.app.core.usecase.impl;

import com.bike.app.core.Bike;
import com.bike.app.core.adapters.datastore.BikeStore;
import com.bike.app.core.usecase.ListRentalBikes;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
@Singleton
public class ListRentalBikesInteractor implements ListRentalBikes {

    private final BikeStore bikeStore;

    @Override
    public List<Bike> perform() {
        return bikeStore.findAll();
    }
}

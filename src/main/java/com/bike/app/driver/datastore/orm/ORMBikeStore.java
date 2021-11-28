package com.bike.app.driver.datastore.orm;

import com.bike.app.core.Bike;
import com.bike.app.core.adapters.datastore.BikeStore;
import com.bike.app.driver.datastore.FeatureORMPersistence;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Singleton
@FeatureORMPersistence
public class ORMBikeStore implements BikeStore {

    @Inject
    EntityManager em;

    @Override
    public List<Bike> findAll() {
        var allBikes = em.createQuery("SELECT b FROM ORMBike b", ORMBike.class)
                .getResultStream();

        return allBikes.map(ORMBike::convert).collect(Collectors.toList());
    }
}

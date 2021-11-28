package com.bike.app.driver.datastore.orm;

import com.bike.app.core.Bike;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "bikes")
public class ORMBike {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "bike_id", updatable = false, nullable = false)
    String id;

    @Column(name = "label")
    String label;

    public Bike convert() {
        return new Bike()
                .withId(id)
                .withLabel(label);
    }
}

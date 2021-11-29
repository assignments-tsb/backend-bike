package com.bike.app.test

import com.bike.app.driver.datastore.orm.ORMBike

trait BikePreparer {

    void prepareTestBikes() {
        em.persist(createBike("BX-1001"))
        em.persist(createBike("BX-1002"))
        em.persist(createBike("BX-1003"))
        em.persist(createBike("BX-1004"))
        em.flush()
    }

    private ORMBike createBike(String label) {
        return new ORMBike(null, label)
    }

}
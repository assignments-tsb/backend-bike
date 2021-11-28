package com.bike.app.test


import com.bike.app.driver.datastore.orm.ORMBike

import javax.persistence.EntityManager

trait BikePreparer {

    abstract EntityManager em

    void prepareTestBikes() {
        def b = createBike("BX-1001")
        em.persist(b)
//        em.persist(createBike("aaaa-aaaaa-aaaaa-aab", "BX-1002"))
//        em.persist(createBike("aaaa-aaaaa-aaaaa-aac", "BX-1003"))
//        em.persist(createBike("aaaa-aaaaa-aaaaa-aad", "BX-1004"))
        em.flush()
    }

    private ORMBike createBike(String label) {
        return new ORMBike(null, label)
    }

}
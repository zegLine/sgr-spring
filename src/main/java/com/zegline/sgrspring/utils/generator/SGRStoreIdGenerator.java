package com.zegline.sgrspring.utils.generator;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.io.Serializable;
import java.util.concurrent.ThreadLocalRandom;

public class SGRStoreIdGenerator implements IdentifierGenerator {

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) {
        String generatedId;

        long randomNum = ThreadLocalRandom.current().nextLong(100000000000L, 1000000000000L);
        generatedId = "STORE" + randomNum;

        return generatedId;
    }

}

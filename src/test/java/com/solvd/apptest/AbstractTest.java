package com.solvd.apptest;

import com.zebrunner.carina.core.IAbstractTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractTest implements IAbstractTest {
    static {
        System.setProperty("log4j.configurationFile", "src/main/resources/log4j2.xml");
    }

    protected static Logger LOGGER;

    public AbstractTest() {
        LOGGER = LoggerFactory.getLogger(getClass());
    }
}

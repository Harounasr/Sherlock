package de.ssherlock.business.cyclic;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.logging.Logger;

public class CyclicEventExecutor extends ScheduledThreadPoolExecutor {

    private Logger logger;

    public CyclicEventExecutor(int corePoolSize) {
        super(corePoolSize);
    }

    public void init() {

    }

    public void destroy() {
        
    }
}

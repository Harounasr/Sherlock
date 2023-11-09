package de.ssherlock.business.maintenance;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.logging.Logger;

public class MaintenanceProcessExecutor extends ScheduledThreadPoolExecutor {

    private static final int CORE_POOL_SIZE = 4;

    private static final int DESTROY_TIMEOUT = 70;

    private Logger logger;

    public MaintenanceProcessExecutor() {
        super(CORE_POOL_SIZE);
    }

    public void init() {

    }

    public void destroy() {

    }
}

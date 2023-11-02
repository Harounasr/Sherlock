package de.ssherlock.business.cyclic;

import java.util.concurrent.RunnableScheduledFuture;

public interface CyclicEvent extends Runnable {
    boolean isRunning();
    void shutdown();
}

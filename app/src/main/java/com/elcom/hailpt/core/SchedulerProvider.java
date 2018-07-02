package com.elcom.hailpt.core;

import io.reactivex.Scheduler;

/**
 * @author ihsan on 12/10/17.
 */

public interface SchedulerProvider {

    Scheduler ui();

    Scheduler io();

}

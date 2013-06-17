package fr.liglab.adele.icasa.service.scheduler.impl.task;

import fr.liglab.adele.icasa.clock.Clock;
import fr.liglab.adele.icasa.service.scheduler.PeriodicRunnable;
import fr.liglab.adele.icasa.service.scheduler.impl.TaskReferenceImpl;


public class PeriodicTaskImpl extends TaskReferenceImpl {

    private final Long period;

    private Long executionTime;

    public PeriodicTaskImpl(Clock service,
                           PeriodicRunnable task,  Long periodTime) {
        super(service,  task) ;

        long registrationTime = getRegistrationTime();


        if (periodTime == null
                || periodTime.longValue() < registrationTime) {
            period = new Long(registrationTime);
        } else {
            period = periodTime;
        }
        executionTime = getRegistrationTime() + periodTime;
    }

    public Long computeNextExecutionTime(long fromTime) {
        if (fromTime > executionTime) {
            executionTime = fromTime + 1;
        } else {
            executionTime = fromTime + period;
        }

        return executionTime;
    }

    public Long getNextExecutionTime(){
        return executionTime;
    }

}

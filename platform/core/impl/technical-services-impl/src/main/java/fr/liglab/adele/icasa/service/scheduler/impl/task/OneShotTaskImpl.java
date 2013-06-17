package fr.liglab.adele.icasa.service.scheduler.impl.task;

import fr.liglab.adele.icasa.clock.Clock;
import fr.liglab.adele.icasa.service.scheduler.impl.TaskReferenceImpl;

public class OneShotTaskImpl extends TaskReferenceImpl {

    private final Long m_executionTime;

    public OneShotTaskImpl(Clock service,
                           Runnable task,  Long executionTime) {
        super(service,  task) ;
        // If the task is registered after the wanted execution time, of if the
        // execution time is not specified, it is an immediate task.
        long registrationTime = getRegistrationTime();
        if (executionTime == null
                || executionTime.longValue() < registrationTime) {
            m_executionTime = new Long(registrationTime);
        } else {
            m_executionTime = executionTime;
        }
    }

    public Long computeNextExecutionTime(long fromTime) {
        if (fromTime > m_executionTime.longValue()) {
            return null;
        }
        return m_executionTime;
    }

    public Long getNextExecutionTime() {
        return m_executionTime;
    }

}

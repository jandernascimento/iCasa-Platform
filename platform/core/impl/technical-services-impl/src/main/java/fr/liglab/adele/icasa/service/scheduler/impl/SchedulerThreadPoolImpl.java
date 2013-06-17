/**
 *
 *   Copyright 2011-2012 Universite Joseph Fourier, LIG, ADELE team
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */
package fr.liglab.adele.icasa.service.scheduler.impl;

import fr.liglab.adele.icasa.clock.Clock;

import java.util.*;
import java.util.Map.Entry;

/**
 * User: torito
 * Date: 6/14/13
 * Time: 2:23 PM
 */
public class SchedulerThreadPoolImpl extends Thread {

    // The access lock
    private final Object m_lock;

    // The parent service
    private final Clock clockService;

    // The period between two scheduler thread ticks
    private final long m_period;

    // The threads in charge of task executions
    private final ExecutorThreadImpl[] m_executors;

    // The tasks to execute, indexed by next wanted iteration time
    private final SortedMap/* <Long, List<TaskReferenceImpl>> */m_tasks;

    // TODO
    public SchedulerThreadPoolImpl(String groupName, Clock service, int poolSize,
                                   long period) {
        super(groupName);
        m_lock = new Object();
        clockService = service;
        m_period = period;
        m_executors = new ExecutorThreadImpl[poolSize];
        m_tasks = new TreeMap/* <Long, List<TaskReferenceImpl>> */();
    }

    // TODO
    public void addTask(TaskReferenceImpl task) {
        long baseTime = task.getRegistrationTime();
        Long expectedTime = task.computeNextExecutionTime(baseTime);
        addTask(task,expectedTime);
    }

    private void addTask(TaskReferenceImpl task, Long expectedTime){
        if (expectedTime == null) {
            System.err.println("Ignoring task : Cannot determine first execution time");
            return;
        }
        synchronized (m_lock) {
            List/* <TaskReferenceImpl> */list = (List) m_tasks
                    .get(expectedTime);
            if (list == null) {
                list = new ArrayList(1);
                list.add(task);
                m_tasks.put(expectedTime, list);
            } else {
                list.add(task);
            }
        }
    }

    // TODO
    public void run() {
        boolean running = true;
        while (running) {
            Long now = new Long(clockService.currentTimeMillis());
            synchronized (m_lock) {
                SortedMap toRun = m_tasks.headMap(now);
                List toReschedule = new ArrayList();
                Iterator i = toRun.entrySet().iterator();
                while (i.hasNext()) {
                    Entry e = (Entry) i.next();
                    List list = (List) e.getValue();
                    Iterator j = list.iterator();
                    while (j.hasNext()) {
                        TaskReferenceImpl task = (TaskReferenceImpl) j.next();
                        if (scheduleNow(task)) {
                            j.remove();
                            Long ntime = task.computeNextExecutionTime(now);
                            if (ntime != null){
                                toReschedule.add(task);
                            }
                        }
                    }
                    // Remove from queue if list is empty
                    if (list.isEmpty()) {
                        i.remove();
                    }
                }
            }
            try {
                Thread.sleep(m_period);
            } catch (InterruptedException e) {
                running = false;
            }
        }
        // Interrupt all executors
        synchronized (m_lock) {
            for (int i = 0; i < m_executors.length; i++) {
                ExecutorThreadImpl executor = m_executors[i];
                if (executor != null) {
                    executor.interrupt();
                }
            }
        }
    }

    private boolean scheduleNow(TaskReferenceImpl task) {
        for (int i = 0; i < m_executors.length; i++) {
            ExecutorThreadImpl executor = m_executors[i];
            if (executor == null || !executor.isAlive()) {
                executor = new ExecutorThreadImpl(i, task);
                m_executors[i] = executor;
                executor.start();
                return true;
            } else if (executor.getCurrentTask() == null) {
                executor.setCurrentTask(task);
                return true;
            }
        }
        return false;
    }

    private static class ExecutorThreadImpl extends Thread {

        private final Object m_lock;
        private volatile TaskReferenceImpl m_currentTask;

        public ExecutorThreadImpl(int slotNumber, TaskReferenceImpl task) {
            super("ExecutorThread-" + slotNumber);
            m_lock = new Object();
            m_currentTask = task;
        }

        public TaskReferenceImpl getCurrentTask() {
            synchronized (m_lock) {
                return m_currentTask;
            }
        }

        public void setCurrentTask(TaskReferenceImpl task) {
            synchronized (m_lock) {
                if (m_currentTask != null) {
                    throw new IllegalStateException();
                }
                m_currentTask = task;
                m_lock.notify();
            }
        }

        // TODO
        public void run() {
            for (;;) {
                synchronized (m_lock) {
                    if (m_currentTask != null) {
                        m_currentTask.run();
                        m_currentTask = null;
                    }
                    try {
                        m_lock.wait();
                    } catch (InterruptedException e) {
                        return;
                    }
                }
            }
        }
    }

}
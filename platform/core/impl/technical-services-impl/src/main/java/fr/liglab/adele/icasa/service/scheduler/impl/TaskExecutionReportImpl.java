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

import fr.liglab.adele.icasa.service.scheduler.TaskExecutionReport;

/**
 * User: torito
 * Date: 6/14/13
 * Time: 2:54 PM
 */
public class TaskExecutionReportImpl implements TaskExecutionReport {

    String name;
    long executionCount;
    long startupTime;
    long terminationTime = -1;
    Object result;
    Throwable exception;

    public String getTaskName() {
        return name;
    }

    public long getExecutionCount() {
        return executionCount;
    }

    public long getStartupTime() {
        return startupTime;
    }

    public long getTerminationTime() {
        return terminationTime;
    }

    public Object getResult() {
        return result;
    }

    public Throwable getException() {
        return exception;
    }



}
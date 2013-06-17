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
package fr.liglab.adele.icasa.service.scheduler;

public interface PeriodicRunnable extends ICasaRunnable {
    /**
     * Gets the scheduled period.
     * @return the period in second.
     */
    public long getPeriod();

    /**
     * If false, the job is scheduled at fixed interval. If true, at fixed rate.
     */
    public boolean isScheduledAtFixedRate();

}

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

import org.slf4j.LoggerFactory;

import java.util.concurrent.ThreadFactory;

/**
 * A defensive thread factory catching error when the initial runnable crashes.
 */
public class DefensiveThreadFactory implements ThreadFactory {

    private static final DefensiveThreadFactory FACTORY = new DefensiveThreadFactory();

    public static ThreadFactory get() {
        return FACTORY;
    }

    @Override
    public Thread newThread(final Runnable runnable) {
        return new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    runnable.run();
                } catch (Throwable e) {
                    LoggerFactory.getLogger(runnable.toString()).error("Error during the execution of {}", runnable,
                            e);
                }
            }
        });
    }
}

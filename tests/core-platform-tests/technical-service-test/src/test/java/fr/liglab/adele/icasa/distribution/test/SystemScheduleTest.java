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
package fr.liglab.adele.icasa.distribution.test;

import javax.inject.Inject;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

import fr.liglab.adele.icasa.clock.Clock;
import fr.liglab.adele.icasa.service.scheduler.PeriodicRunnable;
import fr.liglab.adele.icasa.service.scheduler.ScheduledRunnable;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.ops4j.pax.exam.junit.PaxExam;
import org.ops4j.pax.exam.spi.reactors.ExamReactorStrategy;
import org.ops4j.pax.exam.spi.reactors.PerMethod;
import org.osgi.framework.BundleContext;

import fr.liglab.adele.commons.distribution.test.AbstractDistributionBaseTest;
import fr.liglab.adele.icasa.ContextManager;
import fr.liglab.adele.icasa.location.LocatedDevice;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;


@RunWith(PaxExam.class)
@ExamReactorStrategy(PerMethod.class)
public class SystemScheduleTest extends AbstractDistributionBaseTest {

    static final long ONE_SECOND=1000;

	@Inject
	public BundleContext context;

    @Inject
    public Clock clock;
	
	@Before
	public void setUp() {
		waitForStability(context);
	}

	@After
	public void tearDown() {

	}
	
	/**
	 * Test the creation of a new zone.
	 */
	@Test
	public void scheduleAPeriodictaskTest(){

        PeriodicRunnable runnable = new PeriodicScheduledTestTask(clock);
        ServiceRegistration register = context.registerService(PeriodicRunnable.class.getName(), runnable, new Hashtable());
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        register.unregister();

    }


    /**
     * Test the creation of a new zone.
     */
    @Test
    public void scheduleTaskTest(){

        Date now = new Date();
        Date scheduledTime = new Date(now.getTime() + ONE_SECOND*5);//Scheduled in 5 seconds from now

        ScheduledTestTask runnable = new ScheduledTestTask(clock, scheduledTime, "group1");
        ServiceRegistration register = context.registerService(ScheduledRunnable.class.getName(), runnable, new Hashtable());

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        register.unregister();

    }

}

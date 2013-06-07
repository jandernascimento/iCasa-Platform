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
package test.context.source;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.apache.felix.ipojo.ContextListener;
import org.apache.felix.ipojo.ContextSource;
import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Instantiate;
import org.apache.felix.ipojo.annotations.Provides;
import org.apache.felix.ipojo.annotations.Requires;
import org.apache.felix.ipojo.annotations.Validate;
import org.osgi.framework.BundleContext;

import fr.liglab.adele.icasa.location.LocatedDevice;
import fr.liglab.adele.icasa.simulator.Person;
import fr.liglab.adele.icasa.simulator.SimulationManager;
import fr.liglab.adele.icasa.simulator.listener.PersonListener;

@Component(name="ContextSourceBuilder")
@Provides
@Instantiate
public class ContextSourceBuilder implements ContextSource {

	@Requires
	private SimulationManager manager;
	
	private PersonListener personListener;
	
	Hashtable<String, String> personLocations = new Hashtable<String, String>();
	
	ArrayList<ContextListener> listeners = new ArrayList<ContextListener>();
	
	private ReadWriteLock lock = new ReentrantReadWriteLock();
	
	public ContextSourceBuilder(BundleContext context) {
		personListener = new ContextSourcePersonListener();
	}

	public Object getProperty(String property) {
		if (isPersonProperty(property))
			return personLocations.get(property);
	   return null;
   }

	public Dictionary getContext() {
	   return personLocations;
   }

	public void registerContextListener(ContextListener listener, String[] properties) {
	   lock.writeLock().lock();
	   listeners.add(listener);
	   lock.writeLock().unlock();	   
   }

	public void unregisterContextListener(ContextListener listener) {
	   lock.writeLock().lock();
	   listeners.remove(listener);
	   lock.writeLock().unlock();	 	   
   }
	
	@Validate
	protected void start() {
		manager.addListener(personListener);
	}
	
	protected void stop() {
		manager.removeListener(personListener);
	}
	
	
	private void notifyListeners(String property, String value) {
		lock.readLock().lock();
		ArrayList<ContextListener> copyList = null;
		try {
			copyList = new ArrayList<ContextListener>(listeners);
		} finally {
			lock.readLock().unlock();
		}		
		for (ContextListener contextListener : copyList) {
         contextListener.update(this, property, value);
      }
	}
	
	
	private boolean isPersonProperty(String property) {
		if (property.startsWith("person.")) {
			String[] parts = property.split(".");
			if (parts.length==3)
				if (parts[2].equals("location"))
					return true;
		}
		return false;
	}
	
	
	class ContextSourcePersonListener implements PersonListener {
		
		
		@Override
		public void personRemoved(Person person) {
			String key = getKey(person);
			personLocations.remove(key);
			notifyListeners(key, null);			
		}
		
		
		@Override
		public void personAdded(Person person) {
			String key = getKey(person);
			String location = person.getLocation();
			personLocations.put(key, location);
			notifyListeners(key, location);
		}
		
		@Override
      public void personMoved(Person person, fr.liglab.adele.icasa.location.Position arg1) {
			String key = getKey(person);
			String location = person.getLocation();
			personLocations.put(key, location);
			notifyListeners(key, location);	      
      }

		@Override
      public void personDeviceAttached(Person arg0, LocatedDevice arg1) {
	      // TODO Auto-generated method stub
	      
      }

		@Override
      public void personDeviceDetached(Person arg0, LocatedDevice arg1) {
	      // TODO Auto-generated method stub
	      
      }
		
		private String getKey(Person person) {
			return "person." + person.getName() + ".location";
		}
		

	}

}

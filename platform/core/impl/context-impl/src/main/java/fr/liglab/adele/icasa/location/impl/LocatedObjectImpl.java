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
package fr.liglab.adele.icasa.location.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import fr.liglab.adele.icasa.location.LocatedObject;
import fr.liglab.adele.icasa.location.Position;

public abstract class LocatedObjectImpl implements LocatedObject {

	private Position m_position;

    ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    Lock readLock = lock.readLock();

    Lock writeLock = lock.writeLock();
	
	private List<LocatedObject> attachedObjects = new ArrayList<LocatedObject>();
	
	public LocatedObjectImpl(Position position) {
		m_position = position.clone();
	}
	
	@Override
   public Position getCenterAbsolutePosition() {
        readLock.lock();
        try{
	    return m_position.clone();
        }finally {
            readLock.unlock();
        }
   }

	@Override
   public void setCenterAbsolutePosition(Position position) {
        Position absolutePosition =  getCenterAbsolutePosition();
		int deltaX = position.x - absolutePosition.x ;
		int deltaY = position.y - absolutePosition.y;
        writeLock.lock();
		m_position = position.clone();
        writeLock.unlock();
		moveAttachedObjects(deltaX, deltaY);
   }
		
	protected void moveAttachedObjects(int deltaX, int deltaY) {
        List<LocatedObject> snapshotAttachedObjects = getAttachedObjects();
		for (LocatedObject object : snapshotAttachedObjects) {
	      int newX = object.getCenterAbsolutePosition().x + deltaX;
	      int newY = object.getCenterAbsolutePosition().y + deltaY;
	      Position objectPosition = new Position(newX, newY);
	      object.setCenterAbsolutePosition(objectPosition);
      }
	}

	@Override
   public void attachObject(LocatedObject object) {
		if (object==this)
			return;
		writeLock.lock();
        try{
		    attachedObjects.add(object);
        }finally {
            writeLock.unlock();
        }
        notifyAttachedObject(object);
   }

	@Override
   public void detachObject(LocatedObject object) {
		if (object==this)
			return;
		writeLock.lock();
        try{
		    attachedObjects.remove(object);
        }finally {
            writeLock.unlock();
        }
        notifyDetachedObject(object);
   }

   protected abstract void notifyAttachedObject(LocatedObject attachedObject);

   protected abstract void notifyDetachedObject(LocatedObject attachedObject);

   private List<LocatedObject> getAttachedObjects(){
       List<LocatedObject> snapshotList;
       readLock.lock();
       snapshotList = new ArrayList<LocatedObject>(attachedObjects);
       readLock.unlock();
       return snapshotList;
   }

}

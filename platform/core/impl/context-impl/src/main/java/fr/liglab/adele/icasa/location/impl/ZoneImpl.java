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

import java.util.*;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import fr.liglab.adele.icasa.Variable;
import fr.liglab.adele.icasa.location.*;

public class ZoneImpl extends LocatedObjectImpl implements Zone {

	private String id;
	private int height;
	private int width;
	private Zone parent;
	private Position leftTopPosition;
	private List<Zone> children = new ArrayList<Zone>();
	private List<ZoneListener> listeners = new ArrayList<ZoneListener>();
	private Map<String, Object> variables = new HashMap<String, Object>();
	private boolean useParentVariable = false;
    private ReadWriteLock lock = new ReentrantReadWriteLock();

	public ZoneImpl(String id, int x, int y, int width, int height) {
		this(id, new Position(x, y), width, height);
	}

	public ZoneImpl(String id, Position leftTopPosition, int width, int height) {
		super(leftTopPosition);
		this.id = id;
		this.leftTopPosition = leftTopPosition.clone();
		this.height = height;
		this.width = width;
	}

    private static Zone clone(Zone zone){
        if (zone == null){
            return null;
        }
        Zone returningZone;
        if (zone instanceof ZoneImpl){
            ZoneImpl _zone = (ZoneImpl)zone;
            _zone.lock.readLock().lock();
            try{
                returningZone = new ZoneImpl(_zone.getId(), _zone.getLeftTopRelativePosition(), _zone.getWidth(), _zone.getHeight());
            }finally {
                _zone.lock.readLock().unlock();
            }
       } else {
            returningZone = new ZoneImpl(zone.getId(), zone.getLeftTopRelativePosition(), zone.getWidth(), zone.getHeight());
        }
        return returningZone;
    }

	public String getId() {
		return id;
	}

	@Override
	public boolean fits(Zone aZone) {
        Zone clonedZone = ZoneImpl.clone(aZone);
        Position aZonePosition =  clonedZone.getLeftTopRelativePosition();
        int _width, _height;
        lock.readLock().lock();
        _width = width;
        _height = height;
        Position thisPosition = getLeftTopAbsolutePosition();
        lock.readLock().unlock();
        boolean fits = (aZonePosition.x + clonedZone.getWidth() > _width + thisPosition.x) || (aZonePosition.y + clonedZone.getHeight() > _height + thisPosition.y);
		return !fits;
	}

	@Override
	public Zone getParent() {
        lock.readLock().lock();
        try{
		    return parent;
        }finally {
            lock.readLock().unlock();
        }
	}

	@Override
	public List<Zone> getChildren() {
        lock.readLock().lock();
        try{
		    return new ArrayList<Zone>(children);
        } finally {
            lock.readLock().unlock();
        }
	}

	@Override
	public int getLayer() {
        lock.readLock().lock();
        Zone localParent = getParent();
        lock.readLock().unlock();
		if (localParent == null)
			return 0;
		return localParent.getLayer() + 1;

	}

	@Override
	public Position getLeftTopRelativePosition() {
        lock.readLock().lock();
        try{
		    return leftTopPosition.clone();
        }finally {
            lock.readLock().unlock();
        }
	}

    @Override
    public Position getLeftTopAbsolutePosition() {
        lock.readLock().lock();
        Zone parentZone = getParent();
        int absoluteX = leftTopPosition.x;
        int absoluteY = leftTopPosition.y;
        lock.readLock().unlock();
        if (parentZone!=null) {
            absoluteX += parentZone.getLeftTopAbsolutePosition().x;
            absoluteY += parentZone.getLeftTopAbsolutePosition().y;
        }
        return new Position(absoluteX, absoluteY);
    }

	@Override
	public int getWidth() {
        lock.readLock().lock();
        try {
		    return width;
        }finally {
            lock.readLock().unlock();
        }
	}

	@Override
	public int getHeight() {
        lock.readLock().lock();
        try {
            return height;
        }finally {
            lock.readLock().unlock();
        }
	}

	@Override
	public boolean contains(LocatedObject object) {
		Position objectPosition = object.getCenterAbsolutePosition();
		if (objectPosition == null)
			return false;
		return contains(objectPosition);
	}

	@Override
	public boolean contains(Position position) {
		if (position == null)
			return false;
        lock.readLock().lock();
        try{
		Position absolutePosition = getLeftTopAbsolutePosition();

		return (position.x >= absolutePosition.x && position.x <= absolutePosition.x + width)
		      && (position.y >= absolutePosition.y && position.y <= absolutePosition.y + height);
        }finally {
            lock.readLock().unlock();
        }
	}

	@Override
	public boolean addZone(Zone child) {
		if (fits(child)) {
            lock.writeLock().lock();
            try{
			    children.add(child);
            }finally {
                lock.writeLock().unlock();
            }
			child.setParent(this);
			return true;
		}
		return false;
	}

	@Override
   public Position getCenterAbsolutePosition() {
        lock.readLock().lock();
		Zone parentZone = getParent();
		int absoluteX = leftTopPosition.x;
		int absoluteY = leftTopPosition.y;

		if (parentZone!=null) {
			absoluteX += parentZone.getCenterAbsolutePosition().x;
			absoluteY += parentZone.getCenterAbsolutePosition().y;
		}
		absoluteX += width/2;
		absoluteY += height/2;
        lock.readLock().unlock();
		return new Position(absoluteX, absoluteY);
   }

	@Override
   public void setCenterAbsolutePosition(Position position) {
        lock.writeLock().lock();
        try{
		if (parent==null) {
	      try {
	      	int newX = position.x - width/2;
	      	int newY = position.y - height/2;
	         setLeftTopRelativePosition(new Position(newX, newY));
         } catch (Exception e) {
	         e.printStackTrace();
         }
		} else {
			int newX = (position.x - (width/2)) - parent.getLeftTopAbsolutePosition().x;
			int newY = (position.y -(height/2)) - parent.getLeftTopAbsolutePosition().y;
			try {
	         setLeftTopRelativePosition(new Position(newX, newY));
         } catch (Exception e) {
	         e.printStackTrace();
         }
		}
        }finally {
            lock.writeLock().unlock();
        }
   }

    @Override
    protected void notifyAttachedObject(LocatedObject attachedObject) {
        LocatedDevice childDevice;
        if (attachedObject instanceof  LocatedDevice){
            childDevice = (LocatedDevice)attachedObject;
        } else {
            return; // If attached object is not a locatedDevice we do nothing.
        }
        List<ZoneListener> snapshotListener = getListenerCopy();
        for (ZoneListener listener : snapshotListener) {
            try{
                listener.deviceAttached(this, childDevice);
            }catch (Exception ex){
                System.err.println("Listener error in event zoneVariableModified");
                ex.printStackTrace();
            }
        }
    }

    @Override
    protected void notifyDetachedObject(LocatedObject attachedObject) {
        LocatedDevice childDevice = null;
        if (attachedObject instanceof  LocatedDevice){
            childDevice = (LocatedDevice)attachedObject;
        } else {
            return; // If attached object is not a locatedDevice we do nothing.
        }
        List<ZoneListener> snapshotListener = getListenerCopy();
        for (ZoneListener listener : snapshotListener) {
            try{
                listener.deviceDetached(this, childDevice);
            }catch (Exception ex){
                System.err.println("Listener error in event zoneVariableModified");
                ex.printStackTrace();
            }
        }
    }

    @Override
	public void setParent(Zone parent) {
        lock.writeLock().lock();
		Zone oldParentZone = this.parent;
		this.parent = parent;
        lock.writeLock().unlock();

		// Listeners notification
        List<ZoneListener> snapshotListener = getListenerCopy();

        for (ZoneListener listener : snapshotListener) {
            try{
			    listener.zoneParentModified(this, oldParentZone);
            }catch (Exception ex){
                System.err.println("Listener error in event zoneVariableModified");
                ex.printStackTrace();
            }
		}
	}

	@Override
	public void addListener(ZoneListener listener) {
        synchronized (listeners){
		    listeners.add(listener);
        }
	}

	@Override
	public void removeListener(ZoneListener listener) {
        synchronized (listeners){
		    listeners.remove(listener);
        }
	}

	@Override
	public Object getVariableValue(String name) {
        Object value = null;
        lock.readLock().lock();
        boolean _useParent = useParentVariable;
        Zone localParent = getParent();
        lock.readLock().unlock();
		if (_useParent){
			if (localParent != null){
				value = localParent.getVariableValue(name);
            } else {
				value = null;
            }
            return value;
        }//if using parent Variable
        lock.readLock().lock();
		if (variables.containsKey(name)) {
			value = variables.get(name);
        }
        lock.readLock().unlock();
		return value;
	}

	@Override
	public void setVariableValue(String name, Object newValue) {
        Object oldValue = null;
        lock.readLock().lock();
        boolean _useParent = useParentVariable;
        lock.readLock().unlock();
		if (_useParent)
			return;

       lock.writeLock().lock();
        try{
            if (variables.containsKey(name)){
                oldValue = variables.get(name);
            } else {
                oldValue = null;
            }

            variables.put(name, newValue);
        }finally {
            lock.writeLock().unlock();
        }
		// Listeners notification
        List<ZoneListener> snapshotListener = getListenerCopy();
        for (ZoneListener listener : snapshotListener) {
            try{
			    listener.zoneVariableModified(this, name, oldValue);
            }catch(Exception ex){
                System.err.println("Listener error in event zoneVariableModified");
                ex.printStackTrace();
            }

		}
	}

	@Override
	public void addVariable(String name) {
        lock.readLock().lock();
        boolean _useParent = useParentVariable;
        lock.readLock().unlock();
        if (_useParent)
            return;

        lock.writeLock().lock();
        try {
            if (variables.containsKey(name)){
                return;
            }
            variables.put(name, null);
        }finally {
            lock.writeLock().unlock();
        }

    	// Listeners notification
        List<ZoneListener> snapshotListener = getListenerCopy();
        for (ZoneListener listener : snapshotListener) {
            try{
			    listener.zoneVariableAdded(this, name);
            }catch (Exception ex){
                System.err.println("Listener error in event zoneVariableAdded");
                ex.printStackTrace();
            }
		}
	}

	@Override
	public void removeVariable(String name) {
        lock.writeLock().lock();
        try{
            if (useParentVariable){
                return;
            }
            if (!variables.containsKey(name)){
                return;
            }
            variables.remove(name);
        }finally {
            lock.writeLock().unlock();
        }

		// Listeners notification
        List<ZoneListener> snapshotListener = getListenerCopy();
		for (ZoneListener listener : snapshotListener) {
            try{
			    listener.zoneVariableRemoved(this, name);
            }catch(Exception ex){
                System.err.println("Listener error in event zoneVariableRemoved");
                ex.printStackTrace();
            }
		}
	}

	@Override
	public Set<String> getVariableNames() {
        lock.readLock().lock();
        try{
            if (useParentVariable)
                if (parent != null)
                    return parent.getVariableNames();
                else
                    return null;
            return variables.keySet();
        }finally {
            lock.readLock().unlock();
        }
	}

    @Override
    public Set<Variable> getVariables() {
        Set<Variable> variables = new HashSet<Variable>();
        lock.readLock().lock();

        try{
            for (String varName : getVariableNames()) {
                Object value = getVariableValue(varName);
                Class valueType = null;
                if (value == null)
                    valueType = Object.class;
                else
                    valueType = value.getClass();
                variables.add(new Variable(varName, valueType, "No description"));
            }

            return variables;
        } finally {
            lock.readLock().unlock();
        }
    }

    @Override
	public void setUseParentVariables(boolean useParentVariables) {
        lock.writeLock().lock();
		this.useParentVariable = useParentVariables;
        lock.writeLock().unlock();
	}

	@Override
	public boolean getUseParentVariables() {
        lock.readLock().lock();
		try{
            return useParentVariable;
        }finally {
            lock.readLock().unlock();
        }
	}

	@Override
	public void setLeftTopRelativePosition(Position leftTopPosition) throws Exception {
        Zone _parent = ZoneImpl.clone(getParent());
        lock.writeLock().lock();
		Position oldPosition = this.leftTopPosition;
		this.leftTopPosition = leftTopPosition.clone();
        lock.writeLock().unlock();

		if (_parent != null) {
			if (!_parent.fits(this)) {
                lock.writeLock().lock();
				this.leftTopPosition = oldPosition;
                lock.writeLock().unlock();
				throw new Exception("New size does not fit the parent zone");
			}
		}

		moveAttachedObjects(leftTopPosition.x-oldPosition.x, leftTopPosition.y-oldPosition.y);

		// Listeners notification
        List<ZoneListener> snapshotListener = getListenerCopy();
        for (ZoneListener listener : snapshotListener){
			try{
                listener.zoneMoved(this, oldPosition);
            }catch (Exception ex){
                System.err.println("Listener error in event zoneMoved");
                ex.printStackTrace();
            }
        }

	}

	@Override
	public void setWidth(int width) throws Exception {
		resize(width, this.height);
	}

	@Override
	public void setHeight(int height) throws Exception {
		resize(this.width, height);

	}

	@Override
	public void resize(int newWidth, int newHeight) throws Exception {
        Zone _parent = ZoneImpl.clone(getParent());
        lock.writeLock().lock();
		int oldWidth = this.width;
		int oldHeight = this.height;
        try{
            this.width = newWidth;
            this.height = newHeight;
            if (_parent != null) {
                if (!_parent.fits(this)) {
                    this.width = oldWidth;
                    this.height = oldHeight;
                    throw new Exception("New size does not fit the parent zone");
                }
            }
        }finally {
            lock.writeLock().unlock();
        }

        List<ZoneListener> snapshotListener = getListenerCopy();
		// Listeners notification
		for (ZoneListener listener : snapshotListener) {
            try{
			    listener.zoneResized(this);
            }catch(Exception ex){
                System.err.println("Listener error in event zoneResized");
                ex.printStackTrace();
            }
        }
	}


	@Override
	public Position getRelativePosition(LocatedObject object) {
		if (!(contains(object)))
			return null;
		int relX = object.getCenterAbsolutePosition().x - getCenterAbsolutePosition().x;
		int relY = object.getCenterAbsolutePosition().y - getCenterAbsolutePosition().y;
		return new Position(relX, relY);
	}

    @Override
    public Position getRightBottomRelativePosition() {
        Position leftTopRelativePosition = getLeftTopRelativePosition();
        int newX = leftTopRelativePosition.x + width;
        int newY = leftTopRelativePosition.y + height;
        return new Position(newX, newY);
    }

	@Override
   public Position getRightBottomAbsolutePosition() {
        Position leftTopAbsolutePosition = getLeftTopAbsolutePosition();
        int newX = leftTopAbsolutePosition.x + width;
		int newY = leftTopAbsolutePosition.y + height;
	   return new Position(newX, newY);
   }

	@Override
	public String toString() {
        lock.readLock().lock();
		String parentId = "Unset";
        try{
		if (parent!=null)
			parentId = parent.getId();
		return "Zone: " + id + " X: " + leftTopPosition.x + " Y: " + leftTopPosition.y + " -- Width: " + width + " Height: " + height + " - Parent: " + parentId + " - Use parent: " + useParentVariable;
        }finally {
            lock.readLock().unlock();
        }
	}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ZoneImpl zone = (ZoneImpl) o;

        if (!id.equals(zone.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        return result;
    }

    /**
     * Get a copy of the listener to iterate in it.
     * @return a copy of the listeners
     */
    private List<ZoneListener> getListenerCopy(){
        List<ZoneListener> snapshotListener;
        lock.readLock().lock();
        try{
            snapshotListener = new ArrayList<ZoneListener>(listeners);
        }finally {
            lock.readLock().unlock();
        }
        return snapshotListener;
    }

}

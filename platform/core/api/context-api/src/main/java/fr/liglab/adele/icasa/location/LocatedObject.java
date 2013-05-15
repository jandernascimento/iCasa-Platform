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
package fr.liglab.adele.icasa.location;

/**
 * This interface represents a object that can be placed using a point into a coordinate system.
 * 
 * @author Gabriel Pedraza Ferreira
 * 
 */
public interface LocatedObject {

	/**
	 * Gets the absolute (x,y) point of the object.
	 * 
	 * @return The absolute (x,y) point of the object.
	 */
	public Position getCenterAbsolutePosition();

	/**
	 * Sets the absolute (x,y) point of the object.
	 * 
	 * @param position the new position
	 */
	public void setCenterAbsolutePosition(Position position);

	/**
	 * Attaches other object to this one. Both objects are moved at same time.
	 * 
	 * @param object The object to be attached.
	 */
	public void attachObject(LocatedObject object);

	/**
	 * Detaches the object from this one.
	 * 
	 * @param object The object to be detached.
	 */
	public void detachObject(LocatedObject object);
	
}

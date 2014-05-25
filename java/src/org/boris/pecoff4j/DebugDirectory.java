/*******************************************************************************
 * This program and the accompanying materials
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at 
 * http://www.eclipse.org/legal/cpl-v10.html
 * 
 * Contributors:
 *     Peter Smith
 *******************************************************************************/
package org.boris.pecoff4j;

import org.boris.pecoff4j.util.DataObject;

/**
 * Encapsulates the Debug Directory (Image Only). Section 6.1.1 of the PE/COFF
 * spec v8.
 */
public class DebugDirectory extends DataObject
{
    private int characteristics;
    private int timeDateStamp;
    private int majorVersion;
    private int type;
    private int sizeOfData;
    private int addressOfRawData;
    private int pointerToRawData;

    public int getCharacteristics() {
        return characteristics;
    }

    public int getTimeDateStamp() {
        return timeDateStamp;
    }

    public int getMajorVersion() {
        return majorVersion;
    }

    public int getType() {
        return type;
    }

    public int getSizeOfData() {
        return sizeOfData;
    }

    public int getAddressOfRawData() {
        return addressOfRawData;
    }

    public int getPointerToRawData() {
        return pointerToRawData;
    }

    public void setCharacteristics(int characteristics) {
        this.characteristics = characteristics;
    }

    public void setTimeDateStamp(int timeDateStamp) {
        this.timeDateStamp = timeDateStamp;
    }

    public void setMajorVersion(int majorVersion) {
        this.majorVersion = majorVersion;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setSizeOfData(int sizeOfData) {
        this.sizeOfData = sizeOfData;
    }

    public void setAddressOfRawData(int addressOfRawData) {
        this.addressOfRawData = addressOfRawData;
    }

    public void setPointerToRawData(int pointerToRawData) {
        this.pointerToRawData = pointerToRawData;
    }
}

/*******************************************************************************
 * This program and the accompanying materials
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at 
 * http://www.eclipse.org/legal/cpl-v10.html
 * 
 * Contributors:
 *     Peter Smith
 *******************************************************************************/
package org.boris.pecoff4j.resources;


public class VersionInfo
{
    private int length;
    private int valueLength;
    private int type;
    private String key;
    private FixedFileInfo fixedFileInfo;
    private StringFileInfo stringFileInfo;
    private VarFileInfo varFileInfo;

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getValueLength() {
        return valueLength;
    }

    public void setValueLength(int valueLength) {
        this.valueLength = valueLength;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public FixedFileInfo getFixedFileInfo() {
        return fixedFileInfo;
    }

    public void setFixedFileInfo(FixedFileInfo fixedFileInfo) {
        this.fixedFileInfo = fixedFileInfo;
    }

    public StringFileInfo getStringFileInfo() {
        return stringFileInfo;
    }

    public void setStringFileInfo(StringFileInfo stringFileInfo) {
        this.stringFileInfo = stringFileInfo;
    }

    public VarFileInfo getVarFileInfo() {
        return varFileInfo;
    }

    public void setVarFileInfo(VarFileInfo varFileInfo) {
        this.varFileInfo = varFileInfo;
    }
}

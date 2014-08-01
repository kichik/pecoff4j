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

public class BoundImport {
	private long timestamp;
	private int offsetToModuleName;
	private String moduleName;
	private int numModuleForwarderRefs;

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public int getNumberOfModuleForwarderRefs() {
		return numModuleForwarderRefs;
	}

	public void setNumberOfModuleForwarderRefs(int numModuleForwarderRefs) {
		this.numModuleForwarderRefs = numModuleForwarderRefs;
	}

	public int getOffsetToModuleName() {
		return offsetToModuleName;
	}

	public void setOffsetToModuleName(int offsetToModuleName) {
		this.offsetToModuleName = offsetToModuleName;
	}
}

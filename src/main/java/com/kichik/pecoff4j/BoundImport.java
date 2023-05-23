/*******************************************************************************
 * This program and the accompanying materials
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at 
 * http://www.eclipse.org/legal/cpl-v10.html
 * 
 * Contributors:
 *     Peter Smith
 *******************************************************************************/
package com.kichik.pecoff4j;

import com.kichik.pecoff4j.io.IDataReader;

import java.io.IOException;

public class BoundImport {
	private long timestamp;
	private int offsetToModuleName;
	private String moduleName;
	private int numModuleForwarderRefs;

	public static BoundImport read(IDataReader dr) throws IOException {
		BoundImport bi = new BoundImport();
		bi.setTimestamp(dr.readDoubleWord());
		bi.setOffsetToModuleName(dr.readWord());
		bi.setNumberOfModuleForwarderRefs(dr.readWord());

		if (bi.getTimestamp() == 0 && bi.getOffsetToModuleName() == 0
				&& bi.getNumberOfModuleForwarderRefs() == 0)
			return null;

		return bi;
	}

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

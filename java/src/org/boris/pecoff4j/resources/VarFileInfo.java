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

import java.util.ArrayList;
import java.util.List;

public class VarFileInfo {
	private String key;
	private List<String> names = new ArrayList();
	private List<String> values = new ArrayList();

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public int size() {
		return names.size();
	}

	public String getName(int index) {
		return names.get(index);
	}

	public String getValue(int index) {
		return values.get(index);
	}

	public void add(String name, String value) {
		names.add(name);
		values.add(value);
	}

	public void clear() {
		names.clear();
		values.clear();
	}
}

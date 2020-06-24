/*******************************************************************************
 * This program and the accompanying materials
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at 
 * http://www.eclipse.org/legal/cpl-v10.html
 * 
 * Contributors:
 *     Peter Smith
 *******************************************************************************/
package com.kichik.pecoff4j.util;

import java.util.ArrayList;
import java.util.List;

import com.kichik.pecoff4j.ResourceDirectory;
import com.kichik.pecoff4j.ResourceEntry;

public class ResourceHelper {
	public static ResourceEntry[] findResources(ResourceDirectory rd, int type) {
		return findResources(rd, type, -1, -1);
	}

	public static ResourceEntry[] findResources(ResourceDirectory rd, int type,
			int name) {
		return findResources(rd, type, name, -1);
	}

	public static ResourceEntry[] findResources(ResourceDirectory rd, int type,
			int name, int lang) {
		List<ResourceEntry> entries = new ArrayList<ResourceEntry>();
		if (rd != null) {
			findResources(rd, type, name, lang, entries);
		}
		return entries.toArray(new ResourceEntry[0]);
	}

	private static void findResources(ResourceDirectory parent, int type,
			int name, int language, List<ResourceEntry> entries) {
		int id = type;
		if (id == -1)
			id = name;
		if (id == -1)
			id = language;
		for (int i = 0; i < parent.size(); i++) {
			ResourceEntry e = parent.get(i);
			if (id == -1 || id == e.getId()) {
				if (e.getData() != null)
					entries.add(e);
				else {
					ResourceDirectory rd = e.getDirectory();
					if (rd != null) {
						if (type != -1)
							type = -1;
						else if (name != -1)
							name = -1;
						else
							language = -1;
						findResources(rd, type, name, language, entries);
					}
				}
			}
		}
	}

	public static void addResource(int type, int name, int lang, byte[] data) {

	}
}

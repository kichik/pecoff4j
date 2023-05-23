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

import com.kichik.pecoff4j.constant.ImageDataDirectoryType;
import com.kichik.pecoff4j.io.DataReader;
import com.kichik.pecoff4j.io.IDataWriter;
import com.kichik.pecoff4j.util.IntMap;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BoundImportDirectoryTable {
	private List<BoundImport> imports = new ArrayList();

	public static BoundImportDirectoryTable read(DataReader dr) throws IOException {
		BoundImportDirectoryTable bidt = new BoundImportDirectoryTable();
		List<BoundImport> imports = new ArrayList<BoundImport>();
		BoundImport bi = null;
		while ((bi = BoundImport.read(dr)) != null) {
			bidt.add(bi);
			imports.add(bi);
		}
		Collections.sort(imports, new Comparator<BoundImport>() {
			@Override
			public int compare(BoundImport o1, BoundImport o2) {
				return o1.getOffsetToModuleName() - o2.getOffsetToModuleName();
			}
		});
		IntMap names = new IntMap();
		for (int i = 0; i < imports.size(); i++) {
			bi = imports.get(i);
			int offset = bi.getOffsetToModuleName();
			String n = (String) names.get(offset);
			if (n == null) {
				dr.jumpTo(offset);
				n = dr.readUtf();
				names.put(offset, n);
			}
			bi.setModuleName(n);
		}
		return bidt;
	}

	public void write(PE pe, IDataWriter dw) throws IOException {
		int pos = dw.getPosition();
		List<BoundImport> bil = new ArrayList();

		for (int i = 0; i < size(); i++) {
			BoundImport bi = get(i);
			bil.add(bi);
			dw.writeDoubleWord((int) bi.getTimestamp());
			dw.writeWord(bi.getOffsetToModuleName());
			dw.writeWord(bi.getNumberOfModuleForwarderRefs());
		}

		Collections.sort(bil, new Comparator<BoundImport>() {
			@Override
			public int compare(BoundImport o1, BoundImport o2) {
				return o1.getOffsetToModuleName() - o2.getOffsetToModuleName();
			}
		});

		// Now write out empty block
		dw.writeDoubleWord(0);
		dw.writeDoubleWord(0);

		// Now write out module names
		Set names = new HashSet();
		for (int i = 0; i < bil.size(); i++) {
			String s = bil.get(i).getModuleName();
			if (!names.contains(s))
				dw.writeUtf(s);
			names.add(s);
		}

		// Check for empty block at end - padding for alignment
		int dpos = dw.getPosition() - pos;
		int bis = pe.getOptionalHeader()
				.getDataDirectory(ImageDataDirectoryType.BOUND_IMPORT)
				.getSize();
		if (bis > dpos) {
			dw.writeByte(0, bis - dpos);
		}
	}

	public void add(BoundImport bi) {
		imports.add(bi);
	}

	public int size() {
		return imports.size();
	}

	public BoundImport get(int index) {
		return imports.get(index);
	}
}

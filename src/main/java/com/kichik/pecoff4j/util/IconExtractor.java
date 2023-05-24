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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import com.kichik.pecoff4j.PE;
import com.kichik.pecoff4j.ResourceDirectory;
import com.kichik.pecoff4j.ResourceEntry;
import com.kichik.pecoff4j.constant.ResourceType;
import com.kichik.pecoff4j.io.DataReader;
import com.kichik.pecoff4j.io.DataWriter;
import com.kichik.pecoff4j.io.PEParser;
import com.kichik.pecoff4j.resources.GroupIconDirectory;
import com.kichik.pecoff4j.resources.GroupIconDirectoryEntry;
import com.kichik.pecoff4j.resources.IconDirectory;
import com.kichik.pecoff4j.resources.IconDirectoryEntry;
import com.kichik.pecoff4j.resources.IconImage;

public class IconExtractor {
	public static void extract(File pecoff, File outputDir) throws IOException {
		PE pe = PEParser.parse(pecoff);
		ResourceDirectory rd = pe.getImageData().getResourceTable();
		if (rd == null)
			return;
		ResourceEntry[] entries = ResourceHelper.findResources(rd,
				ResourceType.GROUP_ICON);
		for (int i = 0; i < entries.length; i++) {
			GroupIconDirectory gid = GroupIconDirectory.read(entries[i]
					.getData());
			IconFile icf = new IconFile();
			IconDirectory icd = new IconDirectory();
			icd.setType(1);
			icd.setReserved(0);
			icf.setDirectory(icd);
			IconImage[] images = new IconImage[gid.getCount()];
			icf.setImages(images);

			for (int j = 0; j < gid.getCount(); j++) {
				GroupIconDirectoryEntry gide = gid.getEntry(j);
				IconDirectoryEntry ide = new IconDirectoryEntry();
				ide.copyFrom(gide);
				icd.add(ide);
				ResourceEntry[] icos = ResourceHelper.findResources(rd,
						ResourceType.ICON, gide.getId());
				if (icos == null || icos.length != 1) {
					throw new IOException("Unexpected icons in resource file");
				}
				byte[] d = icos[0].getData();
				ide.setBytesInRes(d.length);
				// Check for PNG data
				if (gide.getWidth() == 0 && gide.getHeight() == 0) {
					IconImage ii = IconImage.readPNG(d);
					images[j] = ii;
				} else {
					IconImage ii = IconImage.readIcon(new DataReader(
							d), gide.getBytesInRes());
					images[j] = ii;
				}
			}

			File outFile = new File(outputDir, pecoff.getName() + "-icon" + i
					+ ".ico");
			DataWriter dw = new DataWriter(new FileOutputStream(outFile));
			icf.write(dw);
			dw.close();
		}
	}
}

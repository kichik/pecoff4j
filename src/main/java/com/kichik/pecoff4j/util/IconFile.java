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
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.kichik.pecoff4j.io.DataReader;
import com.kichik.pecoff4j.io.IDataReader;
import com.kichik.pecoff4j.io.IDataWriter;
import com.kichik.pecoff4j.resources.IconDirectory;
import com.kichik.pecoff4j.resources.IconDirectoryEntry;
import com.kichik.pecoff4j.resources.IconImage;

public class IconFile {
	private IconDirectory directory;
	private final Map<IconDirectoryEntry, IconImage> images = new HashMap<>();

	public static IconFile parse(String filename) throws IOException {
		return read(new DataReader(new FileInputStream(filename)));
	}

	public static IconFile parse(File file) throws IOException {
		return read(new DataReader(new FileInputStream(file)));
	}

	public static IconFile read(IDataReader dr) throws IOException {
		IconFile ic = new IconFile();
		ic.directory = IconDirectory.read(dr);
		for (IconDirectoryEntry entry : ic.directory.getEntries()) {
			dr.jumpTo(entry.getOffset());
			if (entry.getWidth() == 0) {
				byte[] data = new byte[entry.getBytesInRes()];
				dr.read(data);
				ic.images.put(entry, IconImage.readPNG(data));
			} else {
				ic.images.put(entry, IconImage.readIcon(dr, entry.getBytesInRes()));
			}
		}
		return ic;
	}

	public void write(IDataWriter dw) throws IOException {
		int offset = directory.sizeOf();
		for (IconDirectoryEntry entry : directory.getEntries()) {
			entry.setOffset(offset);
			offset += images.get(entry).sizeOf();
		}
		directory.write(dw);
		for (IconDirectoryEntry entry : directory.getEntries()) {
			images.get(entry).write(dw);
		}
	}

	public IconDirectory getDirectory() {
		return directory;
	}

	public void setDirectory(IconDirectory directory) {
		this.directory = directory;
	}

	@Deprecated
	public IconImage[] getImages() {
		return images.values().toArray(new IconImage[0]);
	}

	public IconImage getImage(IconDirectoryEntry entry) {
		return images.get(entry);
	}

	public void setImage(IconDirectoryEntry entry, IconImage iconImage) {
		images.put(entry, iconImage);
	}
}

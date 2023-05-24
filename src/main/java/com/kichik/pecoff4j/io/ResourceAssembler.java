/*******************************************************************************
 * This program and the accompanying materials
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at 
 * http://www.eclipse.org/legal/cpl-v10.html
 * 
 * Contributors:
 *     Peter Smith
 *******************************************************************************/
package com.kichik.pecoff4j.io;

import java.io.IOException;

import com.kichik.pecoff4j.resources.Bitmap;
import com.kichik.pecoff4j.resources.BitmapInfoHeader;
import com.kichik.pecoff4j.resources.FixedFileInfo;
import com.kichik.pecoff4j.resources.IconDirectory;
import com.kichik.pecoff4j.resources.IconDirectoryEntry;
import com.kichik.pecoff4j.resources.IconImage;
import com.kichik.pecoff4j.resources.RGBQuad;

@Deprecated
public class ResourceAssembler {
	/**
	 * @deprecated use {@link FixedFileInfo#write(IDataWriter)} instead
	 */
	@Deprecated
	public static void write(FixedFileInfo info, IDataWriter dw)
			throws IOException {
		info.write(dw);
	}

	/**
	 * @deprecated use {@link RGBQuad#write(IDataWriter)} instead
	 */
	@Deprecated
	public static void write(RGBQuad rgb, IDataWriter dw) throws IOException {
		rgb.write(dw);
	}

	/**
	 * @deprecated use {@link IconImage#write(IDataWriter)} instead
	 */
	@Deprecated
	public static void write(IconImage ii, IDataWriter dw) throws IOException {
		ii.write(dw);
	}

	/**
	 * @deprecated use {@link BitmapInfoHeader#write(IDataWriter)} instead
	 */
	@Deprecated
	public static void write(BitmapInfoHeader bih, IDataWriter dw)
			throws IOException {
		bih.write(dw);
	}

	/**
	 * @deprecated use {@link IconDirectoryEntry#write(IDataWriter)} instead
	 */
	@Deprecated
	public static void write(IconDirectoryEntry ide, IDataWriter dw)
			throws IOException {
		ide.write(dw);
	}

	/**
	 * @deprecated use {@link IconDirectory#write(IDataWriter)} instead
	 */
	@Deprecated
	public static void write(IconDirectory id, IDataWriter dw)
			throws IOException {
		id.write(dw);
	}
}

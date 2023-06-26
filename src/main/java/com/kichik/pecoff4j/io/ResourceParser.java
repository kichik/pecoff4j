/*******************************************************************************
 * This program and the accompanying materials
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at 
 * http://www.eclipse.org/legal/cpl-v10.html
 * 
 * Contributors:
 *     Peter Smith
 *     Amir Szekely
 *******************************************************************************/
package com.kichik.pecoff4j.io;

import java.io.IOException;

import com.kichik.pecoff4j.resources.Bitmap;
import com.kichik.pecoff4j.resources.BitmapFileHeader;
import com.kichik.pecoff4j.resources.BitmapInfoHeader;
import com.kichik.pecoff4j.resources.FixedFileInfo;
import com.kichik.pecoff4j.resources.IconDirectory;
import com.kichik.pecoff4j.resources.IconDirectoryEntry;
import com.kichik.pecoff4j.resources.IconImage;
import com.kichik.pecoff4j.resources.Manifest;
import com.kichik.pecoff4j.resources.RGBQuad;
import com.kichik.pecoff4j.resources.StringFileInfo;
import com.kichik.pecoff4j.resources.StringPair;
import com.kichik.pecoff4j.resources.StringTable;
import com.kichik.pecoff4j.resources.Var;
import com.kichik.pecoff4j.resources.VarFileInfo;
import com.kichik.pecoff4j.resources.VersionInfo;

@Deprecated
public class ResourceParser {
	/**
	 * @deprecated use {@link Bitmap#read(IDataReader)} instead
	 */
	@Deprecated
	public static Bitmap readBitmap(IDataReader dr) throws IOException {
		return Bitmap.read(dr);
	}

	/**
	 * @deprecated use {@link BitmapFileHeader#read(IDataReader)} instead
	 */
	@Deprecated
	public static BitmapFileHeader readBitmapFileHeader(IDataReader dr)
			throws IOException {
		return BitmapFileHeader.read(dr);
	}

	/**
	 * @deprecated use {@link BitmapInfoHeader#read(IDataReader)} instead
	 */
	@Deprecated
	public static BitmapInfoHeader readBitmapInfoHeader(IDataReader dr)
			throws IOException {
		return BitmapInfoHeader.read(dr);
	}

	/**
	 * @deprecated use {@link FixedFileInfo#read(IDataReader)} instead
	 */
	@Deprecated
	public static FixedFileInfo readFixedFileInfo(IDataReader dr)
			throws IOException {
		return FixedFileInfo.read(dr);
	}

	/**
	 * @deprecated use {@link IconImage#readIcon(IDataReader, int)} instead
	 */
	@Deprecated
	public static IconImage readIconImage(IDataReader dr, int bytesInRes)
			throws IOException {
		return IconImage.readIcon(dr, bytesInRes);
	}

	/**
	 * @deprecated use {@link IconImage#readPNG(byte[])} instead
	 */
	@Deprecated
	public static IconImage readPNG(byte[] data) {
		return IconImage.readPNG(data);
	}

	/**
	 * @deprecated use {@link VersionInfo#read(IDataReader)} instead
	 */
	@Deprecated
	public static VersionInfo readVersionInfo(byte[] data) throws IOException {
		return readVersionInfo(new DataReader(data));
	}

	/**
	 * @deprecated use {@link VersionInfo#read(IDataReader)} instead
	 */
	@Deprecated
	public static VersionInfo readVersionInfo(IDataReader dr)
			throws IOException {
		return VersionInfo.read(dr);
	}

	/**
	 * @deprecated use {@link VarFileInfo#readPartial(IDataReader, int, int, int, int, String)} instead
	 */
	@Deprecated
	public static VarFileInfo readVarFileInfo(IDataReader dr, int initialPos, int length, int valueLength, int type, String key) throws IOException {
		return VarFileInfo.readPartial(dr, initialPos, length, valueLength, type, key);
	}

	/**
	 * @deprecated use {@link Var#read(IDataReader)} instead
	 */
	@Deprecated
	public static Var readVar(IDataReader dr) throws IOException {
		return Var.read(dr);
	}

	/**
	 * @deprecated use {@link StringTable#read(IDataReader)} instead
	 */
	@Deprecated
	public static StringTable readStringTable(IDataReader dr)
			throws IOException {
		return StringTable.read(dr);
	}

	/**
	 * @deprecated use {@link StringPair#read(IDataReader)} instead
	 */
	@Deprecated
	public static StringPair readStringPair(IDataReader dr) throws IOException {
		return StringPair.read(dr);
	}

	/**
	 * @deprecated use {@link Manifest#read(IDataReader, int)} instead
	 */
	@Deprecated
	public static Manifest readManifest(IDataReader dr, int length)
			throws IOException {
		return Manifest.read(dr, length);
	}

	/**
	 * @deprecated use {@link RGBQuad#read(IDataReader)} instead
	 */
	@Deprecated
	public static RGBQuad readRGB(IDataReader dr) throws IOException {
		return RGBQuad.read(dr);
	}

	/**
	 * @deprecated use {@link StringFileInfo#readPartial(IDataReader, int, int, int, int, String)}  instead
	 */
	@Deprecated
	public static StringFileInfo readStringFileInfo(IDataReader dr, int initialPos, int length, int valueLength, int type, String key) throws IOException {
		return StringFileInfo.readPartial(dr, initialPos, length, valueLength, type, key);
	}

	/**
	 * @deprecated use {@link StringFileInfo#read(IDataReader)} instead
	 */
	@Deprecated
	public static StringFileInfo readStringFileInfo(IDataReader dr)
			throws IOException {
		return StringFileInfo.read(dr);
	}

	/**
	 * @deprecated use {@link IconDirectoryEntry#read(IDataReader)} instead
	 */
	@Deprecated
	public static IconDirectoryEntry readIconDirectoryEntry(IDataReader dr)
			throws IOException {
		return IconDirectoryEntry.read(dr);
	}

	/**
	 * @deprecated use {@link IconDirectory#read(IDataReader)} instead
	 */
	@Deprecated
	public static IconDirectory readIconDirectory(IDataReader dr)
			throws IOException {
		return IconDirectory.read(dr);
	}
}

/*******************************************************************************
 * This program and the accompanying materials
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at 
 * http://www.eclipse.org/legal/cpl-v10.html
 *
 * Contributors:
 *	 Peter Smith
 *******************************************************************************/
package com.kichik.pecoff4j.io;

import com.kichik.pecoff4j.AttributeCertificateTable;
import com.kichik.pecoff4j.COFFHeader;
import com.kichik.pecoff4j.DOSHeader;
import com.kichik.pecoff4j.DOSStub;
import com.kichik.pecoff4j.DebugDirectory;
import com.kichik.pecoff4j.ExportDirectory;
import com.kichik.pecoff4j.ImageDataDirectory;
import com.kichik.pecoff4j.ImportDirectory;
import com.kichik.pecoff4j.ImportDirectoryEntry;
import com.kichik.pecoff4j.ImportDirectoryTable;
import com.kichik.pecoff4j.ImportEntry;
import com.kichik.pecoff4j.LoadConfigDirectory;
import com.kichik.pecoff4j.OptionalHeader;
import com.kichik.pecoff4j.PE;
import com.kichik.pecoff4j.PESignature;
import com.kichik.pecoff4j.SectionHeader;
import com.kichik.pecoff4j.SectionTable;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class PEParser {
	public static PE parse(InputStream is) throws IOException {
		try (DataReader dr = new DataReader(is)) {
			return read(dr);
		}
	}

	public static PE parse(String filename) throws IOException {
		return parse(new File(filename));
	}

	public static PE parse(File file) throws IOException {
		try (FileInputStream is = new FileInputStream(file);
				DataReader dr = new DataReader(is)) {
			return read(dr);
		}
	}

	public static PE parse(Path path) throws IOException {
		try (InputStream is = Files.newInputStream(path);
			 DataReader dr = new DataReader(is)) {
			return read(dr);
		}
	}

	/**
	 * @deprecated use {@link PE#read(IDataReader)} instead
	 */
	@Deprecated
	public static PE read(IDataReader dr) throws IOException {
		return PE.read(dr);
	}

	/**
	 * @deprecated use {@link DOSHeader#read(IDataReader)} instead
	 */
	@Deprecated
	public static DOSHeader readDos(IDataReader dr) throws IOException {
		return DOSHeader.read(dr);
	}

	/**
	 * @deprecated use {@link DOSStub#read(DOSHeader, IDataReader)} instead
	 */
	@Deprecated
	public static DOSStub readStub(DOSHeader header, IDataReader dr)
			throws IOException {
		return DOSStub.read(header, dr);
	}

	/**
	 * @deprecated use {@link PESignature#read(IDataReader)} instead
	 */
	@Deprecated
	public static PESignature readSignature(IDataReader dr) throws IOException {
		return PESignature.read(dr);
	}

	/**
	 * @deprecated use {@link COFFHeader#read(IDataReader)} instead
	 */
	@Deprecated
	public static COFFHeader readCOFF(IDataReader dr) throws IOException {
		return COFFHeader.read(dr);
	}

	/**
	 * @deprecated use {@link OptionalHeader#read(IDataReader)} instead
	 */
	@Deprecated
	public static OptionalHeader readOptional(IDataReader dr)
			throws IOException {
		return OptionalHeader.read(dr);
	}

	/**
	 * @deprecated use {@link ImageDataDirectory#read(IDataReader)} instead
	 */
	@Deprecated
	public static ImageDataDirectory readImageDD(IDataReader dr)
			throws IOException {
		return ImageDataDirectory.read(dr);
	}

	/**
	 * @deprecated use {@link SectionTable#read(PE, IDataReader)} instead
	 */
	@Deprecated
	public static SectionTable readSectionHeaders(PE pe, IDataReader dr)
			throws IOException {
		return SectionTable.read(pe, dr);
	}

	/**
	 * @deprecated use {@link SectionHeader#read(IDataReader)} instead
	 */
	@Deprecated
	public static SectionHeader readSectionHeader(IDataReader dr)
			throws IOException {
		return SectionHeader.read(dr);
	}

	/**
	 * @deprecated use {@link PE#findNextEntry(int)} instead
	 */
	@Deprecated
	public static DataEntry findNextEntry(PE pe, int pos) {
		return pe.findNextEntry(pos);
	}

	/**
	 * @deprecated use {@link ImportDirectory#read(byte[], int)} instead
	 */
	@Deprecated
	public static ImportDirectory readImportDirectory(byte[] b, int baseAddress)
			throws IOException {
		return ImportDirectory.read(b, baseAddress);
	}

	/**
	 * @deprecated use {@link ImportDirectoryEntry#read(IDataReader)} instead
	 */
	@Deprecated
	public static ImportDirectoryEntry readImportDirectoryEntry(IDataReader dr)
			throws IOException {
		return ImportDirectoryEntry.read(dr);
	}

	/**
	 * @deprecated use {@link ImportDirectoryTable#read(IDataReader, int)} instead
	 */
	@Deprecated
	public static ImportDirectoryTable readImportDirectoryTable(IDataReader dr,
			int baseAddress) throws IOException {
		return ImportDirectoryTable.read(dr, baseAddress);
	}

	/**
	 * @deprecated use {@link ImportEntry#read(IDataReader)} instead
	 */
	@Deprecated
	public static ImportEntry readImportEntry(IDataReader dr)
			throws IOException {
		return ImportEntry.read(dr);
	}

	/**
	 * @deprecated use {@link ExportDirectory#read(byte[])} instead
	 */
	@Deprecated
	public static ExportDirectory readExportDirectory(byte[] b)
			throws IOException {
		return ExportDirectory.read(b);
	}

	/**
	 * @deprecated use {@link LoadConfigDirectory#read(PE, byte[])} instead
	 */
	@Deprecated
	public static LoadConfigDirectory readLoadConfigDirectory(PE pe, byte[] b)
			throws IOException {
		return LoadConfigDirectory.read(pe, b);
	}

	/**
	 * @deprecated use {@link DebugDirectory#read(byte[])} instead
	 */
	@Deprecated
	public static DebugDirectory readDebugDirectory(byte[] b)
			throws IOException {
		return readDebugDirectory(b, new DataReader(b));
	}

	/**
	 * @deprecated use {@link DebugDirectory#read(byte[])} instead
	 */
	@Deprecated
	public static DebugDirectory readDebugDirectory(byte[] b, IDataReader dr)
			throws IOException {
		return DebugDirectory.read(b);
	}

	/**
	 * @deprecated use {@link AttributeCertificateTable#read(byte[])} instead
	 */
	@Deprecated
	public static AttributeCertificateTable readAttributeCertificateTable(byte[] b)
			throws IOException {
		return readAttributeCertificateTable(b, new DataReader(b));
	}

	/**
	 * @deprecated use {@link AttributeCertificateTable#read(byte[])} instead
	 */
	@Deprecated
	public static AttributeCertificateTable readAttributeCertificateTable(byte[] b, IDataReader dr)
			throws IOException {
		return AttributeCertificateTable.read(b);
	}

}

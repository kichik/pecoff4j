package com.kichik.pecoff4j;

import com.kichik.pecoff4j.constant.ResourceType;
import com.kichik.pecoff4j.io.DataReader;
import com.kichik.pecoff4j.io.PEParser;
import com.kichik.pecoff4j.io.ValidatingWriter;
import com.kichik.pecoff4j.resources.GroupIconDirectory;
import com.kichik.pecoff4j.resources.GroupIconDirectoryEntry;
import com.kichik.pecoff4j.resources.IconImage;
import com.kichik.pecoff4j.resources.Manifest;
import com.kichik.pecoff4j.util.IconFile;
import com.kichik.pecoff4j.util.PaddingType;
import com.kichik.pecoff4j.util.ResourceHelper;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ResourceDirectoryTest {
	@Test
	public void testResourceDirectoryTable() throws IOException {
		ResourceDirectoryTable table = readResourceDirectory().getTable();

		assertEquals(0, table.getCharacteristics());
		assertEquals(0, table.getTimeDateStamp());
		assertEquals(4, table.getMajorVersion());
		assertEquals(0, table.getMinVersion());
		assertEquals(0, table.getNumNameEntries());
		assertEquals(4, table.getNumIdEntries());
	}

	@Test
	public void testResourceDirectoryModification() throws IOException {
		ResourceDirectory directory = readResourceDirectory();

		ResourceEntry resourceEntry = directory.getEntries().get(2);

		directory.getEntries().clear();
		directory.rebuild(0);

		ResourceDirectoryTable table = directory.getTable();
		assertEquals(0, table.getNumNameEntries());
		assertEquals(0, table.getNumIdEntries());

		directory.getEntries().add(resourceEntry);
		directory.rebuild(0);

		assertEquals(0, table.getNumNameEntries());
		assertEquals(1, table.getNumIdEntries());
	}

	@Test
	public void testResourceDirectoryCreateFromScratch() throws IOException {
		ResourceDirectory expectedDirectory = readResourceDirectory();

		ResourceDirectory directory = new ResourceDirectory();
		ResourceDirectoryTable table = new ResourceDirectoryTable();
		table.setMajorVersion(4);
		directory.setTable(table);

		// add icon from ico file
		IconFile iconFile = IconFile.read(new DataReader(getClass().getResourceAsStream("/WinRun4J.ico")));
		List<ResourceEntry> iconEntries = new ArrayList<>();
		for (IconImage iconImage : iconFile.getImages()) {
			iconEntries.add(entry(iconEntries.size() + 1,
					directory(entry(2057, iconImage.toByteArray()))));
		}
		directory.getEntries().add(entry(ResourceType.ICON,
				directory(iconEntries.toArray(new ResourceEntry[0]))));

		// add icon directory
		byte[] iconDirData = createIconDirectory(iconFile.getImages()).toByteArray();
		directory.getEntries().add(entry(ResourceType.GROUP_ICON,
				directory(entry(1, directory(entry(2057, iconDirData))))));

		// add version info: use it from expected file as it would be too much effort to create
		byte[] versionInfoData = ResourceHelper.findResources(
				expectedDirectory, ResourceType.VERSION_INFO)[0].getData();
		directory.getEntries().add(entry(ResourceType.VERSION_INFO,
				directory(entry(1, directory(entry(2057, versionInfoData))))));

		// add manifest
		Manifest manifest = new Manifest();
		manifest.set("<assembly xmlns=\"urn:schemas-microsoft-com:asm.v1\" manifestVersion=\"1.0\">\r\n</assembly>");
		directory.getEntries().add(entry(ResourceType.MANIFEST,
				directory(entry(1, directory(entry(1033, manifest.toByteArray()))))));

		directory.rebuild(53248);

		// validate against executable
		ValidatingWriter writer = new ValidatingWriter(new DataReader(expectedDirectory.toByteArray()));
		directory.write(writer);
		writer.assertEndOfStream();
	}

	@Test
	public void testPERebuild() throws IOException {
		PE pe = PEParser.parse(getClass().getResourceAsStream("/WinRun4J.exe"));
		pe.rebuild(PaddingType.PATTERN);
		fixSizeOfInitializedData(pe);

		ValidatingWriter writer = new ValidatingWriter(new DataReader(getClass().getResourceAsStream("/WinRun4J.exe")));
		pe.write(writer);
		writer.assertEndOfStream();
	}

	/**
	 * Test that adding the resources to WinRun4J-no-res.exe again produces the original WinRun4J.exe.
	 * Info: WinRun4J-no-res.exe is the same as WinRun4J.exe except that all resources have been removed (e.g. using
	 * <a href="http://www.angusj.com/resourcehacker/">Resource Hacker</a>).
	 */
	@Test
	public void testAddResourcesToExe() throws IOException {
		ResourceDirectory expectedDirectory = readResourceDirectory();

		PE pe = PEParser.parse(getClass().getResourceAsStream("/WinRun4J-no-res.exe"));
		ResourceDirectory directory = pe.getImageData().getResourceTable();

		// add icon from ico file
		IconFile iconFile = IconFile.read(new DataReader(getClass().getResourceAsStream("/WinRun4J.ico")));
		List<ResourceEntry> iconEntries = new ArrayList<>();
		for (IconImage iconImage : iconFile.getImages()) {
			iconEntries.add(entry(iconEntries.size() + 1,
					directory(entry(2057, iconImage.toByteArray()))));
		}
		directory.getEntries().add(entry(ResourceType.ICON,
				directory(iconEntries.toArray(new ResourceEntry[0]))));

		// add icon directory
		byte[] iconDirData = createIconDirectory(iconFile.getImages()).toByteArray();
		directory.getEntries().add(entry(ResourceType.GROUP_ICON,
				directory(entry(1, directory(entry(2057, iconDirData))))));

		// add version info: use it from expected file as it would be too much effort to create
		byte[] versionInfoData = ResourceHelper.findResources(
				expectedDirectory, ResourceType.VERSION_INFO)[0].getData();
		directory.getEntries().add(entry(ResourceType.VERSION_INFO,
				directory(entry(1, directory(entry(2057, versionInfoData))))));

		// add manifest
		Manifest manifest = new Manifest();
		manifest.set("<assembly xmlns=\"urn:schemas-microsoft-com:asm.v1\" manifestVersion=\"1.0\">\r\n</assembly>");
		directory.getEntries().add(entry(ResourceType.MANIFEST,
				directory(entry(1, directory(entry(1033, manifest.toByteArray()))))));

		pe.rebuild(PaddingType.PATTERN);
		fixSizeOfInitializedData(pe);

		// validate against executable
		ValidatingWriter writer = new ValidatingWriter(new DataReader(getClass().getResourceAsStream("/WinRun4J.exe")));
		pe.write(writer);
		writer.assertEndOfStream();
	}

	/**
	 * In WinRun4J.exe, only the raw data size has been summed up to calculate the size of initialized data.
	 * Most binaries seem to take the max of raw data and virtual size, which is therefore used in
	 * {@link PE#rebuild(PaddingType)}.
	 */
	private void fixSizeOfInitializedData(PE pe) {
		assertEquals(24576, pe.getOptionalHeader().getSizeOfInitializedData());
		// Change the value back for validation against original, including the checksum
		pe.getOptionalHeader().setSizeOfInitializedData(20480);
		pe.getOptionalHeader().setCheckSum(pe.getOptionalHeader().getCheckSum() - 4096);
	}

	private ResourceDirectory readResourceDirectory() throws IOException {
		PE pe = PEParser.parse(getClass().getResourceAsStream("/WinRun4J.exe"));
		return pe.getImageData().getResourceTable();
	}

	private GroupIconDirectory createIconDirectory(IconImage[] icons) {
		GroupIconDirectory directory = new GroupIconDirectory();
		directory.setReserved(0);
		directory.setType(1);

		int id = 1;
		for (IconImage icon : icons) {
			GroupIconDirectoryEntry entry = new GroupIconDirectoryEntry();
			entry.setWidth(icon.getHeader() != null ? icon.getHeader().getWidth() : 0);
			entry.setHeight(icon.getHeader() != null ? icon.getHeader().getHeight() / 2 : 0);
			entry.setColorCount(0);
			entry.setReserved(0);
			entry.setPlanes(icon.getHeader() != null ? icon.getHeader().getPlanes() : 1);
			entry.setBitCount(icon.getHeader() != null ? icon.getHeader().getBitCount() : 32);
			entry.setBytesInRes(icon.sizeOf());
			entry.setId(id++);
			directory.getEntries().add(entry);
		}

		return directory;
	}

	private ResourceEntry entry(int id, ResourceDirectory directory) {
		ResourceEntry entry = new ResourceEntry();
		entry.setId(id);
		entry.setDirectory(directory);
		return entry;
	}

	private ResourceEntry entry(int id, byte[] data) {
		ResourceEntry entry = new ResourceEntry();
		entry.setId(id);
		entry.setCodePage(1252);
		entry.setData(data);
		return entry;
	}

	private ResourceDirectory directory(ResourceEntry... entries) {
		ResourceDirectory dir = new ResourceDirectory();
		ResourceDirectoryTable table = new ResourceDirectoryTable();
		table.setMajorVersion(4);
		dir.setTable(table);
		for (ResourceEntry entry : entries) {
			dir.getEntries().add(entry);
		}
		return dir;
	}
}

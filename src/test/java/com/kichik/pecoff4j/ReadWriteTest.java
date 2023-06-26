package com.kichik.pecoff4j;

import com.kichik.pecoff4j.constant.ResourceType;
import com.kichik.pecoff4j.io.DataReader;
import com.kichik.pecoff4j.io.PEParser;
import com.kichik.pecoff4j.io.ValidatingWriter;
import com.kichik.pecoff4j.resources.GroupIconDirectory;
import com.kichik.pecoff4j.resources.IconImage;
import com.kichik.pecoff4j.resources.Manifest;
import com.kichik.pecoff4j.resources.VersionInfo;
import com.kichik.pecoff4j.util.ResourceHelper;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class ReadWriteTest {
    @Test
    public void testReadWriteExe() throws IOException {
        PE pe = PEParser.parse(getClass().getResourceAsStream("/WinRun4J.exe"));
        ValidatingWriter writer = new ValidatingWriter(new DataReader(getClass().getResourceAsStream("/WinRun4J.exe")));

        pe.write(writer);
        writer.assertEndOfStream();
    }

    @Test
    public void testReadWriteDll() throws IOException {
        PE pe = PEParser.parse(getClass().getResourceAsStream("/clr/ClassLibrary.dll"));
        ValidatingWriter writer = new ValidatingWriter(new DataReader(getClass().getResourceAsStream("/clr/ClassLibrary.dll")));

        pe.write(writer);
        writer.assertEndOfStream();
    }

    @Test
    public void testReadWriteResources() throws IOException {
        PE pe = PEParser.parse(getClass().getResourceAsStream("/WinRun4J.exe"));
        ValidatingWriter writer = new ValidatingWriter(new DataReader(pe.getSectionTable().findSection(".rsrc").getData()));

        pe.getImageData().getResourceTable().write(writer);
        writer.align(pe.getOptionalHeader().getFileAlignment());
        writer.assertEndOfStream();
    }

    @Test
    public void testReadWriteVersionInfo() throws IOException {
        PE pe = PEParser.parse(getClass().getResourceAsStream("/WinRun4J.exe"));

        ResourceDirectory rd = pe.getImageData().getResourceTable();
        ResourceEntry[] entries = ResourceHelper.findResources(rd, ResourceType.VERSION_INFO);
        for (ResourceEntry entry : entries) {
            VersionInfo versionInfo = VersionInfo.read(new DataReader(entry.getData()));
            ValidatingWriter writer = new ValidatingWriter(new DataReader(entry.getData()));
            versionInfo.write(writer);
            writer.assertEndOfStream();
        }
    }

    @Test
    public void testReadWriteManifest() throws IOException {
        PE pe = PEParser.parse(getClass().getResourceAsStream("/WinRun4J.exe"));

        ResourceDirectory rd = pe.getImageData().getResourceTable();
        ResourceEntry[] entries = ResourceHelper.findResources(rd, ResourceType.MANIFEST);
        for (ResourceEntry entry : entries) {
            Manifest manifest = Manifest.read(new DataReader(entry.getData()), entry.getData().length);
            ValidatingWriter writer = new ValidatingWriter(new DataReader(entry.getData()));
            manifest.write(writer);
            writer.assertEndOfStream();
        }
    }

    @Test
    public void testReadWriteIconGroups() throws IOException {
        PE pe = PEParser.parse(getClass().getResourceAsStream("/WinRun4J.exe"));

        ResourceDirectory rd = pe.getImageData().getResourceTable();
        ResourceEntry[] entries = ResourceHelper.findResources(rd, ResourceType.GROUP_ICON);
        for (ResourceEntry entry : entries) {
            GroupIconDirectory dir = GroupIconDirectory.read(new DataReader(entry.getData()));
            ValidatingWriter writer = new ValidatingWriter(new DataReader(entry.getData()));
            dir.write(writer);
            writer.assertEndOfStream();
        }
    }

    @Test
    public void testReadWriteIcons() throws IOException {
        PE pe = PEParser.parse(getClass().getResourceAsStream("/WinRun4J.exe"));

        ResourceDirectory rd = pe.getImageData().getResourceTable();
        ResourceEntry[] entries = ResourceHelper.findResources(rd, ResourceType.ICON);
        for (ResourceEntry entry : entries) {
            IconImage image = IconImage.readIcon(new DataReader(entry.getData()), entry.getData().length);
            ValidatingWriter writer = new ValidatingWriter(new DataReader(entry.getData()));
            image.write(writer);
            writer.assertEndOfStream();
        }
    }
}

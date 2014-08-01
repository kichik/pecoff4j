/*******************************************************************************
 * This program and the accompanying materials
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at 
 * http://www.eclipse.org/legal/cpl-v10.html
 * 
 * Contributors:
 *     Peter Smith
 *******************************************************************************/
package org.boris.pecoff4j.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.boris.pecoff4j.BoundImport;
import org.boris.pecoff4j.BoundImportDirectoryTable;
import org.boris.pecoff4j.COFFHeader;
import org.boris.pecoff4j.DOSHeader;
import org.boris.pecoff4j.DOSStub;
import org.boris.pecoff4j.DebugDirectory;
import org.boris.pecoff4j.ExportDirectory;
import org.boris.pecoff4j.ImageData;
import org.boris.pecoff4j.ImageDataDirectory;
import org.boris.pecoff4j.ImportDirectory;
import org.boris.pecoff4j.ImportDirectoryEntry;
import org.boris.pecoff4j.ImportDirectoryTable;
import org.boris.pecoff4j.ImportEntry;
import org.boris.pecoff4j.LoadConfigDirectory;
import org.boris.pecoff4j.OptionalHeader;
import org.boris.pecoff4j.PE;
import org.boris.pecoff4j.PESignature;
import org.boris.pecoff4j.RVAConverter;
import org.boris.pecoff4j.ResourceDirectory;
import org.boris.pecoff4j.ResourceDirectoryTable;
import org.boris.pecoff4j.ResourceEntry;
import org.boris.pecoff4j.SectionData;
import org.boris.pecoff4j.SectionHeader;
import org.boris.pecoff4j.SectionTable;
import org.boris.pecoff4j.constant.ImageDataDirectoryType;
import org.boris.pecoff4j.util.IntMap;

public class PEParser
{
    public static PE parse(InputStream is) throws IOException {
        return read(new DataReader(is));
    }

    public static PE parse(String filename) throws IOException {
        return parse(new File(filename));
    }

    public static PE parse(File file) throws IOException {
        return read(new DataReader(new FileInputStream(file)));
    }

    public static PE read(IDataReader dr) throws IOException {
        PE pe = new PE();
        pe.setDosHeader(readDos(dr));

        // Check if we have an old file type
        if (pe.getDosHeader().getAddressOfNewExeHeader() == 0 ||
                pe.getDosHeader().getAddressOfNewExeHeader() > 8192) {
            return pe;
        }

        pe.setStub(readStub(pe.getDosHeader(), dr));
        pe.setSignature(readSignature(dr));

        // Check signature to ensure we have a pe/coff file
        if (!pe.getSignature().isValid()) {
            return pe;
        }

        pe.setCoffHeader(readCOFF(dr));
        pe.setOptionalHeader(readOptional(dr));
        pe.setSectionTable(readSectionHeaders(pe, dr));

        // Now read the rest of the file
        DataEntry entry = null;
        while ((entry = findNextEntry(pe, dr.getPosition())) != null) {
            if (entry.isSection) {
                readSection(pe, entry, dr);
            } else if (entry.isDebugRawData) {
                readDebugRawData(pe, entry, dr);
            } else {
                readImageData(pe, entry, dr);
            }
        }

        // Read any trailing data
        byte[] tb = dr.readAll();
        if (tb.length > 0) {
            pe.getImageData().setTrailingData(tb);
        }

        return pe;
    }

    public static DOSHeader readDos(IDataReader dr) throws IOException {
        DOSHeader dh = new DOSHeader();
        dh.setMagic(dr.readWord());
        dh.setUsedBytesInLastPage(dr.readWord());
        dh.setFileSizeInPages(dr.readWord());
        dh.setNumRelocationItems(dr.readWord());
        dh.setHeaderSizeInParagraphs(dr.readWord());
        dh.setMinExtraParagraphs(dr.readWord());
        dh.setMaxExtraParagraphs(dr.readWord());
        dh.setInitialSS(dr.readWord());
        dh.setInitialSP(dr.readWord());
        dh.setChecksum(dr.readWord());
        dh.setInitialIP(dr.readWord());
        dh.setInitialRelativeCS(dr.readWord());
        dh.setAddressOfRelocationTable(dr.readWord());
        dh.setOverlayNumber(dr.readWord());
        int[] reserved = new int[4];
        for (int i = 0; i < reserved.length; i++) {
            reserved[i] = dr.readWord();
        }
        dh.setReserved(reserved);
        dh.setOemId(dr.readWord());
        dh.setOemInfo(dr.readWord());
        int[] reserved2 = new int[10];
        for (int i = 0; i < reserved2.length; i++) {
            reserved2[i] = dr.readWord();
        }
        dh.setReserved2(reserved2);
        dh.setAddressOfNewExeHeader(dr.readDoubleWord());

        // calc stub size
        int stubSize = dh.getFileSizeInPages() * 512 -
                (512 - dh.getUsedBytesInLastPage());
        if (stubSize > dh.getAddressOfNewExeHeader())
            stubSize = dh.getAddressOfNewExeHeader();
        stubSize -= dh.getHeaderSizeInParagraphs() * 16;
        dh.setStubSize(stubSize);

        return dh;
    }

    public static DOSStub readStub(DOSHeader header, IDataReader dr)
            throws IOException {
        DOSStub ds = new DOSStub();
        int pos = dr.getPosition();
        int add = header.getAddressOfNewExeHeader();
        byte[] stub = new byte[add - pos];
        dr.read(stub);
        ds.setStub(stub);
        return ds;
    }

    public static PESignature readSignature(IDataReader dr) throws IOException {
        PESignature ps = new PESignature();
        byte[] signature = new byte[4];
        dr.read(signature);
        ps.setSignature(signature);
        return ps;
    }

    public static COFFHeader readCOFF(IDataReader dr) throws IOException {
        COFFHeader h = new COFFHeader();
        h.setMachine(dr.readWord());
        h.setNumberOfSections(dr.readWord());
        h.setTimeDateStamp(dr.readDoubleWord());
        h.setPointerToSymbolTable(dr.readDoubleWord());
        h.setNumberOfSymbols(dr.readDoubleWord());
        h.setSizeOfOptionalHeader(dr.readWord());
        h.setCharacteristics(dr.readWord());
        return h;
    }

    public static OptionalHeader readOptional(IDataReader dr)
            throws IOException {
        OptionalHeader oh = new OptionalHeader();
        oh.setMagic(dr.readWord());
        boolean is64 = oh.isPE32plus();
        oh.setMajorLinkerVersion(dr.readByte());
        oh.setMinorLinkerVersion(dr.readByte());
        oh.setSizeOfCode(dr.readDoubleWord());
        oh.setSizeOfInitializedData(dr.readDoubleWord());
        oh.setSizeOfUninitializedData(dr.readDoubleWord());
        oh.setAddressOfEntryPoint(dr.readDoubleWord());
        oh.setBaseOfCode(dr.readDoubleWord());

        if (!is64)
            oh.setBaseOfData(dr.readDoubleWord());

        // NT additional fields.
        oh.setImageBase(is64 ? dr.readLong() : dr.readDoubleWord());
        oh.setSectionAlignment(dr.readDoubleWord());
        oh.setFileAlignment(dr.readDoubleWord());
        oh.setMajorOperatingSystemVersion(dr.readWord());
        oh.setMinorOperatingSystemVersion(dr.readWord());
        oh.setMajorImageVersion(dr.readWord());
        oh.setMinorImageVersion(dr.readWord());
        oh.setMajorSubsystemVersion(dr.readWord());
        oh.setMinorSubsystemVersion(dr.readWord());
        oh.setWin32VersionValue(dr.readDoubleWord());
        oh.setSizeOfImage(dr.readDoubleWord());
        oh.setSizeOfHeaders(dr.readDoubleWord());
        oh.setCheckSum(dr.readDoubleWord());
        oh.setSubsystem(dr.readWord());
        oh.setDllCharacteristics(dr.readWord());
        oh.setSizeOfStackReserve(is64 ? dr.readLong() : dr.readDoubleWord());
        oh.setSizeOfStackCommit(is64 ? dr.readLong() : dr.readDoubleWord());
        oh.setSizeOfHeapReserve(is64 ? dr.readLong() : dr.readDoubleWord());
        oh.setSizeOfHeapCommit(is64 ? dr.readLong() : dr.readDoubleWord());
        oh.setLoaderFlags(dr.readDoubleWord());
        oh.setNumberOfRvaAndSizes(dr.readDoubleWord());

        // Data directories
        ImageDataDirectory[] dds = new ImageDataDirectory[16];
        for (int i = 0; i < dds.length; i++) {
            dds[i] = readImageDD(dr);
        }
        oh.setDataDirectories(dds);

        return oh;
    }

    public static ImageDataDirectory readImageDD(IDataReader dr)
            throws IOException {
        ImageDataDirectory idd = new ImageDataDirectory();
        idd.setVirtualAddress(dr.readDoubleWord());
        idd.setSize(dr.readDoubleWord());
        return idd;
    }

    public static SectionTable readSectionHeaders(PE pe, IDataReader dr)
            throws IOException {
        SectionTable st = new SectionTable();
        int ns = pe.getCoffHeader().getNumberOfSections();
        for (int i = 0; i < ns; i++) {
            st.add(readSectionHeader(dr));
        }

        SectionHeader[] sorted = st.getHeadersPointerSorted();
        int[] virtualAddress = new int[sorted.length];
        int[] pointerToRawData = new int[sorted.length];
        for (int i = 0; i < sorted.length; i++) {
            virtualAddress[i] = sorted[i].getVirtualAddress();
            pointerToRawData[i] = sorted[i].getPointerToRawData();
        }

        st.setRvaConverter(new RVAConverter(virtualAddress, pointerToRawData));
        return st;
    }

    public static SectionHeader readSectionHeader(IDataReader dr)
            throws IOException {
        SectionHeader sh = new SectionHeader();
        sh.setName(dr.readUtf(8));
        sh.setVirtualSize(dr.readDoubleWord());
        sh.setVirtualAddress(dr.readDoubleWord());
        sh.setSizeOfRawData(dr.readDoubleWord());
        sh.setPointerToRawData(dr.readDoubleWord());
        sh.setPointerToRelocations(dr.readDoubleWord());
        sh.setPointerToLineNumbers(dr.readDoubleWord());
        sh.setNumberOfRelocations(dr.readWord());
        sh.setNumberOfLineNumbers(dr.readWord());
        sh.setCharacteristics(dr.readDoubleWord());
        return sh;
    }

    public static DataEntry findNextEntry(PE pe, int pos) {
        DataEntry de = new DataEntry();

        // Check sections first
        int ns = pe.getCoffHeader().getNumberOfSections();
        for (int i = 0; i < ns; i++) {
            SectionHeader sh = pe.getSectionTable().getHeader(i);
            if (sh.getSizeOfRawData() > 0 && sh.getPointerToRawData() >= pos &&
                    (de.pointer == 0 || sh.getPointerToRawData() < de.pointer)) {
                de.pointer = sh.getPointerToRawData();
                de.index = i;
                de.isSection = true;
            }
        }

        // Now check image data directories
        RVAConverter rvc = pe.getSectionTable().getRVAConverter();
        int dc = pe.getOptionalHeader().getDataDirectoryCount();
        for (int i = 0; i < dc; i++) {
            ImageDataDirectory idd = pe.getOptionalHeader().getDataDirectory(i);
            if (idd.getSize() > 0) {
                int prd = idd.getVirtualAddress();
                // Assume certificate live outside section ?
                if (i != ImageDataDirectoryType.CERTIFICATE_TABLE &&
                        isInsideSection(pe, idd)) {
                    prd = rvc.convertVirtualAddressToRawDataPointer(idd
                            .getVirtualAddress());
                }
                if (prd >= pos && (de.pointer == 0 || prd < de.pointer)) {
                    de.pointer = prd;
                    de.index = i;
                    de.isSection = false;
                }
            }
        }

        // Check debug
        ImageData id = pe.getImageData();
        DebugDirectory dd = null;
        if (id != null)
            dd = id.getDebug();
        if (dd != null) {
            int prd = dd.getPointerToRawData();
            if (prd >= pos && (de.pointer == 0 || prd < de.pointer)) {
                de.pointer = prd;
                de.index = -1;
                de.isDebugRawData = true;
                de.isSection = false;
                de.baseAddress = prd;
            }
        }

        if (de.pointer == 0)
            return null;

        return de;
    }

    private static boolean isInsideSection(PE pe, ImageDataDirectory idd) {
        int prd = idd.getVirtualAddress();
        int pex = prd + idd.getSize();
        SectionTable st = pe.getSectionTable();
        int ns = st.getNumberOfSections();
        for (int i = 0; i < ns; i++) {
            SectionHeader sh = st.getHeader(i);
            int vad = sh.getVirtualAddress();
            int vex = vad + sh.getVirtualSize();
            if (prd >= vad && prd < vex && pex <= vex)
                return true;
        }
        return false;
    }

    private static void readImageData(PE pe, DataEntry entry, IDataReader dr)
            throws IOException {

        // Read any preamble data
        ImageData id = pe.getImageData();
        byte[] pa = readPreambleData(entry.pointer, dr);
        if (pa != null)
            id.put(entry.index, pa);

        // Read the image data
        ImageDataDirectory idd = pe.getOptionalHeader().getDataDirectory(
                entry.index);
        byte[] b = new byte[idd.getSize()];
        dr.read(b);

        switch (entry.index) {
        case ImageDataDirectoryType.EXPORT_TABLE:
            id.setExportTable(readExportDirectory(b));
            break;
        case ImageDataDirectoryType.IMPORT_TABLE:
            id.setImportTable(readImportDirectory(b, entry.baseAddress));
            break;
        case ImageDataDirectoryType.RESOURCE_TABLE:
            id.setResourceTable(readResourceDirectory(b, entry.baseAddress));
            break;
        case ImageDataDirectoryType.EXCEPTION_TABLE:
            id.setExceptionTable(b);
            break;
        case ImageDataDirectoryType.CERTIFICATE_TABLE:
            id.setCertificateTable(b);
            break;
        case ImageDataDirectoryType.BASE_RELOCATION_TABLE:
            id.setBaseRelocationTable(b);
            break;
        case ImageDataDirectoryType.DEBUG:
            id.setDebug(readDebugDirectory(b));
            break;
        case ImageDataDirectoryType.ARCHITECTURE:
            id.setArchitecture(b);
            break;
        case ImageDataDirectoryType.GLOBAL_PTR:
            id.setGlobalPtr(b);
            break;
        case ImageDataDirectoryType.TLS_TABLE:
            id.setTlsTable(b);
            break;
        case ImageDataDirectoryType.LOAD_CONFIG_TABLE:
            id.setLoadConfigTable(readLoadConfigDirectory(b));
            break;
        case ImageDataDirectoryType.BOUND_IMPORT:
            id.setBoundImports(readBoundImportDirectoryTable(b));
            break;
        case ImageDataDirectoryType.IAT:
            id.setIat(b);
            break;
        case ImageDataDirectoryType.DELAY_IMPORT_DESCRIPTOR:
            id.setDelayImportDescriptor(b);
            break;
        case ImageDataDirectoryType.CLR_RUNTIME_HEADER:
            id.setClrRuntimeHeader(b);
            break;
        case ImageDataDirectoryType.RESERVED:
            id.setReserved(b);
            break;
        }
    }

    private static byte[] readPreambleData(int pointer, IDataReader dr)
            throws IOException {
        if (pointer > dr.getPosition()) {
            byte[] pa = new byte[pointer - dr.getPosition()];
            dr.read(pa);
            boolean zeroes = true;
            for (int i = 0; i < pa.length; i++) {
                if (pa[i] != 0) {
                    zeroes = false;
                    break;
                }
            }
            if (!zeroes)
                return pa;
        }

        return null;
    }

    private static void readDebugRawData(PE pe, DataEntry entry, IDataReader dr)
            throws IOException {
        // Read any preamble data
        ImageData id = pe.getImageData();
        byte[] pa = readPreambleData(entry.pointer, dr);
        if (pa != null)
            id.setDebugRawDataPreamble(pa);
        DebugDirectory dd = id.getDebug();
        byte[] b = new byte[dd.getSizeOfData()];
        dr.read(b);
        id.setDebugRawData(b);
    }

    private static void readSection(PE pe, DataEntry entry, IDataReader dr)
            throws IOException {
        SectionTable st = pe.getSectionTable();
        SectionHeader sh = st.getHeader(entry.index);
        SectionData sd = new SectionData();

        // Read any preamble - store if non-zero
        byte[] pa = readPreambleData(sh.getPointerToRawData(), dr);
        if (pa != null)
            sd.setPreamble(pa);

        // Read in the raw data block
        dr.jumpTo(sh.getPointerToRawData());
        byte[] b = new byte[sh.getSizeOfRawData()];
        dr.read(b);
        sd.setData(b);
        st.put(entry.index, sd);

        // Check for an image directory within this section
        int ddc = pe.getOptionalHeader().getDataDirectoryCount();
        for (int i = 0; i < ddc; i++) {
            if (i == ImageDataDirectoryType.CERTIFICATE_TABLE)
                continue;
            ImageDataDirectory idd = pe.getOptionalHeader().getDataDirectory(i);
            if (idd.getSize() > 0) {
                int vad = sh.getVirtualAddress();
                int vex = vad + sh.getVirtualSize();
                int dad = idd.getVirtualAddress();
                if (dad >= vad && dad < vex) {
                    int off = dad - vad;
                    IDataReader idr = new ByteArrayDataReader(b, off, idd
                            .getSize());
                    DataEntry de = new DataEntry(i, 0);
                    de.baseAddress = sh.getVirtualAddress();
                    readImageData(pe, de, idr);
                }
            }
        }
    }

    private static BoundImportDirectoryTable readBoundImportDirectoryTable(
            byte[] b) throws IOException {
        DataReader dr = new DataReader(b);
        BoundImportDirectoryTable bidt = new BoundImportDirectoryTable();
        List<BoundImport> imports = new ArrayList<BoundImport>();
        BoundImport bi = null;
        while ((bi = readBoundImport(dr)) != null) {
            bidt.add(bi);
            imports.add(bi);
        }
        Collections.sort(imports, new Comparator<BoundImport>() {
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

    private static BoundImport readBoundImport(IDataReader dr)
            throws IOException {
        BoundImport bi = new BoundImport();
        bi.setTimestamp(dr.readDoubleWord());
        bi.setOffsetToModuleName(dr.readWord());
        bi.setNumberOfModuleForwarderRefs(dr.readWord());

        if (bi.getTimestamp() == 0 && bi.getOffsetToModuleName() == 0 &&
                bi.getNumberOfModuleForwarderRefs() == 0)
            return null;

        return bi;
    }

    public static ImportDirectory readImportDirectory(byte[] b, int baseAddress)
            throws IOException {
        DataReader dr = new DataReader(b);
        ImportDirectory id = new ImportDirectory();
        ImportDirectoryEntry ide = null;
        while ((ide = readImportDirectoryEntry(dr)) != null) {
            id.add(ide);
        }

        /* FIXME - name table refer to data outside image directory
        for (int i = 0; i < id.size(); i++) {
            ImportDirectoryEntry e = id.getEntry(i);
            dr.jumpTo(e.getNameRVA() - baseAddress);
            String name = dr.readUtf();
            dr.jumpTo(e.getImportLookupTableRVA() - baseAddress);
            ImportDirectoryTable nt = readImportDirectoryTable(dr, baseAddress);
            dr.jumpTo(e.getImportAddressTableRVA() - baseAddress);
            ImportDirectoryTable at = null; // readImportDirectoryTable(dr,
            // baseAddress);
            id.add(name, nt, at);
        }*/

        return id;
    }

    public static ImportDirectoryEntry readImportDirectoryEntry(IDataReader dr)
            throws IOException {
        ImportDirectoryEntry id = new ImportDirectoryEntry();
        id.setImportLookupTableRVA(dr.readDoubleWord());
        id.setTimeDateStamp(dr.readDoubleWord());
        id.setForwarderChain(dr.readDoubleWord());
        id.setNameRVA(dr.readDoubleWord());
        id.setImportAddressTableRVA(dr.readDoubleWord());

        // The last entry is null
        if (id.getImportLookupTableRVA() == 0) {
            return null;
        }

        return id;
    }

    public static ImportDirectoryTable readImportDirectoryTable(IDataReader dr,
            int baseAddress) throws IOException {
        ImportDirectoryTable idt = new ImportDirectoryTable();
        ImportEntry ie = null;
        while ((ie = readImportEntry(dr)) != null) {
            idt.add(ie);
        }

        for (int i = 0; i < idt.size(); i++) {
            ImportEntry iee = idt.getEntry(i);
            if ((iee.getVal() & 0x80000000) != 0) {
                iee.setOrdinal(iee.getVal() & 0x7fffffff);
            } else {
                dr.jumpTo(iee.getVal() - baseAddress);
                dr.readWord(); // FIXME this is an index into the export table
                iee.setName(dr.readUtf());
            }
        }
        return idt;
    }

    public static ImportEntry readImportEntry(IDataReader dr)
            throws IOException {
        ImportEntry ie = new ImportEntry();
        ie.setVal(dr.readDoubleWord());
        if (ie.getVal() == 0) {
            return null;
        }

        return ie;
    }

    public static ExportDirectory readExportDirectory(byte[] b)
            throws IOException {
        DataReader dr = new DataReader(b);
        ExportDirectory edt = new ExportDirectory();
        edt.set(b);
        edt.setExportFlags(dr.readDoubleWord());
        edt.setTimeDateStamp(dr.readDoubleWord());
        edt.setMajorVersion(dr.readWord());
        edt.setMinorVersion(dr.readWord());
        edt.setNameRVA(dr.readDoubleWord());
        edt.setOrdinalBase(dr.readDoubleWord());
        edt.setAddressTableEntries(dr.readDoubleWord());
        edt.setNumberOfNamePointers(dr.readDoubleWord());
        edt.setExportAddressTableRVA(dr.readDoubleWord());
        edt.setNamePointerRVA(dr.readDoubleWord());
        edt.setOrdinalTableRVA(dr.readDoubleWord());
        return edt;
    }

    public static LoadConfigDirectory readLoadConfigDirectory(byte[] b)
            throws IOException {
        DataReader dr = new DataReader(b);
        LoadConfigDirectory lcd = new LoadConfigDirectory();
        lcd.set(b);
        lcd.setCharacteristics(dr.readDoubleWord());
        lcd.setTimeDateStamp(dr.readDoubleWord());
        lcd.setMajorVersion(dr.readWord());
        lcd.setMinorVersion(dr.readWord());
        lcd.setGlobalFlagsClear(dr.readDoubleWord());
        lcd.setGlobalFlagsSet(dr.readDoubleWord());
        lcd.setCriticalSectionDefaultTimeout(dr.readDoubleWord());
        lcd.setDeCommitFreeBlockThreshold(dr.readLong());
        lcd.setDeCommitTotalFreeThreshold(dr.readLong());
        lcd.setLockPrefixTable(dr.readLong());
        lcd.setMaximumAllocationSize(dr.readLong());
        lcd.setVirtualMemoryThreshold(dr.readLong());
        lcd.setProcessAffinityMask(dr.readLong());
        lcd.setProcessHeapFlags(dr.readDoubleWord());
        lcd.setCsdVersion(dr.readWord());
        lcd.setReserved(dr.readWord());
        lcd.setEditList(dr.readLong());
        lcd.setSecurityCookie(dr.readDoubleWord());
        lcd.setSeHandlerTable(dr.readDoubleWord());
        lcd.setSeHandlerCount(dr.readDoubleWord());

        return lcd;
    }

    public static DebugDirectory readDebugDirectory(byte[] b)
            throws IOException {
        return readDebugDirectory(b, new DataReader(b));
    }

    public static DebugDirectory readDebugDirectory(byte[] b, IDataReader dr)
            throws IOException {
        DebugDirectory dd = new DebugDirectory();
        dd.set(b);
        dd.setCharacteristics(dr.readDoubleWord());
        dd.setTimeDateStamp(dr.readDoubleWord());
        dd.setMajorVersion(dr.readWord());
        dd.setMajorVersion(dr.readWord());
        dd.setType(dr.readDoubleWord());
        dd.setSizeOfData(dr.readDoubleWord());
        dd.setAddressOfRawData(dr.readDoubleWord());
        dd.setPointerToRawData(dr.readDoubleWord());
        return dd;
    }

    private static ResourceDirectory readResourceDirectory(byte[] b,
            int baseAddress) throws IOException {
        IDataReader dr = new ByteArrayDataReader(b);
        return readResourceDirectory(dr, baseAddress);
    }

    private static ResourceDirectory readResourceDirectory(IDataReader dr,
            int baseAddress) throws IOException {
        ResourceDirectory d = new ResourceDirectory();
        d.setTable(readResourceDirectoryTable(dr));
        int ne = d.getTable().getNumNameEntries() +
                d.getTable().getNumIdEntries();
        for (int i = 0; i < ne; i++) {
            d.add(readResourceEntry(dr, baseAddress));
        }

        return d;
    }

    private static ResourceEntry readResourceEntry(IDataReader dr,
            int baseAddress) throws IOException {
        ResourceEntry re = new ResourceEntry();
        int id = dr.readDoubleWord();
        int offset = dr.readDoubleWord();
        int pos = dr.getPosition();
        if ((id & 0x80000000) != 0) {
            dr.jumpTo(id & 0x7fffffff);
            re.setName(dr.readUnicode());
        } else {
            re.setId(id);
        }
        if ((offset & 0x80000000) != 0) {
            dr.jumpTo(offset & 0x7fffffff);
            re.setDirectory(readResourceDirectory(dr, baseAddress));
        } else {
            dr.jumpTo(offset);
            int rva = dr.readDoubleWord();
            int size = dr.readDoubleWord();
            int cp = dr.readDoubleWord();
            int res = dr.readDoubleWord();
            re.setCodePage(cp);
            re.setReserved(res);
            dr.jumpTo(rva - baseAddress);
            byte[] b = new byte[size];
            dr.read(b);
            re.setData(b);
        }
        dr.jumpTo(pos);
        return re;
    }

    private static ResourceDirectoryTable readResourceDirectoryTable(
            IDataReader dr) throws IOException {
        ResourceDirectoryTable t = new ResourceDirectoryTable();
        t.setCharacteristics(dr.readDoubleWord());
        t.setTimeDateStamp(dr.readDoubleWord());
        t.setMajorVersion(dr.readWord());
        t.setMinVersion(dr.readWord());
        t.setNumNameEntries(dr.readWord());
        t.setNumIdEntries(dr.readWord());

        return t;
    }
}

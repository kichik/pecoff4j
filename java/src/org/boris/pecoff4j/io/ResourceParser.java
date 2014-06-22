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
package org.boris.pecoff4j.io;

import java.io.IOException;

import org.boris.pecoff4j.resources.Bitmap;
import org.boris.pecoff4j.resources.BitmapFileHeader;
import org.boris.pecoff4j.resources.BitmapInfoHeader;
import org.boris.pecoff4j.resources.FixedFileInfo;
import org.boris.pecoff4j.resources.IconDirectory;
import org.boris.pecoff4j.resources.IconDirectoryEntry;
import org.boris.pecoff4j.resources.IconImage;
import org.boris.pecoff4j.resources.Manifest;
import org.boris.pecoff4j.resources.RGBQuad;
import org.boris.pecoff4j.resources.StringFileInfo;
import org.boris.pecoff4j.resources.StringPair;
import org.boris.pecoff4j.resources.StringTable;
import org.boris.pecoff4j.resources.VarFileInfo;
import org.boris.pecoff4j.resources.VersionInfo;

public class ResourceParser
{
    public static Bitmap readBitmap(IDataReader dr) throws IOException {
        Bitmap bm = new Bitmap();
        bm.setFileHeader(readBitmapFileHeader(dr));
        bm.setInfoHeader(readBitmapInfoHeader(dr));

        return bm;
    }

    public static BitmapFileHeader readBitmapFileHeader(IDataReader dr)
            throws IOException {
        BitmapFileHeader bfh = new BitmapFileHeader();
        bfh.setType(dr.readWord());
        bfh.setSize(dr.readDoubleWord());
        bfh.setReserved1(dr.readWord());
        bfh.setReserved2(dr.readWord());
        bfh.setOffBits(dr.readDoubleWord());

        return bfh;
    }

    public static BitmapInfoHeader readBitmapInfoHeader(IDataReader dr)
            throws IOException {
        BitmapInfoHeader bh = new BitmapInfoHeader();
        bh.setSize(dr.readDoubleWord());
        bh.setWidth(dr.readDoubleWord());
        bh.setHeight(dr.readDoubleWord());
        bh.setPlanes(dr.readWord());
        bh.setBitCount(dr.readWord());
        bh.setCompression(dr.readDoubleWord());
        bh.setSizeImage(dr.readDoubleWord());
        bh.setXpelsPerMeter(dr.readDoubleWord());
        bh.setYpelsPerMeter(dr.readDoubleWord());
        bh.setClrUsed(dr.readDoubleWord());
        bh.setClrImportant(dr.readDoubleWord());

        return bh;
    }

    public static FixedFileInfo readFixedFileInfo(IDataReader dr)
            throws IOException {
        FixedFileInfo ffi = new FixedFileInfo();
        ffi.setSignature(dr.readDoubleWord());
        ffi.setStrucVersion(dr.readDoubleWord());
        ffi.setFileVersionMS(dr.readDoubleWord());
        ffi.setFileVersionLS(dr.readDoubleWord());
        ffi.setProductVersionMS(dr.readDoubleWord());
        ffi.setProductVersionLS(dr.readDoubleWord());
        ffi.setFileFlagMask(dr.readDoubleWord());
        ffi.setFileFlags(dr.readDoubleWord());
        ffi.setFileOS(dr.readDoubleWord());
        ffi.setFileType(dr.readDoubleWord());
        ffi.setFileSubtype(dr.readDoubleWord());
        ffi.setFileDateMS(dr.readDoubleWord());
        ffi.setFileDateLS(dr.readDoubleWord());
        return ffi;
    }

    public static IconImage readIconImage(IDataReader dr, int bytesInRes)
            throws IOException {
        IconImage ii = new IconImage();
        int quadSize = 0;
        ii.setHeader(readBitmapInfoHeader(dr));
        if (ii.getHeader().getClrUsed() != 0) {
            quadSize = ii.getHeader().getClrUsed();
        } else {
            if (ii.getHeader().getBitCount() <= 8) {
                quadSize = 1 << ii.getHeader().getBitCount();
            } else {
                quadSize = 0;
            }
        }

        int numBytesPerLine = ((((ii.getHeader().getWidth() *
                ii.getHeader().getPlanes() * ii.getHeader().getBitCount()) + 31) >> 5) << 2);
        int xorSize = numBytesPerLine * ii.getHeader().getHeight() / 2;
        int andSize = bytesInRes - (quadSize * 4) - ii.getHeader().getSize() -
                xorSize;

        if (quadSize > 0) {
            RGBQuad[] colors = new RGBQuad[quadSize];
            for (int i = 0; i < quadSize; i++) {
                colors[i] = readRGB(dr);
            }
            ii.setColors(colors);
        }

        byte[] xorMask = new byte[xorSize];
        dr.read(xorMask);
        ii.setXorMask(xorMask);

        byte[] andMask = new byte[andSize];
        dr.read(andMask);
        ii.setAndMask(andMask);

        return ii;
    }

    public static IconImage readPNG(byte[] data) {
        IconImage ii = new IconImage();
        ii.setPngData(data);
        return ii;
    }

    public static VersionInfo readVersionInfo(byte[] data) throws IOException {
        return readVersionInfo(new DataReader(data));
    }

    public static VersionInfo readVersionInfo(IDataReader dr)
            throws IOException {
        VersionInfo vi = new VersionInfo();
        vi.setLength(dr.readWord());
        vi.setValueLength(dr.readWord());
        vi.setType(dr.readWord());
        vi.setKey(dr.readUnicode());
        alignDataReader(dr);
        vi.setFixedFileInfo(ResourceParser.readFixedFileInfo(dr));
        vi.setStringFileInfo(readStringFileInfo(dr));
        // TODO read VarFileInfo

        return vi;
    }

    public static VarFileInfo readVarFileInfo(IDataReader dr)
            throws IOException {
        VarFileInfo vfi = new VarFileInfo();
        vfi.setKey(dr.readUnicode());
        String name = null;
        while ((name = dr.readUnicode()) != null) {
            if (name.length() % 2 == 1)
                dr.readWord();
            vfi.add(name, dr.readUnicode());
        }
        return vfi;
    }

    public static StringTable readStringTable(IDataReader dr)
            throws IOException {
    	int initialPos = dr.getPosition();
    	
        StringTable vfi = new StringTable();
        vfi.setLength(dr.readWord());
        if (vfi.getLength() == 0) {
            return null;
        }
        vfi.setValueLength(dr.readWord());
        vfi.setType(dr.readWord());
        vfi.setKey(dr.readUnicode());
        vfi.setPadding(alignDataReader(dr));
        
        while (dr.getPosition() - initialPos < vfi.getLength())
        	vfi.add(readStringPair(dr));
        
        return vfi;
    }

    public static StringPair readStringPair(IDataReader dr) throws IOException {
        StringPair sp = new StringPair();
        sp.setLength(dr.readWord());
        sp.setValueLength(dr.readWord());
        sp.setType(dr.readWord());
        sp.setKey(dr.readUnicode());
        sp.setPadding(alignDataReader(dr));
       	sp.setValue(dr.readUnicode(sp.getValueLength()));
        alignDataReader(dr);
        return sp;
    }

    public static Manifest readManifest(IDataReader dr, int length)
            throws IOException {
        Manifest mf = new Manifest();
        mf.set(dr.readUtf(length));
        return mf;
    }

    public static RGBQuad readRGB(IDataReader dr) throws IOException {
        RGBQuad r = new RGBQuad();
        r.setBlue(dr.readByte());
        r.setGreen(dr.readByte());
        r.setRed(dr.readByte());
        r.setReserved(dr.readByte());
        return r;
    }

    public static StringFileInfo readStringFileInfo(IDataReader dr)
            throws IOException {
    	int initialPos = dr.getPosition();
    	
        StringFileInfo sfi = new StringFileInfo();
        
        sfi.setLength(dr.readWord());
        sfi.setValueLength(dr.readWord());
        sfi.setType(dr.readWord());
        sfi.setKey(dr.readUnicode());
        sfi.setPadding(alignDataReader(dr));
        
        while (dr.getPosition() - initialPos < sfi.getLength())
        	sfi.add(readStringTable(dr));

        return sfi;
    }

    public static IconDirectoryEntry readIconDirectoryEntry(IDataReader dr)
            throws IOException {
        IconDirectoryEntry ge = new IconDirectoryEntry();
        ge.setWidth(dr.readByte());
        ge.setHeight(dr.readByte());
        ge.setColorCount(dr.readByte());
        ge.setReserved(dr.readByte());
        ge.setPlanes(dr.readWord());
        ge.setBitCount(dr.readWord());
        ge.setBytesInRes(dr.readDoubleWord());
        ge.setOffset(dr.readDoubleWord());

        return ge;
    }

    public static IconDirectory readIconDirectory(IDataReader dr)
            throws IOException {
        IconDirectory gi = new IconDirectory();
        gi.setReserved(dr.readWord());
        gi.setType(dr.readWord());
        int count = dr.readWord();
        for (int i = 0; i < count; i++) {
            gi.add(readIconDirectoryEntry(dr));
        }

        return gi;
    }
    
    private static int alignDataReader(IDataReader dr) throws IOException {
    	int off = (4 - (dr.getPosition() % 4)) % 4;
    	for (int i = 0; i < off; i++)
    		dr.readByte();
    	return off;
    }
}

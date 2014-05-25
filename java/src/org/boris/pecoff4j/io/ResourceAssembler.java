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

import java.io.IOException;

import org.boris.pecoff4j.resources.BitmapInfoHeader;
import org.boris.pecoff4j.resources.FixedFileInfo;
import org.boris.pecoff4j.resources.IconDirectory;
import org.boris.pecoff4j.resources.IconDirectoryEntry;
import org.boris.pecoff4j.resources.IconImage;
import org.boris.pecoff4j.resources.RGBQuad;

public class ResourceAssembler
{
    public static void write(FixedFileInfo info, IDataWriter dw)
            throws IOException {
        dw.writeDoubleWord(info.getSignature());
        dw.writeDoubleWord(info.getStrucVersion());
        dw.writeDoubleWord(info.getFileVersionMS());
        dw.writeDoubleWord(info.getFileVersionLS());
        dw.writeDoubleWord(info.getProductVersionMS());
        dw.writeDoubleWord(info.getProductVersionLS());
        dw.writeDoubleWord(info.getFileFlagMask());
        dw.writeDoubleWord(info.getFileFlags());
        dw.writeDoubleWord(info.getFileOS());
        dw.writeDoubleWord(info.getFileType());
        dw.writeDoubleWord(info.getFileSubtype());
        dw.writeDoubleWord(info.getFileDateMS());
        dw.writeDoubleWord(info.getFileDateLS());
    }

    public static void write(RGBQuad rgb, IDataWriter dw) throws IOException {
        dw.writeByte(rgb.getBlue());
        dw.writeByte(rgb.getGreen());
        dw.writeByte(rgb.getRed());
        dw.writeByte(rgb.getReserved());
    }

    public static void write(IconImage ii, IDataWriter dw) throws IOException {
        if (ii.getHeader() != null) {
            write(ii.getHeader(), dw);
            RGBQuad[] colors = ii.getColors();
            if (colors != null) {
                for (int i = 0; i < colors.length; i++) {
                    write(colors[i], dw);
                }
            }
            dw.writeBytes(ii.getXorMask());
            dw.writeBytes(ii.getAndMask());
        } else {
            dw.writeBytes(ii.getPNG());
        }
    }

    public static void write(BitmapInfoHeader bih, IDataWriter dw)
            throws IOException {
        dw.writeDoubleWord(bih.getSize());
        dw.writeDoubleWord(bih.getWidth());
        dw.writeDoubleWord(bih.getHeight());
        dw.writeWord(bih.getPlanes());
        dw.writeWord(bih.getBitCount());
        dw.writeDoubleWord(bih.getCompression());
        dw.writeDoubleWord(bih.getSizeImage());
        dw.writeDoubleWord(bih.getXpelsPerMeter());
        dw.writeDoubleWord(bih.getYpelsPerMeter());
        dw.writeDoubleWord(bih.getClrUsed());
        dw.writeDoubleWord(bih.getClrImportant());
    }

    public static void write(IconDirectoryEntry ide, IDataWriter dw)
            throws IOException {
        dw.writeByte(ide.getWidth());
        dw.writeByte(ide.getHeight());
        dw.writeByte(ide.getColorCount());
        dw.writeByte(ide.getReserved());
        dw.writeWord(ide.getPlanes());
        dw.writeWord(ide.getBitCount());
        dw.writeDoubleWord(ide.getBytesInRes());
        dw.writeDoubleWord(ide.getOffset());
    }

    public static void write(IconDirectory id, IDataWriter dw)
            throws IOException {
        dw.writeWord(id.getReserved());
        dw.writeWord(id.getType());
        dw.writeWord(id.getCount());
        for (int i = 0; i < id.getCount(); i++) {
            write(id.getEntry(i), dw);
        }
    }
}

/*******************************************************************************
 * This program and the accompanying materials
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at 
 * http://www.eclipse.org/legal/cpl-v10.html
 * 
 * Contributors:
 *     Peter Smith
 *******************************************************************************/
package org.boris.pecoff4j.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.boris.pecoff4j.io.DataReader;
import org.boris.pecoff4j.io.IDataReader;
import org.boris.pecoff4j.io.IDataWriter;
import org.boris.pecoff4j.io.ResourceAssembler;
import org.boris.pecoff4j.io.ResourceParser;
import org.boris.pecoff4j.resources.IconDirectory;
import org.boris.pecoff4j.resources.IconImage;

public class IconFile
{
    private IconDirectory directory;
    private IconImage[] images;

    public static IconFile parse(String filename) throws IOException {
        return read(new DataReader(new FileInputStream(filename)));
    }

    public static IconFile parse(File file) throws IOException {
        return read(new DataReader(new FileInputStream(file)));
    }

    public static IconFile read(IDataReader dr) throws IOException {
        IconFile ic = new IconFile();
        ic.directory = ResourceParser.readIconDirectory(dr);
        ic.images = new IconImage[ic.directory.getCount()];
        for (int i = 0; i < ic.directory.getCount(); i++) {
            dr.jumpTo(ic.directory.getEntry(i).getOffset());
            ic.images[i] = ResourceParser.readIconImage(dr, ic.directory
                    .getEntry(i).getBytesInRes());
        }
        return ic;
    }

    public void write(IDataWriter dw) throws IOException {
        int offset = directory.sizeOf();
        for (int i = 0; i < images.length; i++) {
            directory.getEntry(i).setOffset(offset);
            offset += images[i].sizeOf();
        }
        ResourceAssembler.write(directory, dw);
        for (int i = 0; i < images.length; i++) {
            ResourceAssembler.write(images[i], dw);
        }
    }

    public IconDirectory getDirectory() {
        return directory;
    }

    public void setDirectory(IconDirectory directory) {
        this.directory = directory;
    }

    public IconImage[] getImages() {
        return images;
    }

    public void setImages(IconImage[] images) {
        this.images = images;
    }
}

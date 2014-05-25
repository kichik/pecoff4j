/*******************************************************************************
 * This program and the accompanying materials
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at 
 * http://www.eclipse.org/legal/cpl-v10.html
 * 
 * Contributors:
 *     Peter Smith
 *******************************************************************************/
package org.boris.pecoff4j.resources;


public class Bitmap
{
    private BitmapFileHeader fileHeader;
    private BitmapInfoHeader infoHeader;
    private byte[] colors;
    private byte[] bitmapBits;

    public BitmapFileHeader getFileHeader() {
        return fileHeader;
    }

    public BitmapInfoHeader getInfoHeader() {
        return infoHeader;
    }

    public byte[] getColors() {
        return colors;
    }

    public byte[] getBitmapBits() {
        return bitmapBits;
    }

    public void setFileHeader(BitmapFileHeader fileHeader) {
        this.fileHeader = fileHeader;
    }

    public void setInfoHeader(BitmapInfoHeader infoHeader) {
        this.infoHeader = infoHeader;
    }

    public void setColors(byte[] colors) {
        this.colors = colors;
    }

    public void setBitmapBits(byte[] bitmapBits) {
        this.bitmapBits = bitmapBits;
    }
}

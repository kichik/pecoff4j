/*******************************************************************************
 * This program and the accompanying materials
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at 
 * http://www.eclipse.org/legal/cpl-v10.html
 * 
 * Contributors:
 *     Peter Smith
 *******************************************************************************/
package org.boris.elf;

public class ELFHeader
{
    private byte[] magic;
    private int fileClass;
    private int fileData;
    private int fileVersion;
    private int type;
    private int machine;
    private int version;
    private int entry;
    private int phoff;
    private int shoff;
    private int words;
    private int ehSize;
    private int phEntSize;
    private int phNum;
    private int shEntSize;
    private int shStrndx;
}

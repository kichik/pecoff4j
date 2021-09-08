package com.kichik.pecoff4j;

import com.kichik.pecoff4j.io.PEParser;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;

public class PEParserTest {
    @Test
    public void testParseClrRuntimeHeader() throws IOException {
        /*
        using System;
        using System.Runtime.InteropServices;

        namespace ClassLibrary
        {
            public class Class1
            {
                [DllImport("user32.dll", CharSet = CharSet.Unicode, SetLastError = true)]
                private static extern int MessageBox(IntPtr hWnd, string lpText, string lpCaption, uint uType);
            }
        }
         */
        InputStream in = this.getClass().getResourceAsStream("/clr/ClassLibrary.dll");
        PE pe = PEParser.parse(in);
        CLRRuntimeHeader clrRuntimeHeader = pe.getImageData().getClrRuntimeHeader();

        // test that we read the full 72 bytes in the header
        byte[] b = clrRuntimeHeader.get();
        assertEquals(72, b.length);

        // test we read out all the expected values
        assertEquals(72, clrRuntimeHeader.getHeaderSize());
        assertEquals(2, clrRuntimeHeader.getMajorRuntimeVersion());
        assertEquals(5, clrRuntimeHeader.getMinorRuntimeVersion());
        assertEquals(8284, clrRuntimeHeader.getMetaDataDirectoryAddress());
        assertEquals(1256, clrRuntimeHeader.getMetaDataDirectorySize());
        assertEquals(1, clrRuntimeHeader.getFlags());
        assertEquals(0, clrRuntimeHeader.getEntryPointToken());
        assertEquals(0, clrRuntimeHeader.getResourcesDirectoryAddress());
        assertEquals(0, clrRuntimeHeader.getResourcesDirectorySize());
        assertEquals(0, clrRuntimeHeader.getStrongNameSignatureAddress());
        assertEquals(0, clrRuntimeHeader.getStrongNameSignatureSize());
        assertEquals(0, clrRuntimeHeader.getCodeManagerTableAddress());
        assertEquals(0, clrRuntimeHeader.getCodeManagerTableSize());
        assertEquals(0, clrRuntimeHeader.getvTableFixupsAddress());
        assertEquals(0, clrRuntimeHeader.getvTableFixupsSize());
        assertEquals(0, clrRuntimeHeader.getExportAddressTableJumpsAddress());
        assertEquals(0, clrRuntimeHeader.getExportAddressTableJumpsSize());
        assertEquals(0, clrRuntimeHeader.getManagedNativeHeaderAddress());
        assertEquals(0, clrRuntimeHeader.getManagedNativeHeaderSize());
    }
}

package com.kichik.pecoff4j;

import com.kichik.pecoff4j.asm.AbstractInstruction;
import com.kichik.pecoff4j.asm.AssemblyFormatter;
import com.kichik.pecoff4j.asm.AssemblyParser;
import com.kichik.pecoff4j.constant.ImageDataDirectoryType;
import com.kichik.pecoff4j.io.PEParser;
import com.kichik.pecoff4j.util.Reflection;

import java.io.ByteArrayInputStream;

/**
 * A playground to showcase some of PECOFF4J's features.
 */
public class Demo {
    public static void main(String[] args) throws Exception {
        PE pe = PEParser.parse(Demo.class.getResourceAsStream("/WinRun4J.exe"));

        Demo demo = new Demo();
        demo.printAssemblyCode(pe);
        demo.dumpVA(pe);
    }

    public void printAssemblyCode(PE pe) throws Exception {
        System.out.println("Assembly code");
        System.out.println("=============");
        SectionHeader sh = pe.getSectionTable().findHeader(".text");
        SectionData sd = pe.getSectionTable().findSection(".text");
        ByteArrayInputStream bis = new ByteArrayInputStream(sd.getData());
        AbstractInstruction[] inst = AssemblyParser.parseAll(
                0x400000 + sh.getVirtualAddress(), bis);
        AssemblyFormatter.format(inst, System.out);
        System.out.println();
    }

    public void dumpVA(PE pe) throws Exception {
        System.out.println("Section table");
        System.out.println("=============");
        SectionTable st = pe.getSectionTable();
        System.out.println("name\tprd \tdex \tvad  \tvex");
        System.out.println("----------------------------------------");
        for (int i = 0; i < st.getNumberOfSections(); i++) {
            SectionHeader sh = st.getHeader(i);
            int dex = sh.getPointerToRawData() + sh.getSizeOfRawData();
            int vex = sh.getVirtualAddress() + sh.getVirtualSize();
            System.out.println(sh.getName() + "\t"
                    + make4(Integer.toHexString(sh.getPointerToRawData()))
                    + "\t" + make4(Integer.toHexString(dex)) + "\t"
                    + make4(Integer.toHexString(sh.getVirtualAddress())) + "\t"
                    + make4(Integer.toHexString(vex)));
        }

        System.out.println();
        int dc = pe.getOptionalHeader().getDataDirectoryCount();
        for (int i = 0; i < dc; i++) {
            ImageDataDirectory idd = pe.getOptionalHeader().getDataDirectory(i);
            if (idd.getSize() > 0) {
                String n = Reflection.getConstantName(
                        ImageDataDirectoryType.class, i);
                while (n.length() < 20) {
                    n = n + " ";
                }
                System.out.println(n
                        + "\t"
                        + Integer.toHexString(idd.getVirtualAddress())
                        + "\t"
                        + Integer.toHexString(idd.getVirtualAddress()
                        + idd.getSize()));
            }
        }
        System.out.println();
    }

    private String make4(String s) {
        while (s.length() < 4) {
            s = " " + s;
        }
        return s;
    }
}

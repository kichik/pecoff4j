package org.boris.pecoff4j.asm;

public class SIB
{
    public final int scale;
    public final int index;
    public final int base;

    public SIB(int value) {
        value &= 0xff;
        scale = value >> 6;
        index = value >> 3 & 0x7;
        base = value & 0x7;
    }

    public byte encode() {
        return (byte) (scale << 6 | index << 3 | base);
    }

    public String toString(int imm32) {
        return Register.to32(index) + "*" + (scale * 2) + "+" + Register.to32(base) +
                AbstractInstruction.toHexString(imm32, true);
    }
}

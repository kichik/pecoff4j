/*******************************************************************************
 * This program and the accompanying materials
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at 
 * http://www.eclipse.org/legal/cpl-v10.html
 * 
 * Contributors:
 *     Peter Smith
 *******************************************************************************/
package org.boris.pecoff4j.asm;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.boris.pecoff4j.util.Reflection;

public class AssemblyParser {
	public static AbstractInstruction[] parseAll(int offset, InputStream is)
			throws IOException {
		List<AbstractInstruction> instructions = new ArrayList();
		AbstractInstruction ins = null;
		while ((ins = parse(is)) != null) {
			ins.setOffset(offset);
			offset += ins.size();
			instructions.add(ins);
		}
		return instructions
				.toArray(new AbstractInstruction[instructions.size()]);
	}

	public static AbstractInstruction parse(InputStream is) throws IOException {
		int opcode = is.read() & 0xff;
		int highop = opcode & 0xf0;
		int imm32;
		int disp32;
		ModRM modrm = null;
		SIB sib = null;
		switch (highop) {
		case 0x0:
			switch (opcode) {
			case 0x03:
				modrm = new ModRM(is.read());
				imm32 = readDoubleWord(is);
				return new ADD(opcode, modrm, imm32);
			case 0x0f:
				return new JumpIfInstruction(is.read(), readDoubleWord(is));
			}
			break;
		case 0x30:
			switch (opcode) {
			case 0x3b:
				modrm = new ModRM(is.read());
				imm32 = is.read();
				return new CMP(modrm, (byte) imm32);
			}
			break;
		case 0x50:
			if (opcode < 0x58) {
				return new PUSH(opcode & 0xf);
			} else {
				return new POP(opcode >> 4 & 0xf);
			}
		case 0x60:
			switch (opcode) {
			case 0x68:
				return new PUSH(opcode, readDoubleWord(is));
			case 0x6A:
				return new PUSH((byte) is.read());
			}
			break;
		case 0x70:
			switch (opcode) {
			case 0x7d:
				return new JGE((byte) is.read());
			}
			break;
		case 0x80:
			modrm = new ModRM(is.read());
			switch (opcode) {
			case 0x8b:
				if (modrm.mod < 3 && modrm.reg1 == 0x4)
					sib = new SIB(is.read());
				switch (modrm.mod) {
				case 0:
				case 1:
					imm32 = is.read();
					if (sib != null)
						return new MOV(modrm, sib, (byte) imm32);
					else
						return new MOV(modrm, (byte) imm32);
				case 2:
					imm32 = readDoubleWord(is);
					if (sib != null)
						return new MOV(opcode, modrm, sib, imm32);
					else
						return new MOV(opcode, modrm, imm32);
				}
				return new MOV(modrm);
			case 0x81:
				imm32 = readDoubleWord(is);
				return new SUB(modrm, imm32);
			case 0x83:
				imm32 = is.read();
				return new ADD(modrm, (byte) imm32);
			case 0x89:
				switch (modrm.mod) {
				case 0:
				case 1:
					imm32 = is.read();
					return new MOV(opcode, modrm, (byte) imm32);
				case 2:
					imm32 = readDoubleWord(is);
					return new MOV(modrm, imm32);
				}
			case 0x85:
				return new TEST(modrm);
			case 0x8d:
				if (modrm.mod < 3 && modrm.reg1 == 0x4) {
					sib = new SIB(is.read());
					imm32 = readDoubleWord(is);
					return new LEA(modrm, sib, imm32);
				}
				imm32 = readDoubleWord(is);
				return new LEA(modrm, imm32);
			}
			print(modrm);
			break;
		case 0xa0:
			switch (opcode) {
			case 0xa1:
			case 0xa3:
				return new MOV(opcode, readDoubleWord(is));
			}
			break;
		case 0xc0:
			switch (opcode) {
			case 0xc1:
				modrm = new ModRM(is.read());
				imm32 = is.read();
				return new SHL(modrm, (byte) imm32);
			case 0xc3:
				return new RET();
			case 0xc6:
				modrm = new ModRM(is.read());
				imm32 = is.read();
				return new MOV(opcode, modrm, (byte) imm32);
			case 0xc7:
				modrm = new ModRM(is.read());
				switch (modrm.mod) {
				case 1:
					disp32 = is.read();
					imm32 = readDoubleWord(is);
					return new MOV(modrm, (byte) disp32, imm32);
				}
				disp32 = readDoubleWord(is);
				imm32 = readDoubleWord(is);
				return new MOV(modrm, disp32, imm32);
			}
			break;
		case 0xe0:
			switch (opcode) {
			case 0xe8:
				return new CALL(opcode, readDoubleWord(is));
			case 0xe9:
				return new JMP(readDoubleWord(is));
			case 0xeb:
				return new JMP((byte) is.read());
			}
			break;
		case 0xf0:
			switch (opcode) {
			case 0xff:
				modrm = new ModRM(is.read());
				imm32 = readDoubleWord(is);
				return new CALL(modrm, imm32);
			}
			break;
		}
		println(opcode);
		return null;
	}

	public static int readDoubleWord(InputStream is) throws IOException {
		return is.read() | is.read() << 8 | is.read() << 16 | is.read() << 24;
	}

	public static void print(Object o) {
		System.out.print(Reflection.toString(o));
	}

	public static void println(Object o) {
		System.out.println(Reflection.toString(o));
	}
}

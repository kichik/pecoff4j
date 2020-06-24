/*******************************************************************************
 * This program and the accompanying materials
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at 
 * http://www.eclipse.org/legal/cpl-v10.html
 * 
 * Contributors:
 *     Peter Smith
 *******************************************************************************/
package com.kichik.pecoff4j.resources;

public class RGBQuad {
	private int blue;
	private int green;
	private int red;
	private int reserved;

	public int getBlue() {
		return blue;
	}

	public int getGreen() {
		return green;
	}

	public int getRed() {
		return red;
	}

	public int getReserved() {
		return reserved;
	}

	public void setBlue(int blue) {
		this.blue = blue;
	}

	public void setGreen(int green) {
		this.green = green;
	}

	public void setRed(int red) {
		this.red = red;
	}

	public void setReserved(int reserved) {
		this.reserved = reserved;
	}
}

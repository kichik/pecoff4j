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

public class FixedFileInfo {
	private int signature;
	private int strucVersion;
	private int fileVersionMS;
	private int fileVersionLS;
	private int productVersionMS;
	private int productVersionLS;
	private int fileFlagMask;
	private int fileFlags;
	private int fileOS;
	private int fileType;
	private int fileSubtype;
	private int fileDateMS;
	private int fileDateLS;

	public int getSignature() {
		return signature;
	}

	public void setSignature(int signature) {
		this.signature = signature;
	}

	public int getStrucVersion() {
		return strucVersion;
	}

	public void setStrucVersion(int strucVersion) {
		this.strucVersion = strucVersion;
	}

	public int getFileVersionMS() {
		return fileVersionMS;
	}

	public void setFileVersionMS(int fileVersionMS) {
		this.fileVersionMS = fileVersionMS;
	}

	public int getFileVersionLS() {
		return fileVersionLS;
	}

	public void setFileVersionLS(int fileVersionLS) {
		this.fileVersionLS = fileVersionLS;
	}

	public int getProductVersionMS() {
		return productVersionMS;
	}

	public void setProductVersionMS(int productVersionMS) {
		this.productVersionMS = productVersionMS;
	}

	public int getProductVersionLS() {
		return productVersionLS;
	}

	public void setProductVersionLS(int productVersionLS) {
		this.productVersionLS = productVersionLS;
	}

	public int getFileFlagMask() {
		return fileFlagMask;
	}

	public void setFileFlagMask(int fileFlagMask) {
		this.fileFlagMask = fileFlagMask;
	}

	public int getFileFlags() {
		return fileFlags;
	}

	public void setFileFlags(int fileFlags) {
		this.fileFlags = fileFlags;
	}

	public int getFileType() {
		return fileType;
	}

	public void setFileType(int fileType) {
		this.fileType = fileType;
	}

	public int getFileSubtype() {
		return fileSubtype;
	}

	public void setFileSubtype(int fileSubtype) {
		this.fileSubtype = fileSubtype;
	}

	public int getFileDateMS() {
		return fileDateMS;
	}

	public void setFileDateMS(int fileDateMS) {
		this.fileDateMS = fileDateMS;
	}

	public int getFileDateLS() {
		return fileDateLS;
	}

	public void setFileDateLS(int fileDateLS) {
		this.fileDateLS = fileDateLS;
	}

	public static int sizeOf() {
		return 52;
	}

	public int getFileOS() {
		return fileOS;
	}

	public void setFileOS(int fileOS) {
		this.fileOS = fileOS;
	}
}

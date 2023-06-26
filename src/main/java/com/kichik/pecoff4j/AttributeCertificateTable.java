/*******************************************************************************
 * This program and the accompanying materials
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at 
 * http://www.eclipse.org/legal/cpl-v10.html
 * 
 * Contributors:
 *     Shu-Wei Tan
 *******************************************************************************/
package com.kichik.pecoff4j;

import com.kichik.pecoff4j.io.DataReader;
import com.kichik.pecoff4j.util.DataObject;

import java.io.IOException;

/**
 * Encapsulates the Attribute Certificate Table (Image Only). Section 5.7 of the PE/COFF
 * spec Rev10.
 */
public class AttributeCertificateTable extends DataObject {
	private int length;
	private int revision;
	private int certificateType;
	private byte[] certificate;

	public static AttributeCertificateTable read(byte[] b) throws IOException {
		AttributeCertificateTable dd = new AttributeCertificateTable();
		dd.set(b);
		DataReader dr = new DataReader(b);
		dd.setLength(dr.readDoubleWord());
		dd.setRevision(dr.readWord());
		dd.setCertificateType(dr.readWord());
		byte[] certificate = new byte[dd.getLength() - 8];
		dr.read(certificate);
		dd.setCertificate(certificate);
		return dd;
	}

	public int getLength() {
		return length;
	}
	
	public void setLength(int length) {
		this.length = length;
	}
	
	public int getRevision() {
		return revision;
	}
	
	public void setRevision(int revision) {
		this.revision = revision;
	}
	
	public int getCertificateType() {
		return certificateType;
	}
	
	public void setCertificateType(int certificateType) {
		this.certificateType = certificateType;
	}
	
	public byte[] getCertificate() {
		return certificate;
	}
	
	public void setCertificate(byte[] certificate) {
		this.certificate = certificate;
	}
}

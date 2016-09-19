/*******************************************************************************
 * This program and the accompanying materials
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at 
 * http://www.eclipse.org/legal/cpl-v10.html
 * 
 * Contributors:
 *     Shu-Wei Tan
 *******************************************************************************/
package org.boris.pecoff4j;

import org.boris.pecoff4j.util.DataObject;

/**
 * Encapsulates the Attribute Certificate Table (Image Only). Section 5.7 of the PE/COFF
 * spec Rev10.
 */
public class AttributeCertificateTable extends DataObject {
	private int length;
	private int revision;
	private int certificateType;
	private byte[] certificate;

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

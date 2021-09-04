package com.kichik.pecoff4j;

import com.kichik.pecoff4j.util.DataObject;

public class CLRRuntimeHeader extends DataObject {
    private int headerSize;
    private int majorRuntimeVersion;
    private int minorRuntimeVersion;
    private int metaDataDirectoryAddress;
    private int metaDataDirectorySize;
    private int flags;
    private int entryPointToken;
    private int resourcesDirectoryAddress;
    private int resourcesDirectorySize;
    private int strongNameSignatureAddress;
    private int strongNameSignatureSize;
    private int codeManagerTableAddress;
    private int codeManagerTableSize;
    private int vTableFixupsAddress;
    private int vTableFixupsSize;
    private int exportAddressTableJumpsAddress;
    private int exportAddressTableJumpsSize;
    private int managedNativeHeaderAddress;
    private int managedNativeHeaderSize;

    public int getHeaderSize() {
        return headerSize;
    }

    public void setHeaderSize(int headerSize) {
        this.headerSize = headerSize;
    }

    public int getMajorRuntimeVersion() {
        return majorRuntimeVersion;
    }

    public void setMajorRuntimeVersion(int majorRuntimeVersion) {
        this.majorRuntimeVersion = majorRuntimeVersion;
    }

    public int getMinorRuntimeVersion() {
        return minorRuntimeVersion;
    }

    public void setMinorRuntimeVersion(int minorRuntimeVersion) {
        this.minorRuntimeVersion = minorRuntimeVersion;
    }

    public int getMetaDataDirectoryAddress() {
        return metaDataDirectoryAddress;
    }

    public void setMetaDataDirectoryAddress(int metaDataDirectoryAddress) {
        this.metaDataDirectoryAddress = metaDataDirectoryAddress;
    }

    public int getMetaDataDirectorySize() {
        return metaDataDirectorySize;
    }

    public void setMetaDataDirectorySize(int metaDataDirectorySize) {
        this.metaDataDirectorySize = metaDataDirectorySize;
    }

    public int getFlags() {
        return flags;
    }

    public void setFlags(int flags) {
        this.flags = flags;
    }

    public int getEntryPointToken() {
        return entryPointToken;
    }

    public void setEntryPointToken(int entryPointToken) {
        this.entryPointToken = entryPointToken;
    }

    public int getResourcesDirectoryAddress() {
        return resourcesDirectoryAddress;
    }

    public void setResourcesDirectoryAddress(int resourcesDirectoryAddress) {
        this.resourcesDirectoryAddress = resourcesDirectoryAddress;
    }

    public int getResourcesDirectorySize() {
        return resourcesDirectorySize;
    }

    public void setResourcesDirectorySize(int resourcesDirectorySize) {
        this.resourcesDirectorySize = resourcesDirectorySize;
    }

    public int getStrongNameSignatureAddress() {
        return strongNameSignatureAddress;
    }

    public void setStrongNameSignatureAddress(int strongNameSignatureAddress) {
        this.strongNameSignatureAddress = strongNameSignatureAddress;
    }

    public int getStrongNameSignatureSize() {
        return strongNameSignatureSize;
    }

    public void setStrongNameSignatureSize(int strongNameSignatureSize) {
        this.strongNameSignatureSize = strongNameSignatureSize;
    }

    public int getCodeManagerTableAddress() {
        return codeManagerTableAddress;
    }

    public void setCodeManagerTableAddress(int codeManagerTableAddress) {
        this.codeManagerTableAddress = codeManagerTableAddress;
    }

    public int getCodeManagerTableSize() {
        return codeManagerTableSize;
    }

    public void setCodeManagerTableSize(int codeManagerTableSize) {
        this.codeManagerTableSize = codeManagerTableSize;
    }

    public int getvTableFixupsAddress() {
        return vTableFixupsAddress;
    }

    public void setvTableFixupsAddress(int vTableFixupsAddress) {
        this.vTableFixupsAddress = vTableFixupsAddress;
    }

    public int getvTableFixupsSize() {
        return vTableFixupsSize;
    }

    public void setvTableFixupsSize(int vTableFixupsSize) {
        this.vTableFixupsSize = vTableFixupsSize;
    }

    public int getExportAddressTableJumpsAddress() {
        return exportAddressTableJumpsAddress;
    }

    public void setExportAddressTableJumpsAddress(int exportAddressTableJumpsAddress) {
        this.exportAddressTableJumpsAddress = exportAddressTableJumpsAddress;
    }

    public int getExportAddressTableJumpsSize() {
        return exportAddressTableJumpsSize;
    }

    public void setExportAddressTableJumpsSize(int exportAddressTableJumpsSize) {
        this.exportAddressTableJumpsSize = exportAddressTableJumpsSize;
    }

    public int getManagedNativeHeaderAddress() {
        return managedNativeHeaderAddress;
    }

    public void setManagedNativeHeaderAddress(int managedNativeHeaderAddress) {
        this.managedNativeHeaderAddress = managedNativeHeaderAddress;
    }

    public int getManagedNativeHeaderSize() {
        return managedNativeHeaderSize;
    }

    public void setManagedNativeHeaderSize(int managedNativeHeaderSize) {
        this.managedNativeHeaderSize = managedNativeHeaderSize;
    }
}

package com.kichik.pecoff4j;

import com.kichik.pecoff4j.io.DataWriter;
import com.kichik.pecoff4j.io.IDataWriter;
import com.kichik.pecoff4j.util.PaddingType;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * A structure that be written using a data writer.
 */
public interface WritableStructure {
    /**
     * Write this structure to a data writer.
     *
     * @param dw the writer
     */
    void write(IDataWriter dw) throws IOException;

    /**
     * Write this structure to a byte array using {@link PaddingType#PATTERN} and no alignment.
     * @return the byte array
     */
    default byte[] toByteArray() {
        return toByteArray(PaddingType.PATTERN, 1);
    }
    /**
     * Write this structure to a byte array.
     * @param paddingType the padding mode
     * @param alignment the alignment to use after writing this structure
     * @return the byte array
     */
    default byte[] toByteArray(PaddingType paddingType, int alignment) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            DataWriter writer = new DataWriter(baos);
            writer.setPaddingMode(paddingType);
            write(writer);
            writer.align(alignment);
            writer.close();
            return baos.toByteArray();
        } catch (IOException e) {
            // cannot happen due to in-memory implementation of writer
            throw new RuntimeException("DataWriter must not throw IOException when using ByteArrayOutputStream");
        }
    }
}

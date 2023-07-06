package com.kichik.pecoff4j;

import com.kichik.pecoff4j.io.IDataWriter;

import java.io.IOException;

/**
 * A structure that contains values derived from its content like lengths, checksums or pointers to nested structures.
 *
 * Use {@link #rebuild()} to update these values prior to {@link #write(IDataWriter)}. The latter just stores the values
 * as they are present.
 */
public interface RebuildableStructure {
    /**
     * Update derived values such as lengths, checksums, serialized layout etc.
     *
     * @return the updated length of this structure in bytes (might include padding)
     */
    int rebuild();

    /**
     * Write this structure to a data writer.
     *
     * @param dw the writer
     */
    void write(IDataWriter dw) throws IOException;
}

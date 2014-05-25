/*******************************************************************************
 * This program and the accompanying materials
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at 
 * http://www.eclipse.org/legal/cpl-v10.html
 * 
 * Contributors:
 *     Peter Smith
 *******************************************************************************/
package org.boris.pecoff4j.util;

import java.io.File;
import java.io.FilenameFilter;

public class PEFilenameFilter implements FilenameFilter
{
    public boolean accept(File dir, String name) {
        return name.toLowerCase().endsWith(".exe") ||
                name.toLowerCase().endsWith(".dll");
    }
}

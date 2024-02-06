# PECOFF4J

PE/COFF 4J is a java engineering library for portable executables, the format used by Windows. It has the following features:

* Parser for Windows executables and DLLs.
* Assembler for creating and modifying executables and DLLs.
* Resource directory parser - understands version info, icons.

This is a fork of http://sourceforge.net/projects/pecoff4j/

Imported from CVS on May 24th, 2014

[![Actions Status](https://github.com/kichik/pecoff4j/workflows/Build/badge.svg)](https://github.com/kichik/pecoff4j)
[![Maven Central](https://img.shields.io/maven-central/v/com.kichik.pecoff4j/pecoff4j.svg?label=Maven%20Central)](https://central.sonatype.com/artifact/com.kichik.pecoff4j/pecoff4j/)

## Installation

This fork of PECOFF4J is available on [Maven Central](https://search.maven.org/artifact/com.kichik.pecoff4j/pecoff4j).

```xml
<dependency>
  <groupId>com.kichik.pecoff4j</groupId>
  <artifactId>pecoff4j</artifactId>
  <version>0.4.1</version>
</dependency>
```

See [Maven Central](https://search.maven.org/artifact/com.kichik.pecoff4j/pecoff4j) for more installation options like
Gradle, SBT, Ivy, etc.

## License

Sources are licensed under [Common Public License v1.0](http://www.eclipse.org/legal/cpl-v10.html)

## New Features

The project was forked to implement version string parsing for a [StackOverflow question](http://stackoverflow.com/questions/23845480/how-to-get-windows-file-details/23848792).

Recently, support for modifying the resource directory has been added (e.g. adding or removing icons). See
the [ResourceDirectoryTest](https://github.com/kichik/pecoff4j/blob/master/src/test/java/com/kichik/pecoff4j/ResourceDirectoryTest.java)
for some basic examples. Use the `rebuild` method to re-calculate the internal structures prior to creating the binary
using the `write` method.

### Example

```java
import java.io.IOException;

import com.kichik.pecoff4j.PE;
import com.kichik.pecoff4j.ResourceDirectory;
import com.kichik.pecoff4j.ResourceEntry;
import com.kichik.pecoff4j.constant.ResourceType;
import com.kichik.pecoff4j.io.DataReader;
import com.kichik.pecoff4j.io.PEParser;
import com.kichik.pecoff4j.resources.StringFileInfo;
import com.kichik.pecoff4j.resources.StringPair;
import com.kichik.pecoff4j.resources.StringTable;
import com.kichik.pecoff4j.resources.VersionInfo;
import com.kichik.pecoff4j.util.ResourceHelper;

public class Main {

	public static void main(String[] args) throws IOException {
		PE pe = PEParser.parse("C:/windows/system32/notepad.exe");
		ResourceDirectory rd = pe.getImageData().getResourceTable();

		ResourceEntry[] entries = ResourceHelper.findResources(rd, ResourceType.VERSION_INFO);
		for (ResourceEntry entry : entries) {
			byte[] data = entry.getData();
			VersionInfo version = VersionInfo.read(new DataReader(data));

			StringFileInfo strings = version.getStringFileInfo();
			StringTable table = strings.getTables().get(0);
			for (List<StringPair> pair : table.getStrings()){
				System.out.println(pair.getKey() + " = " + pair.getValue());
			}
		}
	}

}

```

Will print:

```
CompanyName = Microsoft Corporation
FileDescription = Notepad
FileVersion = 6.1.7600.16385 (win7_rtm.090713-1255)
InternalName = Notepad
LegalCopyright = © Microsoft Corporation. All rights reserved.
OriginalFilename = NOTEPAD.EXE
ProductName = Microsoft® Windows® Operating System
ProductVersion = 6.1.7600.16385
```

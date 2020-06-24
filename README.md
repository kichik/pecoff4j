# PECOFF4J

PE/COFF 4J is a java engineering library for portable executables, the format used by Windows. It has the following features:

* Parser for Windows executables and DLLs.
* Assembler for creating and modifying executables and DLLs.
* Resource directory parser - understands version info, icons.

This is a fork of http://sourceforge.net/projects/pecoff4j/

Imported from CVS on May 24th, 2014

[![Actions Status](https://github.com/kichik/pecoff4j/workflows/Build/badge.svg)](https://github.com/kichik/pecoff4j)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.kichik/pecoff4j/pecoff4j/badge.svg)](https://search.maven.org/artifact/com.kichik.pecoff4j/pecoff4j)

## Downloads

https://drone.io/github.com/kichik/pecoff4j/files

## Installation

This fork of PECOFF4J is available on [Maven Central](https://search.maven.org/artifact/com.kichik.pecoff4j/pecoff4j).

```xml
<dependency>
  <groupId>com.kichik.pecoff4j</groupId>
  <artifactId>pecoff4j</artifactId>
</dependency>
```

See [Maven Central](https://search.maven.org/artifact/com.kichik.pecoff4j/pecoff4j) for more installation options like
Gradle, SBT, Ivy, etc.

## License

Sources are licensed under [Common Public License v1.0](http://www.eclipse.org/legal/cpl-v10.html)

## New Features

The project was forked to implement version string parsing for a [StackOverflow question](http://stackoverflow.com/questions/23845480/how-to-get-windows-file-details/23848792).

### Example

```java
import java.io.IOException;

import com.kichik.pecoff4j.PE;
import com.kichik.pecoff4j.ResourceDirectory;
import com.kichik.pecoff4j.ResourceEntry;
import com.kichik.pecoff4j.constant.ResourceType;
import com.kichik.pecoff4j.io.PEParser;
import com.kichik.pecoff4j.io.ResourceParser;
import com.kichik.pecoff4j.resources.StringFileInfo;
import com.kichik.pecoff4j.resources.StringTable;
import com.kichik.pecoff4j.resources.VersionInfo;
import com.kichik.pecoff4j.util.ResourceHelper;

public class Main {

	public static void main(String[] args) throws IOException {
		PE pe = PEParser.parse("C:/windows/system32/notepad.exe");
		ResourceDirectory rd = pe.getImageData().getResourceTable();

		ResourceEntry[] entries = ResourceHelper.findResources(rd, ResourceType.VERSION_INFO);
		for (int i = 0; i < entries.length; i++) {
			byte[] data = entries[i].getData();
			VersionInfo version = ResourceParser.readVersionInfo(data);

			StringFileInfo strings = version.getStringFileInfo();
			StringTable table = strings.getTable(0);
			for (int j = 0; j < table.getCount(); j++) {
				String key = table.getString(j).getKey();
				String value = table.getString(j).getValue();
				System.out.println(key + " = " + value);
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

## Other Forks

[jonnyzzz/PE](https://github.com/jonnyzzz/PE) has even more features and probably got much more love than this fork.

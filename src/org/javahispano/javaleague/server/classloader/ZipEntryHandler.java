package org.javahispano.javaleague.server.classloader;

import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public interface ZipEntryHandler {

    void readZipEntry(ZipEntry entry, ZipInputStream zin) throws IOException;

}

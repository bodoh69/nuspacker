package com.tim.nuspacker;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.Manifest;
import java.util.zip.ZipEntry;

public final class CompileDate {
    private long manifestTime;
    private Attributes attributes;

    public CompileDate() {
        try {
            File self = new File(CompileDate.class.getProtectionDomain()
                    .getCodeSource().getLocation().toURI());
            try (JarFile jf = new JarFile(self)) {
                ZipEntry ze = jf.getEntry("META-INF/MANIFEST.MF");
                manifestTime = ze.getTime();  // in standard millis
                Manifest manifest = new Manifest(jf.getInputStream(ze));
                attributes = manifest.getMainAttributes();
            }
        } catch (IOException | URISyntaxException e) {
        }
    }

    public long getManifestTime() {
        return manifestTime;
    }

    public String getVersion() {
        String version = attributes.getValue("Implementation-Version");
        return version == null ? "0.3-i" : version;
    }

    public String getName() {
        String title = attributes.getValue("Implementation-Title");
        return title == null ? "NUSPacker" : title;
    }
}

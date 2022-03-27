package com.tim.nuspacker;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.Manifest;
import java.util.zip.ZipEntry;

public final class CompileDate {
    static final String NAME = "NUSPacker";
    static final String VERSION = "0.3-j";
    private File self;
    private long manifestTime;
    private Attributes attributes;

    public CompileDate() {
        try {
            self = new File(CompileDate.class.getProtectionDomain()
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

    public File getSelf() {
        return self;
    }

    public long getManifestTime() {
        return manifestTime;
    }

    public String getVersion() {
        String version = attributes == null ?
                VERSION : attributes.getValue("Implementation-Version");
        return version == null || version.trim().length() < 1 ?
                VERSION : version;
    }

    public String getName() {
        String title = attributes == null ?
                NAME : attributes.getValue("Implementation-Title");
        return title == null || title.trim().length() < 1 ?
                NAME : title;
    }
}

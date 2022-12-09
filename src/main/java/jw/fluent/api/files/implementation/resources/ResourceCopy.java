package jw.fluent.api.files.implementation.resources;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.nio.file.Files;
import java.util.Enumeration;
import java.util.Objects;
import java.util.Optional;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class ResourceCopy
{
    private static final String JAR_URI_PREFIX = "jar:path:";

    /**
     * The default buffer size.
     */
    private static final int BUFFER_SIZE = 8 * 1024;


    public File copyResourcesToTempDir(final boolean preserve, final String... paths) throws IOException {
        final File parent = new File(System.getProperty("java.io.tmpdir"));
        File directory;
        do {
            directory = new File(parent, String.valueOf(System.nanoTime()));
        } while (!directory.mkdir());

        return this.copyResourcesToDir(directory, preserve, paths);
    }


    public File copyResourcesToDir(final File directory, final boolean preserve, final String... paths) throws IOException {
        for (final String path : paths) {
            final File target;
            if (preserve) {
                target = new File(directory, path);
                target.getParentFile().mkdirs();
            } else {
                target = new File(directory, new File(path).getName());
            }
            this.writeToFile(
                    Objects.requireNonNull(Thread.currentThread().getContextClassLoader().getResourceAsStream(path)),
                    target
            );
        }
        return directory;
    }


    public void copyResourceDirectory(final JarFile source, final String path, final File target) throws IOException {
        final Enumeration<JarEntry> entries = source.entries();
        final String newPath = String.format("%s/", path);
        while (entries.hasMoreElements()) {
            final JarEntry entry = entries.nextElement();
            if (entry.getName().startsWith(newPath) && !entry.isDirectory()) {
                final File dest = new File(target, entry.getName().substring(newPath.length()));
                final File parent = dest.getParentFile();
                if (parent != null) {
                    parent.mkdirs();
                }
                this.writeToFile(source.getInputStream(entry), dest);
            }
        }
    }


    public Optional<JarFile> jar(final Class<?> clazz) throws IOException {
        final String path = String.format("/%s.class", clazz.getName().replace('.', '/'));
        final URL url = clazz.getResource(path);
        Optional<JarFile> optional = Optional.empty();
        if (url != null) {
            final String jar = url.toString();
            final int bang = jar.indexOf('!');
            if (jar.startsWith(ResourceCopy.JAR_URI_PREFIX) && bang != -1) {
                optional = Optional.of(
                        new JarFile(
                                jar.substring(ResourceCopy.JAR_URI_PREFIX.length(), bang)
                        )
                );
            }
        }
        return optional;
    }


    private void writeToFile(final InputStream input, final File target) throws IOException {
        final OutputStream output = Files.newOutputStream(target.toPath());
        final byte[] buffer = new byte[ResourceCopy.BUFFER_SIZE];
        int length = input.read(buffer);
        while (length > 0) {
            output.write(buffer, 0, length);
            length = input.read(buffer);
        }
        input.close();
        output.close();
    }
}

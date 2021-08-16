/*
 * Copyright 2019 Andy Turner, University of Leeds.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package uk.ac.leeds.ccg.generic.io;

import java.io.IOException;
import java.io.Serializable;
import java.net.URI;
import java.nio.file.FileSystem;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.Objects;

/**
 * Serializable class for a {@link Path}.
 *
 * @author Andy Turner
 * @version 1.0.0
 */
public class Generic_Path implements Serializable, Path {

    private static final long serialVersionUID = 1L;

    /**
     * The String representation of the Path.
     */
    protected final String s;

    /**
     * Create a new instance.
     *
     * @param p The Path to store.
     */
    public Generic_Path(Path p) {
        s = p.toString();
    }

    /**
     * Create a new instance.
     *
     * @param p The Generic_Path containing the String representation of the
     * Path to store.
     */
    public Generic_Path(Generic_Path p) {
        s = p.s;
    }

    /**
     * @return A copy of {@link #s}. 
     */
    public Path getPath() {
        return Paths.get(s);
    }

    @Override
    public String toString() {
        return s;
    }

    @Override
    public FileSystem getFileSystem() {
        return getPath().getFileSystem();
    }

    @Override
    public boolean isAbsolute() {
        return getPath().isAbsolute();
    }

    @Override
    public Path getRoot() {
        return getPath().getRoot();
    }

    @Override
    public Path getFileName() {
        return getPath().getFileName();
    }

    @Override
    public Path getParent() {
        return getPath().getParent();
    }

    @Override
    public int getNameCount() {
        return getPath().getNameCount();
    }

    @Override
    public Path getName(int index) {
        return getPath().getName(index);
    }

    @Override
    public Path subpath(int beginIndex, int endIndex) {
        return getPath().subpath(beginIndex, endIndex);
    }

    @Override
    public boolean startsWith(Path other) {
        return getPath().startsWith(other);
    }

    @Override
    public Path relativize(Path other) {
        return getPath().relativize(other);
    }

    @Override
    public URI toUri() {
        return getPath().toUri();
    }

    @Override
    public Path toAbsolutePath() {
        return getPath().toAbsolutePath();
    }

    @Override
    public Path toRealPath(LinkOption... options) throws IOException {
        return getPath().toRealPath(options);
    }

    @Override
    public WatchKey register(WatchService watcher, WatchEvent.Kind<?>[] events,
            WatchEvent.Modifier... modifiers) throws IOException {
        return getPath().register(watcher, events, modifiers);
    }

    @Override
    public int compareTo(Path other) {
        return getPath().compareTo(other);
    }

    @Override
    public boolean endsWith(Path other) {
        return getPath().endsWith(other);
    }

    @Override
    public Path normalize() {
        return getPath().normalize();
    }

    @Override
    public Path resolve(Path other) {
        return getPath().resolve(other);
    }

    @Override
    public boolean equals(Object o) {
        if (o != null) {
            if (o instanceof Generic_Path) {
                Generic_Path p = (Generic_Path) o;
                if (this.hashCode() == p.hashCode()) {
                    if (this.getPath().normalize().equals(p.getPath().normalize())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.s);
        return hash;
    }
}

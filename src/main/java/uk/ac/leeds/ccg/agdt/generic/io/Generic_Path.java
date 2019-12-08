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
package uk.ac.leeds.ccg.agdt.generic.io;

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

/**
 *
 * @author Andy Turner
 */
public class Generic_Path implements Serializable, Path {

    private transient Path p;

    private final String s;

    Generic_Path(Path p) {
        this.p = p;
        s = p.toString();
    }

    private Path getPath() {
        if (p == null) {
            p = new Paths.get(s);
        }
        return p;
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
    public WatchKey register(WatchService watcher, WatchEvent.Kind<?>[] events, WatchEvent.Modifier... modifiers) throws IOException {
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
}

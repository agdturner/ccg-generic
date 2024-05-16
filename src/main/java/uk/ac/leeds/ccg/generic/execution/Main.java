/*
 * Copyright 2023 Centre for Computational Geography, University of Leeds.
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
package uk.ac.leeds.ccg.generic.execution;

import java.time.Duration;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

/**
 *
 * @author Andy Turner
 */
public class Main {
    
    public Main(){}

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        new Main().run();
    }
    
    public void run(){
        System.out.println("Hello");
        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            IntStream.range(0, 10_000).forEach(i -> {
                executor.submit(() -> {
                    Thread.sleep(Duration.ofSeconds(1));
                    return i;
                });
            });
        }  // executor.close() is called implicitly, and waits
    }
}

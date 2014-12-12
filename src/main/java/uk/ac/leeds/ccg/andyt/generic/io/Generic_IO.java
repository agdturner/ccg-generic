/**
 * Copyright 2010 Andy Turner, The University of Leeds, UK
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy
 * of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package uk.ac.leeds.ccg.andyt.generic.io;

import java.io.File;

public class Generic_IO {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        new Generic_IO().run();
    }

    public void run() {
        File directory = new File("C:/Work/temp/" + this.getClass().getName());
        long range = 100;
        long max_ID = range - 1;
        File archiveDirectory_File = Generic_StaticIO.initialiseArchive(directory, range);
        for (long a_ID = 0; a_ID < (range*range) + 1; a_ID++) {
            if (a_ID == 15){
                boolean debug = true;
            }
            Generic_StaticIO.addToArchive(
                    directory,
                    range);
//            archiveDirectory_File = Generic_StaticIO.createArchive(
//                    archiveDirectory_File.getParentFile(), range);
////            for(int i = 0; i < 10000; i ++){
//            System.out.print("+");
//        }
        }
            Generic_StaticIO.addToArchive(
                    directory,
                    range);
//        for (long a_ID = 0; a_ID < range -1; a_ID++) {
//            archiveDirectory_File = Generic_StaticIO.createArchive(
//                    archiveDirectory_File.getParentFile(), range);
////            for(int i = 0; i < 10000; i ++){
////            System.out.print("+");
////        }
//        }
//
//        archiveDirectory_File = Generic_StaticIO.createArchive(
//                    archiveDirectory_File.getParentFile(), range);
//        archiveDirectory_File
//
//        File objectDirectoryFile;
//        File objectParentDirectoryFile;
//        for (long a_ID = 0; a_ID < range; a_ID++) {
//            objectParentDirectoryFile = Generic_StaticIO.getObjectDirectory(
//                    directory, a_ID, max_ID, range);
//            objectDirectoryFile = new File(objectParentDirectoryFile,"" + a_ID);
//            objectDirectoryFile.mkdirs();
//        }
//
//        for (long a_ID = range; a_ID < range * 2; a_ID++) {
//            objectParentDirectoryFile = Generic_StaticIO.getObjectDirectory(
//                    directory, a_ID, max_ID, range);
//            objectDirectoryFile = new File(objectParentDirectoryFile,"" + a_ID);
//            objectDirectoryFile.mkdirs();
//        }

//        long a_ID = 9; //101;
//        long max_ID = 16; //9999;
//        File objectDirectoryFile = Generic_StaticIO.getObjectDirectory(
//                directory, a_ID, max_ID, range);
//        objectDirectoryFile.mkdirs();
//        //File topOfArchive = directory.listFiles()[0];
//        System.out.println(objectDirectoryFile.toString());
//        max_ID = 20;//999999;
//        File topOfArchive = Generic_StaticIO.growArchive(
//                archiveDirectory_File, max_ID, range);
//        System.out.println("topOfArchive " + topOfArchive);

    }


//    public File init_archive(File directory,
//            long range){
//
//    }
}

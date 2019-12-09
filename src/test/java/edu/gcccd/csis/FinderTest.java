package edu.gcccd.csis;

import org.junit.Test;

import java.io.File;
import java.io.FileWriter;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class FinderTest {

    /**
     * BFS implementation to find extreme file
     *
     * @param p {@link Path} starting path
     * @return {@link File} extreme file
     */

    private static File findExtremeFile(final Path p) {
        final List fileList = new ArrayList();
        fileList.add(p.toFile());
        File x = null;

        while (!fileList.isEmpty()) {

            final File f = (File) fileList.remove(0);

            if (f.isFile()){
                x = Finder.extreme(x,f);
            }
            else if (f.isDirectory()) {

                final File[] fa = f.listFiles();
                if (fa != null) {
                    fileList.addAll(Arrays.asList(fa));
                }
            }
        }

        return x;
    }


    /**
     * Verify that the extreme method identifies the largest etc etc. file
     */
    @Test
    public void testExtreme() throws Exception {
        // check what happens if one file is null ..
        File f1 = null;
        final File f2 = File.createTempFile("test2_", ".tmp");
        f2.deleteOnExit();

        assertEquals(f2, Finder.extreme(f1, f2));
        assertEquals(f2, Finder.extreme(f2, f1));

        //  check what happens if both files have the same length (like 0)




        assertEquals(f2, Finder.extreme(f1, f2));
        assertEquals(f2, Finder.extreme(f2, f1));


        // check what happens if one file is larger
        // .. how to add content to a (tmp-)file:
        // https://www.baeldung.com/java-write-to-file

        FileWriter writer = new FileWriter(f2);
        writer.write("Hello World! I am a file :)");
        writer.close();


        assertEquals(f2, Finder.extreme(f2, f1));
        assertEquals(f2, Finder.extreme(f1, f2));
    }

    /**
     * Verify that DFS and BFS return the same result.
     */
    @Test
    public void findExtremeFile() throws Exception {

        final File f2 = File.createTempFile("test", ".tmp");
        f2.deleteOnExit();

        final Path p = f2.getParentFile().getParentFile().toPath();
        final File extreme1 = Finder.findExtremeFile(p); // DFS in Finder
        final File extreme2 = FinderTest.findExtremeFile(p); // This BFS Test
        assertEquals(extreme1, extreme2);
    }
}
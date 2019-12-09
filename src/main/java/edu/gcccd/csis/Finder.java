package edu.gcccd.csis;

import java.util.Arrays;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Finds the largest file using DFS.
 */
public class Finder {

    /**
     * If no start location is given, the we start the search in the current dir
     *
     * @param args {@link String}[] start location for the largest file search.
     */

    public static void main (final String[] args) {

        final Path path = Paths.get(args.length < 1 ? "." : args[0]);
        final File x = findExtremeFile(path);

       System.out.printf("Starting at : %s, the largest file was found here: \n%s\nits size is: %d bits\n",
                path.toAbsolutePath().toString(),
                x.getAbsolutePath(),
                x.length());

    }

    /**
     * Identifies the more extreme of two given files.
     * Modifying this method allows to search for other extremes, like smallest, oldest, etc.
     *
     * @param f1 {@link File} 1st file
     * @param f2 {@link File} 2nd file
     * @return {@link File} the more extreme of the two given files.
     */

    protected static File extreme(final File f1, final File f2) {

        if (f2 == null) {
            return f1;
        }

        if (f1 == null){
            return f2;
        }

        if (f1.length() == f2.length()){
            return f1.getAbsolutePath().length() > f2.getAbsolutePath().length() ? f1 : f2;
        }

        else {
            return f1.length() > f2.length() ? f1 : f2;
        }
    }

    /**
     * DFS for the most extreme file, starting the search at a given directory path.
     *
     * @param p {@link Path} path to a directory
     * @return {@link File} most extreme file in the given path
     */
    static File findExtremeFile(final Path p) {

        File x = null;
        final File[] fa = p.toFile().listFiles();

        if (fa != null && fa.length>0) { // if null then directory is probably not accessible and if <0 then directory is empty

            for(final File f: fa ){

                if (f.isFile()){
                    x = extreme(x,f);
                }

                else if(f.isDirectory()){
                    x = extreme(x,findExtremeFile(f.toPath()));
                }

            }

        }
        return x;
    }

}

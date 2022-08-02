package org.bananasamirite.minecrafttetris.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Utils {
    public static String getStringFile(String path) throws FileNotFoundException {
        return getStringFile(new File(path));
    }

    public static String getStringFile(File file) throws FileNotFoundException {
        Scanner s = new Scanner(file);
        StringBuilder str = new StringBuilder();
        while (s.hasNext()) {
            str.append(s.nextLine());
            str.append(System.lineSeparator());
        }
        s.close();

        return str.toString();
    }
}

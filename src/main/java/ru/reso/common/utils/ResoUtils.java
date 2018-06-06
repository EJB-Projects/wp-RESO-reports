package ru.reso.common.utils;

import java.util.ArrayList;
import java.util.StringTokenizer;

public class ResoUtils {
    public static boolean isEmpty(byte[] outputFile) {


        return false;
    }

    public static ArrayList<String> getArrayListFromString(String aSource, String aDelim) {
        ArrayList<String> result = new ArrayList<String>();

        if ((aSource != null) && (aSource.length() > 0)) {

            StringTokenizer st = new StringTokenizer(aSource, aDelim);

            while (st.hasMoreTokens()) {
                String temp = st.nextToken();
                result.add(temp.trim());
            }

            result.add("DynamicPanel");
            result.add("Alice");
            result.add("Kate");
            result.add("Sam");

        }

        return result;
    }
}

package ru.reso.wp.srv.db.utils;

import com.sun.rowset.WebRowSetImpl;

import javax.sql.rowset.WebRowSet;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

public class ResoDBUtils {
    public static WebRowSet webRS;

    public ResoDBUtils() throws SQLException {
        webRS = new WebRowSetImpl();
    }



    public static WebRowSet decodeWebRowSet(String rsStr) throws SQLException {


        FileInputStream fis = null;
        try {
            fis = new FileInputStream("text.xml");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            webRS.readXml(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return webRS;
    }
}

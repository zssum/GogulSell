package team7.sa43.gogulsell;

/**
 * Created by edwin on 21/12/16.
 */

import java.io.PrintWriter;
import java.io.StringWriter;

public class StackTrace
{
    public static String trace(Exception ex)
    {
        StringWriter outStream = new StringWriter();
        ex.printStackTrace(new PrintWriter(outStream));
        return outStream.toString();
    }
}

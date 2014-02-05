/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package goeurotest;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Rémi Domingues
 */
public class CSVSerializer
{
    public static final String CSV_FILE_NAME_TEMPLATE = "%s.csv";
    
    public static final String CSV_STRING_FIELD_TEMPLATE = "\"%s\"";
    
    public static final String CSV_INT_FIELD_TEMPLATE = "\"%d\"";
    
    
    public static final DecimalFormat CSV_COORD_FIELD_TEMPLATE = new DecimalFormat("\"#.######\"");
    
    public static final String CSV_SEPARATOR = ", ";
    
    public static final char CSV_CARRIAGE_RETURN = '\n';
    
    public static void serialize(final String fileName, final List<Position> positions) throws FileNotFoundException
    {        
        File file = new File(String.format(CSV_FILE_NAME_TEMPLATE, fileName));
        
        Logger.getLogger(GoEuroTest.class.getName()).log(Level.INFO, String.format("Exporting data to %s...", file.getAbsoluteFile()));
        
        PrintWriter writer = new PrintWriter(file);
        
        for(Position pos : positions)
        {
            writer.write(pos.toCSV());
        }
        
        writer.close();
    }
}

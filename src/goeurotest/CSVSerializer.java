/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package goeurotest;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Service class dedicated to the serialization of a list of positions
 * @author Remi Domingues
 */
public class CSVSerializer
{
    /** File name template */
    public static final String CSV_FILE_NAME_TEMPLATE = "%s.csv";
    
    /** CSV string field template */
    public static final String CSV_STRING_FIELD_TEMPLATE = "\"%s\"";
    
    /** CSV integer field template */
    public static final String CSV_INT_FIELD_TEMPLATE = "\"%d\"";
    
    /** CSV coordinates (latitude or longitude) field template */
    public static final DecimalFormat CSV_COORD_FIELD_TEMPLATE = new DecimalFormat("\"#.######\"");
    
    /** CSV fields separator */
    public static final String CSV_SEPARATOR = ", ";
    
    /** CSV end of object separator */
    public static final char CSV_CARRIAGE_RETURN = '\n';
    
    /**
     * Serializes a list of position in a CSV output file
     * @param fileName Output file name, without the extension
     * @param positions The list of positions to serialize
     * @throws FileNotFoundException If the file cannot be created (insufficient rights)
     */
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

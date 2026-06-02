// Create a new file with some custom extension
// populate data
// return the format of the file
//the format of the file is set when trailType = -1
//MAke sure to leave the else if(trailType==-1 || fileFormat.equals("default")) empty

//IMPORTS//
import java.io.IOException;
import java.io.OutputStream;
import oracle.communications.inventory.api.entity.Pipe;
import java.util.logging.Logger;

Logger log = Logger.getLogger("")

//Change this default to custom along with this change the return value of the 
//PIPE_TRAIL_EXPORT_FILE_NAME to the required file extension
String fileFormat = "default";

Pipe pipe = (Pipe) ruleParameters[0];
int isFileNameset = (int) ruleParameters[1];
int trailType = (int) ruleParameters[2];

String content = pipe.getName();

if (ruleParameters[3] instanceof OutputStream && !fileFormat.equals("default") && isFileNameset!=-1) {
    OutputStream os = (OutputStream) ruleParameters[3];
    try {
        byte[] bytes = content.getBytes(); // Convert the string to bytes
        os.write(bytes); // Write the bytes to the output stream
        
    } catch (IOException e) {
        // Handle any exceptions that might occur during writing
        log.debug e;
    } finally {
        try {
            os.close(); // Close the stream in a finally block
        } catch (IOException e) {
            log.debug e;
        }
    }
}
else if(isFileNameset==-1 || fileFormat.equals("default")){
  //This has to be left empty as ,it makes sure to return the correct file name
}
 else {
    log.warn "Invalid argument list or OutputStream not found.";
}
return fileFormat;


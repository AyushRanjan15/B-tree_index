import java.io.*;
import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.Date;

public class my {

    public static void main(String[] args) throws IOException {
//        String text = args[0];
        String myPageSize = "4096";

        long key = 0;
        String value = null;
        int page_offSet = 0;



        int pageSize = Integer.parseInt(myPageSize);

        String datafile = "heap." + pageSize;
        long startTime = 0;
        long finishTime = 0;
        int numRecordsLoaded = 0;
        int numberOfPagesUsed = 0;

        int numBytesInOneRecord = constants.TOTAL_SIZE;
        int numBytesInSdtnameField = constants.STD_NAME_SIZE;
        int numBytesIntField = Integer.BYTES;
        int numRecordsPerPage = pageSize/numBytesInOneRecord;
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a");
        byte[] page = new byte[pageSize];
        FileInputStream inStream = null;

        try {
            inStream = new FileInputStream(datafile);
            int numBytesRead = 0;
            startTime = System.nanoTime();
            // Create byte arrays for each field
            byte[] sdtnameBytes = new byte[numBytesInSdtnameField];
            byte[] idBytes = new byte[constants.ID_SIZE];
            byte[] dateBytes = new byte[constants.DATE_SIZE];
            byte[] yearBytes = new byte[constants.YEAR_SIZE];
            byte[] monthBytes = new byte[constants.MONTH_SIZE];
            byte[] mdateBytes = new byte[constants.MDATE_SIZE];
            byte[] dayBytes = new byte[constants.DAY_SIZE];
            byte[] timeBytes = new byte[constants.TIME_SIZE];
            byte[] sensorIdBytes = new byte[constants.SENSORID_SIZE];
            byte[] sensorNameBytes = new byte[constants.SENSORNAME_SIZE];
            byte[] countsBytes = new byte[constants.COUNTS_SIZE];

            // until the end of the binary file is reached
            while ((numBytesRead = inStream.read(page)) != -1) {
                // Process each record in page
                for (int i = 0; i < numRecordsPerPage; i++) {

                    // Copy record's SdtName (field is located at multiples of the total record byte length)
                    System.arraycopy(page, (i*numBytesInOneRecord), sdtnameBytes, 0, numBytesInSdtnameField);

                    // Check if field is empty; if so, end of all records found (packed organisation)
                    if (sdtnameBytes[0] == 0) {
                        // can stop checking records
                        break;
                    }

                    // Check for match to "text"
                    String sdtNameString = new String(sdtnameBytes);

//                    System.out.println(sdtNameString);
                    //612/23/2009 11:00:00 AM
                    // sensor_id = 6, date_time = 2/23/2009 11:00:00 AM

                    // not using in this version
//                    String sFormat = String.format("(.*)%s(.*)", text);
//                    System.out.println(sFormat);

                    /*
                     * Fixed Length Records (total size = 112 bytes):
                     * SDT_NAME field = 24 bytes, offset = 0
                     * id field = 4 bytes, offset = 24
                     * date field = 8 bytes, offset = 28
                     * year field = 4 bytes, offset = 36
                     * month field = 9 bytes, offset = 40
                     * mdate field = 4 bytes, offset = 49
                     * day field = 9 bytes, offset = 53
                     * time field = 4 bytes, offset = 62
                     * sensorid field = 4 bytes, offset = 66
                     * sensorname field = 38 bytes, offset = 70
                     * counts field = 4 bytes, offset = 108
                     *
                     * Copy the corresponding sections of "page" to the individual field byte arrays
                     */
                    System.arraycopy(page, ((i*numBytesInOneRecord) + constants.ID_OFFSET), idBytes, 0, numBytesIntField);
                    System.arraycopy(page, ((i*numBytesInOneRecord) + constants.DATE_OFFSET), dateBytes, 0, constants.DATE_SIZE);
                    System.arraycopy(page, ((i*numBytesInOneRecord) + constants.YEAR_OFFSET), yearBytes, 0, numBytesIntField);
                    System.arraycopy(page, ((i*numBytesInOneRecord) + constants.MONTH_OFFSET), monthBytes, 0, constants.MONTH_SIZE);
                    System.arraycopy(page, ((i*numBytesInOneRecord) + constants.MDATE_OFFSET), mdateBytes, 0, numBytesIntField);
                    System.arraycopy(page, ((i*numBytesInOneRecord) + constants.DAY_OFFSET), dayBytes, 0, constants.DAY_SIZE);
                    System.arraycopy(page, ((i*numBytesInOneRecord) + constants.TIME_OFFSET), timeBytes, 0, numBytesIntField);
                    System.arraycopy(page, ((i*numBytesInOneRecord) + constants.SENSORID_OFFSET), sensorIdBytes, 0, numBytesIntField);
                    System.arraycopy(page, ((i*numBytesInOneRecord) + constants.SENSORNAME_OFFSET), sensorNameBytes, 0, constants.SENSORNAME_SIZE);
                    System.arraycopy(page, ((i*numBytesInOneRecord) + constants.COUNTS_OFFSET), countsBytes, 0, numBytesIntField);

                    // Convert long data into Date object
                    Date date = new Date(ByteBuffer.wrap(dateBytes).getLong());

                    // This is key
                    System.out.println(date.getTime());
                    key = date.getTime();

                    // This is value
                    System.out.println(numberOfPagesUsed + "  " + i);
                    value = numberOfPagesUsed + "/" + i;


                    // Get a string representation of the record for printing to stdout
                    String record = sdtNameString.trim() + "," + ByteBuffer.wrap(idBytes).getInt()
                            + "," + dateFormat.format(date) + "," + ByteBuffer.wrap(yearBytes).getInt() +
                            "," + new String(monthBytes).trim() + "," + ByteBuffer.wrap(mdateBytes).getInt()
                            + "," + new String(dayBytes).trim() + "," + ByteBuffer.wrap(timeBytes).getInt()
                            + "," + ByteBuffer.wrap(sensorIdBytes).getInt() + "," +
                            new String(sensorNameBytes).trim() + "," + ByteBuffer.wrap(countsBytes).getInt();
                    System.out.println(record);


                }

                System.out.println("#################"+numberOfPagesUsed+"######################");
                numberOfPagesUsed++;

                // At end of csv, check if there are records in the current page to be written out
//                if (numRecordsLoaded % numRecordsPerPage != 0) {
//
//                }
            }


            ///// timer finish
//            finishTime = System.nanoTime();
        }
        catch (FileNotFoundException e) {
            System.err.println("File not found " + e.getMessage());
        }
        catch (IOException e) {
            System.err.println("IO Exception " + e.getMessage());
        }
        finally {

            if (inStream != null) {
                inStream.close();
            }
        }

//        long timeInMilliseconds = (finishTime - startTime)/constants.MILLISECONDS_PER_SECOND;
//        System.out.println("Time taken: " + timeInMilliseconds + " ms");
    }
}


//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.company;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.Date;

public class treeBuilder {
    public treeBuilder() {
    }

    public static BPlusTree builder(String pageSizeIP) throws IOException {
        int value_inserted = 0;
        int pageSize = Integer.parseInt(pageSizeIP);
        String datafile = "heap." + pageSize;
        long startTime = 0L;
        long finishTime = 0L;
//        int numRecordsLoaded = false;
        int numberOfPagesUsed = 0;
        int numBytesInOneRecord = 112;
        int numBytesInSdtnameField = 24;
        int numBytesIntField = 4;
        int numRecordsPerPage = pageSize / numBytesInOneRecord;
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a");
        byte[] page = new byte[pageSize];
        FileInputStream inStream = null;

        try {
            inStream = new FileInputStream(datafile);
//            int numBytesRead = false;
            startTime = System.nanoTime();
            byte[] sdtnameBytes = new byte[numBytesInSdtnameField];
            byte[] idBytes = new byte[4];
            byte[] dateBytes = new byte[8];
            byte[] yearBytes = new byte[4];
            byte[] monthBytes = new byte[9];
            byte[] mdateBytes = new byte[4];
            byte[] dayBytes = new byte[9];
            byte[] timeBytes = new byte[4];
            byte[] sensorIdBytes = new byte[4];
            byte[] sensorNameBytes = new byte[38];
            byte[] countsBytes = new byte[4];
            BPlusTree testTree = new BPlusTree();
            String value = null;

            for(boolean var32 = false; inStream.read(page) != -1; ++numberOfPagesUsed) {
                for(int i = 0; i < numRecordsPerPage; ++i) {
                    long key = 0L;
                    System.arraycopy(page, i * numBytesInOneRecord, sdtnameBytes, 0, numBytesInSdtnameField);
                    if (sdtnameBytes[0] == 0) {
                        break;
                    }

                    String sdtNameString = new String(sdtnameBytes);
                    System.arraycopy(page, i * numBytesInOneRecord + 24, idBytes, 0, numBytesIntField);
                    System.arraycopy(page, i * numBytesInOneRecord + 28, dateBytes, 0, 8);
                    System.arraycopy(page, i * numBytesInOneRecord + 36, yearBytes, 0, numBytesIntField);
                    System.arraycopy(page, i * numBytesInOneRecord + 40, monthBytes, 0, 9);
                    System.arraycopy(page, i * numBytesInOneRecord + 49, mdateBytes, 0, numBytesIntField);
                    System.arraycopy(page, i * numBytesInOneRecord + 53, dayBytes, 0, 9);
                    System.arraycopy(page, i * numBytesInOneRecord + 62, timeBytes, 0, numBytesIntField);
                    System.arraycopy(page, i * numBytesInOneRecord + 66, sensorIdBytes, 0, numBytesIntField);
                    System.arraycopy(page, i * numBytesInOneRecord + 70, sensorNameBytes, 0, 38);
                    System.arraycopy(page, i * numBytesInOneRecord + 108, countsBytes, 0, numBytesIntField);
                    Date date = new Date(ByteBuffer.wrap(dateBytes).getLong());
                    key = keyBuilder(ByteBuffer.wrap(sensorIdBytes).getInt(), date.getTime());
                    value = numberOfPagesUsed + "/" + i;
                    testTree.insert(key, value);
                    ++value_inserted;
                    (new StringBuilder()).append(sdtNameString.trim()).append(",").append(ByteBuffer.wrap(idBytes).getInt()).append(",").append(dateFormat.format(date)).append(",").append(ByteBuffer.wrap(yearBytes).getInt()).append(",").append((new String(monthBytes)).trim()).append(",").append(ByteBuffer.wrap(mdateBytes).getInt()).append(",").append((new String(dayBytes)).trim()).append(",").append(ByteBuffer.wrap(timeBytes).getInt()).append(",").append(ByteBuffer.wrap(sensorIdBytes).getInt()).append(",").append((new String(sensorNameBytes)).trim()).append(",").append(ByteBuffer.wrap(countsBytes).getInt()).toString();
                }

                float totalRecods = 3574594.0F;
                float percentTreeBuilt = (float)numberOfPagesUsed * (float)numRecordsPerPage / totalRecods;
                System.out.println("Tree is being created#################" + percentTreeBuilt * 100.0F + "% ######################");
            }

            System.out.println("value_inserted" + value_inserted);
            System.out.println("####################################");
            BPlusTree var47 = testTree;
            return var47;
        } catch (FileNotFoundException var43) {
            System.err.println("File not found " + var43.getMessage());
        } catch (IOException var44) {
            System.err.println("IO Exception " + var44.getMessage());
        } finally {
            if (inStream != null) {
                inStream.close();
            }

        }

        System.out.println("value_inserted" + value_inserted);
        return null;
    }

    public static long keyBuilder(int sID, long dateLong) {
        int count = 0;

        while(true) {
            long check = dateLong % 10L;
            if (check != 0L | count >= 4) {
                return (long)sID * 100000000000L + dateLong;
            }

            dateLong /= 10L;
            ++count;
        }
    }
}

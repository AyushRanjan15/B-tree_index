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
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class treeQuery {
    public treeQuery() {
    }

    public static void main(String[] args) throws IOException {
        String pageSize = args[0];
        String fileLocation = args[1];
        BPlusTree myTree = treeBuilder.builder(pageSize);
        boolean loopTerminator = false;

        while(!loopTerminator) {
            Scanner reader = new Scanner(System.in);
            System.out.println("Will you be using using CLUSTERED or UN-CLUSTERED index ?\n press 'C' for CLUSTERED \n puess 'U' for UN-CLUSTERED");
            String heapTypeString = reader.next();
            boolean heapTypeFlag = false;
            if (heapTypeString.equals("C")) {
                heapTypeFlag = true;
            } else {
                if (!heapTypeString.equals("U")) {
                    System.out.println("Please select U/C");
                    continue;
                }

                heapTypeFlag = false;
            }

            System.out.println("Which kind of query you want to execute: \n press 1 for SID_Name EQUALITY query\n press 2 for SID_Name RANGE query\n press 3 for SID EQUALITY query");
            int queryType = reader.nextInt();
            System.out.println("give the record to search for");
            long startTime = 0L;
            long endTime = 0L;
            String fullQuery = reader.next();
            fullQuery = fullQuery + reader.nextLine();
            System.out.println("fullQuery : " + fullQuery);
            long finish;
            long r0_datTimeLong;
            int sID;
            if (fullQuery.length() > 5 & fullQuery.length() < 30 & queryType == 1) {
                try {
                    String[] queryArr = fullQuery.split("-");
                    sID = Integer.parseInt(queryArr[0]);
                    String dateTime = queryArr[1];
                    Date d = new Date(dateTime);
                    finish = d.getTime();
                    r0_datTimeLong = treeBuilder.keyBuilder(sID, finish);
                    String recordLoc = myTree.search(r0_datTimeLong).toString();
                    startTime = System.nanoTime();
                    queryExecuter(recordLoc, pageSize, fileLocation);
                    endTime = System.nanoTime();
                } catch (NumberFormatException var40) {
                    System.out.println("The SID_Name should be in form SID-Datetime : /n example :- 18-11/01/2019 06:00:00 PM");
                    continue;
                }
            } else if (fullQuery.length() < 4 & queryType == 3 & !heapTypeFlag) {
                try {
                    sID = Integer.parseInt(fullQuery);
                    new ArrayList();
                    long start = (long)sID * 100000000000L;
                    finish = (long)(sID + 1) * 100000000000L;
                    System.out.println("QUERY VALUES");
                    startTime = System.nanoTime();
                    ArrayList<String> recArray = myTree.searchRange(start, finish);

                    for(int i = 0; i < recArray.size(); ++i) {
                        queryExecuter((String)recArray.get(i), pageSize, fileLocation);
                    }

                    endTime = System.nanoTime();
                } catch (NumberFormatException var42) {
                    System.out.println("The SID should be in form SID : /n example :- 18");
                    continue;
                }
            } else {
                int r0_sID;
                String r0_dateTime;
                if (fullQuery.length() < 4 & queryType == 3 & heapTypeFlag) {
                    try {
                        sID = Integer.parseInt(fullQuery);
                        new ArrayList();
                        r0_sID = sID + 1;
                        startTime = System.nanoTime();
                        r0_dateTime = myTree.searchRangeClustered((long)sID * 100000000000L);
                        System.out.println("QUERY VALUES");
                        searchRangeClusteredSID(sID, r0_sID, r0_dateTime, pageSize, fileLocation);
                        endTime = System.nanoTime();
                    } catch (NumberFormatException var39) {
                        System.out.println("The SID should be in form SID : /n example :- 18");
                        continue;
                    }
                } else {
                    Date r0_d;
                    String[] r1_queryArr;
                    int r1_sID;
                    String r1_dateTime;
                    Date r1_d;
                    long r1_datTimeLong;
                    long r1_queryKey;
                    String[] ranges;
                    String[] r0_queryArr;
                    long r0_queryKey;
                    if (fullQuery.split(",").length > 1 & queryType == 2 & !heapTypeFlag) {
                        try {
                            System.out.println("here3");
                            new ArrayList();
                            ranges = fullQuery.split(",");
                            if (ranges[0].length() > 8) {
                                r0_queryArr = ranges[0].split("-");
                                r0_sID = Integer.parseInt(r0_queryArr[0]);
                                r0_dateTime = r0_queryArr[1];
                                r0_d = new Date(r0_dateTime);
                                r0_datTimeLong = r0_d.getTime();
                                r0_queryKey = treeBuilder.keyBuilder(r0_sID, r0_datTimeLong);
                                r1_queryArr = ranges[1].split("-");
                                r1_sID = Integer.parseInt(r1_queryArr[0]);
                                r1_dateTime = r1_queryArr[1];
                                r1_d = new Date(r1_dateTime);
                                r1_datTimeLong = r1_d.getTime();
                                r1_queryKey = treeBuilder.keyBuilder(r1_sID, r1_datTimeLong);
                                System.out.println("QUERY VALUES");
                                startTime = System.nanoTime();
                                ArrayList<String> recArray = myTree.searchRange(r0_queryKey, r1_queryKey);

                                for(int i = 0; i < recArray.size(); ++i) {
                                    queryExecuter((String)recArray.get(i), pageSize, fileLocation);
                                }

                                endTime = System.nanoTime();
                            }
                        } catch (NumberFormatException var41) {
                            System.out.println("The SID_Name range should be in form SID-Datetime,SID-Datetime : /n example :- 18-11/01/2019 06:00:00 PM,18-11/01/2019 10:00:00 PM");
                            continue;
                        }
                    } else if (fullQuery.split(",").length > 1 & queryType == 2 & heapTypeFlag) {
                        try {
                            String resultLeafAddress = "";
                            ranges = fullQuery.split(",");
                            if (ranges[0].length() > 8) {
                                r0_queryArr = ranges[0].split("-");
                                r0_sID = Integer.parseInt(r0_queryArr[0]);
                                r0_dateTime = r0_queryArr[1];
                                r0_d = new Date(r0_dateTime);
                                r0_datTimeLong = r0_d.getTime();
                                r0_queryKey = treeBuilder.keyBuilder(r0_sID, r0_datTimeLong);
                                r1_queryArr = ranges[1].split("-");
                                r1_sID = Integer.parseInt(r1_queryArr[0]);
                                r1_dateTime = r1_queryArr[1];
                                r1_d = new Date(r1_dateTime);
                                r1_datTimeLong = r1_d.getTime();
                                r1_queryKey = treeBuilder.keyBuilder(r1_sID, r1_datTimeLong);
                                System.out.println("QUERY VALUES");
                                startTime = System.nanoTime();
                                long startTimeD = System.nanoTime();
                                String startLoc = myTree.search(r0_queryKey).toString();
                                long endTimeD = System.nanoTime();
                                String endLoc = myTree.search(r1_queryKey).toString();
                                queryExecuterClustered(startLoc, endLoc, pageSize, fileLocation);
                                System.out.println("For d : " + (float)(endTimeD - startTimeD) / 1000000.0F);
                                endTime = System.nanoTime();
                            }
                        } catch (NumberFormatException var38) {
                            System.out.println("The SID_Name range should be in form SID-Datetime,SID-Datetime : /n example :- 18-11/01/2019 06:00:00 PM,18-11/01/2019 10:00:00 PM");
                            continue;
                        }
                    }
                }
            }

            System.out.println("FINAL TIME : " + (float)(endTime - startTime) / 1000000.0F + "Ms");
            System.out.println("Do you want to continue? (Press 0 to continue or any other key to stop)");
            sID = reader.nextInt();
            System.out.println(sID);
            if (sID != 0) {
                System.out.println("here");
                break;
            }

            System.out.println("Index is made on SID_Name (SID, Date Time)/n A range of equality query can be performed on SID/SID_Name");
        }

    }

    public static void searchRangeClusteredSID(int start, int finish, String firstPage, String pageSizeIP, String fileLocation) {
//        int value_inserted = false;
        int pageSize = Integer.parseInt(pageSizeIP);
        String datafile = "heap." + pageSize;
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
            String value = null;
//            int page_offSet = false;
            String[] startValues = firstPage.split("/");
            int startpageNo = Integer.parseInt(startValues[0]);
            int currentPagenumber = 0;

            while(true) {
                while(inStream.read(page) != -1) {
                    if (currentPagenumber >= startpageNo) {
                        for(int i = 0; i < numRecordsPerPage; ++i) {
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
                            String record = sdtNameString.trim() + "," + ByteBuffer.wrap(idBytes).getInt() + "," + dateFormat.format(date) + "," + ByteBuffer.wrap(yearBytes).getInt() + "," + (new String(monthBytes)).trim() + "," + ByteBuffer.wrap(mdateBytes).getInt() + "," + (new String(dayBytes)).trim() + "," + ByteBuffer.wrap(timeBytes).getInt() + "," + ByteBuffer.wrap(sensorIdBytes).getInt() + "," + (new String(sensorNameBytes)).trim() + "," + ByteBuffer.wrap(countsBytes).getInt();
                            int startComp = ByteBuffer.wrap(sensorIdBytes).getInt();
                            if (startComp >= start & startComp < finish) {
                                System.out.println(record);
                            } else if (startComp > finish) {
                                break;
                            }
                        }

                        ++currentPagenumber;
                    } else {
                        ++currentPagenumber;
                    }
                }

                return;
            }
        } catch (FileNotFoundException var38) {
            System.err.println("File not found " + var38.getMessage());
        } catch (IOException var39) {
            System.err.println("IO Exception " + var39.getMessage());
        }

    }

    public static void queryExecuter(String pageIdentifier, String pageSizeIP, String fileLocation) throws IOException {
        String[] values = pageIdentifier.split("/");
        int pageNo = Integer.parseInt(values[0]);
        int recordNo = Integer.parseInt(values[1]);
//        int value_inserted = false;
        int pageSize = Integer.parseInt(pageSizeIP);
        String datafile = "heap." + pageSize;
        int numBytesInOneRecord = 112;
        int numBytesInSdtnameField = 24;
        int numBytesIntField = 4;
        int var10000 = pageSize / numBytesInOneRecord;
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a");
        byte[] page = new byte[pageSize];
        FileInputStream inStream = null;

        try {
            inStream = new FileInputStream(datafile);
//            int numBytesRead = false;
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
            String value = null;
//            int page_offSet = false;

            for(int loopControler = 0; inStream.read(page) != -1; ++loopControler) {
                if (loopControler == pageNo) {
                    System.arraycopy(page, recordNo * numBytesInOneRecord, sdtnameBytes, 0, numBytesInSdtnameField);
                    if (sdtnameBytes[0] != 0) {
                        String sdtNameString = new String(sdtnameBytes);
                        System.arraycopy(page, recordNo * numBytesInOneRecord + 24, idBytes, 0, numBytesIntField);
                        System.arraycopy(page, recordNo * numBytesInOneRecord + 28, dateBytes, 0, 8);
                        System.arraycopy(page, recordNo * numBytesInOneRecord + 36, yearBytes, 0, numBytesIntField);
                        System.arraycopy(page, recordNo * numBytesInOneRecord + 40, monthBytes, 0, 9);
                        System.arraycopy(page, recordNo * numBytesInOneRecord + 49, mdateBytes, 0, numBytesIntField);
                        System.arraycopy(page, recordNo * numBytesInOneRecord + 53, dayBytes, 0, 9);
                        System.arraycopy(page, recordNo * numBytesInOneRecord + 62, timeBytes, 0, numBytesIntField);
                        System.arraycopy(page, recordNo * numBytesInOneRecord + 66, sensorIdBytes, 0, numBytesIntField);
                        System.arraycopy(page, recordNo * numBytesInOneRecord + 70, sensorNameBytes, 0, 38);
                        System.arraycopy(page, recordNo * numBytesInOneRecord + 108, countsBytes, 0, numBytesIntField);
                        Date date = new Date(ByteBuffer.wrap(dateBytes).getLong());
                        String record = sdtNameString.trim() + "," + ByteBuffer.wrap(idBytes).getInt() + "," + dateFormat.format(date) + "," + ByteBuffer.wrap(yearBytes).getInt() + "," + (new String(monthBytes)).trim() + "," + ByteBuffer.wrap(mdateBytes).getInt() + "," + (new String(dayBytes)).trim() + "," + ByteBuffer.wrap(timeBytes).getInt() + "," + ByteBuffer.wrap(sensorIdBytes).getInt() + "," + (new String(sensorNameBytes)).trim() + "," + ByteBuffer.wrap(countsBytes).getInt();
                        System.out.println(record);
                    }
                    break;
                }

                if (loopControler > pageNo) {
                    break;
                }
            }
        } catch (FileNotFoundException var41) {
            System.err.println("File not found " + var41.getMessage());
        } catch (IOException var42) {
            System.err.println("IO Exception " + var42.getMessage());
        } finally {
            if (inStream != null) {
                inStream.close();
            }

        }

    }

    public static void queryExecuterClustered(String startLoc, String endLoc, String pageSizeIP, String fileLocation) throws IOException {
        String[] startValues = startLoc.split("/");
        int startpageNo = Integer.parseInt(startValues[0]);
        int startrecordNo = Integer.parseInt(startValues[1]);
        String[] endValues = endLoc.split("/");
        int endpageNo = Integer.parseInt(endValues[0]);
        int endtrecordNo = Integer.parseInt(endValues[1]);
//        int value_inserted = false;
        int pageSize = Integer.parseInt(pageSizeIP);
        String datafile = "heap." + pageSize;
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
            String value = null;
//            int page_offSet = false;

            for(int currentPagenumber = 0; inStream.read(page) != -1; ++currentPagenumber) {
                if (currentPagenumber >= startpageNo & currentPagenumber <= endpageNo) {
                    for(int i = 0; i < numRecordsPerPage; ++i) {
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
                        String record = sdtNameString.trim() + "," + ByteBuffer.wrap(idBytes).getInt() + "," + dateFormat.format(date) + "," + ByteBuffer.wrap(yearBytes).getInt() + "," + (new String(monthBytes)).trim() + "," + ByteBuffer.wrap(mdateBytes).getInt() + "," + (new String(dayBytes)).trim() + "," + ByteBuffer.wrap(timeBytes).getInt() + "," + ByteBuffer.wrap(sensorIdBytes).getInt() + "," + (new String(sensorNameBytes)).trim() + "," + ByteBuffer.wrap(countsBytes).getInt();
                        if (currentPagenumber < endpageNo) {
                            if (i >= startrecordNo) {
                                System.out.println(record);
                            }
                        } else if (currentPagenumber == endpageNo && i <= endtrecordNo) {
                            System.out.println(record);
                        }
                    }
                } else if (currentPagenumber > endpageNo) {
                    break;
                }
            }
        } catch (FileNotFoundException var44) {
            System.err.println("File not found " + var44.getMessage());
        } catch (IOException var45) {
            System.err.println("IO Exception " + var45.getMessage());
        } finally {
            if (inStream != null) {
                inStream.close();
            }

        }

    }
}

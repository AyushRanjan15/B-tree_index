# Heap & B+tree

<!-- ABOUT THE PROJECT -->
## About The Project

### Databases and Files

A database may contain many files.

A file itself is stored as a set of blocks on the disk, that is, the data is made up of many logical blocks.

How do we keep track of the blocks belonging to a file and how do we allocate blocks to a file?

### Unordered (Heap) Files

Simplest structure contains records in no particular order

### Heap File Block Directory Approach

![directory Screen Shot][directory_of_blocks]
  
  
“ A program to load a database relation writing of a heap file ”. The data store in pages which is loaded from the csv file will be unordered and the only way to access all the pages is by repeated request from the files.
  
  
## Index
  
An Index on a file speeds up selections on the search key. An index contains a collection of data entries, and supports efficient retrieval of all data entries k∗ with a given search key value k.
  
  ![index Screen Shot][index]
    
## B+-tree Index File
    
![B+tree Screen Shot][B+tree]
    
The B+-tree is the most widely used index structure. It has the following characteristics:
    
* Internal nodes direct the search, index data entries are in the leaf
* The tree is kept height-balanced
* Insert and delete at logF N cost, where F is the fan-out and N the number of leaves
* Minimum 50% occupancy of nodes (except for the root).
* Each node contains d <= m <= 2d entries, where the parameter d is called the order of the tree.
* Supports equality and range-searches efficiently
* Leaf pages are organised as a double linked list for fast traversal and reorganisation
 
Clustered vs. unclustered: If order of data records is the same as, or “close to” the order of the data entries, then the index is a clustered index.
  
![types of index Screen Shot][clustered-unclustered]

# DESCRIPTION OF PROGRAMME
    
## DATA DESCRIPTION
  
link to data - https://data.melbourne.vic.gov.au/Transport/Pedestrian-Counting-System- Monthly-counts-per-hour/b2ak-trbp
  
![data description screenshot][data description]
    
There are two stages to building a tree file:
    
1. Building a heap file from the provided .csv file (downloaded through provided link)
    
2. Building the tree from the heap file created in 1st stage.
    
## HEAP FILE:

There are two types of B+tree Indexes that can be made using the provided code: 
1. Clustered Index
2. Unclustered Index
    
Experiments done using two different heaps highlighted advantages and disadvantages of both.
    
### 1. UNCLUSTERED INDEX PREPROCESSING
    
To make an unclustered index using the provided code we don’t nee to do any preprocessing. The records can be randomly distributed through the file. We will need to converter the provided csv file to a binary heap, that can be further used to build the tree.
    
`java dbload -p 4096 data.csv`

Note:- 4096 is pagesize
    
After this code finishes processing we will have a ``heap.pagesize`` file. Which will contain all the records in the binary format and can be used to create unclustered indexes.
    
### 2. CLUSTERED INDEX PREPROCESSING
    
To make an unclustered index using the provided code we will have to sort the record according to desired order of the key and write that file in binary format using bulk loading approach.
    
There is a python script provided (sortData.py) which can be used to sort the data with accordance of SID_NAME key.
    
To make a sorted heap file follow the following steps: 
1. Install pandas
`sudo pip install pandas`
2. Run sortData.py
`python sortData.py`
    
After the script has stopped processing we will receive a “clustered.csv” file.

 Note: make sure that the input file and sortData.py are in the same directory. 
 Note: make sure the data file that is input to sortData.py is named data.csv
    
Once we have the “clustered.csv” file we can run our dbload to write the binary file.
    
`java dbload -p 4096 clustered.csv`
    
After this code finishes processing we will have a “heap.pagesize” file. Which will contain all the records in the binary format and can be used to create clusterd indexes.
    
## B+TREE INDEX:
    
The tree can be build in memory using the `“heap.pagesize”` file. Wether the indexes will be clustered or unclustered will depend on which binary file you are loading from the previous step. The operations on the tree can be performed using a command line application which will hold the tree in memory and allow us to make repeated queries.
    
Use the following command to run the tree:
    
java com.company.treeQuery pagesize fullpathfile
    
Once the tree is constructed the prompt will ask about which index structure we are making? (CLUSTERED/UNCLUSTERED).
    
Format of a query:
1- An equality query on SID_Name should be of form
    
  `18-11/01/2019 06:00:00 PM`
    
2- A range query on SID_Name should be of form
    
  `18-11/01/2019 06:00:00 PM,18-11/01/2019 10:00:00 PM`
    
# EXPERIMENTS
    
## 1.FAN-OUT
In the 1st set of experiments I tried to come up with best value of fanout by testing different values and comparing them. (The time’s are average of 5 data points). To experiment was based on SID_Name equality query on an unclustered index.
    
![fan-out Screen Shot][fan_out]
    
From the results above I came to a conclusion that 350 is the best possible fanout for my tree structure.
    
## 2. QUERIES COMPARISONS EQUALITY QUERIES
    
![equality Screen Shot][equality_query]
  
From the table above it can be observed that there is no difference in the execution time of equality queries. The difference is rather based on position of records in the heap. (clustered index is using sorted file).
    
## 3. RANGE QUERIES
    
![range Screen Shot][range_query]
  
From the table above it can be observed that clustered indexes performed better than unclustered indexes in case of a range query. This difference becomes more prominent when the range is large. The system has to jump to multiple pages to assess records. However, In a clustered index all the records are next to each other.
We also have to consider the time took to sort the file and wether it is will be worth it or not. In our case because relatively small size of file and speed of Python pandas sort function the sort time was negligible.
    
    
### Built With

* [Java](https://www.java.com/en/)


[B+tree]: images_project/B+tree.png
[clustered-unclustered]: images_project/clustered-unclustered.png
[data description]: images_project/data_description.png
[directory_of_blocks]: images_project/directory_of_blocks.png
[index]: images_project/index.png
[packed]: images_project/packed.png
[equality_query]: images_project/equality_query.png
[range_query]: images_project/range_query.png
[fan_out]: images_project/fan_out.png


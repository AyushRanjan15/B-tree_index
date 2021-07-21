# B-tree_index

<!--
*** Thanks for checking out the Best-README-Template. If you have a suggestion
*** that would make this better, please fork the repo and create a pull request
*** or simply open an issue with the tag "enhancement".
*** Thanks again! Now go create something AMAZING! :D
-->



<!-- PROJECT SHIELDS -->
<!--
*** I'm using markdown "reference style" links for readability.
*** Reference links are enclosed in brackets [ ] instead of parentheses ( ).
*** See the bottom of this document for the declaration of the reference variables
*** for contributors-url, forks-url, etc. This is an optional, concise syntax you may use.
*** https://www.markdownguide.org/basic-syntax/#reference-style-links
-->
[![Contributors][contributors-shield]][contributors-url]
[![Forks][forks-shield]][forks-url]
[![Stargazers][stars-shield]][stars-url]
[![Issues][issues-shield]][issues-url]
[![MIT License][license-shield]][license-url]
[![LinkedIn][linkedin-shield]][linkedin-url]



<!-- PROJECT LOGO -->
<br />
<p align="center">
  <a href="https://github.com/othneildrew/Best-README-Template">
    <img src="images/logo.png" alt="Logo" width="80" height="80">
  </a>

  <h3 align="center">Best-README-Template</h3>

  <p align="center">
    An awesome README template to jumpstart your projects!
    <br />
    <a href="https://github.com/othneildrew/Best-README-Template"><strong>Explore the docs »</strong></a>
    <br />
    <br />
    <a href="https://github.com/othneildrew/Best-README-Template">View Demo</a>
    ·
    <a href="https://github.com/othneildrew/Best-README-Template/issues">Report Bug</a>
    ·
    <a href="https://github.com/othneildrew/Best-README-Template/issues">Request Feature</a>
  </p>
</p>



<!-- TABLE OF CONTENTS -->
<details open="open">
  <summary>Table of Contents</summary>
  <ol>
    <li>
      <a href="#about-the-project">About The Project</a>
      <ul>
        <li><a href="#built-with">Built With</a></li>
      </ul>
    </li>
    <li>
      <a href="#getting-started">Getting Started</a>
      <ul>
        <li><a href="#prerequisites">Prerequisites</a></li>
        <li><a href="#installation">Installation</a></li>
      </ul>
    </li>
    <li><a href="#usage">Usage</a></li>
    <li><a href="#roadmap">Roadmap</a></li>
    <li><a href="#contributing">Contributing</a></li>
    <li><a href="#license">License</a></li>
    <li><a href="#contact">Contact</a></li>
    <li><a href="#acknowledgements">Acknowledgements</a></li>
  </ol>
</details>



<!-- ABOUT THE PROJECT -->
## About The Project

[![Product Name Screen Shot][product-screenshot]](https://example.com)

There are many great README templates available on GitHub, however, I didn't find one that really suit my needs so I created this enhanced one. I want to create a README template so amazing that it'll be the last one you ever need -- I think this is it.

## Databases and Files

A database may contain many files.

A file itself is stored as a set of blocks on the disk, that is, the data is made up of many logical blocks.

How do we keep track of the blocks belonging to a file and how do we allocate blocks to a file?

### Unordered (Heap) Files

Simplest structure contains records in no particular order (more on records later and file structures in the next lecture)

### Heap File Block Directory Approach

<insert directory of blocks image>
  
## 1st part of program
  
# DATA DESCRIPTION
  
  link to data - https://data.melbourne.vic.gov.au/Transport/Pedestrian-Counting-System- Monthly-counts-per-hour/b2ak-trbp
  
<data description image>
  
This section describes the working of task “ A program to load a database relation writing of a heap file ”. The data store in pages which is loaded from the csv file will be unordered and the only way to access all the pages is by repeated request from the files.
  
RUNNING THE CODE
As described in the task specifications, the dbload.class file can be run using 
  
  Java dboad -p pagesize datafile
  
The output will be received in a folder named 
  
  dbload.pagesize
  
  
## Index
  
An Index on a file speeds up selections on the search key. An index contains a collection of data entries, and supports efficient retrieval of all data entries k∗ with a given search key value k.
  
  <index image>
    
  ## B+-tree Index File
    
  The B+-tree is the most widely used index structure.  It has the following characteristics:
    
• Internal nodes direct the search, index data entries are in the leaf
• The tree is kept height-balanced
• Insert and delete at logF N cost, where F is the fan-out and N the number of leaves
• Minimum 50% occupancy of nodes (except for the root).
• Each node contains d <= m <= 2d entries, where the parameter d is called the order of the tree.
• Supports equality and range-searches efficiently
• Leaf pages are organised as a double linked list for fast traversal and reorganisation
 
  Clustered vs. unclustered: If order of data records is the same as, or “close to” the order of the data entries, then the index is a clustered index.
  
## 2nd part of project
    
    ### Implement a B+-tree Index in Java
    
    Implement a B+-tree Index in Java for you heap file and conduct experiments querying (equality query and range query) with and without the index.
    
    
    
    ---------------------------------
    
    There are two stages to building a tree file:
    
1. Building a heap file from the provided .csv file
    
2. Building the tree from the heap file created in 1st stage.
    
HEAP FILE:
There are two types of B+tree Indexes that can be made using the provided code: 
    1. Clustered Index
    2. Unclustered Index
    
Experiments done using two different heaps highlighted advantages and disadvantages of both.
    
1. UNCLUSTERED INDEX PREPROCESSING
    
To make an unclustered index using the provided code we don’t nee to do any preprocessing. The records can be randomly distributed through the file. We will need to converter the provided csv file to a binary heap, that can be further used to build the tree.
    
java dbload -p 4096 data.csv
    
After this code finishes processing we will have a “heap.pagesize” file. Which will contain all the records in the binary format and can be used to create unclustered indexes.
    
2. CLUSTERED INDEX PREPROCESSING
    
To make an unclustered index using the provided code we will have to sort the record according to desired order of the key and write that file in binary format using bulk loading approach.
    
There is a python script provided (sortData.py) which can be used to sort the data with accordance of SID_NAME key.
    
To make a sorted heap file follow the following steps: 
    1. Install pandas
    
    sudo pip install pandas
  
    2. Run sortData.py
    
    python sortData.py
    
After the script has stopped processing we will receive a “clustered.csv” file.

 Note: make sure that the input file and sortData.py are in the same directory. 
 Note: make sure the data file that is input to sortData.py is named data.csv
    
Once we have the “clustered.csv” file we can run our dbload to write the binary file.
    
java dbload -p 4096 clustered.csv
    
After this code finishes processing we will have a “heap.pagesize” file. Which will contain all the records in the binary format and can be used to create clusterd indexes.
    
B+TREE INDEX:
    
The tree can be build in memory using the “heap.pagesize” file. Wether the indexes will be clustered or unclustered will depend on which binary file you are loading from the previous step. The operations on the tree can be performed using a command line application which will hold the tree in memory and allow us to make repeated queries.
    
Use the following command to run the tree:
    
java com.company.treeQuery pagesize fullpathfile
    
Once the tree is constructed the prompt will ask about which index structure we are making? (CLUSTERED/UNCLUSTERED).
    
Format of a query:
1- An equality query on SID_Name should be of form
    
  18-11/01/2019 06:00:00 PM
    
2- A range query on SID_Name should be of form
    
  18-11/01/2019 06:00:00 PM,18-11/01/2019 10:00:00 PM
    
## EXPERIMENTS
    
    1.FAN-OUT
In the 1st set of experiments I tried to come up with best value of fanout by testing different values and comparing them. (The time’s are average of 5 data points). To experiment was based on SID_Name equality query on an unclustered index.
    
    D avg. time (Ms)
10 20020.484
100 20653.184
200 21296.061
350 19981.15
500 21605.014
700 22066.51
    
    From the results above I came to a conclusion that 350 is the best possible fanout for my tree structure.
    
    2. QUERIES COMPARISONS EQUALITY QUERIES
CLUSTERED QUERIES UNCLUSTERED
1.126893Ms 1-01/01/2010 05:00:00 AM 8.742448Ms
64.640526Ms 2-11/01/2019 08:00:00 PM 0.436348Ms
115.64136Ms 30-02/28/2021 08:00:00 PM 170.55524Ms
153.2405Ms 46-11/01/2019 09:00:00 PM 0.87124Ms
173.71156Ms 72-02/28/2021 10:00:00 PM 173.73566Ms
From the table above it can be observed that there is no difference in the execution time of equality queries. The difference is rather based on position of records in the heap. (clustered index is using sorted file).
    
    RANGE QUERIES
QUERIES STARTING POINT
QUERIES
ENDING N UNCLUSTERED CLUSTERED POINT
1-05/01/2009 1-05/01/2009 12:00:00 AM 06:00:00 AM
5 14.55439Ms 0.96154Ms
1-05/01/2009 1-05/05/2009 12:00:00 AM 03:00:00 AM
100 215.57169Ms 3.782933Ms
1-05/01/2009 1-06/11/2009 12:00:00 AM 04:00:00 PM
1000 2212.2744Ms 52.857834Ms
1-05/01/2009 1-11/25/2009 12:00:00 AM 08:00:00 AM
5000 21431.334Ms 250.37111Ms
1-05/01/2009 1-06/21/2010 12:00:00 AM 04:00:00 PM
10000 60400.08Ms 415.1099Ms
From the table above it can be observed that clustered indexes performed better than unclustered indexes in case of a range query. This difference becomes more prominent when the range is large. The system has to jump to multiple pages to assess records. However, In a clustered index all the records are next to each other.
We also have to consider the time took to sort the file and wether it is will be worth it or not. In our case because relatively small size of file and speed of Python pandas sort function the sort time was negligible.
    
    
### Built With

This section should list any major frameworks that you built your project using. Leave any add-ons/plugins for the acknowledgements section. Here are a few examples.
* [Bootstrap](https://getbootstrap.com)
* [JQuery](https://jquery.com)
* [Laravel](https://laravel.com)



<!-- GETTING STARTED -->
## Getting Started

This is an example of how you may give instructions on setting up your project locally.
To get a local copy up and running follow these simple example steps.

### Prerequisites

This is an example of how to list things you need to use the software and how to install them.
* npm
  ```sh
  npm install npm@latest -g
  ```

### Installation

1. Get a free API Key at [https://example.com](https://example.com)
2. Clone the repo
   ```sh
   git clone https://github.com/your_username_/Project-Name.git
   ```
3. Install NPM packages
   ```sh
   npm install
   ```
4. Enter your API in `config.js`
   ```JS
   const API_KEY = 'ENTER YOUR API';
   ```



<!-- USAGE EXAMPLES -->
## Usage

Use this space to show useful examples of how a project can be used. Additional screenshots, code examples and demos work well in this space. You may also link to more resources.

_For more examples, please refer to the [Documentation](https://example.com)_



<!-- ROADMAP -->
## Roadmap

See the [open issues](https://github.com/othneildrew/Best-README-Template/issues) for a list of proposed features (and known issues).



<!-- CONTRIBUTING -->
## Contributing

Contributions are what make the open source community such an amazing place to be learn, inspire, and create. Any contributions you make are **greatly appreciated**.

1. Fork the Project
2. Create your Feature Branch (`git checkout -b feature/AmazingFeature`)
3. Commit your Changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the Branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request



<!-- LICENSE -->
## License

Distributed under the MIT License. See `LICENSE` for more information.



<!-- CONTACT -->
## Contact

Your Name - [@your_twitter](https://twitter.com/your_username) - email@example.com

Project Link: [https://github.com/your_username/repo_name](https://github.com/your_username/repo_name)



<!-- ACKNOWLEDGEMENTS -->
## Acknowledgements
* [GitHub Emoji Cheat Sheet](https://www.webpagefx.com/tools/emoji-cheat-sheet)
* [Img Shields](https://shields.io)
* [Choose an Open Source License](https://choosealicense.com)
* [GitHub Pages](https://pages.github.com)
* [Animate.css](https://daneden.github.io/animate.css)
* [Loaders.css](https://connoratherton.com/loaders)
* [Slick Carousel](https://kenwheeler.github.io/slick)
* [Smooth Scroll](https://github.com/cferdinandi/smooth-scroll)
* [Sticky Kit](http://leafo.net/sticky-kit)
* [JVectorMap](http://jvectormap.com)
* [Font Awesome](https://fontawesome.com)





<!-- MARKDOWN LINKS & IMAGES -->
<!-- https://www.markdownguide.org/basic-syntax/#reference-style-links -->
[contributors-shield]: https://img.shields.io/github/contributors/othneildrew/Best-README-Template.svg?style=for-the-badge
[contributors-url]: https://github.com/othneildrew/Best-README-Template/graphs/contributors
[forks-shield]: https://img.shields.io/github/forks/othneildrew/Best-README-Template.svg?style=for-the-badge
[forks-url]: https://github.com/othneildrew/Best-README-Template/network/members
[stars-shield]: https://img.shields.io/github/stars/othneildrew/Best-README-Template.svg?style=for-the-badge
[stars-url]: https://github.com/othneildrew/Best-README-Template/stargazers
[issues-shield]: https://img.shields.io/github/issues/othneildrew/Best-README-Template.svg?style=for-the-badge
[issues-url]: https://github.com/othneildrew/Best-README-Template/issues
[license-shield]: https://img.shields.io/github/license/othneildrew/Best-README-Template.svg?style=for-the-badge
[license-url]: https://github.com/othneildrew/Best-README-Template/blob/master/LICENSE.txt
[linkedin-shield]: https://img.shields.io/badge/-LinkedIn-black.svg?style=for-the-badge&logo=linkedin&colorB=555
[linkedin-url]: https://linkedin.com/in/othneildrew
[product-screenshot]: images/screenshot.png

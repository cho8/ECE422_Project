To get setup:
- in the 'src' directory
    make
- set LD_LIBRARY_PATH to the current directory
  > `export LD_LIBRARY_PATH=$LD_LIBRARY_PATH:.`
    
To generate ints
- in the 'src' directory
  > `java lib.DataGenerator <outputfile> <number of ints>`
    
To sort ints
- in the 'src' Directory
  > `java lib.DataSorter <inputfile> <outputfile> <heapsort error prob> <insertionsort error prob> <timelimit in ms>`
    

# ParallelProgrammingAssignment3


Problem 1:

The issue that the minotaur had with his original approach was that he was using a sequential chain and updating it from multiple threads (the servants) even though it wasn't thread safe. 

To fix this I used lazy synchronization from the textbook chapter 9. I used a randomSet data structure with the added functionality of pollRandom to be able to randomly pull a unique present from this bag and add it to my concurrent list. I simulated a random choice between 3 options ( add present from bag to chain, remove present from chain and make letter, check if a present is in the chain) and wrote a switch statement based on that. I realized that I always wanted to take the first item in the list so I had a seperate TreeSet store the ordered values of my concurrent list so that Id always have the value for the smallest value in my linkedlist without having to add that as a seperate function. This program is correct because my threads are looping on the condition that either my bag isn't empty or my list isn't empty. So knowing this, I can verify that this program is correct by printing the amount of thank you cards I have (I've kept track using an atomic int and increasing it everytime i create a thank you card). As long as that value is equal to the number of presents, that means we've wrote a card for every single present. I've tested this with values up to 1 million presents. To test this code with a different sample size all you need to do is change the value of the variable size in the Servant class in the MinotaurBirthday java file. Everything else will work with that variable.


Problem 2: 
There was 2 main ways I saw of handling data for this assignment. You could either print all your report information after one hour then delete everything for the next report or you could store all of it. I chose to use a 3d matrix to mimic storing data for every single sensor result across an arbitrary amount of hours since I felt that NASA probably stores most of the data they get in case they need to go back and study something. The only shared data structures across all threads are treesets and the 3d matrix. The 3d matrix doesn't need a lock because I'm pulling unique information from each thread (their id) to get the index that they should be updating, so there isn't any collisions. The treeset itself is guarenteed to be correct because I'm explicitly using a mutex for it. I've tested this producing reports for up to 100000 hours. Although as the default I set it to output for 12 for readability. To produce different amounts of reports you just need to change 1 variable (numHours).

Thank you for your time

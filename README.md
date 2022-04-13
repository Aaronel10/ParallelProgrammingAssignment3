# ParallelProgrammingAssignment3


Problem 1:





Problem 2: 
There was 2 main ways I saw of handling data for this assignment. You could either print all your report information after one hour then delete everything for the next report or you could store all of it. I chose to use a 3d matrix to mimic storing data for every single sensor result across an arbitrary amount of hours since I felt that NASA probably stores most of the data they get in case they need to go back and study something. The only shared data structures across all threads are treesets and the 3d matrix. The 3d matrix doesn't need a lock because I'm pulling unique information from each thread (their id) to get the index that they should be updating, so there isn't any collisions. The treeset itself is guarenteed to be correct because I'm explicitly using a mutex for it. I've tested this producing reports for up to 100000 hours. Although as the default I set it to output for 12 for readability. To produce different amounts of reports you just need to change 1 variable (numHours).

Thank you for your time

# Solver for Puzzles and Dragons

A* implementation for solving boards in the mobile game, Puzzles and Dragons. Follows a greedy traversal path using a heuristic function based on parameters such as the number of complete connections (n-connected), number of moves, and number of incomplete connections(1..n-1 connected; weighted much lower).  A simple applet is given to provide a graphical representation of the result; the orbs are randomized, but input can be easily provided in the code. In addition, changing the heuristic values in the Move.java can modify how the algorithm traverses the boards.

TO RUN: 
(a) clone the file
(b) Compile PADApplet.java in an IDE or place in html file and run through browser

EXAMPLE IMAGE: https://gyazo.com/ea96783685f7a0e3231e607f3b8c6e48

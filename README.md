<h1 align='center'>An Android Application: Simple Dictionary</h1>
<h4 align='center'>Implemented using Red-Black Trees data structure in Java, a simple dictionary that inserts, removes and searches for existence of english words.</h4>
<p align='center'> <img src="https://upload.wikimedia.org/wikipedia/commons/thumb/6/66/Red-black_tree_example.svg/500px-Red-black_tree_example.svg.png"/></p>
<h4 align='center'>This team project was developed as assignment for Data Structures And Algorithms Course Spring 2018 offering at CCE department, Faculty of Engineering, Alexandria University</h4>

## Overview & Goals
This assignment focuses on balanced binary search trees, specifically red-black trees and implementing them. 

A **red-black tree** is a binary search tree with one extra bit of storage per node: its color, which can be either RED or
BLACK. By constraining the node colors on any simple path from the root to a leaf, red-black trees ensure that no such path is more than twice as long as any other, so that the tree is approximately balanced.
It satisfies the following **properties**: 
- Every node is either red or black.
- The root is black.
- Every leaf (NIL) is black.
- If a node is red, then both its children are black.
- For each node, all simple paths from the node to descendant leaves contain the same number of black nodes.

## Application on Red Black Trees: English Simple Dictionary

As an application on our red-black trees implementation, we build a simple dictionary with the following requirements:
- **Load Dictionary:** Provided with a text file containing a list of words. Each word will be in a separate line. Load the dictionary into a Red-Black
Tree data structure to support efficient insertions, deletions and search operations.

- **Print Dictionary Size:** Prints the current size of your dictionary.

- **Insert Word:** Takes a word from the user and inserts it, only if it is not already in the dictionary. Otherwise, print the appropriate error message (e.g.“ERROR: Word already in the dictionary!").

- **Look-up a Word:** Takes a word from the user and prints “YES" or “NO" according to whether it is found or not.

- **Remove Word:** Takes a word from the user and removes it from the dictionary. If the word is not in the dictionary, then print the appropriate error message.

## Team Acknolwedgments
- Mostafa Yosury ([@MostafaYousry](https://github.com/MostafaYousry))
- Moamen Mohamed Ali ([@MoamenMohamedd](https://github.com/MoamenMohamedd))

## References

- *Thomas H. Cormen, Charles E. Leiserson, Ronald L.Rivest, Clifford Stein “Introduction to Algorithms 3rd Edition - Thomas H. Cormen, Charles E. Leiserson, R”*
- *Weiss, Mark Allen. “Data structures and algorithm analysis in Java." Addison-Wesley Long-man Publishing Co., Inc., 1998.*

---

<h3 align='center'>Made with :heart:</h3>

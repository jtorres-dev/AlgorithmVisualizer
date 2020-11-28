# Pathfinding Visualizer #
This directory contains `AStar.java`, `Node.java`, `Sorter.java`, and `SorterTester.java`. Eventually, sorting algorithms will be added to be visualized and the name of this repository will change.

# Contents: #
- `AStar.java`: A pathfinding algorithm that has hard-defined values and will eventually become much more flexible. In the early stages of working and needs minor improvements for structure.
- `Node.java`: A `Node` object to be represented in a grid for `AStar.java`. Later improvements will make this object generic.
- `Sorter.java`: A `Sorter` class that contains a merge sorting algorithm. This `Sorter` class will contain other sorting algorithms such as bubble sort, selection sort, quick sort, and others. These sorting algorithms will be a part of the visualization.
- `SorterTester.java`: A simple tester file. This tester will become a *JUnit* tester and will contain other testers for the other algorithms.

# Improvements: #
- `AStar.java`: 
   - [ ] Improve flexibility in code and stray away from grid. Implement graph structure and use breadth-first searching to accomplish tasks.
   - [ ] Create a visualization for the algorithm in *JavaFX*.
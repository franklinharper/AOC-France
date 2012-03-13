Contents of the Assets directory
================================

aoc.csv
-------
Contains the list of all AOC wine producing cities in France.

See below for a description of the steps necessary to produce
this file.

aoc-complete.csv
----------------
This file was created by concatenating the 2 AOC opendata files
available at 
* http://www.data.gouv.fr/donnees/view/Aire-g%C3%A9ographique-des-AOC-de-A-%C3%A0-L-30379422?xtmc=aoc&xtcr=4
* http://www.data.gouv.fr/donnees/view/Aire-g%C3%A9ographique-des-AOC-de-M-%C3%A0-Z-30379420?xtmc=aoc&xtcr=3

Originally the character 'œ' was incorrectly encoded in both opendata files.
This has been corrected in aoc-complete.csv by replacing the incorrectly
encoded 'ligurated oe character' by the two characters 'oe'.

This file does *not* contain long-lat coordinates.

aoc-wine.csv
------------
Contains the list of all wine AOC codes.

aoc-non-wine.csv
----------------
Contains the list of all AOC that are *not* for wine.
This file is included in case mistakes were made while
manually filtering the complete AOC list.

aoc-filter.py
-------------
A small python program that reads aoc-wine.csv, aoc-complete.csv
and writes all the wine French wine AOCs to standard out. This
programe needs to be rerun after changes are made to aoc-wine.csv
or aoc-complete.csv. The output does *not* contain long-lat coordinates.


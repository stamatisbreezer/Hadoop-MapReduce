# Hadoop-MapReduce
Development of MapReduce programs for processing big data on the Hadoop platform


- Numeronyms
Creating and counting “numeronyms”. Numeronyms correspond to words like below:
s5n – shorten
h7k – hyperlink
l10n – localization
i18n – internationalization

The numeronym of a word is defined as the alphanumeric number that is formed by taking the first one
and the last character of the word, sandwiching the number of characters between
of the first and the last. More specifically, the program will take into account
of the words with a length of 3 characters or more, it will create numeronyms and in
it will then print the appearance count of each.

The program should ignore:
* words shorter than 3 characters
* Punctuation marks
* capital / small letters, that is, to be case-insensitive

Also parametrically, the minimum number of occurrences k of a numeronym for the will be given
which we are interested in. The output of the program is a list of numeronyms and the
number of occurrences of each numeronyms that is greater than or equal to the parameter k which
given by the user.

As input the file SherlockHolmes.txt

In your report also include the run results for k = 10.

*********

- Movie Analysis
We will analyze an IMDB dataset about movies and draw conclusions that will help the IMDB team make recommendations for
films by genre and country. The dataset (movies.zip) we will use includes the following elements:
imdbID
Movie title
Year
Duration
Species
Date of 1st view
Score
Country/s


MapReduce algorithms calculate the following:
1. The total duration of all films per country. It should be noted that for co-productions
between countries, movie time counts for all countries.
2. The total of films per year and per genre that have a score greater than 8. For
movies that have more than one genre, the total will be separate for each
kind.
E.G
2005_Horror 296
2015_Drama 162
…

If the data of a line in the file is not acceptable (eg wrong number of columns) ignore that line.
The report will also include the execution results for the above queries.


***********

- DNA Sequence
A DNA sequence consists of the symbols A, G, C, T. For example, below
the first 4 lines of the input file are given which refer to its DNA sequence
E. coli bacteria (https://en.wikipedia.org/wiki/Pathogenic_Escherichia_coli).

AGCTTTTCATTCTGACTGCAACGGGCAATATGTCTCTGTGTGGATTAAAAAAAGAGTGTCTGATAGCAGC
TTCTGAACTGGTTACCTGCCGTGAGTAAAATTAAATTTTATTGACTTAGGTCACTAAATACTTTTAACCAA
TATAGGCATAGCGCACAGACAGATAAAAAATTACAGAGTACACAACATCCATGAAACGCATTAGCACCACC
ATTACCACCACCATCACCATTACCACAGGTAACGGTGCGGGCTGACGCGTACAGGAAACACAGAAAAAG

Java program for Hadoop MapReduce which for each line of
input file will count the number of occurrences of consecutive 2's, 3's and
4 of the above DNA sequence.
The processing of one line should be independent from the others.
That is, do not deal with the case that in a 2-team, 3-team or 4-team
symbols some symbols are on one line and the rest on the next.
Use the ecoli.txt file as input.

The results that show for each 2-team, 3-team, 4-team the number of appearances will
must exist in a txt file (eg output.txt)

**********

- Graphs
A text file where in each line there is a connection between two vertices 
of a network and a probability value. For each edge e (connection) there is a probability value
p(e) which indicates the probability that the two vertices are connected by an edge. Obviously the
p(e) values ​​are between 0 and 1. Values ​​on each line are separated by a space character.

graph.png

The sample network is shown in the previous figure. The edge joining vertices 4 and
5 has probability 0.8, the edge joining vertices 2 and 3 has probability 0.2, etc. The
file corresponding to this graph would be:

1 2 0.6
1 3 0.9
2 3 0.2
3 4 0.75
2 5 0.2
4 5 0.8

Edges are generally stored in random order in the file, so we can't suppose they have any particular arrangement.

1. Calculate the average grade for all peaks.
The average degree is defined as the sum of the probabilities of the edges falling on
a top. For example, in the previous figure the average degree of the peak 3
is 0.9 + 0.2 + 0.75 = 1.85.
Also, before this calculation ignore all vertices with probability value
less than a threshold T which will be passed as a parameter to the main() function.

2. Change the code to 1 so that in the end only the vertices that have an average degree
 greater than the average of the degrees of all tops.

Use the collins.txt file as input.
The results in a txt file.
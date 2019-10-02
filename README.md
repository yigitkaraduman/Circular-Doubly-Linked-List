# Circular-Doubly-Linked-List

The goal of this program is to use a linked list to count the words from a given text file.
Each word and its frequency (number of times it appears in the given text) will be stored in
one node of the linked list. The nodes keep sorted in decreasing order of frequencies. If
two words have the same frequency, they are sorted alphabetically.
When a new word is read from the file, the word is added into the list. If the word
already exists in the list, the value of "frequency" for that word is incremented. Since
the words are kept in sorted order in the linked list by their frequency order, when the frequency
of a word has changed,the word is moved to correct position in the linked list.

# Input Files: Your program will receive input from two different files.

*text file: The text file from which the words are read and counted.

*directives file: This file will contain one directive per line. Each directive consists of the
name of the directive followed by 0 or more arguments, separated by spaces. You may assume
that only the directives given below will appear in the file, and the specified arguments will
always appear. The output of the directives will be written to standard output.

Directives: Here are the possible directives and their meanings.

*load filename:

Parse the words in the file named filename and construct the frequency linked list.

*print-max N:
Print the N highest frequency words and their frequencies in the list, one word per line and
sorted highest to lowest. If N is larger than the number of words in the list, print the entire
list.

*print-min N:

Print the N lowest frequency words and their frequencies in the list, one word per line and
sorted lowest to highest. If N is larger than the number of words in the list, print the entire
list.

*print-range N1 N2:

Print the words whose frequency is within the range [N1, N2]. Print each word and its
frequency, one word per line and sorted highest to lowest. If no words are in that range, print
'This range is empty'.

*print-freq word:

Print the frequency of the given word.

*print-nth N:

Print the word whose frequency is the N-th highest in the list. For example if N=1 you print
the word with the highest frequency, if N=2 you print the second highest, etc.

*truncate-list N:

Remove the N lowest frequency words from the list. If N is larger than the number of words
in the list, clear the entire list.

# How to run:
The program will be invoked from the command line as: java word-frequency directives-file

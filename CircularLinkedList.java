import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

//Yigit Karaduman 161101064

public class CircularLinkedList{
    PrintWriter p = null; // reference for save file.

    //---------------- nested Node class ----------------
    /**
     * Node of a doubly linked list, which stores a reference to its
     * element and to both the previous and next node in the list.
     */
    private static class Node{

        /** The word stored at this node */
        private String word;               // reference to the element stored at this node

        /** The word frequency stored at this node */
        private int frequency;

        /** A reference to the preceding node in the list */
        private Node prev;            // reference to the previous node in the list

        /** A reference to the subsequent node in the list */
        private Node next;            // reference to the subsequent node in the list

        /**
         * Creates a node with the given element and next node.
         *
         * @param s  the word to be stored
         * @param freq  the frequence of the word
         * @param p  reference to a node that should precede the new node
         * @param n  reference to a node that should follow the new node
         */
        public Node(String s, int freq, Node p, Node n) {
            word = s;
            frequency = freq;
            prev = p;
            next = n;
        }

        // public accessor methods
        /**
         * Returns the element stored at the node.
         * @return the element stored at the node
         */
        public String getElement() {
            return word + " " + frequency;
        }

        public String getWord() {
            return word;
        }

        public int getFrequency() {
            return frequency;
        }

        /**
         * Returns the node that precedes this one (or null if no such node).
         * @return the preceding node
         */
        public Node getPrev() { return prev; }

        /**
         * Returns the node that follows this one (or null if no such node).
         * @return the following node
         */
        public Node getNext() { return next; }

        // Update methods
        /**
         * Sets the node's previous reference to point to Node n.
         * @param p    the node that should precede this one
         */
        public void setPrev(Node p) { prev = p; }

        /**
         * Sets the node's next reference to point to Node n.
         * @param n    the node that should follow this one
         */
        public void setNext(Node n) { next = n; }
    } //----------- end of nested Node class -----------

    // instance variables of the DoublyLinkedList
    private Node head;                    // head

    private Node tail;                   // tail
    /** Number of elements in the list (not including sentinels) */
    private int size = 0;                      // number of elements in the list

    /** Constructs a new empty list. */
    public CircularLinkedList() { }

    /**
     * Returns the number of elements in the linked list.
     * @return number of elements in the linked list
     */
    public int size() { return size; }

    /**
     * Tests whether the linked list is empty.
     * @return true if the linked list is empty, false otherwise
     */
    public boolean isEmpty() { return size == 0; }

    /**
     * Returns (but does not remove) the first element of the list.
     * @return element at the front of the list (or null if empty)
     */
    public String first() {
        if (isEmpty()) return null;
        return head.getElement();   // first element is beyond header
    }

    /**
     * Returns (but does not remove) the last element of the list.
     * @return element at the end of the list (or null if empty)
     */
    public String last() {
        if (isEmpty()) return null;
        return tail.getElement();    // last element is before trailer
    }

    // public update methods
    /**
     * Adds an element to the front of the list.
     * @param e   the new element to add
     */

    /**
     * Adds an element to the end of the list.
     * @param word and frequency   the new element to add
     */
    public void add(String word, int frequency) {
        Node tmp = new Node(word, frequency, tail, head);
        if(tail != null) {tail.setNext(tmp);}
        tail = tmp;
        if(head == null) { head = tmp; head.setNext(tmp); head.setPrev(tmp);}
        head.setPrev(tail);
        size++;
        //System.out.println("adding: "+ e);
    }

    /**
     * Removes and returns the first element of the list.
     * @return the removed element (or null if empty)
     */
    public String removeFirst() {
        if (isEmpty()) return null;                  // nothing to remove
        Node tmp = head;
        head = head.getNext();
        tail.setNext(head);
        head.setPrev(tail);
        size--;
        return tmp.getElement();             // first element is beyond header
    }

    /**
     * Removes and returns the last element of the list.
     * @return the removed element (or null if empty)
     */
    public String removeLast() {
        if (isEmpty()) return null;                  // nothing to remove
        Node tmp = tail;
        tail = tail.getPrev();
        tail.setNext(head);
        head.setPrev(tail);
        size--;
        return tmp.getElement();
    }

    // private update methods
    /**
     * Adds an element to the linked list in between the given nodes.
     * The given predecessor and successor should be neighboring each
     * other prior to the call.
     *
     * @param predecessor   node just before the location where the new element is inserted
     * @param successor     node just after the location where the new element is inserted
     */
    private void addBetween(String word, int frequency, Node predecessor, Node successor) {
        // create and link a new node
        Node newest = new Node(word, frequency, predecessor, successor);
        predecessor.setNext(newest);
        successor.setPrev(newest);
        if(predecessor.equals(tail))
            tail = newest;
        size++;
    }

    /**
     * Removes the given node from the list and returns its element.
     * @param node    the node to be removed (must not be a sentinel)
     */
    private String remove(Node node) {
        Node predecessor = node.getPrev();
        Node successor = node.getNext();
        predecessor.setNext(successor);
        successor.setPrev(predecessor);
        size--;
        return node.getElement();
    }

    /**
     * Produces a string representation of the contents of the list.
     * This exists for debugging purposes only.
     */
    public String toString() {
        StringBuilder sb = new StringBuilder("(");
        Node walk = head;
        int i = 0;
        while (i < size) {
            sb.append(walk.getElement());
            if(walk != tail)
                sb.append(", ");
            walk = walk.getNext();
            i++;
        }
        sb.append(")");
        return sb.toString();
    }

    Scanner in = null;
    public void readDirectiveFile(String name){
        try{
            in = new Scanner(new FileInputStream(name));
        }catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
        while(in.hasNext()) {
            String s = in.next();
            if (s.equals("load")) {
                SaveChanges(in.next());
                readWordFile("SavedFile.txt");
                System.out.println(this);
            }
            else if (s.equals("print-freq")) {
                s = in.next();
                s = reduceSign2(s);
                s = s.toLowerCase();
                if(ifExists(s) == false)
                    System.out.println("The word is not in the list.");
                else
                    System.out.println(printFreq(s));
            }
            else if(s.equals("print-range")){
                String s1 = in.next();
                String s2 = in.next();
                printRange(s1, s2);
                System.out.println();
            }
            else if(s.equals("print-max")) {
                printMax(in.next());
                System.out.println();
            }
            else if(s.equals("print-min")) {
                printMin(in.next());
                System.out.println();
            }
            else if(s.equals("print-nth")){
                printNth(in.next());
                System.out.println();
            }
            else if(s.equals("truncate-list")){
                truncateList(in.next());
                System.out.println(this);
            }
        }
            in.close();
    }

    Scanner inTxtFile = null;
    public void readWordFile(String name){
        String s = null;
        try{
            inTxtFile = new Scanner(new FileInputStream(name));
        }catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
        while(inTxtFile.hasNext()){
            s = inTxtFile.next();
            if(!ifExists(s)) {
                add(s, 1);
                alphabeticOrdering(findNode(s));
            }
            else{
                Node toIncrementFreq = findNode(s);
                updateFrequence(toIncrementFreq);
                swap(toIncrementFreq);
                alphabeticOrdering(toIncrementFreq);
            }
        }
            inTxtFile.close();
    }

    public void createSaveFile(){//Creates file to save changes in the original word list.
        try{
            p = new PrintWriter(new FileOutputStream("SavedFile.txt"));
        }catch (FileNotFoundException e){
            System.out.println(e.getMessage());
        }
    }

    public void SaveChanges(String name){//after processing the original word file (like reducing signs), it saves it changes to another file.
        createSaveFile();
        String s = null;
        try{
            inTxtFile = new Scanner(new FileInputStream(name));
        }catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
        while(inTxtFile.hasNext()) {
            s = inTxtFile.next();
            s = reduceSign2(s);
            s = s.toLowerCase();
            p.write(s + " ");
        }
        inTxtFile.close();
        p.close();
    }



    public String reduceSign2(String s){//Reduces from signs and returns the words.
        if(s.contains(","))
            s = s.replace(","," ");
        if(s.contains("."))
            s = s.replace("."," ");
        if(s.contains("!"))
            s = s.replace("!"," ");
        if(s.contains("?"))
            s = s.replace("?"," ");
        if(s.contains("(") || s.contains(")")) {
            s = s.replace("(", " ");
            if (s.contains(")"))
                s = s.replace(")", " ");
        }
        if(s.contains(":"))
            s = s.replace(":"," ");
        if(s.contains(";"))
            s = s.replace(";"," ");
        if(s.contains("\""))
            s = s.replace("\""," ");
        if(s.contains("[") || s.contains("]")) {
            s = s.replace("[", " ");
            if (s.contains("]"))
                s = s.replace("]", " ");
        }
        if(s.contains("“") || s.contains("”")) {
            s = s.replace("\\“", "\\”");
            if (s.contains("\\”"))
                s = s.replace("\\”", " ");
        }
        return s;

    }

    public boolean ifExists(String s){ // The word that is loaded from the file, does exist in the list or not?
        Node walk = head;
        int i = 0;
        while (i < size) {
            if(walk.getWord().equals(s))
                return true;
            walk = walk.getNext();
            i++;
        }
        return false;
    }

    public Node findNode(String s){ // finds the node that includes the word in the given parameter.
        Node found = null;
        Node walk = head;
        int i = 0;
        while (i < size) {
            if(walk.getWord().equals(s))
                found = walk;
            else
                walk = walk.getNext();
                i++;
        }
        return found;
    }

    public void updateFrequence(Node node){
        node.frequency++;
    } //updates frequence by incrementing.

    public void swap(Node node){ // swaps datas in the nodes in decreasing word frequence order.
        while(node != head) {
            if (node.frequency > node.getPrev().frequency) {
               String tmpWord = node.getPrev().word;
               int tmpFrequency = node.getPrev().frequency;
               node.getPrev().word = node.word;
               node.getPrev().frequency = node.frequency;
               node.word =  tmpWord;
               node.frequency = tmpFrequency;
            }
            node = node.getPrev();
        }
    }

    public void alphabeticOrdering(Node node) { // swaps datas in the nodes in alphabetically order if the frequences of nodes are equal.
        while (node != head) {
            if (node.frequency == node.getPrev().frequency) {
                if (node.getWord().charAt(0) < node.getPrev().getWord().charAt(0)) {
                    String tmpWord = node.getPrev().word;
                    int tmpFrequency = node.getPrev().frequency;
                    node.getPrev().word = node.word;
                    node.getPrev().frequency = node.frequency;
                    node.word = tmpWord;
                    node.frequency = tmpFrequency;
                }
            }
                node = node.getPrev();
        }
    }

    private int printFreq(String s){ // print the frequence of the given word as a parameter.
        Node node = findNode(s);
        return node.getFrequency();
    }

    private void bubbleSort(ArrayList<Node> arr){
        boolean swapped = true;
        int j = 0;
        Node tmp = null;
        while (swapped) {
            swapped = false;
            j++;
            for (int x = 0; x < arr.size() - j; x++) {
                if (arr.get(x).frequency > arr.get(x + 1).frequency) {
                    tmp = arr.get(x);
                    arr.set(x,arr.get(x+1));
                    arr.set(x+1,tmp);
                    swapped = true;
                }
            }
        }
    }

    private void printRange(String s1, String s2){//Prints the words corresponding to given frequency parameters.
        ArrayList<Node> list = new ArrayList<>();
        int n1 = Integer.parseInt(s1);
        int n2 = Integer.parseInt(s2);
        Node walk = head;
        int i = 0;
        while(i < size){
            if(walk.getFrequency() >= n1 && walk.getFrequency() <= n2)
                list.add(walk);
            walk = walk.getNext();
            i++;
        }
        if(list.size() == 0){
            System.out.print("This range is empty.");
            return;
        }
        bubbleSort(list);
        for(int k = list.size()-1; k >= 0; k--)
            System.out.print(list.get(k).getElement() + ", ");
    }

    private void printMax(String n){//Print n highest frequency words.
        int N  = Integer.parseInt(n);
        Node walk = head;
        int i = 0;
        int var = 0;
        if(N <= 0){
            System.out.println("Empty list.");
            return;
        }
        while(i < size) {
            System.out.print(walk.getElement() + ", ");
            walk = walk.getNext();
            if(walk.getFrequency() != walk.getPrev().getFrequency())
                var++;
            if(var == N)
                break;
            i++;
        }
    }

    private void printMin(String n){ //Print n lowest frequency words.
        int N  = Integer.parseInt(n);
        Node walk = tail;
        int i = 0;
        int var = 0;
        if(N <= 0){
            System.out.println("Empty list.");
            return;
        }
        while(i < size) {
            System.out.print(walk.getElement() + ", ");
            walk = walk.getPrev();
            if(walk.getFrequency() != walk.getNext().getFrequency())
                var++;
            if(var == N)
                break;
            i++;
        }
    }

    private void printNth(String n){
        int N = Integer.parseInt(n);
        Node walk = head;
        int i = 0;
        int var = 0;
        int fre = -1;
        while(i < size){
          if(walk.getFrequency() != fre){
               var++;
               fre = walk.getFrequency();
          }
          if(var == N) {
              while (walk.frequency == fre) {
                  System.out.print(walk.getElement() + ", ");
                  walk = walk.getNext();
              }
          }
          //System.out.print(walk.getElement() + ", ");
          i++;
          walk = walk.getNext();
        }
    }

    private void truncateList(String n){
        int N  = Integer.parseInt(n);
        Node walk = tail;
        int i = 0;
        int var = 0;
        if(N <= 0){
            System.out.println("Empty list.");
            return;
        }
        int tmpSize = size;
        while(i < tmpSize) {
            tail = tail.getPrev();
            size--;
            walk = walk.getPrev();
            if(walk.getFrequency() != walk.getNext().getFrequency()){
                var++;
            }
            if(var == N)
                break;
            i++;
        }
        tail.setNext(head);
        head.setPrev(tail);
    }
} //----------- end of DoublyLinkedList class -----------

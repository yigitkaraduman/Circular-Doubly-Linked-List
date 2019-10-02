public class CLLDriver {
    public static void main(String[] args) {
        CircularLinkedList cll = new CircularLinkedList();
        cll.readDirectiveFile(args[0]);
    }
}

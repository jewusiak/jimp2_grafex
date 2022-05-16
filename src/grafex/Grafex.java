package grafex;

public class Grafex {
    public static void main(String[] args) {
        Graph gr=Graph.readFromFile();
        System.out.println(gr.getSize());

    }
}

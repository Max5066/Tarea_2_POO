import java.io.PrintStream;

public class Monitor extends Subscriber {
    public Monitor (String name, String topicName, PrintStream out) {
        super(name, topicName);
        this.out = out;
    }

    public void update(String message){
        try {
        String[] coords = message.split(" ");
        int x = Integer.parseInt(coords[0]);
        int y = Integer.parseInt(coords[1]);

        if (Math.sqrt(x * x + y * y) > 500) {
            out.println(getName() + " Posicion lejana: (" + x + "," + y + ")");
        }
    }catch(Exception  e ) {System.err.println("Error");}
    }


    private PrintStream out;
}
import java.io.PrintStream;
import java.util.Scanner;

public class Recorder extends Subscriber {
   protected Recorder() {}  // to ban calls to this constructor.
   public Recorder(String name, String topicName, PrintStream out) {
      super(name, topicName);
      this.out = out;
   }
   public void update(String message) {
      Scanner in = new Scanner(message);
      out.println(getName()+","+getTopicName()+","+in.nextInt()+","+in.nextInt());
   }
   private PrintStream out;
} 
public class Publisher extends Component {
   protected Publisher() {} // to ban calls to this constructor

   public Publisher(String name, Broker broker, String topicName) {
      super(name, topicName);
      this.broker = broker;
      // Crear el tópico si no existe
      Topic topic = broker.findTopic(topicName);
      if (topic == null) {
         topic = broker.createTopic(topicName);
      }
      this.topic = topic;
   }

   protected void publishNewEvent(String message) {
      topic.notify(message);
   }

   private Topic topic;
   private Broker broker;
}
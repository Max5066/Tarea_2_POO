
/**
 * Clase base para los publicadores en el sistema Publisher-Subscriber.
 * Se encarga de enviar mensajes a sus suscriptores a través de un tópico.
 */
public class Publisher extends Component {
   /**
    * Constructor base protegido.
    */
   protected Publisher() {}

   /**
    * Crea un nuevo publicador.
    * @param name Nombre del publicador
    * @param broker Broker para gestionar tópicos
    * @param topicName Nombre del tópico asociado
    */
   public Publisher(String name, Broker broker, String topicName) {
      super(name, topicName);
      this.broker = broker;
      this.topic = broker.findTopic(topicName);
      if (this.topic == null) {
         this.topic = broker.createTopic(topicName);
      }
   }

   /**
    * Envía un mensaje a todos los suscriptores del tópico.
    * @param message Mensaje a publicar
    */
   protected void publishNewEvent(String message) {
      topic.notify(message);
   }

   private Topic topic;
   private Broker broker;
}
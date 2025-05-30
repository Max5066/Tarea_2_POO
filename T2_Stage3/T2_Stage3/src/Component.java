
/**
 * Clase base abstracta para todos los componentes del sistema (publicadores y suscriptores)
 */
public class Component {
   protected Component() {}  // to ban creation of publisher or subscriber without name

   /**
    * Crea un nuevo componente
    * @param componentName Nombre del componente
    * @param topicName Tópico al que está asociado
    */
   public Component(String componentName, String topicName) {
      this.name = componentName;
      this.topicName = topicName;
   }

   /**
    * @return Nombre del componente
    */
   public String getName() {
      return name;
   }

   /**
    * @return Nombre del tópico asociado
    */
   public String getTopicName() {
      return topicName;
   }

   protected String name;
   protected String topicName;
}
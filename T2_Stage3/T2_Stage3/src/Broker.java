
import java.util.ArrayList;

public class Broker {
    public Broker() {
        topics = new ArrayList<Topic>();
    }

    public Topic createTopic(String topicName) {
        Topic topic = new Topic(topicName);
        topics.add(topic);
        return topic;
    }

    public boolean subscribe(Subscriber sub) {
        String topicName = sub.getTopicName();
        Topic topic = findTopic(topicName);

        if (topic != null) {
            topic.subscribe(sub);
            return true;
        } else {
            return false; // topic does not exist
        }
    }

    public Topic findTopic(String topicName) {
        for (Topic topic : topics) {
            if (topic.hasThisName(topicName)) {
                return topic;
            }
        }
        return null;  // topic not found
    }

    private ArrayList<Topic> topics;
}
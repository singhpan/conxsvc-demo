package conxsvc.demo;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ActiveMQSession;

import javax.jms.*;

public class AMQConn {
    private static final String AMQURL="tcp://localhost:61616";
    private final ActiveMQConnection conn;
    private final ActiveMQSession session;
    private final MessageProducer producer;

    public AMQConn() throws JMSException {

        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(AMQURL);
        factory.setAlwaysSessionAsync(false);
        factory.setAlwaysSyncSend(false);
        factory.setUseCompression(false);
        factory.setDispatchAsync(true);
        factory.setUserName("test");
        factory.setPassword("test");
        Connection c = factory.createConnection();
        c.start();

        this.conn = (ActiveMQConnection)c;
        this.session = (ActiveMQSession) conn.createSession(false,ActiveMQSession.AUTO_ACKNOWLEDGE);
        this.producer = session.createProducer(null);
        this.producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
    }

    public Queue createQueue(String name){
        Queue q = null;
        try {
            q = session.createQueue(name);
        } catch (JMSException e) {
            e.printStackTrace();
        }
        return q;
    }

    public void send(Destination queue,Message m){
        try {
            this.producer.send(queue,m);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    public void addListener(Destination q, MessageListener l) {
        try{
            MessageConsumer consumer = session.createConsumer(q);
            consumer.setMessageListener(l);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}

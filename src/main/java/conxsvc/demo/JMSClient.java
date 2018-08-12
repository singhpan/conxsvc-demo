package conxsvc.demo;

import org.apache.activemq.command.ActiveMQBytesMessage;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

public class JMSClient {
    private AMQConn amqConn = new AMQConn();

    public JMSClient() throws JMSException {
    }

    public void send(String queue, String message, String replyQ, MessageListener l) throws JMSException {
        Message msg = AMQUtil.getMsg(message);
        msg.setJMSReplyTo(amqConn.createQueue(replyQ));
        amqConn.addListener(msg.getJMSReplyTo(),l);
        amqConn.send(amqConn.createQueue(queue),msg);
    }
    public void reply(Message m) throws JMSException {
        amqConn.send(m.getJMSReplyTo(),m);
    }

    public void addListener(String queue, MessageListener l){
        amqConn.addListener(amqConn.createQueue(queue),l);
    }
}

package conxsvc.demo;

import org.apache.activemq.command.ActiveMQBytesMessage;

import javax.jms.JMSException;
import javax.jms.Message;

public class AMQUtil {

    static String getMsg(Message msg) throws JMSException {
        ActiveMQBytesMessage m = (ActiveMQBytesMessage)msg;
        byte[]  b = new byte[(int)m.getBodyLength()];
        m.readBytes(b);
        return new String(b);

    }

    static Message getMsg(String msg) throws JMSException {
        ActiveMQBytesMessage m = new ActiveMQBytesMessage();
        m.writeBytes(msg.getBytes());
        return m;
    }

}

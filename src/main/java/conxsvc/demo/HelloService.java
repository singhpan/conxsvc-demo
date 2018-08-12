package conxsvc.demo;

import org.apache.activemq.command.ActiveMQBytesMessage;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

public class HelloService implements MessageListener{
    private JMSClient client;
    private String svcName = this.getClass().getCanonicalName();

    public HelloService() throws JMSException {
        client = new JMSClient();
    }

    public static void main(String[] args) {
        try {
            HelloService svc = new HelloService();
            svc.client.addListener(svc.svcName, new HelloService());
            System.out.println(svc.svcName + " has started");
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    public void onMessage(Message message) {
        try {
            ActiveMQBytesMessage m = (ActiveMQBytesMessage)message;
            String response = "Hello " + AMQUtil.getMsg(m);
            Message res = AMQUtil.getMsg(response);
            res.setJMSReplyTo(message.getJMSReplyTo());
            System.out.println("Service sending message "+ response);
            client.reply(res);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}

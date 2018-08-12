package conxsvc.demo;

import javax.jms.JMSException;

public class HelloClient {

    public static void main(String[] args) {
        try {
            JMSClient client = new JMSClient();
            String m = "Jane Doe";
            System.out.println("Sending message: " + m);
            client.send("conxsvc.demo.HelloService", m, "helloclient", msg -> {
                try {
                    System.out.println("Got Response: " + AMQUtil.getMsg(msg));
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            });
        } catch (JMSException e) {
            e.printStackTrace();
        }

    }
}

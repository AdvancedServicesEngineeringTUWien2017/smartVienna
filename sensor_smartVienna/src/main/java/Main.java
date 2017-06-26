import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Hashtable;

/**
 * Created by Michael on 02.06.2017.
 */
public class Main {


    public static void main(String[] args) throws Exception {
        String uri = System.getenv("CLOUDAMQP_URL");
        if (uri == null)
        {
            /*********************************/
            //URI OF RABBITMQ CLOUD AMPQ
            //Please insert here (or set as System variable)
            /*********************************/
            uri = "amqp://XXXX.rmq.cloudamqp.com/XXXX";
        }

        ConnectionFactory factory = new ConnectionFactory();
        factory.setUri(uri);

        //Recommended settings
        factory.setRequestedHeartbeat(30);
        factory.setConnectionTimeout(30000);

        Connection connection = factory.newConnection();
        boolean durable = false;    //durable - RabbitMQ will never lose the queue if a crash occurs
        boolean exclusive = false;  //exclusive - if queue only will be used by one connection
        boolean autoDelete = false; //autodelete - queue is deleted when last consumer unsubscribes

        final Channel consumerQueue = connection.createChannel();
        final String consumerQueueName = "intervalQueue";
        consumerQueue.queueDeclare(consumerQueueName, durable, exclusive, autoDelete, null);

        new Thread(new Runnable() {
            public void run() {

                QueueingConsumer consumer = new QueueingConsumer(consumerQueue);

                try {

                    HashMap<String, Thread> threadHashMap = new HashMap<String, Thread>();

                    while (true) {
                        System.out.println("start consuming...");
                        consumerQueue.basicConsume(consumerQueueName, true, consumer);

                        printStatus(threadHashMap);

                        QueueingConsumer.Delivery delivery = consumer.nextDelivery();
                        String m = new String(delivery.getBody());
                        System.out.println(m);
                        JSONObject message = new JSONObject(m);
                        System.out.println(" [x] Received '" + m.toString() + "'");

                        String rbl = message.get("rbl").toString();
                        Thread thread = threadHashMap.get(rbl);
                        if(thread == null && new Integer(message.get("interval").toString()) != 0) {
                            //Start Request Thread!
                            RequestThread requestThread = new RequestThread(message.get("rbl").toString(), new Integer(message.get("interval").toString()));
                            Thread t = new Thread(requestThread);

                            threadHashMap.put(message.get("rbl").toString(), t);


                            t.start();
                        }
                        else
                        {
                            System.out.println("stop thread for rbl " + rbl);
                            thread.interrupt();

                            threadHashMap.remove(rbl);
                            String interval = message.get("interval").toString();
                            int i = Integer.parseInt(interval);
                            if(i != 0) {
                                System.out.println("start new thread for rbl " + rbl + " with interval " + message.get("interval").toString());
                                RequestThread requestThread = new RequestThread(message.get("rbl").toString(), new Integer(message.get("interval").toString()));
                                Thread t = new Thread(requestThread);
                                t.setName(message.get("interval").toString());
                                threadHashMap.put(message.get("rbl").toString(), t);
                                t.start();
                            }

                            printStatus(threadHashMap);

                        }

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();




    }

    private static void printStatus(HashMap<String, Thread> threadHashMap)
    {
        System.out.println("--------STATUS-----------");
        for(String rb : threadHashMap.keySet())
        {
            Thread thread1  = threadHashMap.get(rb);
            System.out.println("Running Thread with rbl " + rb + " and interval " + thread1.getName());
        }
        System.out.println("-------------------------");
    }
}

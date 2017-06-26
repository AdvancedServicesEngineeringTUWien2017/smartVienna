package at.ac.tuwien.ase.batch;

import at.ac.tuwien.ase.domain.*;
import at.ac.tuwien.ase.repository.StationRepository;
import at.ac.tuwien.ase.repository.StationTimeRepository;
import at.ac.tuwien.ase.repository.ViennaLineRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by Michael on 14.06.2017.
 */
@Service
@Transactional
public class MonitorStations {

    private ConnectionFactory connectionFactory;
    private Connection connection;
    private Channel consumerQueue;
    private String consumerQueueName;

    @Autowired
    private MonitorService monitorService;

    public MonitorStations()
    {

        try {
            String uri = System.getenv("CLOUDAMQP_URL");
            if (uri == null){
                /*********************************/
                //URI OF RABBITMQ CLOUD AMPQ
                //Please insert here (or set as System variable)
                /*********************************/
                uri = "amqp://XXXX.rmq.cloudamqp.com/XXXX";
            }
            connectionFactory = new ConnectionFactory();

            connectionFactory.setUri(uri);

            //Recommended settings
            connectionFactory.setRequestedHeartbeat(30);
            connectionFactory.setConnectionTimeout(30000);

            connection = connectionFactory.newConnection();
            boolean durable = false;    //durable - RabbitMQ will never lose the queue if a crash occurs
            boolean exclusive = false;  //exclusive - if queue only will be used by one connection
            boolean autoDelete = false; //autodelete - queue is deleted when last consumer unsubscribes
            consumerQueue = connection.createChannel();
            consumerQueueName = "smartViennaQueue";
            consumerQueue.queueDeclare(consumerQueueName, durable, exclusive, autoDelete, null);

        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    @Async
    @Transactional
    public void run() {




        QueueingConsumer consumer = new QueueingConsumer(consumerQueue);

        try {
            consumerQueue.basicConsume(consumerQueueName, true, consumer);

            while (true) {

                System.out.println("Start listening...");
                QueueingConsumer.Delivery delivery = consumer.nextDelivery();
                String message = new String(delivery.getBody());
                System.out.println(" [x] Received 'station Details'");

                JSONObject object = new JSONObject(message);

                try {
                    if (object.has("untrack") && object.get("untrack") != null) {
                        String untrack = (String) object.get("untrack");
                        monitorService.untrack(untrack);
                    } else {

                        JSONArray data = (JSONArray) object.get("data");
                        int interval = (int) object.get("interval");


                        monitorService.monitor(data, interval);
                    }
                }
                catch(JSONException je)
                {
                    je.printStackTrace();
                    System.out.println("Don't care about JSON Exception");
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}

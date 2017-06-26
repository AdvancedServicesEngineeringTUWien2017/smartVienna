import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Michael on 14.06.2017.
 */
public class RequestThread implements Runnable {


    private String rbl;
    private int interval;

    public RequestThread(String rbl, int interval)
    {
        this.rbl = rbl;
        this.interval = interval;
    }

    public void run() {

        try {

            System.out.println("track station with rbl " + rbl + " in interval of " + interval);

            String uri = System.getenv("CLOUDAMQP_URL");
            if (uri == null){
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

            Channel smartVienna = connection.createChannel();

            String queue = "smartViennaQueue";     //queue name
            boolean durable = false;    //durable - RabbitMQ will never lose the queue if a crash occurs
            boolean exclusive = false;  //exclusive - if queue only will be used by one connection
            boolean autoDelete = false; //autodelete - queue is deleted when last consumer unsubscribes

            smartVienna.queueDeclare(queue, durable, exclusive, autoDelete, null);

            /*********************************/
            //API KEY OF WIENER LINIEN
            //Please insert here
            /*********************************/
            String apiKey = "XXXX";

            //GET info from vienna public transport api


            boolean stop = false;
            while(!stop) {
                JSONObject response = readJsonFromUrl("http://www.wienerlinien.at/ogd_realtime/monitor?rbl=" + rbl + "&activateTrafficInfo=stoerungkurz&activateTrafficInfo=stoerunglang&activateTrafficInfo=aufzugsinfo&sender=" + apiKey);
                System.out.println(response.toString());
                JSONObject data = (JSONObject) response.get("data");
                JSONArray monitors = (JSONArray) data.get("monitors");

                System.out.println(monitors);

                JSONObject monitorAndInterval = new JSONObject();
                monitorAndInterval.put("data", monitors);
                monitorAndInterval.put("interval", interval);

                String exchangeName = "";
                String routingKey = "smartViennaQueue";

                smartVienna.basicPublish(exchangeName, routingKey, null, monitorAndInterval.toString().getBytes());
                System.out.println(" [x] Sent details for station '" + rbl + "'");

                try {
                    Thread.sleep(interval);
                } catch (InterruptedException e) {
                    e.printStackTrace();

                    //untrack
                    JSONObject untrack = new JSONObject();
                    untrack.put("untrack", rbl);
                    System.out.println(untrack.toString());
                    smartVienna.basicPublish(exchangeName, routingKey, null, untrack.toString().getBytes());

                    stop = true;
                }
            }

        } catch (IOException ioe)
        {
            ioe.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
        InputStream is = new URL(url).openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            JSONObject json = new JSONObject(jsonText);
            return json;
        } finally {
            is.close();
        }
    }

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }
}

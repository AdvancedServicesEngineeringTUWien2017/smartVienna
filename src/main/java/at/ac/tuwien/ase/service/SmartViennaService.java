package at.ac.tuwien.ase.service;

import at.ac.tuwien.ase.domain.*;
import at.ac.tuwien.ase.repository.StationRepository;
import at.ac.tuwien.ase.repository.StationTimeRepository;
import at.ac.tuwien.ase.repository.ViennaLineRepository;
import at.ac.tuwien.ase.service.dto.DelayDTO;
import at.ac.tuwien.ase.service.dto.LineDTO;
import at.ac.tuwien.ase.service.dto.StatDTO;
import at.ac.tuwien.ase.service.dto.StatisticsDTO;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.io.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Michael on 14.06.2017.
 */
@Service
@Transactional
public class SmartViennaService {


    private static final Logger log = LoggerFactory.getLogger(SmartViennaService.class);

    private ConnectionFactory connectionFactory;

    private Connection connection;

    private Channel producerQueue;
    private String consumerQueueName;

    @Autowired
    private StationRepository stationRepository;

    @Autowired
    private StationTimeRepository stationTimeRepository;

    public SmartViennaService()
    {

    }

    @PostConstruct
    public void init()
    {
        try {
            String uri = System.getenv("CLOUDAMQP_URL");
            if (uri == null) uri = "amqp://aeuvhavi:n5_ThNGJZ1GZ33gmBJAGglVUoYdvgk0x@orangutan.rmq.cloudamqp.com/aeuvhavi";
            connectionFactory = new ConnectionFactory();

            connectionFactory.setUri(uri);

            //Recommended settings
            connectionFactory.setRequestedHeartbeat(30);
            connectionFactory.setConnectionTimeout(30000);

            connection = connectionFactory.newConnection();
            boolean durable = false;    //durable - RabbitMQ will never lose the queue if a crash occurs
            boolean exclusive = false;  //exclusive - if queue only will be used by one connection
            boolean autoDelete = false; //autodelete - queue is deleted when last consumer unsubscribes
            producerQueue = connection.createChannel();
            consumerQueueName = "intervalQueue";
            producerQueue.queueDeclare(consumerQueueName, durable, exclusive, autoDelete, null);

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

    public String startMonitoringStation(String rbl, int interval)
    {
        String exchangeName = "";
        String routingKey = "intervalQueue";
        log.info("start monitor");

        JSONObject object = new JSONObject();
        object.put("rbl", rbl);
        object.put("interval", interval);
        try {
            //System.out.println("Send " + object.toString());
            producerQueue.basicPublish(exchangeName, routingKey, null, object.toString().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
            return "failure";
        }

        return "success";


    }

    public Station getStation(String name)
    {
        return stationRepository.findByName(name);
    }

    public List<Station> getAllStation()
    {
        return stationRepository.findAll();
    }

    public List<StationTime> getAllStationTimes()
    {
        return stationTimeRepository.findAll();
    }

    public List<StationTime> getStationTimes(String rbl)
    {
        return stationTimeRepository.findByStationRbl(rbl);
    }

    public StatisticsDTO getStatistics(String rbl)
    {
        List<Object[]> statDTOS = stationTimeRepository.getStatistics(rbl);

        //System.out.println("size" + statDTOS.length);
        StatisticsDTO statisticsDTO = new StatisticsDTO();

        List<LineDTO> lines = new ArrayList<>();
        for(Object[] a : statDTOS)
        {
            StatDTO s = new StatDTO((String)a[0], (BigInteger)a[1], (BigInteger)a[2], (BigInteger)a[3], (BigDecimal) a[4]);


            LineDTO lineDTO = new LineDTO();
            lineDTO.setName(s.getName());
            DelayDTO d = new DelayDTO();
            d.setAverageDelay(String.valueOf(s.getAvg()));
            d.setHighestDelay(String.valueOf(s.getMax()));
            d.setShortestDelay(String.valueOf(s.getMin()));
            d.setOverallDelay(String.valueOf(s.getSum()));
            lineDTO.setStatistics(d);
            lines.add(lineDTO);
        }
        statisticsDTO.setLine(lines);

        return statisticsDTO;

    }

    public List<Stop> readCSV()
    {
        File file = null;
        File stations = null;
        try {
            file = new ClassPathResource("wienerlinien-ogd-steige.csv").getFile();
            stations = new ClassPathResource("wienerlinien-ogd-haltestellen.csv").getFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String line = "";
        String cvsSplitBy = ";";

        List<Stop> stopList = new ArrayList<>();
        HashMap<String, String> t = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"))) {

            while ((line = br.readLine()) != null) {

                // use comma as separator
                String[] stopString = line.split(cvsSplitBy);
                if(stopString[5] != null && stopString[5].length() > 2)
                    stopString[5] = stopString[5].substring(1,stopString[5].length()-1);


                t.put(stopString[2], stopString[5]);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(stations), "UTF-8"))) {

            while ((line = br.readLine()) != null) {

                // use comma as separator
                String[] stopString = line.split(cvsSplitBy);

                String rbl = t.get(stopString[0]);

                if(stopString[3] != null && stopString[3].length() > 2)
                    stopString[3] = stopString[3].substring(1,stopString[3].length()-1);

                Stop stop = new Stop();
                stop.setRbl(rbl);
                stop.setStationId(stopString[0]);
                stop.setName(stopString[3]);

                List<Station> stationList = stationRepository.findByTitle(stop.getName());
                Station s = null;
                if(stationList != null  && stationList.size() > 0)
                    s = stationList.get(0);
                //for(Station s : stationList) {
                    //System.out.println("StationName: " + stop.getName());
                    if (s != null) {
                        //System.out.println("Station: " + s.getName());
                        stop.setTracking(s.isTrack());
                        stop.setInterval(s.getInterval());
                    } else {
                        stop.setTracking(false);
                    }
                    if (rbl != null && !rbl.isEmpty())
                        stopList.add(stop);
                //}
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return stopList;
    }

}

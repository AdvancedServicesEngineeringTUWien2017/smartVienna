package at.ac.tuwien.ase.batch;

import at.ac.tuwien.ase.domain.*;
import at.ac.tuwien.ase.repository.StationRepository;
import at.ac.tuwien.ase.repository.StationTimeRepository;
import at.ac.tuwien.ase.repository.ViennaLineRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.QueueingConsumer;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by Michael on 20.06.2017.
 */
@Service
@Transactional
public class MonitorService {

    @Autowired
    private ViennaLineRepository viennaLineRepository;

    @Autowired
    private StationRepository stationRepository;

    @Autowired
    private StationTimeRepository stationTimeRepository;

    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public void untrack(String untrack) throws IOException, InterruptedException {


        for(Station s : stationRepository.findByRbl(untrack)) {
            s.setTrack(false);
            stationRepository.save(s);
        }


    }

    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public void monitor(Object data, int interval) throws IOException, InterruptedException {

            ObjectMapper objectMapper = new ObjectMapper();

            Monitor[] monitors = objectMapper.readValue(data.toString(),Monitor[].class);

            for(Monitor m : monitors)

            {
                for (Line l : m.getLines()) {
                    for (Departure d : l.getDepartures().getDeparture()) {

                        System.out.println("Departure: " + l.getName());

                        System.out.println("Line: " + l.getName());
                        System.out.println("Towards: " + l.getTowards());
                        System.out.println("Direction: " + l.getDirection());
                        System.out.println("RichtungsId: " + l.getRichtungsId());


                        System.out.println("Time Planned: " + d.getDepartureTime().getTimePlanned());
                        System.out.println("Time Real: " + d.getDepartureTime().getTimeReal());
                        Date timePlanned = d.getDepartureTime().getTimePlanned();
                        Date timeReal = d.getDepartureTime().getTimeReal();
                        long duration = 0;
                        if (d.getDepartureTime().getTimeReal() != null && d.getDepartureTime().getTimePlanned() != null) {

                            duration = d.getDepartureTime().getTimeReal().getTime() - d.getDepartureTime().getTimePlanned().getTime();
                            System.out.println("Delay: " + TimeUnit.SECONDS.toSeconds(duration));
                        }
                        System.out.println("------------------");

                        ViennaLine viennaLine = viennaLineRepository.findByName(l.getName());
                        if (viennaLine == null) {
                            viennaLine = new ViennaLine();
                            viennaLine.setName(l.getName());
                        }

                        String rbl = m.getLocationStop().getProperties().getAttributes().getRbl();
                        System.out.println("RBL: " + rbl);
                        //String rbl = (String)rblObject.get("rbl");

                        Station station = stationRepository.findByRblAndViennaLineName(rbl, l.getName());
                        if (station == null) {
                            station = new Station();
                            station.setInterval(interval);
                            station.setTrack(true);
                            station.setRbl(rbl);
                            station.setName(m.getLocationStop().getProperties().getName());
                            station.setTitle(m.getLocationStop().getProperties().getTitle());
                        }

                        System.out.println("--erg:" + stationRepository.findByRblAndStationTimesTimePlannedAndViennaLineName(rbl, d.getDepartureTime().getTimePlanned(), l.getName()));
                        if (d.getDepartureTime().getTimePlanned() != null && d.getDepartureTime().getTimeReal() != null
                            && stationRepository.findByRblAndStationTimesTimePlannedAndViennaLineName(rbl, d.getDepartureTime().getTimePlanned(), l.getName()) == null) {
                            StationTime stationTime = new StationTime();
                            stationTime.setTimeReal(timeReal);
                            stationTime.setTimePlanned(timePlanned);
                            stationTime.setCountdown(d.getDepartureTime().getCountdown());
                            stationTime.setDelayInSeconds(TimeUnit.SECONDS.toSeconds(duration));
                            stationTime.setRichtungsId(l.getRichtungsId());
                            stationTime.setTowards(l.getTowards());
                            stationTime.setStation(station);

                            List<StationTime> stationTimeList = stationTimeRepository.findAll();
                            stationTimeList.add(stationTime);
                            station.setStationTimes(stationTimeList);

                            List<Station> stationList = stationRepository.findAll();
                            station.setViennaLine(viennaLine);
                            stationList.add(station);
                            viennaLine.setStation(stationList);

                            viennaLineRepository.saveAndFlush(viennaLine);


                            List<ViennaLine> viennaLines = viennaLineRepository.findAll();
                            System.out.println("Size of ViennaLine: " + viennaLines.size());
                            System.out.println("size of Station:" + stationRepository.findAll().size());

                            for (ViennaLine v : viennaLines) {
                                System.out.println("---SELECT LINE---");
                                System.out.println(v.getName());
                                System.out.println("-----------------");
                            }

                        }


                    }

                }
            }



        //Start Request Thread!

    }
}

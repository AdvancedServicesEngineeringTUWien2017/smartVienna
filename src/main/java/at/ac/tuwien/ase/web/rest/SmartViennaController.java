package at.ac.tuwien.ase.web.rest;

import at.ac.tuwien.ase.domain.*;
import at.ac.tuwien.ase.service.SmartViennaService;
import at.ac.tuwien.ase.service.dto.StatisticsDTO;
import at.ac.tuwien.ase.service.dto.TrackDTO;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by Michael on 19.06.2017.
 */
@RestController
@RequestMapping("/api/smartVienna")
public class SmartViennaController {

    private SmartViennaService smartViennaService;

    public SmartViennaController(SmartViennaService smartViennaService)
    {
        this.smartViennaService = smartViennaService;
    }

    @GetMapping
    public List<Stop> getStations()
    {
        return smartViennaService.readCSV();
    }

    @PostMapping
    public String trackStation(@Valid @RequestBody TrackDTO trackDTO)
    {
        System.out.println("rbl:" + trackDTO.getRbl());
        System.out.println("interval:" + trackDTO.getInterval());
        return smartViennaService.startMonitoringStation(trackDTO.getRbl(), trackDTO.getInterval());
    }



    @GetMapping(path = "/getStation",params = {"name"})
    public Station getStation(@RequestParam(value="name") String name)
    {
        return smartViennaService.getStation(name);
    }
    @GetMapping(path = "/getAllStation")
    public List<Station> getAllStations()
    {
        return smartViennaService.getAllStation();
    }


    @GetMapping(path = "/getAllStationTimes")
    public List<StationTime> getAllStationTimes()
    {
        return smartViennaService.getAllStationTimes();
    }

    @GetMapping(path = "/getStationTimes/{rbl}")
    public List<StationTime> getStationTimes(@PathVariable String rbl)
    {
        return smartViennaService.getStationTimes(rbl);
    }


    @GetMapping(path = "/getStatistics/{rbl}")
    public StatisticsDTO getStatistics(@PathVariable String rbl)
    {
        return smartViennaService.getStatistics(rbl);
    }
}

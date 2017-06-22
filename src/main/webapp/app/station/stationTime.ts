export interface StationTimes {

    id;
    timePlanned;
    timeReal;
    countdown;
    delayInSeconds;
    towards;
    richtungsId;

    station :
        {
            id;
            name;
            rbl;
            interval;
            title;
            stationTimes;
            viennaLine :
                {
                    id;
                    name;
                    station;
                }
        }
}

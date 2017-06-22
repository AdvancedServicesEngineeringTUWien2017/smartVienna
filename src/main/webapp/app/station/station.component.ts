import { Component, OnInit } from '@angular/core';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, JhiLanguageService } from 'ng-jhipster';

import { Account, LoginModalService, Principal } from '../shared';

import {Http, URLSearchParams, RequestOptions, Headers} from "@angular/http";
import {Observable} from "rxjs";
import {Router} from "@angular/router";
import {Message} from "primeng/components/common/message";
import {Station} from "./station";
import {StationTimes} from "./stationTime";
import {Statistics} from "./statistics";

@Component({
    selector: 'jhi-home',
    templateUrl: './station.component.html',
    styleUrls: [
        'station.css'
    ]

})
export class StationComponent implements OnInit{

    stations: [Station];
    station: Station;
    stationTimes: [StationTimes];
    interval: number;
    msgs: Message[] = [];
    overallDelay : String;
    statistics : Statistics;

    ENDPOINT: string = "/api/smartVienna";
    ENDPOTNT_STATION_TIME: string = "/api/smartVienna/getAllStationTimes";
    ENDPOTNT_STATION_TIME_RBL : string = "/api/smartVienna/getStationTimes";
    ENDPOTNT_STATION_TIME_STAT : string = "/api/smartVienna/getStatistics";

    data: any;
    msg:any;

    display: boolean = false;
    displayUpdateDialog : boolean = false;

    showDialog(station : Station) {
        this.station = station;
        this.display = true;
    }

    showUpdateDialog(station : Station) {
        this.station = station;
        this.interval = station.interval;
        this.displayUpdateDialog = true;
    }

    ngOnInit():void {
        this
            .getStops()
            .subscribe((stop) => {

                    this.stations = stop;
                },
                error =>  console.log(error));

        this.overallDelay = "500";

    }

    constructor(private _http: Http, private router: Router,) {}

    getStationTime(station : Station): Observable<any> {
        let data = {
            rbl: station.rbl,
        };
        return this._http
            .get(this.ENDPOTNT_STATION_TIME_RBL + "/" + station.rbl)
            .map((r) => r.json());
    }
    getStationStat(station : Station): Observable<any> {
        let data = {
            rbl: station.rbl,
        };
        return this._http
            .get(this.ENDPOTNT_STATION_TIME_STAT + "/" + station.rbl)
            .map((r) => r.json());
    }

    getStops(): Observable<any> {
        return this._http
            .get(this.ENDPOINT)
            .map((r) => r.json());
    }

    trackStation() : void {
        console.log('track station with rbl : ' + this.station.rbl);
        this.getStation(this.station).subscribe((stop) => {


                this.msg = stop;
            },
            error =>  console.log(error));
    }

    selectStationTracking() : void {

        if(this.interval <= 0)
        {
            this.msgs = [];
            this.msgs.push({severity:'error', summary:'Error Message', detail:'Interval 0 not possible!'});

        }
        else {
            this.trackStation();


            this.msgs = [];
            this.msgs.push({severity: 'info', summary: 'Info Message', detail: 'Station tracked successfully'});

            console.log('close dialog!');
            this.display = false;
            this.station.tracking = true;
            this.station.interval = this.interval;
        }

    }

    selectStationUpdate() : void {


        this.trackStation();
        if(this.interval == 0) {
            this.msgs = [];
            this.msgs.push({severity: 'info', summary: 'Info Message', detail: 'Station untracked successfully'});
            this.station.tracking = false;
            this.station.interval = this.interval;
        }
        else {
            this.msgs = [];
            this.msgs.push({severity:'info', summary:'Info Message', detail:'Station updated successfully'});

            console.log('close dialog!');
            this.displayUpdateDialog = false;
            this.station.tracking = true;
            this.station.interval = this.interval;
        }



    }

    getStation(station: Station) : Observable<any> {
        let headers = new Headers({ 'Content-Type': 'application/json' });
        let options = new RequestOptions({ headers: headers });

        let data = {
            rbl: station.rbl,
            interval: this.interval,
        };

        return this._http.post(this.ENDPOINT,
            data)
            .map(t => t);
    }
    showTimes(station: Station) : void {



        this
            .getStationTime(station)
            .subscribe((times) => {

                    this.stationTimes = times;
                },
                error =>  console.log(error));

        this
            .getStationStat(station)
            .subscribe((times) => {

                    this.statistics = times.line;
                },
                error =>  console.log(error));
    }


}

import {NgModule, CUSTOM_ELEMENTS_SCHEMA, OnInit} from '@angular/core';
import { RouterModule } from '@angular/router';
import { SmartViennaSharedModule } from '../shared';
import {PanelModule} from 'primeng/primeng';
import { STATION_ROUTE } from './';
import {StationComponent} from './station.component';
import {BrowserModule} from '@angular/platform-browser';
import {ButtonModule} from 'primeng/primeng';
import {DataTableModule,SharedModule} from 'primeng/primeng';
import {DialogModule} from 'primeng/primeng';

import {InputTextModule} from 'primeng/primeng';

import {MessagesModule} from 'primeng/primeng';

import {BrowserAnimationsModule, NoopAnimationsModule} from "@angular/platform-browser/animations";


@NgModule({
    imports: [
        SmartViennaSharedModule,
        DataTableModule,
        SharedModule,
        ButtonModule,
        BrowserModule,
        DialogModule,
        PanelModule,
        NoopAnimationsModule,
        MessagesModule,
        InputTextModule,
        RouterModule.forRoot([ STATION_ROUTE], { useHash: true })
    ],
    declarations: [
        StationComponent,
    ],
    entryComponents: [
    ],
    providers: [
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})

export class SmartViennaStationModule {


}



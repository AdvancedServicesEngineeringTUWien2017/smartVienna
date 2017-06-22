import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { ButtonModule } from 'primeng/primeng';

import { SmartViennaSharedModule } from '../shared';

import { HOME_ROUTE, HomeComponent } from './';
import {STATION_ROUTE} from "../station/station.route";


@NgModule({
    imports: [
        SmartViennaSharedModule,
        ButtonModule,
        RouterModule.forRoot([ HOME_ROUTE, STATION_ROUTE ], { useHash: true })
    ],
    declarations: [
        HomeComponent,
    ],
    entryComponents: [
    ],
    providers: [
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SmartViennaHomeModule {}

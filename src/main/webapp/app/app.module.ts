import './vendor.ts';

import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { Ng2Webstorage } from 'ng2-webstorage';

import { SmartViennaSharedModule, UserRouteAccessService } from './shared';
import { SmartViennaHomeModule } from './home/home.module';
import {SmartViennaStationModule} from './station/station.module';

import { SmartViennaAdminModule } from './admin/admin.module';
import { SmartViennaAccountModule } from './account/account.module';
import { SmartViennaEntityModule } from './entities/entity.module';

import { LayoutRoutingModule } from './layouts';
import { customHttpProvider } from './blocks/interceptor/http.provider';
import { PaginationConfig } from './blocks/config/uib-pagination.config';

import {
    JhiMainComponent,
    NavbarComponent,
    FooterComponent,
    ProfileService,
    PageRibbonComponent,
    ActiveMenuDirective,
    ErrorComponent
} from './layouts';


@NgModule({
    imports: [
        BrowserModule,
        LayoutRoutingModule,
        Ng2Webstorage.forRoot({ prefix: 'jhi', separator: '-'}),
        SmartViennaSharedModule,
        SmartViennaHomeModule,
        SmartViennaAdminModule,
        SmartViennaAccountModule,
        SmartViennaEntityModule,
        SmartViennaStationModule,
    ],
    declarations: [
        JhiMainComponent,
        NavbarComponent,
        ErrorComponent,
        PageRibbonComponent,
        ActiveMenuDirective,
        FooterComponent
    ],
    providers: [
        ProfileService,
        customHttpProvider(),
        PaginationConfig,
        UserRouteAccessService
    ],
    bootstrap: [ JhiMainComponent ]
})
export class SmartViennaAppModule {}

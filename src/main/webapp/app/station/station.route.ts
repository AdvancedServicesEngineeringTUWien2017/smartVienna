import { Route } from '@angular/router';

import { UserRouteAccessService } from '../shared';


import {StationComponent} from './station.component';

export const STATION_ROUTE: Route = {
  path: 'station',
  component: StationComponent,
  data: {
    authorities: [],
    pageTitle: 'home.title'
  }
};

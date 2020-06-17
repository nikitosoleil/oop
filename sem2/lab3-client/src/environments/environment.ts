// This file can be replaced during build by using the `fileReplacements` array.
// `ng build --prod` replaces `environment.ts` with `environment.prod.ts`.
// The list of file replacements can be found in `angular.json`.

import {KeycloakConfig} from 'keycloak-angular';

const keycloakConfig: KeycloakConfig = {
    url: 'http://localhost:8080/auth',
    realm: 'phone',
    clientId: 'Lab3_frontend',
    credentials: {
        secret: 'e2d9c35e-4647-455d-9f3d-f261ddf90d00'
    }
};


export const environment = {
    production: true,
    envName: 'local',
    keycloak: keycloakConfig,
    registerService: 'http://localhost:8180/registration',
    userService: 'http://localhost:8180/user/',
    serviceService: 'http://localhost:8180/service',
    billService: 'http://localhost:8180/bill',
    logoutService: 'http://localhost:8180/sso/logout'
};

/*
 * For easier debugging in development mode, you can import the following file
 * to ignore zone related error stack frames such as `zone.run`, `zoneDelegate.invokeTask`.
 *
 * This import should be commented out in production mode because it will have a negative impact
 * on performance if an error is thrown.
 */
// import 'zone.js/dist/zone-error';  // Included with Angular CLI.

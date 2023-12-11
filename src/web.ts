import { WebPlugin } from '@capacitor/core';

import type { InstalledAppsPlugin } from './definitions';

export class InstalledAppsWeb extends WebPlugin implements InstalledAppsPlugin {
  async echo(options: { value: string }): Promise<{ value: string }> {
    console.log('ECHO', options);
    return options;
  }
}

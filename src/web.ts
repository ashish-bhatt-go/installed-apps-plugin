import { WebPlugin } from '@capacitor/core';

import type { InstalledAppsPlugin } from './definitions';

export class InstalledAppsWeb extends WebPlugin implements InstalledAppsPlugin {
  getInstalledApps(): Promise<{ value: string }> {
    return Promise.resolve({ value: '' });
  }
}

import { registerPlugin } from '@capacitor/core';

import type { InstalledAppsPlugin } from './definitions';

const InstalledApps = registerPlugin<InstalledAppsPlugin>('InstalledApps', {
  web: () => import('./web').then(m => new m.InstalledAppsWeb()),
});

export * from './definitions';
export { InstalledApps };

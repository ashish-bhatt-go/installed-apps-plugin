export interface InstalledAppsPlugin {
  echo(options: { value: string }): Promise<{ value: string }>;
}

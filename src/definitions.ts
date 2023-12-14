export interface InstalledAppsPlugin {
  /**
   * This method returns all the installed applications in your device!
   * It returns JSON string containing an array of objects having the following structure:
   * ```json
   * [{
   *   "packageName": "",
   *   "encodedIcon": "" -- Base64 encoded string
   * }]
   * ```
   * NOTE: Currently only supported for Android
   */
  getInstalledApps(): Promise<{value: string}>;
}

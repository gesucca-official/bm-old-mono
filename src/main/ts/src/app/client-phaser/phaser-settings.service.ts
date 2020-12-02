export class PhaserSettingsService {

  // here I am subverting Angular dep injection pattern and
  static get instance(): PhaserSettingsService {
    if (this._instance == null)
      this._instance = new PhaserSettingsService();
    return this._instance;
  }

  private static _instance: PhaserSettingsService;

  getScreenWidth(): number {
    return window.innerWidth * window.devicePixelRatio;
  }

  getScreenHeight(): number {
    return window.innerHeight * window.devicePixelRatio;
  }

  // I can specify numbers based on 1080p resolutions
  scaleForWidth(value: number) {
    return (value * this.getScreenWidth()) / 1920;
  }

  scaleForHeight(value: number) {
    return (value * this.getScreenHeight()) / 1080;
  }

  scaleForMin(value: number) {
    return this.scaleForWidth(value) > this.scaleForHeight(value) ? this.scaleForHeight(value) : this.scaleForWidth(value);
  }

}

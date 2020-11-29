import {Resources} from "./character";

export interface Card {
  name: string,
  cost: Resources,
  effect: string,
  priority: number
  canTarget: string[],
  characterBound: true,
  lastResort: boolean
}

import {Status} from "./status";
import {Card} from "./card";

export interface Character {
  name: string,
  dead: boolean,
  immunities: string[],
  resources: Resources,
  statuses: Status[],
  items: Card[],
  sprite: string
}

export interface Resources {
  [resource: string]: number;
}

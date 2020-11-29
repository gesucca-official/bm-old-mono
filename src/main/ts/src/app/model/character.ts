import {Status} from "./status";
import {Card} from "./card";

export interface Character {
  name: string,
  dead: boolean,
  immunities: string[],
  resources: Map<string, number>,
  statuses: Status[],
  items: Card[]
}

import {Status} from "./status";

export interface Character {
  name: string,
  dead: boolean,
  immunities: string[],
  resources: Map<string, number>,
  statuses: Status[]
}

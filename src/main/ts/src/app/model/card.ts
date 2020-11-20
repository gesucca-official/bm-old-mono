export interface Card {
  name: string,
  cost: Map<string, number>,
  effect: string,
  priority: number
  canTarget: string[],
  characterBound: true,
  lastResort: boolean
}

export interface Move {
  playedCardName: string,
  playerId: string,
  targetId: string,
  gameId: string,
  choices: Map<string, string>,
  moveReport?: Map<string, string[]>
}

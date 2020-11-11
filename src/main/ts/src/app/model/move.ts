export interface Move {
  playedCardName: string,
  playerId: string,
  targetId: string,
  gameId: string,
  choices: Map<String, String>
}

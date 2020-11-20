import {Move} from "./move";
import {Player} from "./player";

export interface GameState {
  gameId: string,
  players: Map<string, Player>,
  pendingMoves: Move[]
  lastResolvedMoves: Move[],
  lastResolvedTimeBasedEffects: Map<string, string[]>,
  over: boolean,
  winner: string
}

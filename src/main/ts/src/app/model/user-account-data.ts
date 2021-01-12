import {Card} from "./card";
import {Deck} from "./deck";

export interface UserAccountData {
  username?: string,
  email?: string,
  role?: string
  decks?: Deck[],
  collection?: Card[]
}

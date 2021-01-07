import {Card} from "./card";

export interface UserAccountData {
  username?: string,
  email?: string,
  role?: string
  collection?: Card[]
}

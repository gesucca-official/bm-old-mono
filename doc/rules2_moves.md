## Moves

Characters actions are represented by Moves.

Making a move consists in playing an Action Card on a declared Target.

## Action Cards

An Action Card is a Card that represent an action made by a Character towards someone or something. Action Cards always
have a Target. There are various things that can be targeted by an Action Card in a game:

- the Character him or herself;
- other Characters (*Opponents*);
- Item Cards or Tokens *placed nearby* the Character him or herself (they could be though as *your Items*)
- Item Cards or Token *placed far away* from the Character (*Items your Opponents has*)

There are 3 types of Action Cards:

- **Regular Action Cards**: they are Cards in Hand, drawn from Deck and used only once and then Discarded. They always
  require Player to pay some kind of Cost in Character Resources in order to activate them.
- **Character-Bound Action Cards**: they are Cards bound to a particular Character. Players always have 2 of these in
  their Hand. They are not discarded after use, but they require Players to discard another Regular Action Card in their
  Hand (for example, an Action Card they cannot Cast). Cards like this have no cost, providing a means almost always
  have an Action to perform each turn.
- **Basic Action Card**: like Character-Bound Action Cards, they have no cost and are not discarded after use, but they
  don't require Player to do any additional action to employ them. Players always have 1 of these on their Hand. They
  provide a mean to always being able to perform a particular Action regardless of any other aspect of the Game.
- **Last Resort Action Card**: they are Regular Action Cards except that they always have no Cost. They are Drawn in
  place of a Regular Action Card when a Player's Deck is empty. They cannot be discarded to play Character-Bound Cards.

## Object Cards

Object Cards are cards that represents objects that can be placed on the Field and can be grabbed by Characters.

An Object Card has to be placed on the Field before it can be used. When a Player draws an Object card, he immediately
places it on *his Side of the Field* (or, for short, *nearby* him/her) and draw another card; Players repeat this
process until they draw an Action Card from their Deck. This action is not optional, and it's automatically done in the
web client: an Object Card in a Player's Hand has no use, and should never end there by any regular means.

A Player can use an *Action Card* (for example, the card *Grab*) to get hold on an Object on the Field and immediately
apply its effects. Some kind of Action Cards may impact Objects on other Players' side of the Field (for example, the
card *Steal*).

### Object Tokens

Some Object Cards may spawn an Object Token. They work exactly the same as Object Cards, except that they are not
represented by their own Card.

### Characters Attention Threshold

There is a limited amount of Objects a Character can be "aware of". Objects exceeding this limit are discarded following
a First-In-First-Out model: for example, if a Character's Attention Threshold is 3, when the 4th object spawns on his
side of the Field, the 1st object immediately ceases to exist ("is forgotten" by the not-so-smart Character).
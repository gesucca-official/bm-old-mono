# Botte Micidiali

Botte Micidiali is a Card Game that models a street brawl between shady and gloomy figures. 

Players play Characters busy trying to best each others using brute force, clever tactics or any other means they have access to.

## Characters

Characters are represented through Characters Cards. 

### Resources

Each Character has a starting set of Resources that can be expended to pay Move Costs or saved to impacts the game in particular ways. 

There are two main Resources:

- **Health**: it represents the Character's health level. When it falls below 0, the Character no longer able to fight (i.e. is dead).
- **Alertness**: it represents the Character's responsiveness to what is happening in the Battlefield. The more Alertness a Character has, the more he/she is likely to Move first.

Each Character can start the game with some amount of one or more Special Resources, described and explained on the Character Card itself.
## Moves

Characters actions are represented by Moves.

Making a move consists in playing an Action Card on a declared Target.

### Action Cards

An Action Card is a Card that represent an action made by a Character towards someone or something.

Action Cards always have a Target. There are various things that can be targeted by an Action Card in a game:

- the Character him or herself;
- other Characters (*Opponents*);
- Item Cards or Tokens *placed nearby* the Character him or herself (they could be though as *you Items*)
- Item Cards or Token *placed far away* from the Character (*Items your Opponents has*)

There are 3 types of Action Cards:

- **Regular Action Cards**: 
# Object Cards

*Object Cards* are cards that represents objects that can be placed on the Field and can be grabbed by Characters.

An Object Card has to be placed on the Field before it can be used. When a Player draws an Object card, he can immediately place it on *his Side of the Field*. If he choses not to, the Object Card is treated as any other Card that Player has in his Hand.

A Player can use a *Basic Action* (represented by the card **Grab**) to get hold on an Object on the Field and use his effects.

## Basic Action: Grab

The Basic Action *Grab* targets an Object *on **any** Side the Field*.

- When a Player targets an Object on *his side* of the Field with Grab, the Action is treated as a regular Action Card and its effect is replaced by the targeted Object's effect.
- When a Player targets an Object on *his opponent's Side of the Field* with Grab, it is treated as an Action Card with *Low Priority (-1)* ???and its effect is to move targeted Object on the Player's Side of the Field???.

pay an alertness cost when I grab other's objects??

## Object Types

There are three type of Object Cards:

- *Consumable Objects*: when they are used by Grab, they apply their effects and then are Discarded.
- *Durable Objects*: they can be used by Grab, but they return to the Side of the Field where they were Grabbed after their use. They can also have Static Effects.
- *Equippable Objects*: when they are Grabbed, they remain equipped to the Character and provides Equip Status(es).

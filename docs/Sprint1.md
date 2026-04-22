# Strategic Interaction Tournament

# Simulator (SITS)

```
Omkar Zarikar
Nathan Varner
Darren Davis
Tenghoit Kouch
```
## Problem

A research organization is looking to study decision-making strategies in competitive games. To
run their experiment, they need a flexible simulation platform capable of running tournaments
across a variety of games and formats between automated agents. These agents should be
able to exhibit distinct behavior in their decision process. And the system as a whole should
support the logging of different kinds of results.

## Diagrams

```
Figure 1: UML of the design
```

```
Figure 2: Sequence diagram of the design
```
## Robot

Each robot is an automated agent that makes an action based on an internal decision-making
process. A robot holds a history of their previous interactions with other players such as player
moves and outcome. Using this history along with a concrete implementation of getAction() in
the subclasses results in each robot exhibiting a distinct behavior. This is shown in the figure 1
above, which demonstrates how different robots can be created by implementing their own
getAction().

## Game

Our game class is truly the center piece of this design that essentially does 90% of the work
here. The game is well the game of which is being played in this tournament or you could call it
the rules of the competition. When Game is called by tournament it then calls the given robots to
give an action. Those two actions are then returned to the game in which is put as a parameter
into the games run function and according to the rules an outcome is decided which is then
delivered to the bots so they can help make future decisions and the game is run until the check
end method tells us that it is where it is then returned back to the tournament to decide future
matches.


## Logging System

As part of this simulation, the researchers need a robust logging system with the ability to target
only certain variables while ignoring others. To accomplish this, we utilize the observer pattern
by having Game act as the subject. As seen in figure 1, Game contains two lists of listeners:
one for moves and one for scores. A listener can add or remove themselves from one or both
lists. This strategy allows one type of listener to not be affected by changes from another,
leading to a more efficient logging process.

## Tournament

The Tournament class is essentially the main function, being the class where the process starts
and ends. CheckEnd is our main function that repeatedly calls on Run with different Robots until
the tournament is over. Run takes the two arguments it's given and sends them to the Game
class so the outcome of each match can be decided. The getBracket method decides which
Robots are seeded against each other, with differing conditions based on which type of
Tournament is being run. GetBracket is the one abstract method, meaning it is what determines
the differences in each different type of tournament making it easy to add new types of
tournaments by requiring only one new concrete method in each subclass.


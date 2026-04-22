# Game Viewer

```
Omkar Zarikar
Nathan Varner
Darren Davis
Tenghoit Kouch
```
## Overview

Director Coderson once again is impressed with the success of SITS and wants to expand it to
gain more appeal. We are tasked with creating a viewable user interface for the program. The
interface is expected to have a list of both closed and open tournaments, with each functioning
as a button leading to a registration or spectator page for that tournament.

## UML Diagram

_Figure 1 shows the updated UML diagram for the Tournament and related classes._
As seen in figure 1 above, our existing architecture remains relatively unchanged for the most
part. Most of the new modification will be focused around the Tournament class. We have


decided to omit all other classes not shown in the diagram as they did not have any changes
made to them.
As part of the effort to enhance the viewing experience, we want to make sure that moves are
done in real-time, but not instantly. This is handled through the addition of the moveDelay
attribute inside Tournament, which adds a small delay between moves and will change
depending on the registration state.
One notable change we had was changing the placement of our observer function. Previously,
our Game class contained our observer logic; however with the new sprint requirements, we
decided that it would make more sense for Tournament to handle our observer logic. Since our
Tournament class already holds a history of all actions (which it uses to determine player scores
and bracket placement), adding support to notify listeners is quite simple. This is further
supported by a new type of Listener, RemoteListener. Similar to our RemoteBot,
RemoteListener handles the passing of information to a remote location, which in this case
would be our client. When a client wants to view a running tournament, they can call spectate()
inside the server, which adds a new RemoteListener to the tournament thereby allowing the
client to be passed all the information they need.
_Figure 2 shows the sequence diagram of how a client can spectate a tournament._


## Model Views

_Figure 2 Tournament View_


_Figure 3 Spectator View_


_Figure 4 Registration View_

## Tournaments View

The Tournament page is where the real hub of our system. When opened we see two input
fields below our title and two scroll panes in a split pane. This way we can display our open
tournaments and our closed tournaments. Our text fields are used to take in values for an ip and
port to select a tournament and move on to the registration view if its open and to the spectator
view if its closed.

## Registration View

The registration page appears after a user clicks on a currently open tournament in the
Tournaments View. This view will give the user three options to choose from. The top right of the
screen will be a back button that simply redirects the user back to the Tournaments View page.
The other two buttons will register the user as either human player, or as the user’s imputed
Robot.

## Spectator View

The spectator view is very simple. After choosing a closed tournament you arrive here a true
vacation destination. No work or inputs needed just sit back and relax as our listeners print
tournament information to your scroll pane for your entertainment.


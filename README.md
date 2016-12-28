#Lottery application

This is a lottery draw application with a command line frontend allowing users to purchase tickets for the lottery draw, trigger the draw and display the lottery draw winners with their prizes amounts.

When the application is run, it creates a new lottery draw with a bucket of numbered balls and a set of tickets numbered the same way as the lottery balls.

The lottery draw has an initial pot amount that is used to grant prizes to the lottery winners.

Tickets can be purchased by participants who provide their first names.
Each ticket refers to a ball from the lottery draw bucket and has a fixed price.
Tickets can be purchased as long as the draw did not happen and there are still tickets left for sale.

The following command is used to purchase a ticket:
```
> purchase <first name of the participant>
```

The draw randomly picks a number of balls from the balls bucket.
Only one draw can happen for a running instance of the application.

The following command triggers the lottery draw:
```
> draw
```

The lottery participants who purchased tickets referring to the balls that were drawn are the winners of the draw.

The following command displays the lottery winners first names and the amounts of their prizes:
```
> winners
```

##Running the application
To run the application:

Clone the git repository containing the source code of the application:
```
git clone https://github.com/jihedamine/lottery.git
```

Go to the root folder of the cloned repository:

```
cd lottery
```

Run the monthly lottery by issuing the following command:
```
gradle run -q
```
The -q parameter is to run gradle quietly so that the gradle steps are not displayed in the standard output.


You can pass custom values to the lottery draw using the param option:
```
gradle run -q -PstartIdx=1 -PnbItems=5 -PnbDraws=3 -PinitialPot=200 -PticketPrice=10
```
The previous params value means the following: 
```
startIdx = initial number to start the sequence of lottery balls (default is 1)
nbItems = the number of balls in the lottery draw (default is 50)
nbDraws = the number of balls to randomly pick during the draw (default is 3)
initialPot = the initial pot amount in dollars (default is 200)
ticketPrice = the price of a lottery ticket in dollars (default is 10)
```
If you don't specify custom values for all the parameters, default values will be used.

For example, the following command starts a lottery draw with 10 items and an initial pot of 1000. startIndex, nbDraws and ticketPrices will have their default values.
```
gradle run -q -PnbItems=10 -PinitialPot=1000
```

The following sequence of commands illustrates a typical use case of the application:

- Run a lottery draw with 5 balls numbered from 1 to 5 (and 5 corresponding tickets), a draw that randomly picks 3 balls, an initial pot amount of 200 dollars and a ticket price of 10 dollars.
```
> gradle run -q -PstartIdx=1 -PnbItems=5 -PnbDraws=3 -PinitialPot=200 -PticketPrice=10

Welcome to the lottery draw of the month!
This month's lottery contains 5 items, 3 items will be picked during the draw.
Type help to display the list of available commands
> 
```

- Type help to see the list of available commands

```
> help

 Available commands:
 - purchase: Assigns a lottery ticket to the participant whose first name is passed as argument (purchase <first name of the participant>)
 - draw: Draws balls from the lottery bucket
 - winners: Displays the winners of the draw
 - exit: Quits the application
 - help: Displays the list of available commands
```

- Purchase a ticket for user Dave

```
> purchase Dave

  Purchased number for Dave: 1
```

- Purchase a ticket for user Remy

```
> purchase Remy

  Purchased number for Remy: 3
```

- Purchase a ticket for user Greg
  
```
> purchase Greg
  
  Purchased number for Greg: 2
```
  
- Trigger the lottery draw

```
> draw

  Drawn ball: 3
  Drawn ball: 2
  Drawn ball: 1
```

- Display the winners

```
> winners

             1st ball            2nd ball            3rd ball
         Remy: 86.25$        Greg: 17.25$        Dave: 11.50$
```

- Exit the application

```
> exit
```
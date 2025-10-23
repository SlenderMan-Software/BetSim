public class BetSim
{
    public static void main(String[] args){
        if (args.length != 3)
        {
            System.out.println("Usage: java BetSim <stake> <bet> <goal>");
            return;
        }
        System.out.flush();
        System.out.print("""
░▒▓███████▓▒░░▒▓████████▓▒░▒▓████████▓▒░▒▓███████▓▒░▒▓█▓▒░▒▓██████████████▓▒░  
░▒▓█▓▒░░▒▓█▓▒░▒▓█▓▒░         ░▒▓█▓▒░  ░▒▓█▓▒░      ░▒▓█▓▒░▒▓█▓▒░░▒▓█▓▒░░▒▓█▓▒░ 
░▒▓█▓▒░░▒▓█▓▒░▒▓█▓▒░         ░▒▓█▓▒░  ░▒▓█▓▒░      ░▒▓█▓▒░▒▓█▓▒░░▒▓█▓▒░░▒▓█▓▒░ 
░▒▓███████▓▒░░▒▓██████▓▒░    ░▒▓█▓▒░   ░▒▓██████▓▒░░▒▓█▓▒░▒▓█▓▒░░▒▓█▓▒░░▒▓█▓▒░ 
░▒▓█▓▒░░▒▓█▓▒░▒▓█▓▒░         ░▒▓█▓▒░         ░▒▓█▓▒░▒▓█▓▒░▒▓█▓▒░░▒▓█▓▒░░▒▓█▓▒░ 
░▒▓█▓▒░░▒▓█▓▒░▒▓█▓▒░         ░▒▓█▓▒░         ░▒▓█▓▒░▒▓█▓▒░▒▓█▓▒░░▒▓█▓▒░░▒▓█▓▒░ 
░▒▓███████▓▒░░▒▓████████▓▒░  ░▒▓█▓▒░  ░▒▓███████▓▒░░▒▓█▓▒░▒▓█▓▒░░▒▓█▓▒░░▒▓█▓▒░ 
                                                                               
                                                                               
                """);
        int stake = Integer.parseInt(args[0]);
        int stake1 = stake;
        int bet = Integer.parseInt(args[1]);
        int goal = Integer.parseInt(args[2]);
        int numOfBets = 0; // number of times a bet had to be placed per game
        int numOfWins = 0; // number of times the goal was reached
        int numOfLosses = 0; //number of times bankrupt
        int numOfGames = 1000000; //number of games played
        int numOfGames1 = numOfGames;
        System.out.println("Starting stake: " + stake);
        System.out.println("Goal: " + goal);
        System.out.println("Bet: " + bet);
        System.out.println("Running " + numOfGames + " simulations...");
     while (numOfGames > 0)
     { stake = stake1;
        while (stake < goal && stake > 0)
        {
            if (Math.random() > 0.5)
            {
                stake = stake + bet;
                
            }
            else
            {
                stake = stake - bet;

            }
                numOfBets = numOfBets + 1;

        }
        if (stake >= goal)
        {
            numOfWins = numOfWins + 1;
        }
        else
        {
            numOfLosses = numOfLosses + 1;
        }
        numOfGames = numOfGames - 1;
        ProgBar.printProgress(numOfGames1 - numOfGames, numOfGames1);
    }
    double chance = 100 * numOfWins / numOfGames1;
    System.out.println("\nSimulation Complete!");
    System.out.println("Wins: " + numOfWins);
    System.out.println("Losses " + numOfLosses);
    System.out.println("Chance of success: with current strategy: " + chance + "%");
    System.out.println("Average bets per game: " + (double) numOfBets / numOfGames1);
    System.out.println("Starting Stake: " + stake1);
    System.out.println("Goal: " + goal);
    System.out.println("Bet: " + bet);
    
}
}
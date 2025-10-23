import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

public class BetSimTurbo {
    public static void main(String[] args) throws InterruptedException {
        if (args.length != 3) {
            System.out.println("Usage: java BetSimTurbo <stake> <bet> <goal>");
            return;
        }

        // Banner
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
        int bet = Integer.parseInt(args[1]);
        int goal = Integer.parseInt(args[2]);
        final int totalGames = 1_000_000;

        System.out.println("Starting stake: " + stake);
        System.out.println("Goal: " + goal);
        System.out.println("Bet: " + bet);
        System.out.println("Running " + totalGames + " simulations using "
                + Runtime.getRuntime().availableProcessors() + " threads...");

        AtomicInteger wins = new AtomicInteger(0);
        AtomicInteger losses = new AtomicInteger(0);
        AtomicInteger bets = new AtomicInteger(0);
        AtomicInteger done = new AtomicInteger(0);

        long startTime = System.nanoTime();

        // Progress thread with ETA
        Thread progressThread = new Thread(() -> {
            while (done.get() < totalGames) {
                ProgBar.printProgress(done.get(), totalGames, startTime);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ignored) {}
            }
            ProgBar.printProgress(totalGames, totalGames, startTime);
        });
        progressThread.start();

        // Parallel simulations
        IntStream.range(0, totalGames).parallel().forEach(i -> {
            int s = stake;
            int localBets = 0;
            var rand = java.util.concurrent.ThreadLocalRandom.current();

            while (s > 0 && s < goal) {
                s += rand.nextBoolean() ? bet : -bet;
                localBets++;
            }

            if (s >= goal)
                wins.incrementAndGet();
            else
                losses.incrementAndGet();

            bets.addAndGet(localBets);
            done.incrementAndGet();
        });

        progressThread.join();

        long elapsed = (System.nanoTime() - startTime) / 1_000_000_000;
        System.out.printf("\n\nSimulation Complete in %d seconds!\n", elapsed);

        double chance = 100.0 * wins.get() / totalGames;
        double avgBets = (double) bets.get() / totalGames;

        System.out.println("Wins: " + wins);
        System.out.println("Losses: " + losses);
        System.out.printf("Chance of success: %.6f%%\n", chance);
        System.out.printf("Average bets per game: %.2f\n", avgBets);
    }
}

public class ProgBar {
    private static final String GREEN = "\u001B[32m";
    private static final String RESET = "\u001B[0m";

    public static void printProgress(int current, int total, long startNano) {
        int barLength = 50;
        double percent = (double) current / total;
        int filled = (int) (barLength * percent);

        // Compute elapsed time
        long elapsedNano = System.nanoTime() - startNano;
        double elapsedSec = elapsedNano / 1_000_000_000.0;

        // Estimate ETA
        double etaSec = percent > 0 ? (elapsedSec / percent) - elapsedSec : 0;

        StringBuilder bar = new StringBuilder();
        bar.append("\r[");
        for (int i = 0; i < barLength; i++) {
            bar.append(i < filled ? "█" : " ");
        }
        bar.append("] ");
        bar.append(String.format("%3d%%", (int) (percent * 100)));

        bar.append(String.format(" | Elapsed: %5.1fs | ETA: %5.1fs", elapsedSec, etaSec));

        System.out.print(GREEN + bar + RESET);
        System.out.flush();
    }
}

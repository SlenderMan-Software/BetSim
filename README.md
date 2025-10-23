# BetSim

**BetSim** — Coin Flip Simulator (v1.1, Parallel Edition)

A simple, fast Java coin-flip simulator that can run millions of independent simulations. Includes a big ASCII startup banner, a green terminal progress bar with ETA, and both parallel (multi-core) and classic single-threaded modes. `ProgBar` is backwards-compatible so older code works unchanged.

---

## Features
- Run millions of simulations (configurable).
- Parallel execution using all CPU cores (`IntStream.parallel()`).
- Fast per-thread RNG (`ThreadLocalRandom` / `SplittableRandom`).
- Dedicated progress-monitor thread: single-line green progress bar with ETA (no flicker).
- Backwards-compatible `ProgBar.printProgress(int, int)` overload.
- Big ASCII banner printed at startup (uses Java text blocks).

---

## Requirements
- Java 11+ recommended.
  - If you want to use the ASCII banner text blocks exactly as shown, Java 15+ is required.
- Terminal that supports ANSI escape codes (Linux, macOS Terminal, Windows Terminal / PowerShell on Windows 10+).

---

## Included Files
- `BetSim.java` — main parallel simulator (example uses 1,000,000 games).
- `ProgBar.java` — progress bar with ETA and backward-compatible overload.
- (Optional) `BetSimClassic.java` — example single-threaded version.

---

## Build

Compile all Java files in the project directory:

```bash
javac *.java
```

---

## Run

Usage:
```bash
java -Xms512m -Xmx1g BetSim <stake> <bet> <goal>
```

Example:
```bash
java -Xms512m -Xmx1g BetSim 10 1 1000000
```

Arguments:
1. `stake` — starting money (e.g., `10`)
2. `bet` — bet per flip (e.g., `1`)
3. `goal` — target money (e.g., `1000000`)

Example output:
```
<big ascii banner>
Starting stake: 10
Goal: 1000000
Bet: 1
Running 1000000 simulations using 8 threads...
[█████████████████           ]  45% | Elapsed: 12.4s | ETA: 15.2s
...
Simulation Complete!
Wins: 9
Losses: 999991
Chance of success: 0.000900%
Average bets per game: 3.23
```

---

## Notes & Tips
- The parallel version uses `IntStream.range(...).parallel()` and atomic counters to safely collect results.
- For best throughput:
  - Use `ThreadLocalRandom` or `SplittableRandom` for per-thread RNG.
  - Reduce console updates (progress printed at intervals).
  - Tune JVM heap and parallelism if needed, e.g.:
    ```bash
    java -Xms512m -Xmx1g -Djava.util.concurrent.ForkJoinPool.common.parallelism=16 BetSim 10 1 1000000
    ```
- If you hide the terminal cursor in the bar, the code registers a JVM shutdown hook to restore it on exit. If the cursor is missing after a crash, run `printf '\e[?25h'` (POSIX) to restore it.

---

## Backwards Compatibility
`ProgBar` provides both:
- `printProgress(int current, int total)` — for older, classic code
- `printProgress(int current, int total, long startNano)` — for ETA-accurate progress (pass `System.nanoTime()` at start)

This allows existing single-threaded code to continue working without changes.

---

## License
MIT License — free to use and modify. Include attribution if you like.

---

## Contributing
Pull requests and improvements are welcome. If you add features (custom thread pools, GPU offload, GraalVM native image, or automatic terminal-width handling), please add usage examples in this README.

---

If you want, I can:
- Add a short `run.sh` script for common configurations,
- Add an option flag to choose **fast-mode** (analytic estimate) vs full brute-force,
- Or generate a smaller README variant for GitHub display.

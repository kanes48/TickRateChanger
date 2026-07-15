package mc.mod;

public class TickRateManager {

    private static volatile int tps = 20;


    public static void setTPS(int value) {

        tps = Math.max(
                1,
                Math.min(
                        1000,
                        value
                )
        );
    }


    public static int getTPS() {

        return tps;
    }


    public static long getMillisPerTick() {

        return Math.max(
                1L,
                1000L / tps
        );
    }
}
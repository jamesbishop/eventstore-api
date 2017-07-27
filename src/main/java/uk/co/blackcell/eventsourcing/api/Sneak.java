package uk.co.blackcell.eventsourcing.api;

public class Sneak {
    public static RuntimeException sneakyThrow(Throwable throwable) {
        if ( throwable == null ) throw new NullPointerException("t");
        Sneak.<RuntimeException>sneakyThrow0(throwable);
        return null;
    }

    @SuppressWarnings("unchecked")
    private static <T extends Throwable> void sneakyThrow0(Throwable throwable) throws T {
        throw (T)throwable;
    }
}
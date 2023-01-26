package lab8;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.Clock;
import java.time.Duration;
import java.util.function.IntFunction;

import org.junit.Test;

import static org.junit.Assert.*;

public class EventsStatisticImplTest {
    private final double maxError = 0.0001;
    private final double minutesInHour = 60.0;
    private final Clock realClock = Clock.systemDefaultZone();

    boolean doubleEquals(double a, double b) {
        return Math.abs(a - b) < maxError;
    }

    @Test
    public void singleEvent() {
        var impl = new EventsStatisticImpl(realClock);

        impl.incEvent("event 1");
        assertTrue(doubleEquals(impl.getEventStatisticByName("event 1"), 1.0 / minutesInHour));
        assertTrue(doubleEquals(impl.getAllEventStatistic(), 1.0 / minutesInHour));
    }

    @Test
    public void multipleRecordsOfSingleEvent() {
        var impl = new EventsStatisticImpl(realClock);

        int numberOfEvents = 10_000;
        for (int i = 0; i < numberOfEvents; i++)
            impl.incEvent("event 1");

        assertTrue(doubleEquals(impl.getEventStatisticByName("event 1"), (double) numberOfEvents / minutesInHour));
        assertTrue(doubleEquals(impl.getAllEventStatistic(), (double) numberOfEvents / minutesInHour));
    }

    @Test
    public void multipleRecordsOfMultipleEvents() {
        var impl = new EventsStatisticImpl(realClock);

        int numberOfEvents1 = 10_000;
        for (int i = 0; i < numberOfEvents1; i++)
            impl.incEvent("event 1");

        int numberOfEvents2 = 100_000;
        for (int i = 0; i < numberOfEvents2; i++)
            impl.incEvent("event 2");

        assertTrue(doubleEquals(impl.getEventStatisticByName("event 1"), (double) numberOfEvents1 / minutesInHour));
        assertTrue(doubleEquals(impl.getEventStatisticByName("event 2"), (double) numberOfEvents2 / minutesInHour));
        assertTrue(doubleEquals(impl.getAllEventStatistic(),
                (double) (numberOfEvents1 + numberOfEvents2) / minutesInHour));
    }

    @Test
    public void fakeClockSingleEvent() {
        var fakeClock = new FakeClock(realClock);
        var impl = new EventsStatisticImpl(fakeClock);

        impl.incEvent("event 1");

        fakeClock.turn(Duration.ofMinutes(59));
        assertTrue(doubleEquals(impl.getEventStatisticByName("event 1"), 1.0 / minutesInHour));
        assertTrue(doubleEquals(impl.getAllEventStatistic(), 1.0 / minutesInHour));

        fakeClock.turn(Duration.ofMinutes(1));
        assertTrue(doubleEquals(impl.getEventStatisticByName("event 1"), 0.0));
        assertTrue(doubleEquals(impl.getAllEventStatistic(), 0.0));
    }

    @Test
    public void fakeClockMultipleRecordsOfSingleEvent() {
        var fakeClock = new FakeClock(realClock);
        var impl = new EventsStatisticImpl(fakeClock);

        var offsetInMinutes = 30;
        IntFunction<Integer> minuteToNumberOfAliveEvents = (minutes) -> {
            switch (minutes) {
                case 0:
                    return 1;
                case 30:
                    return 2;
                case 60:
                    return 2;
                default:
                    return 2;
            }
        };

        var totalMinutes = 1_000;
        for (int i = 0; i < totalMinutes; i += offsetInMinutes) {
            impl.incEvent("event 1");
            assertTrue(doubleEquals(impl.getEventStatisticByName("event 1"),
                    ((Number) minuteToNumberOfAliveEvents.apply(i)).doubleValue() / minutesInHour));
            assertTrue(doubleEquals(impl.getAllEventStatistic(),
                    ((Number) minuteToNumberOfAliveEvents.apply(i)).doubleValue() / minutesInHour));
            fakeClock.turn(Duration.ofMinutes(offsetInMinutes));
        }
        assertTrue(doubleEquals(impl.getEventStatisticByName("event 1"), 1.0 / minutesInHour));
        assertTrue(doubleEquals(impl.getAllEventStatistic(), 1.0 / minutesInHour));

        fakeClock.turn(Duration.ofMinutes(offsetInMinutes));
        assertTrue(doubleEquals(impl.getEventStatisticByName("event 1"), 0.0));
        assertTrue(doubleEquals(impl.getAllEventStatistic(), 0.0));
    }

    @Test
    public void print() {
        var stdout = System.out;
        var outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        var impl = new EventsStatisticImpl(realClock);

        int numberOfEvents2 = 10_000;
        for (int i = 0; i < numberOfEvents2; i++)
            impl.incEvent("event 2");

        int numberOfEvents1 = 1_000;
        for (int i = 0; i < numberOfEvents1; i++)
            impl.incEvent("event 1");

        impl.printStatistic();

        var lines = outputStream.toString().trim().split(System.lineSeparator());
        assertTrue(lines.length == 2);

        assertEquals(lines[0], "event 1" + ": " + impl.getEventStatisticByName("event 1"));
        assertEquals(lines[1], "event 2" + ": " + impl.getEventStatisticByName("event 2"));

        System.setOut(stdout);
    }

    @Test
    public void printEmpty() {
        var stdout = System.out;
        var outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        var impl = new EventsStatisticImpl(realClock);
        impl.printStatistic();

        var line = outputStream.toString().trim();
        assertEquals(line, "No events recorded");

        System.setOut(stdout);
    }
}

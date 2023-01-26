package lab8;

import java.util.HashMap;
import java.util.LinkedList;
import java.time.Clock;
import java.time.Duration;
import java.time.Instant;

public class EventsStatisticImpl implements EventsStatistic {
    public static void main(String[] args) {
        var impl = new EventsStatisticImpl(Clock.systemDefaultZone());

    }

    private final Duration eventLifeTime = Duration.ofMinutes(60);

    private final Clock clock;
    private final HashMap<String, Integer> currentEvents = new HashMap<>();
    private final LinkedList<EventRecord> removalQueue = new LinkedList<>();

    public EventsStatisticImpl(Clock clock) {
        this.clock = clock;
    }

    @Override
    public void incEvent(String name) {
        removalQueue.add(new EventRecord(name, clock.instant()));
        currentEvents.merge(name, 1, Integer::sum);
    }

    @Override
    public double getEventStatisticByName(String name) {
        removeOutdated();

        if (!currentEvents.containsKey(name))
            return 0.0;

        return (double) currentEvents.get(name) / (double) eventLifeTime.toMinutes();
    }

    @Override
    public double getAllEventStatistic() {
        return currentEvents
                .keySet()
                .stream()
                .map(name -> getEventStatisticByName(name))
                .reduce(0.0, Double::sum);
    }

    @Override
    public void printStatistic() {
        removeOutdated();

        if (currentEvents.isEmpty())
            System.out.println("No events recorded");
        else
            currentEvents
                    .keySet()
                    .stream()
                    .sorted()
                    .forEachOrdered(name -> System.out.println(name + ": " + getEventStatisticByName(name)));
    }

    private void decEvent(String name) {
        if (!currentEvents.containsKey(name))
            return;

        assert currentEvents.get(name) > 0;
        if (currentEvents.get(name) == 1)
            currentEvents.remove(name);
        else
            currentEvents.compute(name, (n, count) -> --count);
    }

    private boolean isOutdated() {
        if (removalQueue.isEmpty())
            return false;

        return removalQueue
                .getFirst().timeAdded
                .plus(eventLifeTime)
                .isBefore(Instant.now(clock));
    }

    private void removeOutdated() {
        while (isOutdated()) {
            decEvent(removalQueue.getFirst().name);
            removalQueue.removeFirst();
        }
    }

    private record EventRecord(String name, Instant timeAdded) {
    }
}

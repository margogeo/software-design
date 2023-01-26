package lab8;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;

public class FakeClock extends Clock {
    private Clock clock;

    public FakeClock(Clock clock) {
        this.clock = clock;
    }

    @Override
    public ZoneId getZone() {
        return clock.getZone();
    }

    @Override
    public Instant instant() {
        return clock.instant();
    }

    @Override
    public Clock withZone(ZoneId zone) {
        return clock.withZone(zone);
    }

    public void turn(Duration duration) {
        clock = Clock.offset(clock, duration);
    }
}

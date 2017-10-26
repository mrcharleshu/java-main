package com.charles.runtime;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.util.concurrent.TimeUnit;

/**
 * Jvm takes a long time to resolve ip-address for localhost
 * 很多时候都是本地hostname不可ping，解析错误导致
 * ping $(hostname) = ping localhost = ping 127.0.0.1
 * https://thoeni.io/post/macos-sierra-java/
 * https://github.com/thoeni/inetTester
 */
public class InetTester {

    public static void main(String[] args) throws UnknownHostException {
        final Clock clock = new NanoClock();
        System.out.printf("Calling the hostname resolution method...%n");
        final Instant startTime = Instant.now(clock);
        String hostName = InetAddress.getLocalHost().getHostName();
        final Instant endTime = Instant.now(clock);
        System.out.printf("Method called, hostname %s, elapsed time: %d (ms)%n", hostName, TimeUnit.NANOSECONDS.toMillis(Duration.between(startTime, endTime).toNanos()));
    }

    private static class NanoClock extends Clock {
        private final Clock clock;
        private final long initialNanos;
        private final Instant initialInstant;

        public NanoClock() {
            this(Clock.systemUTC());
        }

        public NanoClock(final Clock clock) {
            this.clock = clock;
            initialInstant = clock.instant();
            initialNanos = getSystemNanos();
        }

        @Override
        public ZoneId getZone() {
            return clock.getZone();
        }

        @Override
        public Instant instant() {
            return initialInstant.plusNanos(getSystemNanos() - initialNanos);
        }

        @Override
        public Clock withZone(final ZoneId zone) {
            return new NanoClock(clock.withZone(zone));
        }

        private long getSystemNanos() {
            return System.nanoTime();
        }
    }
}
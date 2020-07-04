package com.charles.thread;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.concurrent.Phaser;

/**
 * @author Charles
 */
@Slf4j
public class PhaserStateAnalysis {
    private static final int MAX_PARTIES = 0xffff;
    private static final int MAX_PHASE = Integer.MAX_VALUE;
    private static final int PARTIES_SHIFT = 16;
    private static final int PHASE_SHIFT = 32;
    private static final int UNARRIVED_MASK = 0xffff;      // to mask ints
    private static final long PARTIES_MASK = 0xffff0000L; // to mask longs
    private static final long COUNTS_MASK = 0xffffffffL;
    private static final long TERMINATION_BIT = 1L << 63;

    // some special values
    private static final int ONE_ARRIVAL = 1;
    private static final int ONE_PARTY = 1 << PARTIES_SHIFT;
    private static final int ONE_DEREGISTER = ONE_ARRIVAL | ONE_PARTY;
    private static final int EMPTY = 1;

    private static int unarrivedOf(long s) {
        int counts = (int) s;
        return (counts == EMPTY) ? 0 : (counts & UNARRIVED_MASK);
    }

    private static int partiesOf(long s) {
        return (int) s >>> PARTIES_SHIFT;
    }

    private static int phaseOf(long s) {
        return (int) (s >>> PHASE_SHIFT);
    }

    private static int arrivedOf(long s) {
        int counts = (int) s;
        return (counts == EMPTY) ? 0 :
                (counts >>> PARTIES_SHIFT) - (counts & UNARRIVED_MASK);
    }

    private static long getStateValue(Phaser phaser, Field field) {
        //noinspection ConstantConditions
        return (long) ReflectionUtils.getField(field, phaser);
    }

    public static void main(String[] args) throws NoSuchFieldException {
        Phaser phaser = new Phaser(1);
        Field stateField = Phaser.class.getDeclaredField("state");
        stateField.setAccessible(true);
        long state = getStateValue(phaser, stateField);
        log.info("StateValue = {}, BinaryString={}", state, Long.toBinaryString(state));
        log.info("unarrivedOf={}", unarrivedOf(state));
        log.info("partiesOf={}", partiesOf(state));
        log.info("phaseOf={}", phaseOf(state));
        log.info("arrivedOf={}", arrivedOf(state));
    }
}

package com.charles.thread.state;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Phaser;

/**
 * @author Charles
 */
@Slf4j(topic = "PhaserStateAnalysis")
public class PhaserStateAnalysis {
    private static final ExecutorService es = Executors.newFixedThreadPool(5);
    private static Field stateField;

    static {
        try {
            stateField = Phaser.class.getDeclaredField("state");
            stateField.setAccessible(true);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    private static final int PARTIES_SHIFT = 16;
    private static final int PHASE_SHIFT = 32;
    // 16位的整形最大值0000 0000 0000 0000 1111 1111 1111 1111
    private static final int UNARRIVED_MASK = 0xffff; // to mask ints

    // some special values
    private static final int EMPTY = 1;

    // Primary state representation, holding four bit-fields:
    //
    // unarrived  -- the number of parties yet to hit barrier (bits  0-15)
    // parties    -- the number of parties to wait            (bits 16-31)
    // phase      -- the generation of the barrier            (bits 32-62)
    // terminated -- set if barrier is terminated             (bit  63 / sign)

    // 求值过程
    // 0,----------------- phase --------------, ----- parties -----, ---- unarrived ----
    // 0,000 0000 0000 0000 0000 0000 0000 0001, 0000 0000 0000 0010, 0000 0000 0000 0001
    //                                           0000 0000 0000 0010, 0000 0000 0000 0001 (cast to int)
    //                                           0000 0000 0000 0000, 1111 1111 1111 1111 (& UNARRIVED_MASK)
    //                                           0000 0000 0000 0000, 0000 0000 0000 0001 (得到低16位的unarrived值)
    private static int unarrivedOf(long s) {
        int counts = (int) s;
        return (counts == EMPTY) ? 0 : (counts & UNARRIVED_MASK);
    }

    // 求值过程
    // 0,----------------- phase --------------, ----- parties -----, ---- unarrived ----
    // 0,000 0000 0000 0000 0000 0000 0000 0001, 0000 0000 0000 0010, 0000 0000 0000 0001
    //                                           0000 0000 0000 0010, 0000 0000 0000 0001 (cast to int)
    //                                           0000 0000 0000 0000, 0000 0000 0000 0001 (无符号右移16位)
    private static int partiesOf(long s) {
        return (int) s >>> PARTIES_SHIFT;
    }

    // 求值过程
    // 0,----------------- phase --------------, ----- parties -----, ---- unarrived ----
    // 0,000 0000 0000 0000 0000 0000 0000 0001, 0000 0000 0000 0010, 0000 0000 0000 0001
    // 0000 0000 0000 0000, 0000 0000 0000 0000, 0000 0000 0000 0000, 0000 0000 0000 0001 (无符号右移32位)
    //                                           0000 0000 0000 0000, 0000 0000 0000 0001 (cast to int得到phase的值)
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

    private static String splitByFourBit(String bits) {
        StringBuilder sb = new StringBuilder();
        boolean first = true;
        for (int i = bits.length(); i > 0; i -= 4) {
            String temp = bits.substring(Math.max(i - 4, 0), i);
            if (first) {
                first = false;
                sb.insert(0, temp);
            } else {
                sb.insert(0, temp + " ");
            }
        }
        return sb.toString();
    }

    private static String getFormattedPhaserStateOutput(long state, String desc) {
        int stateLength = Long.SIZE;
        StringBuilder binaryString = new StringBuilder(Long.toBinaryString(state));
        if (binaryString.length() < stateLength) {
            for (int i = binaryString.length(); i < stateLength; i++) {
                binaryString.insert(0, "0");
            }
        }
        char terminated = binaryString.charAt(0);
        String phase = binaryString.substring(stateLength - 63, stateLength - 32);
        String parties = binaryString.substring(stateLength - 32, stateLength - 16);
        String unarrived = binaryString.substring(stateLength - 16, stateLength);
        String formatStateValue = String.format("* >>> %-10s: %s *", "state_val_", terminated + "," +
                splitByFourBit(phase) + "," + splitByFourBit(parties) + "," + splitByFourBit(unarrived));
        String formatTerminated = String.format(">>> %-10s: %-32s", "terminated", terminated);
        String formatPhase = String.format(">>> %-10s: %-32s", "phase", phase);
        String formatParties = String.format(">>> %-10s: %-32s", "parties", parties);
        String formatUnarrived = String.format(">>> %-10s: %-32s", "unarrived", unarrived);
        String lineSeparator = System.lineSeparator();
        // Linux环境下输出字体颜色
        // https://stackoverflow.com/questions/565252/how-to-set-a-strings-color
        // https://www.cnblogs.com/lr-ting/archive/2013/02/28/2936792.html
        return lineSeparator +
                String.format("****************************************************** \033[31m%-30s\033[0m", desc) +
                lineSeparator + formatStateValue + lineSeparator +
                String.format("* %-48s               %-33s *", formatTerminated, "") + lineSeparator +
                String.format("* %-48s| phaseOf    : %-33s *", formatPhase, phaseOf(state)) + lineSeparator +
                String.format("* %-48s| partiesOf  : %-33s *", formatParties, partiesOf(state)) + lineSeparator +
                String.format("* %-48s| unarrivedOf: %-33s *", formatUnarrived, unarrivedOf(state)) + lineSeparator +
                String.format("* %-48s| arrivedOf  : %-33s *", ">>>", arrivedOf(state)) + lineSeparator +
                "****************************************************************************************************";
    }

    private static void peekPhaserState(Phaser phaser, String desc) {
        long state = getStateValue(phaser, stateField);
        log.info(">>>>>>>>>>>>>>> PhaserStateValue: {}", getFormattedPhaserStateOutput(state, desc));
    }

    private static class HouseholdJob implements Runnable {
        private final Phaser phaser;
        private final String name;

        private HouseholdJob(Phaser phaser, String name) {
            this.name = name;
            this.phaser = phaser;
            phaser.register();
        }

        @Override
        public void run() {
            phaser.arriveAndAwaitAdvance();
            log.info("开始{}...", name);
            try {
                Thread.sleep(new Random().nextInt(1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info("完成{}.", name);
            // 大家都完成后自己取消登记（影分身消失）
            phaser.arriveAndDeregister();
        }
    }

    private static void executePhaser1(Phaser phaser) {
        es.submit(new HouseholdJob(phaser, "打扫主卧"));
        es.submit(new HouseholdJob(phaser, "打扫次卧"));
        es.submit(new HouseholdJob(phaser, "打扫客厅"));
        es.submit(new HouseholdJob(phaser, "打扫卫生间"));
        es.submit(new HouseholdJob(phaser, "打扫阳台"));
        peekPhaserState(phaser, "召唤5个影分身打扫5个房间，都给我动起来！查看中间状态");
        phaser.arriveAndAwaitAdvance();
        peekPhaserState(phaser, "打扫完成了，满意！换批影分身来弄个3菜1汤吧");
    }

    private static void executePhaser2(Phaser phaser) {
        es.submit(new HouseholdJob(phaser, "炒青菜"));
        es.submit(new HouseholdJob(phaser, "红烧肉"));
        es.submit(new HouseholdJob(phaser, "油焖大虾"));
        es.submit(new HouseholdJob(phaser, "番茄鸡蛋汤"));
        phaser.arriveAndAwaitAdvance();
        peekPhaserState(phaser, "菜炒好了，看起来还不错，要喊老婆孩子出来吃饭了");
    }

    private static void executePhaser3(Phaser phaser) {
        es.submit(new HouseholdJob(phaser, "喊老婆出来吃饭"));
        es.submit(new HouseholdJob(phaser, "喊孩子出来吃饭"));
        phaser.arriveAndAwaitAdvance();
        peekPhaserState(phaser, "看看都吃完了没有，等会还得洗碗，我就是个工具人......");
    }

    private static void executePhaser4(Phaser phaser) {
        es.submit(new HouseholdJob(phaser, "洗碗"));
        phaser.arriveAndAwaitAdvance();
        peekPhaserState(phaser, "来看看洗的干不干净。");
    }

    public static void main(String[] args) throws NoSuchFieldException, InterruptedException {
        // 传入parties为1，表示注册当前线程，当前线程可作为协调线程参与工作。
        Phaser phaser = new Phaser(1) {
            @Override
            protected boolean onAdvance(int phase, int registeredParties) {
                long state = getStateValue(this, stateField);
                log.info("\033[33m第{}件事就绪，current phase = {}, registeredParties = {}.\033[0m{}",
                        phase + 1, phase, registeredParties, getFormattedPhaserStateOutput(
                                state, "parties都arrived，任务就绪待执行，phase会在onAdvance方法调用完自增。"));
                return super.onAdvance(phase, registeredParties);
            }
        };
        log.info("周末起床了，开始家务了......");
        peekPhaserState(phaser, "刚创建好一个Phaser，默认parties为1，表示我carry全场");
        executePhaser1(phaser);
        executePhaser2(phaser);
        executePhaser3(phaser);
        executePhaser4(phaser);
        phaser.arriveAndDeregister();
        // 完成所有工作了，老夫要休息了
        phaser.forceTermination();
        peekPhaserState(phaser, "哎呀，肚子叫了，居然饿醒了，看来最近火影看太多了啊～");
    }
}

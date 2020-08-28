package com.charles.akka;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.testkit.TestKit;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import scala.concurrent.duration.Duration;

import java.util.concurrent.TimeUnit;

import static com.charles.akka.Buncher.Flush.Flush;

public class BuncherTest {

    static ActorSystem system;

    @BeforeClass
    public static void setup() {
        system = ActorSystem.create("BuncherTest");
    }

    @AfterClass
    public static void tearDown() {
        TestKit.shutdownActorSystem(system,
                Duration.create(1, TimeUnit.SECONDS), false);
        system = null;
    }

    @Test
    public void testBuncherActorBatchesCorrectly() {
        new TestKit(system) {{
            final ActorRef buncher = system.actorOf(Props.create(Buncher.class));
            // final ActorRef probe = getRef();
            final ActorRef probe = testActor();
            buncher.tell(new Buncher.SetTarget(probe), probe);
            System.out.println("Target Set");
            buncher.tell(new Buncher.Queue(42), probe);
            System.out.println("First Queue sent");
            buncher.tell(new Buncher.Queue(43), probe);
            System.out.println("Second Queue sent");
            expectMsgClass(Buncher.Batch.class);
            // LinkedList<Object> list1 = new LinkedList<>();
            // list1.add(42);
            // list1.add(43);

            // Assert.assertEquals(list1, buncher.);
            // expectMsgAllOf(new Buncher.Batch(list1));
            // expectMsgEquals(new Buncher.Batch(list1));
            buncher.tell(new Buncher.Queue(44), probe);
            buncher.tell(Flush, probe);
            buncher.tell(new Buncher.Queue(45), probe);
            // LinkedList<Object> list2 = new LinkedList<>();
            // list2.add(44);
            // expectMsg(new Buncher.Batch(list2));
            // LinkedList<Object> list3 = new LinkedList<>();
            // list3.add(45);
            // expectMsg(new Buncher.Batch(list3));
            // system.stop(buncher);
        }};
    }

    @Test
    public void testBuncherActorDoesntBatchUninitialized() {
        new TestKit(system) {{
            final ActorRef buncher = system.actorOf(Props.create(Buncher.class));
            final ActorRef probe = testActor();

            buncher.tell(new Buncher.Queue(42), probe);
            expectNoMessage();
            system.stop(buncher);
        }};
    }
}
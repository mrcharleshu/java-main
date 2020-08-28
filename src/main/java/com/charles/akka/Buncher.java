package com.charles.akka;

import akka.actor.AbstractFSM;
import akka.actor.ActorRef;
import akka.japi.pf.UnitMatch;

import java.time.Duration;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static com.charles.akka.Buncher.State.Active;
import static com.charles.akka.Buncher.State.Idle;

/**
 * https://cloud.tencent.com/developer/article/1435523
 * Akka 指南 之「FSM」
 */
public class Buncher extends AbstractFSM<Buncher.State, Buncher.Data> {
    {
        startWith(Idle, Uninitialized.Uninitialized);

        when(Idle, matchEvent(
                SetTarget.class,
                Uninitialized.class,
                (setTarget, uninitialized) -> {
                    log().info("When Idle, self:{} sender:{}", getSelf(), getSender());
                    return stay().using(new Todo(setTarget.getRef(), new LinkedList<>()));
                }));

        when(Active, Duration.ofSeconds(1L), matchEvent(
                Arrays.asList(Flush.class, StateTimeout()),
                Todo.class,
                (event, todo) -> {
                    log().info("When Active, self:{} sender:{}", getSelf(), getSender());
                    return goTo(Idle).using(todo.copy(new LinkedList<>()));
                }));

        whenUnhandled(matchEvent(
                Queue.class,
                Todo.class,
                (queue, todo) -> {
                    log().info("whenUnhandled [addElement] self:{} sender:{}", getSelf(), getSender());
                    return goTo(Active).using(todo.addElement(queue.getObj()));
                })
                .anyEvent((event, state) -> {
                    log().info("received unhandled request {} in state {}/{}", event, stateName(), state);
                    return stay();
                }));

        onTransition(
                matchState(Active, Idle, () -> {
                    // reuse this matcher
                    final UnitMatch<Data> m = UnitMatch.create(
                            matchData(Todo.class, todo -> {
                                        log().info("Active to Idle");
                                        log().info("----------------------------------------------------------------------");
                                        todo.getTarget().tell(new Batch(todo.getQueue()), getSelf());
                                    }
                            ));
                    m.match(stateData());
                }).state(Idle, Active, () -> {
                    log().info("Idle to Active, self:{} sender:{}", getSelf(), getSender());
                })
        );

        initialize();
    }

    static final class SetTarget {
        private final ActorRef ref;

        public SetTarget(ActorRef ref) {
            this.ref = ref;
        }

        public ActorRef getRef() {
            return ref;
        }

        @Override
        public String toString() {
            return "SetTarget{" + "ref=" + ref + '}';
        }
    }

    static final class Queue {
        private final Object obj;

        public Queue(Object obj) {
            this.obj = obj;
        }

        public Object getObj() {
            return obj;
        }

        @Override
        public String toString() {
            return "Queue{" + "obj=" + obj + '}';
        }
    }

    static final class Batch {
        private final List<Object> list;

        public Batch(List<Object> list) {
            this.list = list;
        }

        public List<Object> getList() {
            return list;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Batch batch = (Batch) o;

            return list.equals(batch.list);
        }

        @Override
        public int hashCode() {
            return list.hashCode();
        }

        @Override
        public String toString() {
            final StringBuilder builder = new StringBuilder();
            builder.append("Batch{list=");
            list.stream().forEachOrdered(e -> {
                builder.append(e);
                builder.append(",");
            });
            int len = builder.length();
            builder.replace(len, len, "}");
            return builder.toString();
        }
    }

    enum Flush {
        Flush
    }

    // states
    enum State {
        Idle,
        Active
    }

    /******************************************** data ********************************************/

    // state data
    interface Data {
    }

    enum Uninitialized implements Data {
        Uninitialized
    }

    static final class Todo implements Data {
        private final ActorRef target;
        private final List<Object> queue;

        public Todo(ActorRef target, List<Object> queue) {
            this.target = target;
            this.queue = queue;
        }

        public ActorRef getTarget() {
            return target;
        }

        public List<Object> getQueue() {
            return queue;
        }

        @Override
        public String toString() {
            return "Todo{" + "target=" + target + ", queue=" + queue + '}';
        }

        public Todo addElement(Object element) {
            List<Object> nQueue = new LinkedList<>(queue);
            nQueue.add(element);
            return new Todo(this.target, nQueue);
        }

        public Todo copy(List<Object> queue) {
            System.out.println("BeforeCopy_queue:" + this.toString());
            return new Todo(this.target, queue);
        }

        public Todo copy(ActorRef target) {
            System.out.println("BeforeCopy_target:" + this.toString());
            return new Todo(target, this.queue);
        }
    }
}


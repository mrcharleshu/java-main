package com.charles.rx2;

import io.reactivex.*;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.concurrent.TimeUnit;

/**
 * @author Charles
 */
@Slf4j
public class FlowableVsObservable {

    public static void main(String[] args) throws InterruptedException {
        // observableBackpressure();
        flowableBackpressure();
        TimeUnit.HOURS.sleep(1L);
        // startup1();
        // 先执行onSubscribe,再执行onNext和onComplete
        // startup2();
        // Flowable.fromPublisher(handler.handle(ctx, event)).doOnNext(o -> eventStream.publish(o)).subscribe();
        // Flowable.fromPublisher(handler.handle(ctx, event)).subscribe(o -> eventStream.publish(o));
        // Flowable flowable = Flowable.create(new FlowableOnSubscribe<Object>() {
        //     @Override
        //     public void subscribe(@NonNull FlowableEmitter<Object> emitter) throws Exception {
        //         emitter.onNext("Hello world");
        //     }
        // }, BackpressureStrategy.BUFFER);
        // Flowable flowable2 = Flowable.fromPublisher(new Flowable<Object>() {
        //     @Override
        //     protected void subscribeActual(Subscriber<? super Object> s) {
        //         s.onNext("Hello world");
        //     }
        // });
        // flowable.doOnNext((Consumer<Object>) System.out::println).subscribe();
        // flowable.subscribe(System.out::println);
        // // Flowable.just("Hello world").subscribe(System.out::println);
        // // syncOperation();
        // Observable.interval(1, TimeUnit.MILLISECONDS)
        //         .observeOn(Schedulers.newThread())
        //         .subscribe(
        //                 i -> {
        //                     System.out.println("subscribe value: " + i);
        //                     try {
        //                         Thread.sleep(100);
        //                     } catch (Exception e) {
        //                     }
        //                 },
        //                 new Consumer<Throwable>() {
        //                     @Override
        //                     public void accept(Throwable throwable) throws Exception {
        //                         System.out.println("Error: " + throwable.getMessage());
        //                     }
        //                 },
        //                 new Action() {
        //                     @Override
        //                     public void run() throws Exception {
        //                         System.out.println("On complete");
        //                     }
        //                 });
    }

    private static void syncOperation() {
        Observable<Integer> producer = Observable.create(o -> {
            o.onNext(1);
            o.onNext(2);
            o.onComplete();
        });
        producer.subscribe(i -> {
            try {
                Thread.sleep(1000);
                System.out.println(i);
            } catch (Exception e) {
            }
        });
    }

    private static void startup1() {
        Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Object> emitter) throws Exception {
                emitter.onNext("Hello world");
            }
        }).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                System.out.println(o);
            }
        });
        Observable.just("Hello world").subscribe(System.out::println);
    }

    private static void startup2() {
        Observable.just("Hello world").subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                System.out.println(s);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                System.out.println("Error:" + throwable.getMessage());
            }
        }, new Action() {
            @Override
            public void run() throws Exception {
                System.out.println("On complete");
            }
        }, new Consumer<Disposable>() {
            @Override
            public void accept(Disposable disposable) throws Exception {
                System.out.println("subscribe: " + disposable);
            }
        });

    }

    public static void observableBackpressure() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                int i = 0;
                while (true) {
                    Thread.sleep(5);
                    i++;
                    e.onNext(i);
                    log.info("每5ms发送一次数据：" + i);
                }
            }
        }).subscribeOn(Schedulers.newThread())//使被观察者存在独立的线程执行
                .observeOn(Schedulers.newThread())//使观察者存在独立的线程执行
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Thread.sleep(5000);
                        log.info("每5000ms接收一次数据：" + integer);
                    }
                });
    }

    public static void flowableBackpressure() {
        Flowable.create(new FlowableOnSubscribe<Integer>() {
            @Override
            public void subscribe(FlowableEmitter<Integer> e) throws Exception {
                for (int j = 0; j <= 150; j++) {
                    e.onNext(j);
                    log.info(" 发送数据：" + j);
                    Thread.sleep(50);
                }
            }
        }, BackpressureStrategy.ERROR)
                .subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.newThread())
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                        s.request(Long.MAX_VALUE); //观察者设置接收事件的数量,如果不设置接收不到事件
                    }

                    @Override
                    public void onNext(Integer integer) {
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        log.info("onNext : " + (integer));
                    }

                    @Override
                    public void onError(Throwable t) {
                        log.info("onError : " + t.toString());
                    }

                    @Override
                    public void onComplete() {
                        log.info("onComplete");
                    }
                });
    }

}

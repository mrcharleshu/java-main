package com.charles.rx2;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

/**
 * @author Charles
 */
public class FlowableVsObservable {

    public static void main(String[] args) {
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

}

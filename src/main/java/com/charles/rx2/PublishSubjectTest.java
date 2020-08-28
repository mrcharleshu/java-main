package com.charles.rx2;

import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.PublishSubject;
import scala.Tuple2;

public class PublishSubjectTest {

    public static void main(String[] args) {
        Tuple2<PublishSubject<String>, Disposable> tuple2 = getTuple();
        tuple2._1().onNext("CHARLES");
    }

    private static Tuple2<PublishSubject<String>, Disposable> getTuple() {
        PublishSubject<String> flow = PublishSubject.create();
        Disposable disposable = flow
                .map(v -> v.toLowerCase())
                .doOnNext(v -> System.out.println("doOnNext:" + v))
                .doOnError(v -> System.out.println("doOnError:" + v))
                .subscribe(v -> System.out.println(v));
        return Tuple2.apply(flow, disposable);
    }
}

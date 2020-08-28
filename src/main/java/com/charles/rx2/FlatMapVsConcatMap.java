package com.charles.rx2;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;

public class FlatMapVsConcatMap {
    public static void main(String[] args) {
        List<String> resultList = new CopyOnWriteArrayList<>();
        Observable.create((ObservableOnSubscribe<String>) emitter -> {
            for (int i = 1; i < 6; i++) {
                emitter.onNext(String.valueOf(i));
            }
            emitter.onComplete();
            // }).flatMap(s -> {
        }).concatMap(s -> {
            StringBuilder tail = new StringBuilder();
            List<String> list = new ArrayList<>();
            for (int i = 0; i < 3; i++) {
                list.add(s + tail);
                tail.append("0");
            }
            // return Observable.fromIterable(list);
            return Observable.fromIterable(list).delay(100, TimeUnit.MILLISECONDS);
        }).doOnComplete(() -> {
            System.out.println("Result: " + resultList.toString());
        }).subscribe((s) -> {
            System.out.println("Subscribe: " + s);
            resultList.add(s);
        });
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}

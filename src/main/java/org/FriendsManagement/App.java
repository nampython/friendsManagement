package org.FriendsManagement;

import org.reactivestreams.Publisher;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import rx.Observable;
import rx.Subscriber;

//@SpringBootApplication
public class App {
    public static void main(String[] args) {
//        SpringApplication.run(App.class, args);
        Observable.just("1", "2", "3", "4").subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {
                System.out.println("Done");
                // After emit all data
            }

            @Override
            public void onError(Throwable e) {
                // Error has occurred here
            }

            @Override
            public void onNext(String s) {
                // Data will be emitted here 4 times
            }
        });
    }
}
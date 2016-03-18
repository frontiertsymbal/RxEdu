package net.mobindustry.rxedu;

import android.content.Context;
import android.util.Log;

import net.mobindustry.rxedu.ui.dialog.ProgressDialog;
import net.mobindustry.rxedu.utils.DialogHelper;

import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.functions.Func3;
import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;

public class Rx {

    private static final String TAG = Rx.class.getSimpleName();
    private Integer[] array = {1, 2, 3, 3, 3, 3, 4, 5, 6, 6, 6, 7, 2, 3, 4, 4, 3};

    public static <T> Observable.Transformer<T, T> showProgressDialogAndApplySchedulers(Context context, ProgressDialog dialog) {
        return observable -> observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(() -> DialogHelper.showProgressDialog(context, dialog))
                .doOnCompleted(() -> DialogHelper.dismissProgressDialog(context))
                .doOnError(e -> DialogHelper.dismissProgressDialog(context));
    }

    /*********************************************************************************
     * \
     * *
     * *
     * RRRRRRRRRRRRRRRRRR              XXXXXX                 XXXXXX          *
     * RRRRRRRRRRRRRRRRRRRRRR           XXXXXX               XXXXXX           *
     * RRRRRR           RRRRRRR          XXXXXX             XXXXXX            *
     * RRRRR             RRRRRRR          XXXXXX           XXXXXX             *
     * RRRRR              RRRRRR           XXXXXX         XXXXXX              *
     * RRRRR             RRRRRRR            XXXXXX       XXXXXX               *
     * RRRRRR           RRRRRRR              XXXXXX     XXXXXX                *
     * RRRRRRRRRRRRRRRRRRRRRR                 XXXXXX   XXXXXX                 *
     * RRRRRRRRRRRRRRRRRR                      XXXXXX XXXXXX                  *
     * RRRRRRRRRRRRRRR                          XXXXXXXXXXX                   *
     * RRRRRR    RRRRRR                          XXXXXXXXX                    *
     * RRRRRR     RRRRRR                        XXXXXXXXXXX                   *
     * RRRRRR      RRRRRR                      XXXXXX XXXXXX                  *
     * RRRRRR       RRRRRR                    XXXXXX   XXXXXX                 *
     * RRRRRR        RRRRRR                  XXXXXX     XXXXXX                *
     * RRRRRR         RRRRRR                XXXXXX       XXXXXX               *
     * RRRRRR          RRRRRR              XXXXXX         XXXXXX              *
     * RRRRRR           RRRRRR            XXXXXX           XXXXXX             *
     * RRRRRR            RRRRRR          XXXXXX             XXXXXX            *
     * RRRRRR             RRRRRR        XXXXXX               XXXXXX           *
     * *
     * *
     * \
     *********************************************************************************/

    private void helloWorld() {

        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                try {
                    for (int i = 0; i < 10; i++) {
                        subscriber.onNext("Hello World" + i);
                    }
                    subscriber.onNext("Hello World");
                    subscriber.onCompleted();
                } catch (Throwable e) {
                    subscriber.onError(e);
                }
            }
        }).subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {
                Log.i(TAG, "onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "onError: ", e);

            }

            @Override
            public void onNext(String s) {
                System.out.println(s);
            }
        });

    }

    private void hellowWorldJust() {

        // Лямбда
        Observable.create(subscriber -> {
            try {
                subscriber.onNext("Hello World");
                subscriber.onCompleted();
            } catch (Throwable e) {
                subscriber.onError(e);
            }
        }).subscribe(
                System.out::println,
                e -> Log.e(TAG, "onError: ", e),
                () -> Log.i(TAG, "onCompleted"));

        // или используя Observable.just();

        // В этом примере игнорируются onComplete() и onError() - не лучшая идея.
        // Если в just() вылетит ошибка - приложение упадет, т. к. никто не обрабатывает ошибку.
        Observable.just("Hello World").subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {

            }
        });
        Observable.just("Hello World", "Hello World", "Hello World").subscribe(System.out::println);

        // Также используется преобразования Java объектов в Observable
        Observable.just(getHelloWorldString()).subscribe(System.out::println);

    }

    // Observable.empty(), Observable.throw() и Observable.never() - используются чаще всего для имитации данных в тестах.

    private String getHelloWorldString() {
        return "Hello World";
    }

    private void helloWorldFromArrayOrList() {

        String[] helloWorld = {"Hello", "World"};

        Observable.from(helloWorld).subscribe(System.out::println);
        //В результате получим 2 строки
        // Hello
        // World

    }

    private void publishSubject() {
        PublishSubject<String> stringPublishSubject = PublishSubject.create();
        Subscription subscriptionPrint = stringPublishSubject.subscribe(
                System.out::println,
                e -> System.out.println("Oh no! Something wrong happened!"),
                () -> System.out.println("Observable completed")
        );

        stringPublishSubject.onNext("Hello World");
    }

    private void subjects() {
        // http://reactivex.io/RxJava/javadoc/rx/subjects/PublishSubject.html

        // http://reactivex.io/RxJava/javadoc/rx/subjects/BehaviorSubject.html

        // http://reactivex.io/RxJava/javadoc/rx/subjects/AsyncSubject.html

        // http://reactivex.io/RxJava/javadoc/rx/subjects/ReplaySubject.html

        // http://reactivex.io/RxJava/javadoc/rx/subjects/SerializedSubject.html
    }

    private void emitOperators() {
        Observable.range(0, 10).subscribe(integer -> System.out.println(String.valueOf(integer)));
        Observable.interval(10, TimeUnit.SECONDS).subscribe(aLong -> System.out.println(String.valueOf(aLong)));
        Observable.timer(10, TimeUnit.SECONDS).subscribe(aLong -> System.out.println(String.valueOf(aLong)));
    }

    private void filteringOperators() {
        Observable.interval(1, TimeUnit.SECONDS)
                .filter(l -> l % 3 == 0)
                .filter(new Func1<Long, Boolean>() {
                    @Override
                    public Boolean call(Long aLong) {
                        return null;
                    }
                })
                .take(3)
                .subscribe(integer -> System.out.println(String.valueOf(integer)));

        Observable.interval(1, TimeUnit.SECONDS)
                .filter(i -> i % 3 == 0)
                .take(10)
                .repeat(3) //
                .skip(3)
                .takeLast(2)
                .skipLast(1)
                .elementAt(0)
                .first()
                .last()
                .ignoreElements()
                .subscribe(integer -> System.out.println(String.valueOf(integer)));

        Observable.from(array).distinct().subscribe(integer -> System.out.println(String.valueOf(integer)));

        Observable.from(array).distinctUntilChanged().subscribe(integer -> System.out.println(String.valueOf(integer))); // 1 2 3 4 5 6 7 2 3 4 3

        Observable
                .merge(Observable.interval(100, TimeUnit.MILLISECONDS), Observable.interval(200, TimeUnit.MILLISECONDS))
                .debounce(100, TimeUnit.MILLISECONDS)
                .subscribe(integer -> System.out.println(String.valueOf(integer)));

        Observable<String> stringEmits = Observable.from(array).map(String::valueOf);
        stringEmits.sample(250, TimeUnit.MILLISECONDS).subscribe();

        stringEmits.timeout(3, TimeUnit.SECONDS).subscribe();

    }

    private void transformingObservables() {

        Observable
                .from(array)
                .map(integer -> integer * 10)
                .flatMap(integer -> Observable.just(integer.toString())) // and other *map - concatMap, switchMap ect.
                .groupBy(String::hashCode)
                .buffer(10)
                .window(3)
                .cast(String.class);

        Observable.just(1, 2, 3, 4, 5)
                .scan((sum, item) -> sum + item)
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onNext(Integer item) {
                        Log.d("RXJAVA", "item is: " + item);
                    }

                    @Override
                    public void onError(Throwable error) {
                        Log.e("RXJAVA", "Something went south!");
                    }

                    @Override
                    public void onCompleted() {
                        Log.d("RXJAVA", "Sequence completed.");
                    }
                });
        /*
            item is: 1
            item is: 3
            item is: 6
            item is: 10
            item is: 15
         */

    }

    private void combiningObservable() {

        Observable.merge(Observable.from(array), Observable.from(array), Observable.from(array));
        Observable.mergeDelayError(Observable.from(array), Observable.from(array), Observable.from(array));

        Observable.zip(Observable.from(array), Observable.from(array), Observable.from(array), new Func3<Integer, Integer, Integer, String>() {
            @Override
            public String call(Integer integer, Integer integer2, Integer integer3) {
                return String.valueOf(integer + " " + integer2 + " " + integer3);
            }
        });

        Observable.from(array).startWith(Observable.just(10000));

        Observable.combineLatest(Observable.from(array).toList(), Observable.from(array).toList(), new Func2<List<Integer>, List<Integer>, String>() {
            @Override
            public String call(List<Integer> integers, List<Integer> integers2) {
                return integers.toString() + integers2.toString();
            }
        });

    }

    private void schedulers() {

        Observable
                .from(array)
                .compose(showProgressDialogAndApplySchedulers(null, ProgressDialog.newInstance("Some dialog"))) //Context = null!!!
                .filter(i -> i % 3 == 0)
                .subscribeOn(Schedulers.io())
                .flatMap(integer -> Observable.just(integer.hashCode()))
                .take(5)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Integer integer) {
                        // TODO AlexTsymbal: add item to adapter todo
                    }
                });

        /*
                .io()
                .computation()
                .immediate()
                .newThread()
                .trampoline()
         */
    }

}

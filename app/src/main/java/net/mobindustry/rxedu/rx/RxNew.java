package net.mobindustry.rxedu.rx;

import android.content.Context;
import android.util.Log;

import net.mobindustry.rxedu.ui.dialog.ProgressDialog;
import net.mobindustry.rxedu.utils.DialogHelper;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class RxNew {

    private static final String TAG = "RxNew";

    public static <T> Observable.Transformer<T, T> showProgressDialogAndApplySchedulers(Context context, ProgressDialog dialog) {
        return observable -> observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(() -> DialogHelper.showProgressDialog(context, dialog))
                .doOnCompleted(() -> DialogHelper.dismissProgressDialog(context))
                .doOnError(e -> DialogHelper.dismissProgressDialog(context));
    }

    public void edu() {

        Observable<String> fullObservable = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                // some action
                try {
                    for (int i = 0; i < 10; i++) {
                        subscriber.onNext("On next " + i);
                    }
                } catch (Exception e) {
                    subscriber.onError(e);
                }
                subscriber.onCompleted();
            }
        });

        Subscriber<String> fullSubscriber = new Subscriber<String>() {
            @Override
            public void onCompleted() {
                Log.e(TAG, "onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "onError: ", e);
            }

            @Override
            public void onNext(String s) {
                Log.e(TAG, "onNext: " + s);
            }
        };

        Subscription subscription = fullObservable.subscribe(fullSubscriber);

        // onPause() do
        if (!subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }

        // run in different thread

        Subscription subscriptionInThreads = fullObservable
                .subscribeOn(Schedulers.io()) // all hard work do on background thread
                .observeOn(AndroidSchedulers.mainThread()) // return result to main android thread (UI)
                .subscribe(fullSubscriber);

        Observable<String> lambdaObservable = Observable.create(s -> {
            // some action
            try {
                for (int i = 0; i < 10; i++) {
                    s.onNext("On next " + i);
                }
            } catch (Exception e) {
                s.onError(e);
            }
            s.onCompleted();
        });

        // combine result

        lambdaObservable.subscribe(
                s -> Log.e(TAG, "onNext: " + s), // onNext
                e -> Log.e(TAG, "onError: ", e), // onError
                () -> Log.e(TAG, "onCompleted") // onComplete
        );

        // or

        lambdaObservable.subscribe(
                s -> { // onNext
                    Log.e(TAG, "onNext: " + s);
                    //do something
                },
                e -> { // onError
                    Log.e(TAG, "onError: ", e);
                    //do something
                },
                () -> { // onComplete
                    Log.e(TAG, "onCompleted");
                    //do something
                }
        );
    }

    /*

    .flatMap(new Func1<String, Observable<?>>() {
            @Override
            public Observable<?> call(String s) {
                return null;
            }
        }).map(new Func1<Object, Object>() {
            @Override
            public Object call(Object o) {
                return null;
            }
        })

     */
//    {
//        RxView.clicks(getPostListButton).subscribe(click -> {
//            resultAdapter.clear();
//
//            apiManager.getUsers()
//                    .subscribeOn(Schedulers.io())
//                    .subscribe(new Subscriber<User>() {
//                        @Override
//                        public void onCompleted() {
//                            Log.e(TAG, "onCompleted");
//                        }
//
//                        @Override
//                        public void onError(Throwable e) {
//                            Log.e(TAG, "onError ", e);
//                        }
//
//                        @Override
//                        public void onNext(User user) {
//                            Observable.zip(apiManager.getPosts(user.getUserId()), apiManager.getAlbums(user.getUserId()), apiManager.getTodos(user.getUserId()), new Func3<Post, Album, Todo, User>() {
//                                @Override
//                                public User call(Post post, Album album, Todo todo) {
//                                    if (user.getPostList() == null) {
//                                        user.setPostList(new ArrayList<>());
//                                    }
//                                    if (user.getAlbumList() == null) {
//                                        user.setAlbumList(new ArrayList<>());
//                                    }
//                                    if (user.getTodoList() == null) {
//                                        user.setTodoList(new ArrayList<>());
//                                    }
//
//                                    apiManager.getPhotos(album.getUserId()).subscribe(new Subscriber<Photo>() {
//                                        @Override
//                                        public void onCompleted() {
//                                            Log.e(TAG, "onCompleted");
//                                        }
//
//                                        @Override
//                                        public void onError(Throwable e) {
//                                            Log.e(TAG, "onError ", e);
//                                        }
//
//                                        @Override
//                                        public void onNext(Photo photo) {
//                                            if (album.getPhotoList() == null) {
//                                                album.setPhotoList(new ArrayList<>());
//                                            }
//                                            album.getPhotoList().add(photo);
//                                        }
//                                    });
//
//                                    apiManager.getComments(post.getUserId()).subscribe(new Subscriber<Comment>() {
//                                        @Override
//                                        public void onCompleted() {
//                                            Log.e(TAG, "onCompleted");
//                                        }
//
//                                        @Override
//                                        public void onError(Throwable e) {
//                                            Log.e(TAG, "onError ", e);
//                                        }
//
//                                        @Override
//                                        public void onNext(Comment comment) {
//                                            if (post.getCommentList() == null) {
//                                                post.setCommentList(new ArrayList<>());
//                                            }
//                                            post.getCommentList().add(comment);
//                                        }
//                                    });
//
//                                    user.getPostList().add(post);
//                                    user.getAlbumList().add(album);
//                                    user.getTodoList().add(todo);
//
//                                    return user;
//                                }
//                            })
//                                    .distinct(User::getUserId)
//                                    .subscribeOn(Schedulers.io())
//                                    .observeOn(AndroidSchedulers.mainThread())
//                                    .subscribe(new Subscriber<User>() {
//                                        @Override
//                                        public void onCompleted() {
//                                            Log.e(TAG, "onCompleted");
//                                            for (User user : usersList) {
//                                                showResult(user.toString());
//                                            }
//                                        }
//
//                                        @Override
//                                        public void onError(Throwable e) {
//                                            Log.e(TAG, "onError ", e);
//                                        }
//
//                                        @Override
//                                        public void onNext(User user) {
//                                            usersList.add(user);
//                                        }
//                                    });
//                        }
//                    });
//        });
//    }


    /*********************************************************************************\
     *                                                                                 *
     *                                                                                 *
     *          RRRRRRRRRRRRRRRRRR              XXXXXX                 XXXXXX          *
     *          RRRRRRRRRRRRRRRRRRRRRR           XXXXXX               XXXXXX           *
     *          RRRRRR           RRRRRRR          XXXXXX             XXXXXX            *
     *          RRRRR             RRRRRRR          XXXXXX           XXXXXX             *
     *          RRRRR              RRRRRR           XXXXXX         XXXXXX              *
     *          RRRRR             RRRRRRR            XXXXXX       XXXXXX               *
     *          RRRRRR           RRRRRRR              XXXXXX     XXXXXX                *
     *          RRRRRRRRRRRRRRRRRRRRRR                 XXXXXX   XXXXXX                 *
     *          RRRRRRRRRRRRRRRRRR                      XXXXXX XXXXXX                  *
     *          RRRRRRRRRRRRRRR                          XXXXXXXXXXX                   *
     *          RRRRRR    RRRRRR                          XXXXXXXXX                    *
     *          RRRRRR     RRRRRR                        XXXXXXXXXXX                   *
     *          RRRRRR      RRRRRR                      XXXXXX XXXXXX                  *
     *          RRRRRR       RRRRRR                    XXXXXX   XXXXXX                 *
     *          RRRRRR        RRRRRR                  XXXXXX     XXXXXX                *
     *          RRRRRR         RRRRRR                XXXXXX       XXXXXX               *
     *          RRRRRR          RRRRRR              XXXXXX         XXXXXX              *
     *          RRRRRR           RRRRRR            XXXXXX           XXXXXX             *
     *          RRRRRR            RRRRRR          XXXXXX             XXXXXX            *
     *          RRRRRR             RRRRRR        XXXXXX               XXXXXX           *
     *                                                                                 *
     *                                                                                 *
     \*********************************************************************************/
}

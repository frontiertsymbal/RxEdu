package net.mobindustry.rxedu;

import android.util.Log;

import com.jakewharton.rxbinding.view.RxView;

import net.mobindustry.rxedu.model.Album;
import net.mobindustry.rxedu.model.Comment;
import net.mobindustry.rxedu.model.Photo;
import net.mobindustry.rxedu.model.Post;
import net.mobindustry.rxedu.model.Todo;
import net.mobindustry.rxedu.model.User;

import java.util.ArrayList;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func3;
import rx.schedulers.Schedulers;

public class RxNew {

    private <T> Observable.Transformer<T, T> applyScheduler() {
        return observable -> observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public void newTest() {
        Observable.just("Some text")
                .compose(applyScheduler())
                .subscribe(System.out::println);

    }

    public void newTest1() {
        Observable.just("111").subscribe(
                s -> Log.e("TAG", "onNext: " + s),
                e -> Log.e("TAG", "onError: ", e),
                () -> Log.e("TAG", "onCompleted: ")
        );

        Observable.just("111").subscribe(
                this::showMessage,
                this::showError,
                this::showMessage
        );

        Observable.just("111").subscribe(
                s -> Log.e("TAG", "onNext: " + s),
                e -> Log.e("TAG", "onError: ", e)
        );

        Observable.just("111").subscribe(
                s -> Log.e("TAG", "onNext: " + s)
        );

        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                try {
                    subscriber.onNext("111");
                } catch (Exception e) {
                    subscriber.onError(e);
                }
                subscriber.onCompleted();
            }
        }).subscribe(new Subscriber<String>() {
                         @Override
                         public void onCompleted() {
                             Log.e("TAG", "onCompleted: ");
                         }

                         @Override
                         public void onError(Throwable e) {
                             Log.e("TAG", "onError: ", e);
                         }

                         @Override
                         public void onNext(String s) {
                             Log.e("TAG", "onNext: " + s);
                         }
                     }

        );
    }

    private void showMessage(String message) {
        Log.e("TAG", message);
    }

    private void showError(Throwable throwable) {
        Log.e("TAG", "Error ", throwable);
    }

    private void showMessage() {
        Log.e("TAG", "onCompleted");
    }

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
//                            Observable.zip(apiManager.getPosts(user.getId()), apiManager.getAlbums(user.getId()), apiManager.getTodos(user.getId()), new Func3<Post, Album, Todo, User>() {
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
//                                    apiManager.getPhotos(album.getId()).subscribe(new Subscriber<Photo>() {
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
//                                    apiManager.getComments(post.getId()).subscribe(new Subscriber<Comment>() {
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
//                                    .distinct(User::getId)
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
}

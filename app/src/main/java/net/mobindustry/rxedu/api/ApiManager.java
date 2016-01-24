package net.mobindustry.rxedu.api;

import com.squareup.okhttp.OkHttpClient;

import net.mobindustry.rxedu.model.Album;
import net.mobindustry.rxedu.model.Comment;
import net.mobindustry.rxedu.model.Photo;
import net.mobindustry.rxedu.model.Post;
import net.mobindustry.rxedu.model.Todo;
import net.mobindustry.rxedu.model.User;

import java.util.concurrent.TimeUnit;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;
import rx.Observable;

public class ApiManager {

    private static final String URL = "http://jsonplaceholder.typicode.com";

    private static ApiManager singleton;
    private static ApiInterface mService;
    private static final int TIMEOUT = 60;

    public synchronized static ApiManager getInstance() {
        if (null == singleton) {
            singleton = new ApiManager();

            OkHttpClient okHttpClient = new OkHttpClient();
            okHttpClient.setConnectTimeout(TIMEOUT, TimeUnit.SECONDS);
            okHttpClient.setReadTimeout(TIMEOUT, TimeUnit.SECONDS);

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(URL)
                    .client(okHttpClient)
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            retrofit.client().setConnectTimeout(60, TimeUnit.SECONDS);

            mService = retrofit.create(ApiInterface.class);
        }
        return singleton;
    }

    public Observable<User> getUsers() {
        return mService.getUserList()
                .flatMap(Observable::from);
    }

    public Observable<Post> getPosts(int userId) {
        return mService.getPostList(userId)
                .flatMap(Observable::from);
    }

    public Observable<Comment> getComments(int postId) {
        return mService.getCommentList(postId)
                .flatMap(Observable::from);
    }

    public Observable<Todo> getTodos(int userId) {
        return mService.getTodoList(userId)
                .flatMap(Observable::from);
    }

    public Observable<Album> getAlbums(int userId) {
        return mService.getAlbumList(userId)
                .flatMap(Observable::from);
    }

    public Observable<Photo> getPhotos(int albumId) {
        return mService.getPhotoList(albumId)
                .flatMap(Observable::from);
    }
}

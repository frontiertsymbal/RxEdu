package net.mobindustry.rxedu.api;

import net.mobindustry.rxedu.model.Album;
import net.mobindustry.rxedu.model.Comment;
import net.mobindustry.rxedu.model.Photo;
import net.mobindustry.rxedu.model.Post;
import net.mobindustry.rxedu.model.Todo;
import net.mobindustry.rxedu.model.User;

import java.util.ArrayList;
import java.util.List;

import retrofit.http.GET;
import retrofit.http.Query;
import rx.Observable;

public interface ApiInterface {

    @GET("users")
    Observable<ArrayList<User>> getUserList();

    @GET("posts")
    Observable<ArrayList<Post>> getPostList(
            @Query("userId") int userId
    );

    @GET("comments")
    Observable<ArrayList<Comment>> getCommentList(
            @Query("postId") int postId
    );

    @GET("todos")
    Observable<ArrayList<Todo>> getTodoList(
            @Query("userId") int userId
    );

    @GET("albums")
    Observable<ArrayList<Album>> getAlbumList(
            @Query("userId") int userId
    );

    @GET("photos")
    Observable<ArrayList<Photo>> getPhotoList(
            @Query("albumId") int albumId
    );

    /**
     * Request for test
     * @return 5000 elements with links
     */
    @GET("photos")
    Observable<ArrayList<Photo>> getAllPhotoList();
}

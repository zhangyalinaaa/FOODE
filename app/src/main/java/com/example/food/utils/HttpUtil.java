package com.example.food.utils;
 import android.accounts.NetworkErrorException;
 import android.os.RecoverySystem;
 import android.util.Log;

        import java.io.File;
        import java.util.concurrent.TimeUnit;

        import okhttp3.Callback;
        import okhttp3.MediaType;
        import okhttp3.MultipartBody;
        import okhttp3.OkHttpClient;
        import okhttp3.Request;
        import okhttp3.RequestBody;
        import okhttp3.Response;

public class HttpUtil {

//    private static OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(10000, TimeUnit.MILLISECONDS)
//            .readTimeout(10000, TimeUnit.MILLISECONDS)
//            .writeTimeout(10000, TimeUnit.MILLISECONDS).build();
//    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
//    public static final MediaType MEDIA_TYPE_MARKDOWN = MediaType.parse("text/x-markdown; charset=utf-8");
//
//    /**
//     * 上传视频
//     * @param url
//     * @param listener
//     * @param callback
//     * @param files
//     */
//    public static void postFile(String url, final RecoverySystem.ProgressListener listener, okhttp3.Callback callback, File... files) {
//
//        MultipartBody.Builder builder = new MultipartBody.Builder();
//        builder.setType(MultipartBody.FORM);
//        Log.i("huang", "files[0].getName()==" + files[0].getName());
//        //第一个参数要与Servlet中的一致
//        builder.addFormDataPart("myfile", files[0].getName(), RequestBody.create(MediaType.parse("application/octet-stream"), files[0]));
//
//        MultipartBody multipartBody = builder.build();
//
//        Request request = new Request.Builder().url(url).post(new ProgressRequestBody(multipartBody, listener)).build();
//
//        okHttpClient.newCall(request).enqueue(callback);
//    }
//
//    /**
//     * 上传图片
//     * @param url
//     * @param callback
//     * @param files
//     */
//    private static final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");
//    public static void postFileimg(String url, okhttp3.Callback callback, File... files) {
//        RequestBody fileBody = RequestBody.create(MEDIA_TYPE_PNG, files[0]);
//
//        RequestBody requestBody = new MultipartBody.Builder()
//                .setType(MultipartBody.FORM)
//                .addFormDataPart("ss",files[0].getName(),fileBody)
//                // .addFormDataPart("userName", userName)
//                .build();
//
//        Request request = new Request.Builder()
//                .url(url)
//                .post(requestBody)
//                .build();
//        okHttpClient.newCall(request).enqueue(callback);
//
//    }
//
//    /**
//     * 下载图片
//     * @param url
//     * @param imagePath 图片路径
//     * @return byte[]
//     */
//    public static byte[] downloadImage(String url, String imagePath) throws Exception {
//        OkHttpClient okHttpClient = new OkHttpClient();
//        Request request = new Request.Builder()
//                .url(url+"/show?fileName="+imagePath)
//                .build();
//        Response response = okHttpClient.newCall(request).execute();
//        byte[] bytes = response.body().bytes();
//        return bytes;
//    }
//




}

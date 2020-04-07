package main.master.c31.networknotificationtest;

;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.core.app.JobIntentService;
import androidx.core.app.NotificationCompat;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import main.master.c31.Network.UserService;
import main.master.c31.R;
import main.master.c31.UploadActivity.UploadActivityMain;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static main.master.c31.networknotificationtest.RetryJobReceiver.ACTION_CLEAR;
import static main.master.c31.networknotificationtest.RetryJobReceiver.ACTION_RETRY;

public class FileUploadService extends JobIntentService {
    private static final String TAG = "FileUploadService";
    UserService apiService;
    Disposable mDisposable;
    public static final int NOTIFICATION_ID = 1;
    public static final int NOTIFICATION_RETRY_ID = 2;
    /**
     * Unique job ID for this service.
     */
    private static final int JOB_ID = 102;
    private ArrayList<String> mFilePath;
    NotificationHelper mNotificationHelper;
    public static void enqueueWork(Context context, Intent intent) {
        enqueueWork(context, FileUploadService.class, JOB_ID, intent);
    }
    @Override
    public void onCreate() {
        super.onCreate();
        mNotificationHelper = new NotificationHelper(this);
    }
    @Override
    protected void onHandleWork(@NonNull Intent intent) {
        Log.d(TAG, "onHandleWork: ");
        /**
         * Download/Upload of file
         * The system or framework is already holding a wake lock for us at this point
         */
        // get file file here
        mFilePath = intent.getStringArrayListExtra("mFilePath");
        if (mFilePath == null) {
            Log.e(TAG, "onHandleWork: Invalid file URI");
            return;
        }
        apiService = RetrofitInstance.getApiService();
        Flowable<Double> fileObservable = Flowable.create(new FlowableOnSubscribe<Double>() {
            @Override
            public void subscribe(FlowableEmitter<Double> emitter) throws Exception {
            /*    apiService.Activity(
                        FileUploadService.this.createRequestBodyFromText("19"),
                        FileUploadService.this.createRequestBodyFromText("info@androidwav"),
                        FileUploadService.this.createRequestBodyFromText("info@androidwav"),
                        FileUploadService.this.createRequestBodyFromText("20-11-2020"),
                        FileUploadService.this.createRequestBodyFromText("19"),
                        FileUploadService.this.createMultipart(mFilePath, emitter));
                emitter.onComplete();
       */     }
        }, BackpressureStrategy.LATEST);
        mDisposable = fileObservable.subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Double>() {
                    @Override
                    public void accept(Double progress) throws Exception {
                        // call onProgress()
                        FileUploadService.this.onProgress(progress);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        // call onErrors() if error occurred during file upload
                        FileUploadService.this.onErrors(throwable);
                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {
                        // call onSuccess() while file upload successful
                        FileUploadService.this.onSuccess();
                    }
                });
    }
    private void onErrors(Throwable throwable) {
        /**
         * Error occurred in file uploading
         */
        Intent successIntent = new Intent("com.wave.ACTION_CLEAR_NOTIFICATION");
        successIntent.putExtra("notificationId", NOTIFICATION_ID);
        sendBroadcast(successIntent);
        PendingIntent resultPendingIntent = PendingIntent.getActivity(this,
                0 /* Request code */, new Intent(this, UploadActivityMain.class),
                PendingIntent.FLAG_UPDATE_CURRENT);
        /**
         * Add retry action button in notification
         */
        Intent retryIntent = new Intent(this, RetryJobReceiver.class);
        retryIntent.putExtra("notificationId", NOTIFICATION_RETRY_ID);
        retryIntent.putExtra("mFilePath", mFilePath);
        retryIntent.setAction(ACTION_RETRY);
        /**
         * Add clear action button in notification
         */
        Intent clearIntent = new Intent(this, RetryJobReceiver.class);
        clearIntent.putExtra("notificationId", NOTIFICATION_RETRY_ID);
        clearIntent.putExtra("mFilePath", mFilePath);
        clearIntent.setAction(ACTION_CLEAR);
        PendingIntent retryPendingIntent = PendingIntent.getBroadcast(this, 0, retryIntent, 0);
        PendingIntent clearPendingIntent = PendingIntent.getBroadcast(this, 0, clearIntent, 0);
        NotificationCompat.Builder mBuilder =
                mNotificationHelper.getNotification(getString(R.string.error_upload_failed),
                        getString(R.string.message_upload_failed), resultPendingIntent);
        // attached Retry action in notification
        mBuilder.addAction(android.R.drawable.ic_menu_revert, getString(R.string.btn_retry_not),
                retryPendingIntent);
        // attached Cancel action in notification
        mBuilder.addAction(android.R.drawable.ic_menu_revert, getString(R.string.btn_cancel_not),
                clearPendingIntent);
        // Notify notification
        mNotificationHelper.notify(NOTIFICATION_RETRY_ID, mBuilder);
    }
    /**
     * Send Broadcast to FileProgressReceiver with progress
     *
     * @param progress file uploading progress
     */
    private void onProgress(Double progress) {
        Intent progressIntent = new Intent(this, FileProgressReceiver.class);
        progressIntent.setAction("com.wave.ACTION_PROGRESS_NOTIFICATION");
        progressIntent.putExtra("notificationId", NOTIFICATION_ID);
        progressIntent.putExtra("progress", (int) (100 * progress));
        sendBroadcast(progressIntent);
    }
    /**
     * Send Broadcast to FileProgressReceiver while file upload successful
     */
    private void onSuccess() {
        Intent successIntent = new Intent(this, FileProgressReceiver.class);
        successIntent.setAction("com.wave.ACTION_UPLOADED");
        successIntent.putExtra("notificationId", NOTIFICATION_ID);
        successIntent.putExtra("progress", 100);
        sendBroadcast(successIntent);
    }
    private RequestBody createRequestBodyFromFile(File file, String mimeType) {
        return RequestBody.create(MediaType.parse(mimeType), file);
    }
    private RequestBody createRequestBodyFromText(String mText) {
        return RequestBody.create(MediaType.parse("text/plain"), mText);
    }
    /**
     * return multi part body in format of FlowableEmitter
     */
    private MultipartBody.Part[] createMultipart(List<String> imagePaths, FlowableEmitter<Double> e) {
        MultipartBody.Part[] partArrayList = new MultipartBody.Part[imagePaths.size()];
        for (int i=0;i<imagePaths.size();i++){
            File file = new File(imagePaths.get(i));
            partArrayList[i]= MultipartBody.Part.createFormData("files[]",file.getName(),createCountingRequestBody(file,"image/*",e));
        }
        return partArrayList;
    }

    private RequestBody createCountingRequestBody(File file, String mimeType, final FlowableEmitter<Double> emitter) {
        RequestBody requestBody = createRequestBodyFromFile(file, mimeType);
        return new CountingRequestBody(requestBody, new CountingRequestBody.Listener() {
            @Override
            public void onRequestProgress(long bytesWritten, long contentLength) {
                double progress = (1.0 * bytesWritten) / contentLength;
                emitter.onNext(progress);
            }
        });
    }


}

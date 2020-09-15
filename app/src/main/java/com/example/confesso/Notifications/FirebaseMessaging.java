package com.example.confesso.Notifications;

import android.app.NotificationChannel;
import android.app.NotificationChannelGroup;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.example.confesso.AnonymousCommentsActivity;
import com.example.confesso.CommentsActivity;
import com.example.confesso.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;


public class FirebaseMessaging extends FirebaseMessagingService {

    private static final String LIKE_CHANNEL_GROUP_ID = "like_channel_group";
    private static final String COMMENT_CHANNEL_GROUP_ID = "comment_channel_group";
    private static final String SHARE_CHANNEL_GROUP_ID = "share_channel_group";




    private static final String LIKE_CHANNEL_ID = "like_channel";
    private static final String ANONYMOUS_LIKE_CHANNEL_ID = "Anonymous_like_channel";
    private static final String COMMENT_CHANNEL_ID = "comment_channel";
    private static final String ANONYMOUS_COMMENT_CHANNEL_ID = "Anonymous_comment_channel";
    private static final String MENTION_CHANNEL_ID = "mention_channel";
    private static final String ANONYMOUS_MENTION_CHANNEL_ID = "Anonymous_mention_channel";
    private static final String SHARE_CHANNEL_ID = "share_channel";
    private static final String ANONYMOUS_SHARE_CHANNEL_ID = "Anonymous_share_channel";






    FirebaseUser firebaseUser;
    Bitmap myBitmap;
    // String pImage;



    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        firebaseUser =FirebaseAuth.getInstance().getCurrentUser();


            String notificationType = remoteMessage.getData().get("notificationType");

            if (notificationType.equals("AnonymousMentionNotification"))
            {
                String sender = remoteMessage.getData().get("sender");
                String pId = remoteMessage.getData().get("pId");
                String pTitle = remoteMessage.getData().get("pTitle");
                String hisUid = remoteMessage.getData().get("hisUid");
                String pDescription = remoteMessage.getData().get("pDescription");
                String pImage = remoteMessage.getData().get("pImage");

                if (!sender.equals(firebaseUser.getUid()))
                {

                    new generateAnonymousMentionNotification(this,""+pTitle, ""+pDescription,
                            ""+pImage,""+pId,""+hisUid,""+notificationType).execute();
                }
            }

            else {

                String sender = remoteMessage.getData().get("sender");
                String pId = remoteMessage.getData().get("pId");
                String pTitle = remoteMessage.getData().get("pTitle");
                String hisUid = remoteMessage.getData().get("hisUid");
                String pDescription = remoteMessage.getData().get("pDescription");
                String pImage = remoteMessage.getData().get("pImage");
                String senderImage = remoteMessage.getData().get("senderImage");


                if (!sender.equals(firebaseUser.getUid()))
                {
                    if(!senderImage.equals(""))
                    {
                        new generateLikeNotification(this,""+pTitle, ""+pDescription,
                                ""+pImage,""+pId,""+hisUid,""+senderImage,""+notificationType).execute();
                    }
                    else{
                        new generateLikeNotification1(this,""+pTitle, ""+pDescription,
                                ""+pImage,""+pId,""+hisUid,""+notificationType).execute();

                    }
                }

                }



    }
    public class generateLikeNotification extends AsyncTask<String, Void, ArrayList<Bitmap>> {

        private Context mContext;
        private String pTitle, pDescription, imageUrl, pId, hisUid,senderImage,notificationType;

        NotificationChannelGroup likegroup = new NotificationChannelGroup(LIKE_CHANNEL_GROUP_ID,"Likes");
        NotificationChannelGroup commentgroup = new NotificationChannelGroup(COMMENT_CHANNEL_GROUP_ID,"Comments");
        NotificationChannelGroup sharegroup = new NotificationChannelGroup(SHARE_CHANNEL_GROUP_ID,"Shares");


        public generateLikeNotification(Context mContext, String pTitle, String pDescription, String imageUrl, String pId, String hisUid, String senderImage, String notificationType) {
            this.mContext = mContext;
            this.pTitle = pTitle;
            this.pDescription = pDescription;
            this.imageUrl = imageUrl;
            this.pId = pId;
            this.hisUid = hisUid;
            this.senderImage = senderImage;
            this.notificationType = notificationType;
        }

        @Override
        protected ArrayList<Bitmap> doInBackground(String... params) {

            InputStream in;
            InputStream in1;
            try {
                URL url = new URL(this.senderImage);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                in = connection.getInputStream();
                Bitmap bitmap = BitmapFactory.decodeStream(in);

                Bitmap output;
                Rect srcRect, dstRect;
                float r;
                final int width = bitmap.getWidth();
                final int height = bitmap.getHeight();

                if (width > height){
                    output = Bitmap.createBitmap(height, height, Bitmap.Config.ARGB_8888);
                    int left = (width - height) / 2;
                    int right = left + height;
                    srcRect = new Rect(left, 0, right, height);
                    dstRect = new Rect(0, 0, height, height);
                    r = height / 2;
                }else{
                    output = Bitmap.createBitmap(width, width, Bitmap.Config.ARGB_8888);
                    int top = (height - width)/2;
                    int bottom = top + width;
                    srcRect = new Rect(0, top, width, bottom);
                    dstRect = new Rect(0, 0, width, width);
                    r = width / 2;
                }

                Canvas canvas = new Canvas(output);

                final int color = 0xff424242;
                final Paint paint = new Paint();

                paint.setAntiAlias(true);
                canvas.drawARGB(0, 0, 0, 0);
                paint.setColor(color);
                canvas.drawCircle(r, r, r, paint);
                paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
                canvas.drawBitmap(bitmap, srcRect, dstRect, paint);

                bitmap.recycle();

                URL url1 = new URL(this.imageUrl);
                HttpURLConnection connection1 = (HttpURLConnection) url1.openConnection();
                connection1.setDoInput(true);
                connection1.connect();
                in1 = connection1.getInputStream();
                Bitmap bitmap1 = BitmapFactory.decodeStream(in1);


                ArrayList<Bitmap> bitmapArray = new ArrayList<Bitmap>();
                bitmapArray.add(bitmap1);
                bitmapArray.add(output);// Add a bitmap

                return bitmapArray;
                //return myBitmap;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<Bitmap> bitArr) {
            super.onPostExecute(bitArr);

            NotificationManager notificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannelGroup(likegroup);
            notificationManager.createNotificationChannelGroup(commentgroup);
            notificationManager.createNotificationChannelGroup(sharegroup);



            if (notificationType.equals("LikeNotification"))
            {

                int notificationID = new Random().nextInt(3000);

                if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.O)
                {
                    setupLikeNotificationChannel(notificationManager);
                }

                Intent intent = new Intent(mContext, CommentsActivity.class);
                intent.putExtra("un",hisUid);
                intent.putExtra("postid",pId);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                PendingIntent pendingIntent = PendingIntent.getActivity(mContext,notificationID,intent,PendingIntent.FLAG_UPDATE_CURRENT);



                Uri notificationSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

                NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(mContext,""+LIKE_CHANNEL_ID)
                        .setSmallIcon(R.drawable.ic_froken2)
                        .setLargeIcon(bitArr.get(1))
                        .setContentTitle(pTitle)
                        .setContentText(pDescription)
                        .setColor(Color.BLUE)
                        .setStyle(new NotificationCompat.BigPictureStyle()
                                .bigPicture(bitArr.get(0))
                                .bigLargeIcon(null))

                        .setSound(notificationSoundUri)
                        .setContentIntent(pendingIntent)
                        .setAutoCancel(true);

                notificationManager.notify(notificationID,notificationBuilder.build());


            }


            else if (notificationType.equals("AnonymousLikeNotification"))
            {
                int notificationID = new Random().nextInt(3000);

                if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.O)
                {
                    setupAnonymousLikeNotificationChannel(notificationManager);
                }

                Intent intent = new Intent(mContext, AnonymousCommentsActivity.class);
                intent.putExtra("un",hisUid);
                intent.putExtra("postid",pId);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                PendingIntent pendingIntent = PendingIntent.getActivity(mContext,notificationID,intent,PendingIntent.FLAG_UPDATE_CURRENT);



                Uri notificationSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

                NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(mContext,""+ANONYMOUS_LIKE_CHANNEL_ID)
                        .setSmallIcon(R.drawable.ic_froken2)
                        .setLargeIcon(bitArr.get(1))
                        .setContentTitle(pTitle)
                        .setContentText(pDescription)
                        .setColor(Color.BLUE)
                        .setStyle(new NotificationCompat.BigPictureStyle()
                                .bigPicture(bitArr.get(0))
                                .bigLargeIcon(null))

                        .setSound(notificationSoundUri)
                        .setContentIntent(pendingIntent)
                        .setAutoCancel(true);

                notificationManager.notify(notificationID,notificationBuilder.build());



            }

            else if (notificationType.equals("CommentNotification"))
            {
                int notificationID = new Random().nextInt(3000);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    setupCommentNotificationChannel(notificationManager);
                }

                Intent intent = new Intent(mContext, CommentsActivity.class);
                intent.putExtra("un", hisUid);
                intent.putExtra("postid", pId);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                PendingIntent pendingIntent = PendingIntent.getActivity(mContext, notificationID, intent, PendingIntent.FLAG_UPDATE_CURRENT);


                Uri notificationSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

                NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(mContext, "" + COMMENT_CHANNEL_ID)
                        .setSmallIcon(R.drawable.ic_froken2)
                        .setLargeIcon(bitArr.get(1))
                        .setContentTitle(pTitle)
                        .setContentText(pDescription)
                        .setColor(Color.BLUE)
                        .setStyle(new NotificationCompat.BigPictureStyle()
                                .bigPicture(bitArr.get(0))
                                .bigLargeIcon(null))

                        .setSound(notificationSoundUri)
                        .setContentIntent(pendingIntent)
                        .setAutoCancel(true);

                notificationManager.notify(notificationID, notificationBuilder.build());

            }

            else if (notificationType.equals("AnonymousCommentNotification"))
            {
                int notificationID = new Random().nextInt(3000);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    setupAnonymousCommentNotificationChannel(notificationManager);
                }

                Intent intent = new Intent(mContext, AnonymousCommentsActivity.class);
                intent.putExtra("un", hisUid);
                intent.putExtra("postid", pId);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                PendingIntent pendingIntent = PendingIntent.getActivity(mContext, notificationID, intent, PendingIntent.FLAG_UPDATE_CURRENT);


                Uri notificationSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

                NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(mContext, "" + ANONYMOUS_COMMENT_CHANNEL_ID)
                        .setSmallIcon(R.drawable.ic_froken2)
                        .setLargeIcon(bitArr.get(1))
                        .setContentTitle(pTitle)
                        .setContentText(pDescription)
                        .setColor(Color.BLUE)
                        .setStyle(new NotificationCompat.BigPictureStyle()
                                .bigPicture(bitArr.get(0))
                                .bigLargeIcon(null))

                        .setSound(notificationSoundUri)
                        .setContentIntent(pendingIntent)
                        .setAutoCancel(true);

                notificationManager.notify(notificationID, notificationBuilder.build());

            }
            else if (notificationType.equals("MentionNotification"))
            {
                int notificationID = new Random().nextInt(3000);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    setupMentionNotificationChannel(notificationManager);
                }

                Intent intent = new Intent(mContext, CommentsActivity.class);
                intent.putExtra("un", hisUid);
                intent.putExtra("postid", pId);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                PendingIntent pendingIntent = PendingIntent.getActivity(mContext, notificationID, intent, PendingIntent.FLAG_UPDATE_CURRENT);


                Uri notificationSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

                NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(mContext, "" + MENTION_CHANNEL_ID)
                        .setSmallIcon(R.drawable.ic_froken2)
                        .setLargeIcon(bitArr.get(1))
                        .setContentTitle(pTitle)
                        .setContentText(pDescription)
                        .setColor(Color.BLUE)
                        .setStyle(new NotificationCompat.BigPictureStyle()
                                .bigPicture(bitArr.get(0))
                                .bigLargeIcon(null))

                        .setSound(notificationSoundUri)
                        .setContentIntent(pendingIntent)
                        .setAutoCancel(true);

                notificationManager.notify(notificationID, notificationBuilder.build());

            }

            else if (notificationType.equals("ShareNotification"))
            {
                int notificationID = new Random().nextInt(3000);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    setupShareNotificationChannel(notificationManager);
                }

                Intent intent = new Intent(mContext, CommentsActivity.class);
                intent.putExtra("un", hisUid);
                intent.putExtra("postid", pId);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                PendingIntent pendingIntent = PendingIntent.getActivity(mContext, notificationID, intent, PendingIntent.FLAG_UPDATE_CURRENT);


                Uri notificationSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

                NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(mContext, "" + SHARE_CHANNEL_ID)
                        .setSmallIcon(R.drawable.ic_froken2)
                        .setLargeIcon(bitArr.get(1))
                        .setContentTitle(pTitle)
                        .setContentText(pDescription)
                        .setColor(Color.BLUE)
                        .setStyle(new NotificationCompat.BigPictureStyle()
                                .bigPicture(bitArr.get(0))
                                .bigLargeIcon(null))

                        .setSound(notificationSoundUri)
                        .setContentIntent(pendingIntent)
                        .setAutoCancel(true);

                notificationManager.notify(notificationID, notificationBuilder.build());

            }

            else if (notificationType.equals("AnonymousShareNotification"))
            {
                int notificationID = new Random().nextInt(3000);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    setupAnonymousShareNotificationChannel(notificationManager);
                }

                Intent intent = new Intent(mContext, AnonymousCommentsActivity.class);
                intent.putExtra("un", hisUid);
                intent.putExtra("postid", pId);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                PendingIntent pendingIntent = PendingIntent.getActivity(mContext, notificationID, intent, PendingIntent.FLAG_UPDATE_CURRENT);


                Uri notificationSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

                NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(mContext, "" + ANONYMOUS_SHARE_CHANNEL_ID)
                        .setSmallIcon(R.drawable.ic_froken2)
                        .setLargeIcon(bitArr.get(1))
                        .setContentTitle(pTitle)
                        .setContentText(pDescription)
                        .setColor(Color.BLUE)
                        .setStyle(new NotificationCompat.BigPictureStyle()
                                .bigPicture(bitArr.get(0))
                                .bigLargeIcon(null))

                        .setSound(notificationSoundUri)
                        .setContentIntent(pendingIntent)
                        .setAutoCancel(true);

                notificationManager.notify(notificationID, notificationBuilder.build());

            }




        }
    }


    public class generateAnonymousMentionNotification extends AsyncTask<String, Void, ArrayList<Bitmap>> {

        private Context mContext;
        private String pTitle, pDescription, imageUrl, pId, hisUid,notificationType;


        public generateAnonymousMentionNotification(Context mContext, String pTitle, String pDescription, String imageUrl, String pId, String hisUid, String notificationType) {
            this.mContext = mContext;
            this.pTitle = pTitle;
            this.pDescription = pDescription;
            this.imageUrl = imageUrl;
            this.pId = pId;
            this.hisUid = hisUid;
            this.notificationType = notificationType;
        }

        @Override
        protected ArrayList<Bitmap> doInBackground(String... params) {

            InputStream in;
            InputStream in1;
            try {

                Bitmap output = BitmapFactory.decodeResource(getResources(),R.drawable.ic_froken2);

              /*  Bitmap output;
                Rect srcRect, dstRect;
                float r;
                final int width = bitmap.getWidth();
                final int height = bitmap.getHeight();

                if (width > height){
                    output = Bitmap.createBitmap(height, height, Bitmap.Config.ARGB_8888);
                    int left = (width - height) / 2;
                    int right = left + height;
                    srcRect = new Rect(left, 0, right, height);
                    dstRect = new Rect(0, 0, height, height);
                    r = height / 2;
                }else{
                    output = Bitmap.createBitmap(width, width, Bitmap.Config.ARGB_8888);
                    int top = (height - width)/2;
                    int bottom = top + width;
                    srcRect = new Rect(0, top, width, bottom);
                    dstRect = new Rect(0, 0, width, width);
                    r = width / 2;
                }

                Canvas canvas = new Canvas(output);

                final int color = 0xff424242;
                final Paint paint = new Paint();

                paint.setAntiAlias(true);
                canvas.drawARGB(0, 0, 0, 0);
                paint.setColor(color);
                canvas.drawCircle(r, r, r, paint);
                paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
                canvas.drawBitmap(bitmap, srcRect, dstRect, paint);

                bitmap.recycle();

               */

                URL url1 = new URL(this.imageUrl);
                HttpURLConnection connection1 = (HttpURLConnection) url1.openConnection();
                connection1.setDoInput(true);
                connection1.connect();
                in1 = connection1.getInputStream();
                Bitmap bitmap1 = BitmapFactory.decodeStream(in1);


                ArrayList<Bitmap> bitmapArray = new ArrayList<Bitmap>();
                bitmapArray.add(bitmap1);
                bitmapArray.add(output);// Add a bitmap

                return bitmapArray;
                //return myBitmap;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<Bitmap> bitArr) {
            super.onPostExecute(bitArr);

            NotificationManager notificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);



            int notificationID = new Random().nextInt(3000);

                if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.O)
                {
                    setupAnonymousMentionNotificationChannel(notificationManager);
                }

                Intent intent = new Intent(mContext, AnonymousCommentsActivity.class);
                intent.putExtra("un",hisUid);
                intent.putExtra("postid",pId);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                PendingIntent pendingIntent = PendingIntent.getActivity(mContext,notificationID,intent,PendingIntent.FLAG_UPDATE_CURRENT);



                Uri notificationSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

                NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(mContext,""+ANONYMOUS_MENTION_CHANNEL_ID)
                        .setSmallIcon(R.drawable.ic_froken2)
                        .setLargeIcon(bitArr.get(1))
                        .setContentTitle(pTitle)
                        .setContentText(pDescription)
                        .setColor(Color.BLUE)
                        .setStyle(new NotificationCompat.BigPictureStyle()
                                .bigPicture(bitArr.get(0))
                                .bigLargeIcon(null))

                        .setSound(notificationSoundUri)
                        .setContentIntent(pendingIntent)
                        .setAutoCancel(true);

                notificationManager.notify(notificationID,notificationBuilder.build());





        }
    }


    public class generateLikeNotification1 extends AsyncTask<String, Void, Bitmap> {

        private Context mContext;
        private String pTitle, pDescription, imageUrl, pId, hisUid,senderImage,notificationType;

        NotificationChannelGroup likegroup = new NotificationChannelGroup(LIKE_CHANNEL_GROUP_ID,"Likes");
        NotificationChannelGroup commentgroup = new NotificationChannelGroup(COMMENT_CHANNEL_GROUP_ID,"Comments");
        NotificationChannelGroup sharegroup = new NotificationChannelGroup(SHARE_CHANNEL_GROUP_ID,"Shares");


        public generateLikeNotification1(Context mContext, String pTitle, String pDescription, String imageUrl, String pId, String hisUid, String notificationType) {
            this.mContext = mContext;
            this.pTitle = pTitle;
            this.pDescription = pDescription;
            this.imageUrl = imageUrl;
            this.pId = pId;
            this.hisUid = hisUid;
            this.notificationType = notificationType;
        }

        @Override
        protected Bitmap doInBackground(String... params) {

            InputStream in1;
            try {

                URL url1 = new URL(this.imageUrl);
                HttpURLConnection connection1 = (HttpURLConnection) url1.openConnection();
                connection1.setDoInput(true);
                connection1.connect();
                in1 = connection1.getInputStream();
                Bitmap bitmap1 = BitmapFactory.decodeStream(in1);



                return bitmap1;
                //return myBitmap;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);

            NotificationManager notificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannelGroup(likegroup);
            notificationManager.createNotificationChannelGroup(commentgroup);
            notificationManager.createNotificationChannelGroup(sharegroup);



            if (notificationType.equals("LikeNotification"))
            {

                int notificationID = new Random().nextInt(3000);

                if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.O)
                {
                    setupLikeNotificationChannel(notificationManager);
                }

                Intent intent = new Intent(mContext, CommentsActivity.class);
                intent.putExtra("un",hisUid);
                intent.putExtra("postid",pId);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                PendingIntent pendingIntent = PendingIntent.getActivity(mContext,notificationID,intent,PendingIntent.FLAG_UPDATE_CURRENT);



                Uri notificationSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

                NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(mContext,""+LIKE_CHANNEL_ID)
                        .setSmallIcon(R.drawable.ic_froken2)
                        .setLargeIcon(null)
                        .setContentTitle(pTitle)
                        .setContentText(pDescription)
                        .setColor(Color.BLUE)
                        .setStyle(new NotificationCompat.BigPictureStyle()
                                .bigPicture(bitmap)
                                .bigLargeIcon(null))

                        .setSound(notificationSoundUri)
                        .setContentIntent(pendingIntent)
                        .setAutoCancel(true);

                notificationManager.notify(notificationID,notificationBuilder.build());


            }


            else if (notificationType.equals("AnonymousLikeNotification"))
            {
                int notificationID = new Random().nextInt(3000);

                if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.O)
                {
                    setupAnonymousLikeNotificationChannel(notificationManager);
                }

                Intent intent = new Intent(mContext, AnonymousCommentsActivity.class);
                intent.putExtra("un",hisUid);
                intent.putExtra("postid",pId);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                PendingIntent pendingIntent = PendingIntent.getActivity(mContext,notificationID,intent,PendingIntent.FLAG_UPDATE_CURRENT);



                Uri notificationSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

                NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(mContext,""+ANONYMOUS_LIKE_CHANNEL_ID)
                        .setSmallIcon(R.drawable.ic_froken2)
                        .setLargeIcon(null)
                        .setContentTitle(pTitle)
                        .setContentText(pDescription)
                        .setColor(Color.BLUE)
                        .setStyle(new NotificationCompat.BigPictureStyle()
                                .bigPicture(bitmap)
                                .bigLargeIcon(null))


                        .setSound(notificationSoundUri)
                        .setContentIntent(pendingIntent)
                        .setAutoCancel(true);

                notificationManager.notify(notificationID,notificationBuilder.build());



            }

            else if (notificationType.equals("CommentNotification"))
            {
                int notificationID = new Random().nextInt(3000);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    setupCommentNotificationChannel(notificationManager);
                }

                Intent intent = new Intent(mContext, CommentsActivity.class);
                intent.putExtra("un", hisUid);
                intent.putExtra("postid", pId);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                PendingIntent pendingIntent = PendingIntent.getActivity(mContext, notificationID, intent, PendingIntent.FLAG_UPDATE_CURRENT);


                Uri notificationSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

                NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(mContext, "" + COMMENT_CHANNEL_ID)
                        .setSmallIcon(R.drawable.ic_froken2)
                        .setLargeIcon(null)
                        .setContentTitle(pTitle)
                        .setContentText(pDescription)
                        .setColor(Color.BLUE)
                        .setStyle(new NotificationCompat.BigPictureStyle()
                                .bigPicture(bitmap)
                                .bigLargeIcon(null))


                        .setSound(notificationSoundUri)
                        .setContentIntent(pendingIntent)
                        .setAutoCancel(true);

                notificationManager.notify(notificationID, notificationBuilder.build());

            }

            else if (notificationType.equals("AnonymousCommentNotification"))
            {
                int notificationID = new Random().nextInt(3000);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    setupAnonymousCommentNotificationChannel(notificationManager);
                }

                Intent intent = new Intent(mContext, AnonymousCommentsActivity.class);
                intent.putExtra("un", hisUid);
                intent.putExtra("postid", pId);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                PendingIntent pendingIntent = PendingIntent.getActivity(mContext, notificationID, intent, PendingIntent.FLAG_UPDATE_CURRENT);


                Uri notificationSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

                NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(mContext, "" + ANONYMOUS_COMMENT_CHANNEL_ID)
                        .setSmallIcon(R.drawable.ic_froken2)
                        .setLargeIcon(null)
                        .setContentTitle(pTitle)
                        .setContentText(pDescription)
                        .setColor(Color.BLUE)
                        .setStyle(new NotificationCompat.BigPictureStyle()
                                .bigPicture(bitmap)
                                .bigLargeIcon(null))


                        .setSound(notificationSoundUri)
                        .setContentIntent(pendingIntent)
                        .setAutoCancel(true);

                notificationManager.notify(notificationID, notificationBuilder.build());

            }
            else if (notificationType.equals("MentionNotification"))
            {
                int notificationID = new Random().nextInt(3000);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    setupMentionNotificationChannel(notificationManager);
                }

                Intent intent = new Intent(mContext, CommentsActivity.class);
                intent.putExtra("un", hisUid);
                intent.putExtra("postid", pId);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                PendingIntent pendingIntent = PendingIntent.getActivity(mContext, notificationID, intent, PendingIntent.FLAG_UPDATE_CURRENT);


                Uri notificationSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

                NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(mContext, "" + MENTION_CHANNEL_ID)
                        .setSmallIcon(R.drawable.ic_froken2)
                        .setLargeIcon(null)
                        .setContentTitle(pTitle)
                        .setContentText(pDescription)
                        .setColor(Color.BLUE)
                        .setStyle(new NotificationCompat.BigPictureStyle()
                                .bigPicture(bitmap)
                                .bigLargeIcon(null))


                        .setSound(notificationSoundUri)
                        .setContentIntent(pendingIntent)
                        .setAutoCancel(true);

                notificationManager.notify(notificationID, notificationBuilder.build());

            }

            else if (notificationType.equals("ShareNotification"))
            {
                int notificationID = new Random().nextInt(3000);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    setupShareNotificationChannel(notificationManager);
                }

                Intent intent = new Intent(mContext, CommentsActivity.class);
                intent.putExtra("un", hisUid);
                intent.putExtra("postid", pId);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                PendingIntent pendingIntent = PendingIntent.getActivity(mContext, notificationID, intent, PendingIntent.FLAG_UPDATE_CURRENT);


                Uri notificationSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

                NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(mContext, "" + SHARE_CHANNEL_ID)
                        .setSmallIcon(R.drawable.ic_froken2)
                        .setLargeIcon(null)
                        .setContentTitle(pTitle)
                        .setContentText(pDescription)
                        .setColor(Color.BLUE)
                        .setStyle(new NotificationCompat.BigPictureStyle()
                                .bigPicture(bitmap)
                                .bigLargeIcon(null))


                        .setSound(notificationSoundUri)
                        .setContentIntent(pendingIntent)
                        .setAutoCancel(true);

                notificationManager.notify(notificationID, notificationBuilder.build());

            }

            else if (notificationType.equals("AnonymousShareNotification"))
            {
                int notificationID = new Random().nextInt(3000);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    setupAnonymousShareNotificationChannel(notificationManager);
                }

                Intent intent = new Intent(mContext, AnonymousCommentsActivity.class);
                intent.putExtra("un", hisUid);
                intent.putExtra("postid", pId);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                PendingIntent pendingIntent = PendingIntent.getActivity(mContext, notificationID, intent, PendingIntent.FLAG_UPDATE_CURRENT);


                Uri notificationSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

                NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(mContext, "" + ANONYMOUS_SHARE_CHANNEL_ID)
                        .setSmallIcon(R.drawable.ic_froken2)
                        .setLargeIcon(null)
                        .setContentTitle(pTitle)
                        .setContentText(pDescription)
                        .setColor(Color.BLUE)
                        .setStyle(new NotificationCompat.BigPictureStyle()
                                .bigPicture(bitmap)
                                .bigLargeIcon(null))


                        .setSound(notificationSoundUri)
                        .setContentIntent(pendingIntent)
                        .setAutoCancel(true);

                notificationManager.notify(notificationID, notificationBuilder.build());

            }




        }
    }



    private void setupLikeNotificationChannel(NotificationManager notificationManager) {

        CharSequence channelName= "Posts' Likes";
        String channelDescription = "Notify you about your posts' likes";

        NotificationChannel likeChannel = new NotificationChannel(LIKE_CHANNEL_ID,channelName,NotificationManager.IMPORTANCE_DEFAULT);
        likeChannel.setDescription(channelDescription);
        likeChannel.enableLights(true);
        likeChannel.setLightColor(Color.RED);
        likeChannel.enableVibration(true);
        likeChannel.setGroup(LIKE_CHANNEL_GROUP_ID);

        if (notificationManager!=null)
        {
            notificationManager.createNotificationChannel(likeChannel);
        }
    }

    private void setupAnonymousLikeNotificationChannel(NotificationManager notificationManager) {

        CharSequence channelName= "Anonymous Posts' Likes";
        String channelDescription = "Notify you about your anonymous posts' likes";

        NotificationChannel likeChannel = new NotificationChannel(ANONYMOUS_LIKE_CHANNEL_ID,channelName,NotificationManager.IMPORTANCE_DEFAULT);
        likeChannel.setDescription(channelDescription);
        likeChannel.enableLights(true);
        likeChannel.setLightColor(Color.RED);
        likeChannel.enableVibration(true);
        likeChannel.setGroup(LIKE_CHANNEL_GROUP_ID);

        if (notificationManager!=null)
        {
            notificationManager.createNotificationChannel(likeChannel);
        }
    }

    private void setupCommentNotificationChannel(NotificationManager notificationManager) {

        CharSequence channelName= "Posts' Comments";
        String channelDescription = "Notify you about your posts' comments";

        NotificationChannel commentchannel = new NotificationChannel(COMMENT_CHANNEL_ID,channelName,NotificationManager.IMPORTANCE_DEFAULT);
        commentchannel.setDescription(channelDescription);
        commentchannel.enableLights(true);
        commentchannel.setLightColor(Color.RED);
        commentchannel.enableVibration(true);
        commentchannel.setGroup(COMMENT_CHANNEL_GROUP_ID);

        if (notificationManager!=null)
        {
            notificationManager.createNotificationChannel(commentchannel);
        }
    }

    private void setupAnonymousCommentNotificationChannel(NotificationManager notificationManager) {

        CharSequence channelName= "Anonymous Posts' Comments";
        String channelDescription = "Notify you about your anonymous posts' comments";

        NotificationChannel commentchannel = new NotificationChannel(ANONYMOUS_COMMENT_CHANNEL_ID,channelName,NotificationManager.IMPORTANCE_DEFAULT);
        commentchannel.setDescription(channelDescription);
        commentchannel.enableLights(true);
        commentchannel.setLightColor(Color.RED);
        commentchannel.enableVibration(true);
        commentchannel.setGroup(COMMENT_CHANNEL_GROUP_ID);

        if (notificationManager!=null)
        {
            notificationManager.createNotificationChannel(commentchannel);
        }
    }

    private void setupMentionNotificationChannel(NotificationManager notificationManager) {

        CharSequence channelName= "Posts' Mentions";
        String channelDescription = "Notify, if someone mentions you in their posts";

        NotificationChannel mentionchannel = new NotificationChannel(MENTION_CHANNEL_ID,channelName,NotificationManager.IMPORTANCE_DEFAULT);
        mentionchannel.setDescription(channelDescription);
        mentionchannel.enableLights(true);
        mentionchannel.setLightColor(Color.RED);
        mentionchannel.enableVibration(true);


        if (notificationManager!=null)
        {
            notificationManager.createNotificationChannel(mentionchannel);
        }
    }

    private void setupAnonymousMentionNotificationChannel(NotificationManager notificationManager) {

        CharSequence channelName= "Anonymous Posts' Mentions";
        String channelDescription = "Notify, if someone mentions you in their anonymous posts";

        NotificationChannel mentionChannel = new NotificationChannel(ANONYMOUS_MENTION_CHANNEL_ID,channelName,NotificationManager.IMPORTANCE_DEFAULT);
        mentionChannel.setDescription(channelDescription);
        mentionChannel.enableLights(true);
        mentionChannel.setLightColor(Color.RED);
        mentionChannel.enableVibration(true);


        if (notificationManager!=null)
        {
            notificationManager.createNotificationChannel(mentionChannel);
        }
    }

    private void setupShareNotificationChannel(NotificationManager notificationManager) {

        CharSequence channelName= "Shared Posts";
        String channelDescription = "Notify, if someone shares your post on their profile";

        NotificationChannel sharechannel = new NotificationChannel(SHARE_CHANNEL_ID,channelName,NotificationManager.IMPORTANCE_DEFAULT);
        sharechannel.setDescription(channelDescription);
        sharechannel.enableLights(true);
        sharechannel.setLightColor(Color.RED);
        sharechannel.enableVibration(true);
        sharechannel.setGroup(SHARE_CHANNEL_GROUP_ID);



        if (notificationManager!=null)
        {
            notificationManager.createNotificationChannel(sharechannel);
        }
    }

    private void setupAnonymousShareNotificationChannel(NotificationManager notificationManager) {

        CharSequence channelName= "Anonymous Shared Posts";
        String channelDescription = "Notify, if someone shares your anonymous post on their profile";

        NotificationChannel sharechannel = new NotificationChannel(ANONYMOUS_SHARE_CHANNEL_ID,channelName,NotificationManager.IMPORTANCE_DEFAULT);
        sharechannel.setDescription(channelDescription);
        sharechannel.enableLights(true);
        sharechannel.setLightColor(Color.RED);
        sharechannel.enableVibration(true);
        sharechannel.setGroup(SHARE_CHANNEL_GROUP_ID);


        if (notificationManager!=null)
        {
            notificationManager.createNotificationChannel(sharechannel);
        }
    }






    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);

        FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        if(firebaseUser!=null){
            updateToken(s);
        }
    }
    private void updateToken(String refreshToken){
        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Tokens");
        Token token= new Token(refreshToken);
        ref.child(user.getUid()).setValue(token);
    }



}

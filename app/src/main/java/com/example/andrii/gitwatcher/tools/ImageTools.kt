package com.example.andrii.gitwatcher.tools

import android.content.Context
import android.graphics.*
import android.net.Uri
import android.os.Environment
import android.util.Log
import android.widget.ImageView
import com.example.andrii.gitwatcher.R
import com.example.andrii.gitwatcher.data.models.User
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.Picasso
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.io.File
import java.io.FileOutputStream


class ImageTools constructor(private val context: Context){

    fun downloadUserImage(imageView: ImageView, user: User,save:Boolean) {
        val root = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES).path
        val file = File(root, user.id + ".jpg")
        if(file.exists()){
            Picasso.with(context)
                    .load(file)
                    .placeholder(R.color.colorPrimary)
                    .error(R.color.colorPrimary)
                    .memoryPolicy(MemoryPolicy.NO_CACHE,MemoryPolicy.NO_STORE)
                    .into(imageView)
        }else{
             Observable.just(user.avatar_url)

                     .map{ url: String? ->
                        // Picasso.with(context).cancelRequest(imageView)
                         Picasso.with(context)
                                 .load(Uri.parse(url))
                                 .placeholder(R.color.colorPrimary)
                                 .error(R.color.colorPrimary)
                                 .memoryPolicy(MemoryPolicy.NO_CACHE,MemoryPolicy.NO_STORE)
                                 .get()
                     }
                     .subscribeOn(Schedulers.io())
                     .observeOn(AndroidSchedulers.mainThread())
                     .subscribe({ bitmap: Bitmap? ->
                         imageView.setImageBitmap(bitmap)

                         if (save) saveImage(file,bitmap)
                     },{t: Throwable? -> Log.e("Tools",t.toString()) }
                     )

        }
    }

    fun downloadImage(url:String,imageView: ImageView,round:Boolean){
        Observable.just(url)
                .map{ u: String? ->
                   // Picasso.with(context).cancelRequest(imageView)
                    Picasso.with(context)
                            .load(Uri.parse(u))
                            .placeholder(R.color.colorPrimary)
                            .error(R.color.colorPrimary)
                            .memoryPolicy(MemoryPolicy.NO_CACHE,MemoryPolicy.NO_STORE)
                            .get()
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ bitmap: Bitmap? -> var endBitmap = bitmap
                   if(round) endBitmap = roundCorners(bitmap!!)

                       imageView.setImageBitmap(endBitmap)
                },{t: Throwable? -> Log.e("Tools",t.toString()) }
                )

    }

    private fun roundCorners(bitmap: Bitmap): Bitmap {
        val output = Bitmap.createBitmap(bitmap.width, bitmap.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(output)
        val color = -0xbdbdbe
        val paint = Paint()
        val rect = Rect(0, 0, bitmap.width, bitmap.height)
        val rectF = RectF(rect)
        val roundPx = 12f

        paint.isAntiAlias = true
        canvas.drawARGB(0, 0, 0, 0)
        paint.color = color
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint)

        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
        canvas.drawBitmap(bitmap, rect, rect, paint)

        return output
    }

    private fun saveImage(file: File, image: Bitmap?) {
        Observable.just(image)
                .observeOn(Schedulers.io())
                .subscribe({ bitmap ->
                    if (file.exists()) file.delete()
                        val out = FileOutputStream(file)
                        bitmap?.compress(Bitmap.CompressFormat.JPEG, 90, out)
                        out.flush()
                        out.close()
                },{t: Throwable? -> Log.e("Tools",t.toString()) }
                )
    }
}
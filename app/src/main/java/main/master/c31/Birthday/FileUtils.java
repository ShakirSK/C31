package main.master.c31.Birthday;


import android.content.Context;
import android.os.Environment;
import java.io.File;
import java.util.Random;
import me.echodev.resizer.Resizer;

public class FileUtils {

    public static double calculateFileSizeInMB(File file){
        double fileSizeInMB=0.0;
        try {
            if(file.exists()) {
                double fileSizeInBytes = file.length();
                // Convert the bytes to Kilobytes (1 KB = 1024 Bytes)
                double fileSizeInKB = fileSizeInBytes / 1024;
                // Convert the KB to MegaBytes (1 MB = 1024 KBytes)
                fileSizeInMB = fileSizeInKB / 1024;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return fileSizeInMB;
    }

    public  static File resizeImageFile(File file, Context mContext){
        File resizedImage=null;
        File storageDir = Environment.getExternalStorageDirectory();
        Random random = new Random();
        int numbersw = random.nextInt(9999 - 1000) + 1000;
        String str = "VidAppDrImage"+String.valueOf(numbersw);
        try {
            resizedImage = new Resizer(mContext)
                    .setTargetLength(1024)
                    .setQuality(90)
                    .setOutputFormat("JPEG")
                    .setOutputFilename(str)
                    .setOutputDirPath(storageDir.getAbsolutePath())
                    .setSourceImage(file)
                    .getResizedFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resizedImage;
    }

    public static File checkImageFileAndResize(File imgFile,Context context) {
        File drImagFile = null;
        if (FileUtils.calculateFileSizeInMB(imgFile) > 1) {
            if (FileUtils.resizeImageFile(imgFile, context) != null) {
                if (FileUtils.resizeImageFile(imgFile, context).exists()) {
                    drImagFile = FileUtils.resizeImageFile(imgFile, context);

                }
            }

        } else {
            drImagFile = imgFile;
        }
        return drImagFile;
    }
}

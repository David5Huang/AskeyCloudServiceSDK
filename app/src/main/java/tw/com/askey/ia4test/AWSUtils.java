package tw.com.askey.ia4test;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.regions.Regions;

import java.io.File;

/**
 * Created by david5_huang on 2016/7/25.
 */
public class AWSUtils {
    public static final int NON = 0;
    public static final int TEST_01 = 1;
    public static final int TEST_02 = 2;

    private static final String TEST01_POOL = "us-east-1:690571dd-c37d-4153-8a7a-3849e0964b66";
    private static final String TEST02_POOL = "us-east-1:c7e5e179-c0d2-4f87-a5a8-caff5c610295";

    public static CognitoCachingCredentialsProvider getCredProvider(Context context, int type){
        String poolID = type==TEST_01?TEST01_POOL:TEST02_POOL;
        Log.e("testing", "pool id: "+poolID);
        CognitoCachingCredentialsProvider credentialsProvider = new CognitoCachingCredentialsProvider(
                context,
                poolID, // Identity Pool ID
                Regions.US_EAST_1 // Region
        );
        return credentialsProvider;
    }

    public static String getTestName(int type){
        if(type == TEST_01){
            return "test01";
        }
        else if(type == TEST_02){
            return "test02";
        }
        return null;
    }

    public static File getTestData(int type){
        File ext = Environment.getExternalStorageDirectory();
        if(type == TEST_01){
            return new File(ext.getAbsoluteFile()+"/DCIM/"+"test01.jpg");
        }
        else if(type == TEST_02){
            return new File(ext.getAbsoluteFile()+"/DCIM/"+"test02.jpg");
        }
        return null;
    }

    public static String getPoolId(int number){
        return number == TEST_01?TEST01_POOL:TEST02_POOL;
    }
}

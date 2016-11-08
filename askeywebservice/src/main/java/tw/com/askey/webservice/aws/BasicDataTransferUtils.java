package tw.com.askey.webservice.aws;

import com.amazonaws.auth.CognitoCachingCredentialsProvider;

import java.io.File;

/**
 * Created by david5_huang on 2016/7/5.
 */
abstract public class BasicDataTransferUtils {

    protected CognitoCachingCredentialsProvider credentialsProvider;

//    abstract public ArrayList<FileModel> queryFileList(FileQuerySetting setting);
    abstract public String updateFile(String fileName, File updateFile);

}

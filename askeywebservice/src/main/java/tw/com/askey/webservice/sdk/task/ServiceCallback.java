package tw.com.askey.webservice.sdk.task;

/**
 * Created by david5_huang on 2016/7/5.
 */
public interface ServiceCallback {
    abstract public void success(String tag, Object result);
    abstract public void error(String tag, Object result);
    abstract public void problem(String tag, Object result);
}

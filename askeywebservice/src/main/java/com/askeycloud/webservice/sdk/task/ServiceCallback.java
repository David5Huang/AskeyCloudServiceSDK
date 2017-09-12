package com.askeycloud.webservice.sdk.task;

/**
 * SDK task return result via this callback.
 */
public interface ServiceCallback {
    /**
     * When task execute success.
     * @param tag Task result tag.
     * @param result Task result.
     */
    abstract public void success(String tag, Object result);

    /**
     * When task execute error.
     * @param tag Task result tag.
     * @param result Error result(or message).
     */
    abstract public void error(String tag, Object result);

    /**
     * When task execute get uncatch error.
     * @param tag Task result tag.
     * @param result Error result(or message).
     */
    abstract public void problem(String tag, Object result);
}

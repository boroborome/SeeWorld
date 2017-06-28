package com.happy3w.seeworld.task;

import com.happy3w.seeworld.entity.SystemConfig;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by ysgao on 28/06/2017.
 */
public class DownloadTaskTest {
    @Test
    public void should_download_success() {
        DownloadTask task = new DownloadTask();
        String content = task.download("http://www.sina.com.cn");
        Assert.assertNotNull(content);
        Assert.assertTrue(content.length() > 0);
    }
}

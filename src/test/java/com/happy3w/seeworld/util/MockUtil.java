package com.happy3w.seeworld.util;

import org.junit.Assert;
import org.mockito.ArgumentCaptor;

/**
 * Created by ysgao on 30/06/2017.
 */
public class MockUtil {
    public static void verifyParameter(ArgumentCaptor[] argumentCaptors, Object[][] expectValues) {
        Assert.assertNotNull(argumentCaptors);
        Assert.assertNotNull(expectValues);
        Assert.assertTrue(argumentCaptors.length > 0);
        Assert.assertTrue(expectValues.length > 0);

        for (int row = 0, len = expectValues.length; row < len; row++) {
            Object[] values = expectValues[row];
            Assert.assertEquals(argumentCaptors.length, values.length);

            for (int column = 0, columnCount = argumentCaptors.length; column < columnCount; column++) {
                Assert.assertEquals(values[column], argumentCaptors[column].getAllValues().get(row));
            }
        }
    }
}

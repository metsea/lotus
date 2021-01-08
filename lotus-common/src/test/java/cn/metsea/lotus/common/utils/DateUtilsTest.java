package cn.metsea.lotus.common.utils;

import org.junit.Assert;
import org.junit.Test;

public class DateUtilsTest {

    @Test
    public void parseDateStringTest() {
        String dateString = "2021-01-08 15:37:37";
        String format = "yyyy-MM-dd HH:mm:ss";
        Assert.assertEquals(dateString, DateUtils.dateToString(DateUtils.parse(dateString, format)));
    }

}

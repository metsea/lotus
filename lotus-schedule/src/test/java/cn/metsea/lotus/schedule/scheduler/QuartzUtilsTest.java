package cn.metsea.lotus.schedule.scheduler;

import cn.metsea.lotus.common.utils.DateUtils;
import cn.metsea.lotus.schedule.utils.QuartzUtils;
import java.text.ParseException;
import java.util.Date;
import org.junit.Test;

public class QuartzUtilsTest {

    @Test
    public void getCronFireTimeListTest() throws ParseException {
        Date startDate = DateUtils.parse("2021-01-08 00:00:00", "yyyy-MM-dd HH:mm:ss");
        Date endDate = DateUtils.parse("2021-01-08 23:59:59", "yyyy-MM-dd HH:mm:ss");
        QuartzUtils.getCronFireTimeList("0 1 */1 * * ? *", startDate, endDate).forEach(System.out::println);
    }

}

package cn.metsea.lotus.schedule.utils;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import org.quartz.TriggerUtils;
import org.quartz.impl.triggers.CronTriggerImpl;

/**
 * Quartz Utils
 */
public class QuartzUtils {

    public static List<Date> getCronFireTimeList(String cron, Date startDate, Date endDate) throws ParseException {
        CronTriggerImpl cronTrigger = new CronTriggerImpl();
        cronTrigger.setCronExpression(cron);
        return TriggerUtils.computeFireTimesBetween(cronTrigger, null, startDate, endDate);
    }

}

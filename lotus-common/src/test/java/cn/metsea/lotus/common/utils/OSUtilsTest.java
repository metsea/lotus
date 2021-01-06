package cn.metsea.lotus.common.utils;

import org.junit.Test;

public class OSUtilsTest {

    @Test
    public void test() {
        System.out.println("availablePhysicalMemorySize : " + OsUtils.availablePhysicalMemorySize());
        System.out.println("totalMemorySize : " + OsUtils.totalMemorySize());
        System.out.println("loadAverage : " + OsUtils.loadAverage());
        System.out.println("cpuUsage : " + OsUtils.cpuUsage());
        System.out.println("processID : " + OsUtils.getProcessId());
    }

}

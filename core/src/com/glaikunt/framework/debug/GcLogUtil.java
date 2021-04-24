package com.glaikunt.framework.debug;

import com.sun.management.GarbageCollectionNotificationInfo;
import com.sun.management.GcInfo;

import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryUsage;
import java.util.Map;

import javax.management.Notification;
import javax.management.NotificationListener;
import javax.management.openmbean.CompositeData;

/**
 * https://stackoverflow.com/questions/28495280/how-to-detect-a-long-gc-from-within-a-jvm
 * and modified by Pete
 */
public class GcLogUtil {

    private static long gc;
    private static long cumeMs;
    private static long cumeMBytes;
    private static long lastMs;
    private static long lastMBytes;

    private GcLogUtil() {}

    public static long getGc() {
        return gc;
    }

    public static long getCumeMBytes() {
        return cumeMBytes;
    }

    public static long getCumeMs() {
        return cumeMs;
    }

    public static long getLastMBytes() {
        return lastMBytes;
    }

    public static long getLastMs() {
        return lastMs;
    }

    public static void startLoggingGc() {
        // http://www.programcreek.com/java-api-examples/index.php?class=javax.management.MBeanServerConnection&method=addNotificationListener
        // https://docs.oracle.com/javase/8/docs/jre/api/management/extension/com/sun/management/GarbageCollectionNotificationInfo.html#GARBAGE_COLLECTION_NOTIFICATION
        for (GarbageCollectorMXBean gcMbean : ManagementFactory.getGarbageCollectorMXBeans()) {
            try {
                ManagementFactory.getPlatformMBeanServer().
                        addNotificationListener(gcMbean.getObjectName(), listener, null,null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static NotificationListener listener = new NotificationListener() {
        @Override
        public void handleNotification(Notification notification, Object handback) {
            if (notification.getType().equals(GarbageCollectionNotificationInfo.GARBAGE_COLLECTION_NOTIFICATION)) {
                // https://docs.oracle.com/javase/8/docs/jre/api/management/extension/com/sun/management/GarbageCollectionNotificationInfo.html
                CompositeData cd = (CompositeData) notification.getUserData();
                GarbageCollectionNotificationInfo gcNotificationInfo = GarbageCollectionNotificationInfo.from(cd);
                GcInfo gcInfo = gcNotificationInfo.getGcInfo();
                gc++;
                lastMs = gcInfo.getDuration();
                lastMBytes = sumUsedMb(gcInfo.getMemoryUsageBeforeGc())-sumUsedMb(gcInfo.getMemoryUsageAfterGc());
                cumeMBytes += lastMBytes;
                cumeMs += lastMs;
            }
        }
    };

    private static long sumUsedMb(Map<String, MemoryUsage> memUsages) {
        long sum = 0;
        for (MemoryUsage memoryUsage : memUsages.values()) {
            sum += memoryUsage.getUsed();
        }
        return sum / (1024 * 1024);
    }
}

package com.tom.quartz.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author Mr Tom
 * @date 2017/10/16
 * @email ptomjie@gmail.com
 * @description
 * @since 2017/10/16
 */
public class IdGenerateFactory {

    /**
     * 配置生成id的线程数
     */
    private static int initialThreads = 2;

    /**
     * 配置生成id的数据中心个数
     */
    private static int initialDatacenterNum = 2;

    private static final Random random = new Random();

    private static IdGenerateFactory factory = new IdGenerateFactory();

    private List<IdWorker> workList = null;

    private IdGenerateFactory() {
        workList = new ArrayList<>(initialThreads * initialDatacenterNum);
        for (int i = 0; i < initialThreads; i++) {
            for (int j = 0; j < initialDatacenterNum; j++) {
                workList.add(i, new IdWorker(i, j));
            }
        }
    }


    public static long nextId() {
        return factory.workList.get(random.nextInt(initialThreads * initialDatacenterNum)).nextId();
    }


    public static void main(String[] args) {
        System.out.println(IdGenerateFactory.nextId());
    }

}

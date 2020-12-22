package com.godmonth.status.executor.impl;

import org.junit.jupiter.api.Test;

/**
 * <p></p >
 *
 * @author shenyue
 */
class DefaultOrderExecutorTest {
    @Test
    void name() {
        DefaultOrderExecutor<Object, Object, Object> build = DefaultOrderExecutor.builder().build();
        System.out.println(build);
    }
}
package com.neulab.fund.util;
import org.junit.jupiter.api.Test;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;
class SparkAiUtilTest {
    @Test
    void testAskAi_Mock() throws Exception {
        SparkAiUtil util = new SparkAiUtil();
        java.lang.reflect.Field field = SparkAiUtil.class.getDeclaredField("useMock");
        field.setAccessible(true);
        field.set(util, true);
        Map<String, Object> result = util.askAi("test");
        assertNotNull(result.get("summary"));
        assertNotNull(result.get("advice"));
        assertNotNull(result.get("profiles"));
        assertNotNull(result.get("segments"));
    }
    @Test
    void testJsonExtraction() {
        SparkAiUtil util = new SparkAiUtil();
        util.testJsonExtraction(); // 只要不抛异常即可
    }
} 
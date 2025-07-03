package com.neulab.fund.vo;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ApiResponseTest {
    @Test
    void testSuccessNoData() {
        ApiResponse<?> res = ApiResponse.success();
        assertEquals(0, res.getCode());
        assertEquals("success", res.getMessage());
        assertNull(res.getData());
    }

    @Test
    void testSuccessWithData() {
        String data = "hello";
        ApiResponse<String> res = ApiResponse.success(data);
        assertEquals(0, res.getCode());
        assertEquals("success", res.getMessage());
        assertEquals(data, res.getData());
    }

    @Test
    void testError() {
        ApiResponse<?> res = ApiResponse.error("fail");
        assertEquals(-1, res.getCode());
        assertEquals("fail", res.getMessage());
        assertNull(res.getData());
    }

    @Test
    void testCustomMessage() {
        ApiResponse<String> res = ApiResponse.success("ok");
        assertEquals(0, res.getCode());
        assertEquals("success", res.getMessage());
        assertEquals("ok", res.getData());
    }
} 
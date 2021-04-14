package com.platform.mservice.clickhouse;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * @Author: wejam
 * @Description
 * @Date: 2021/3/29 下午2:31
 */
public class ClickHouseConnectionITest extends AbstractITest {

    @Test
    public void testCatalog() throws Exception {
        withNewConnection(connection -> {
            assertNull(connection.getCatalog());
            connection.setCatalog("abc");
            assertNull(connection.getCatalog());
        });
    }

    @Test
    public void testSchema() throws Exception {
        withNewConnection(connection -> {
            assertEquals("default", connection.getSchema());
            connection.setSchema("abc");
            assertEquals("abc", connection.getSchema());
        });
    }


}

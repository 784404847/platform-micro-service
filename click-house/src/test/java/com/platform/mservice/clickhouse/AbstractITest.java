package com.platform.mservice.clickhouse;

import com.github.housepower.jdbc.ClickHouseDriver;
import com.github.housepower.jdbc.misc.StrUtil;
import com.platform.mservice.clickhouse.util.SystemUtil;
import io.netty.util.ResourceLeakDetector;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.testcontainers.containers.ClickHouseContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.MountableFile;

import javax.sql.DataSource;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.ZoneId;
import java.util.Enumeration;

/**
 * @Author: wejam
 * @Description
 * @Date: 2021/3/29 上午11:48
 */
@Slf4j
@Testcontainers
public abstract class AbstractITest implements Serializable {

    protected static final ZoneId CLIENT_TZ = ZoneId.systemDefault();
    protected static final ZoneId SERVER_TZ = ZoneId.of("UTC");
    protected static final String DRIVER_CLASS_NAME = "com.github.housepower.jdbc.ClickHouseDriver";

    // ClickHouse support gRPC from v21.1.2.15-stable 2021-01-18
    // link: https://github.com/ClickHouse/ClickHouse/blob/master/CHANGELOG.md#clickhouse-release-v211215-stable-2021-01-18
    public static final String CLICKHOUSE_IMAGE = SystemUtil.loadProp("CLICKHOUSE_IMAGE", "yandex/clickhouse-server:21.3");

    protected static final String CLICKHOUSE_USER = SystemUtil.loadProp("CLICKHOUSE_USER", "default");
    protected static final String CLICKHOUSE_PASSWORD = SystemUtil.loadProp("CLICKHOUSE_PASSWORD", "");
    protected static final String CLICKHOUSE_DB = SystemUtil.loadProp("CLICKHOUSE_DB", "");

    protected static final int CLICKHOUSE_GRPC_PORT = 9100;

    static {
        String levelProp = SystemUtil.loadProp("NETTY_RESOURCE_LEAK_DETECT_LEVEL", "SIMPLE");
        ResourceLeakDetector.Level level = ResourceLeakDetector.Level.SIMPLE;
        try {
            level = ResourceLeakDetector.Level.valueOf(levelProp);
        } catch (IllegalArgumentException ex) {
            log.warn("Invalid value of NETTY_RESOURCE_LEAK_DETECT_LEVEL: {}", levelProp);
        }
        ResourceLeakDetector.setLevel(level);
        log.info("Set NETTY_RESOURCE_LEAK_DETECT_LEVEL: {}", level);
    }

    @Container
    public static ClickHouseContainer container = (ClickHouseContainer) new ClickHouseContainer(CLICKHOUSE_IMAGE)
            .withEnv("CLICKHOUSE_USER", CLICKHOUSE_USER)
            .withEnv("CLICKHOUSE_PASSWORD", CLICKHOUSE_PASSWORD)
            .withEnv("CLICKHOUSE_DB", CLICKHOUSE_DB)
            .withExposedPorts(CLICKHOUSE_GRPC_PORT)
            .withCopyFileToContainer(MountableFile.forClasspathResource("grpc_config.xml"), "/etc/clickhouse-server/config.d/grpc_config.xml");

    protected static String CK_HOST;
    protected static String CK_IP;
    protected static int CK_PORT;
    protected static int CK_GRPC_PORT;

    @BeforeAll
    public static void extractContainerInfo() {
        CK_HOST = container.getHost();
        CK_IP = container.getContainerIpAddress();
        CK_PORT = container.getMappedPort(ClickHouseContainer.NATIVE_PORT);
        CK_GRPC_PORT = container.getMappedPort(CLICKHOUSE_GRPC_PORT);
    }

    /**
     * just for compatible with scala
     */
    protected String getJdbcUrl() {
        return getJdbcUrl("");
    }

    protected String getJdbcUrl(Object... params) {
        StringBuilder sb = new StringBuilder();
        int port = container.getMappedPort(ClickHouseContainer.NATIVE_PORT);
        sb.append("jdbc:clickhouse://").append(container.getHost()).append(":").append(port);
        if (StrUtil.isNotEmpty(CLICKHOUSE_DB)) {
            sb.append("/").append(container.getDatabaseName());
        }
        for (int i = 0; i + 1 < params.length; i++) {
            sb.append(i == 0 ? "?" : "&");
            sb.append(params[i]).append("=").append(params[i + 1]);
        }

        // Add user
        sb.append(params.length < 2 ? "?" : "&");
        sb.append("user=").append(container.getUsername());

        // Add password
        // ignore blank password
        if (!StrUtil.isBlank(CLICKHOUSE_PASSWORD)) {
            sb.append("&password=").append(container.getPassword());
        }

        return sb.toString();
    }

    // this method should be synchronized
    synchronized protected void resetDriverManager() throws SQLException {
        // remove all registered jdbc drivers
        Enumeration<Driver> drivers = DriverManager.getDrivers();
        while (drivers.hasMoreElements()) {
            DriverManager.deregisterDriver(drivers.nextElement());
        }
        DriverManager.registerDriver(new ClickHouseDriver());
    }

    protected void withNewConnection(WithConnection withConnection, Object... args) throws Exception {
        resetDriverManager();

        String connectionStr = getJdbcUrl(args);
        try (Connection connection = DriverManager.getConnection(connectionStr)) {
            withConnection.apply(connection);
        }
    }

    protected void withNewConnection(DataSource ds, WithConnection withConnection) throws Exception {
        try (Connection connection = ds.getConnection()) {
            withConnection.apply(connection);
        }
    }

    @FunctionalInterface
    public interface WithConnection {
        void apply(Connection connection) throws Exception;
    }
}

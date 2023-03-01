package org.micro.common.upgrade;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.micro.common.upgrade.utils.ResourceUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;


public class SQLUpgrade {

    private static final Logger logger = LoggerFactory.getLogger(SQLUpgrade.class);
    private static final String default_sql = "classpath:/upgrade.sql";

    private UpgradeProperties upgradeProperties;

    private Upgrade current;

    public SQLUpgrade(UpgradeProperties upgradeProperties) {
        super();
        this.upgradeProperties = upgradeProperties;
    }

    public void upgradeDb(DataSource dataSource) {
        if (upgradeProperties == null
                || dataSource == null
                || StringUtils.isBlank(upgradeProperties.getVersion())) {
            return;
        }
        logger.info("upgrade database to version {} beginning ...", upgradeProperties.getVersion());
        if (upgradeProperties.getUpgradeScript() == null || !upgradeProperties.getUpgradeScript().exists()) {
            try {
                upgradeProperties.setUpgradeScript(ResourceUtil.getResource(default_sql));
            } catch (Exception e) {
                logger.error("", e);
            }
        }

        String database;
        Connection conn = null;
        ResultSet res1 = null;
        try {
            conn = dataSource.getConnection();
            conn.setAutoCommit(false);

            database = getDatabaseName(conn.getMetaData().getURL());
            res1 = conn.getMetaData().getColumns(database, null, Upgrade.TABLE, "%");
            if (!res1.next()) {
                // 表不存在创建表
                createTable(conn);
            }
            if (findUpgrade(conn) != null) {
                logger.info("'{}' is latest version", upgradeProperties.getVersion());
                return;
            }

            // 执行脚本
            executeScript(conn);
            conn.commit();
        } catch (Exception e) {
            logger.error("upgradeDb() error", e);
        } finally {
            if (res1 != null) {
                try {
                    res1.close();
                } catch (Exception e2) {
                    // ignore
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (Exception e2) {
                    // ignore
                }
            }
            logger.info("upgrade database to version {} finished", upgradeProperties.getVersion());
        }
    }

    public void upgradeMongo() {
        logger.info("upgrade mongodb to version {} begining ...", upgradeProperties.getVersion());
        logger.info("upgrade mongodb to version {} finished", upgradeProperties.getVersion());
    }

    /**
     * 创建表
     **/
    private void createTable(Connection conn) throws SQLException {
        try (Statement stmt = conn.createStatement()) {
            logger.info(Upgrade.DDL);
            stmt.execute(Upgrade.DDL);
        }
    }

    /**
     * 查询当前升级版本
     *
     * @param conn 连接
     * @return Upgrade
     * @throws java.sql.SQLException 异常
     */
    private Upgrade findUpgrade(Connection conn) throws SQLException {

        String sql = "SELECT %s FROM %s WHERE app_version='%s'";
        sql = String.format(sql, Upgrade.COLUMN, Upgrade.TABLE, upgradeProperties.getVersion());

        Statement stmt = null;
        ResultSet res = null;
        try {
            stmt = conn.createStatement();
            logger.info(sql);
            res = stmt.executeQuery(sql);

            if (!res.next()) {
                return null;
            }

            String[] columns = Upgrade.COLUMN.split(",");
            if (columns.length < 3) {
                throw new SQLException("select error: " + Upgrade.COLUMN);
            }

            this.current = new Upgrade();
            this.current.setAppName(res.getString(1));
            this.current.setAppVersion(res.getString(2));
            this.current.setUpgradeTime(res.getDate(3));
            return this.current;

        } finally {
            if (res != null) {
                try {
                    res.close();
                } catch (Exception e2) {
                    // ignore
                }
            }
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (Exception e2) {
                    // ignore
                }
            }
        }
    }

    /**
     * 执行SQL脚本文件
     **/
    private void executeScript(Connection conn) {

        logger.info("execute upgrade script {}", upgradeProperties.getUpgradeScript().getFilename());
        this.current = new Upgrade();
        this.current.setAppName(upgradeProperties.getApplicationName());
        this.current.setAppVersion(upgradeProperties.getVersion());
        this.current.setUpgradeTime(new Date());

        String insertVersion = "INSERT INTO %s VALUES ('%s', '%s', '%s')";
        insertVersion = String.format(insertVersion,
                Upgrade.TABLE,
                this.current.getAppName(),
                this.current.getAppVersion(),
                DateFormatUtils.format(this.current.getUpgradeTime(), "yyyy-MM-dd HH:mm:ss"));

        BufferedReader reader = null;
        try (Statement stmt = conn.createStatement()) {

            if (upgradeProperties.getUpgradeScript().exists()) {
                reader = new BufferedReader(new InputStreamReader(upgradeProperties.getUpgradeScript().getInputStream(),
                        StandardCharsets.UTF_8));
                StringBuilder sql = new StringBuilder(0);
                while (reader.ready()) {
                    String line = reader.readLine();
                    // 跳过空行、注释行
                    if (StringUtils.isBlank(line)
                            || line.trim().startsWith("--")
                            || line.trim().startsWith("#")) {
                        continue;
                    }
                    sql.append(line);
                    if (line.trim().endsWith(";")) {
                        logger.info(sql.toString());
                        stmt.addBatch(sql.toString());
                        stmt.executeBatch();
                        sql.setLength(0);
                    }
                }
            }
            // 添加版本号
            logger.info(insertVersion);
            stmt.addBatch(insertVersion);
            stmt.executeBatch();
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        } finally {
            IOUtils.closeQuietly(reader);
            // ignore
        }
    }

    public UpgradeProperties getUpgradeProperties() {
        return upgradeProperties;
    }

    public void setUpgradeProperties(UpgradeProperties upgradeProperties) {
        this.upgradeProperties = upgradeProperties;
    }

    protected String getDatabaseName(String url) {
        if (StringUtils.isBlank(url)) {
            return null;
        }

        String[] arr = url.split("[;|?]");
        String tmp = arr[0].trim();
        String dbName = tmp.substring(tmp.lastIndexOf("/") + 1);
        return StringUtils.isBlank(dbName) ? null : dbName;
    }

    /**
     * 获取当前升级版本
     *
     * @return 版本信息
     */
    public Upgrade getCurrent() {
        return current;
    }

}

/*
 * This file is generated by jOOQ.
*/
package c.b.s.common.logging.infrastructure.jooq.tables;


import c.b.s.common.logging.infrastructure.jooq.BoxinSaasTenant;
import c.b.s.common.logging.infrastructure.jooq.Keys;
import c.b.s.common.logging.infrastructure.jooq.tables.records.CasLoginLogRecord;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Identity;
import org.jooq.Name;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.TableImpl;
import org.jooq.types.UByte;
import org.jooq.types.ULong;


/**
 * 智牛SaaS平台登录日志表, owner: guiqingqing, manager: zhengkeshuang
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.10.8"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class CasLoginLog extends TableImpl<CasLoginLogRecord> {

    private static final long serialVersionUID = 965019861;

    /**
     * The reference instance of <code>boxin_saas_tenant.cas_login_log</code>
     */
    public static final CasLoginLog CAS_LOGIN_LOG = new CasLoginLog();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<CasLoginLogRecord> getRecordType() {
        return CasLoginLogRecord.class;
    }

    /**
     * The column <code>boxin_saas_tenant.cas_login_log.id</code>. 主键ID
     */
    public static final TableField<CasLoginLogRecord, ULong> ID = createField("id", org.jooq.impl.SQLDataType.BIGINTUNSIGNED.nullable(false).identity(true), CAS_LOGIN_LOG, "主键ID");

    /**
     * The column <code>boxin_saas_tenant.cas_login_log.user_id</code>. 用户ID
     */
    public static final TableField<CasLoginLogRecord, ULong> USER_ID = createField("user_id", org.jooq.impl.SQLDataType.BIGINTUNSIGNED.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.BIGINTUNSIGNED)), CAS_LOGIN_LOG, "用户ID");

    /**
     * The column <code>boxin_saas_tenant.cas_login_log.user_name</code>. 用户名
     */
    public static final TableField<CasLoginLogRecord, String> USER_NAME = createField("user_name", org.jooq.impl.SQLDataType.VARCHAR(64).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), CAS_LOGIN_LOG, "用户名");

    /**
     * The column <code>boxin_saas_tenant.cas_login_log.real_name</code>. 用户姓名
     */
    public static final TableField<CasLoginLogRecord, String> REAL_NAME = createField("real_name", org.jooq.impl.SQLDataType.VARCHAR(64).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), CAS_LOGIN_LOG, "用户姓名");

    /**
     * The column <code>boxin_saas_tenant.cas_login_log.ip</code>. IP地址
     */
    public static final TableField<CasLoginLogRecord, String> IP = createField("ip", org.jooq.impl.SQLDataType.VARCHAR(32).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), CAS_LOGIN_LOG, "IP地址");

    /**
     * The column <code>boxin_saas_tenant.cas_login_log.type</code>. 登陆日志类型 1:登入 2:登出
     */
    public static final TableField<CasLoginLogRecord, UByte> TYPE = createField("type", org.jooq.impl.SQLDataType.TINYINTUNSIGNED.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.TINYINTUNSIGNED)), CAS_LOGIN_LOG, "登陆日志类型 1:登入 2:登出");

    /**
     * The column <code>boxin_saas_tenant.cas_login_log.inserttime</code>. 创建时间
     */
    public static final TableField<CasLoginLogRecord, Timestamp> INSERTTIME = createField("inserttime", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.field("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), CAS_LOGIN_LOG, "创建时间");

    /**
     * The column <code>boxin_saas_tenant.cas_login_log.updatetime</code>. 更新时间
     */
    public static final TableField<CasLoginLogRecord, Timestamp> UPDATETIME = createField("updatetime", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.field("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), CAS_LOGIN_LOG, "更新时间");

    /**
     * The column <code>boxin_saas_tenant.cas_login_log.isactive</code>. 逻辑删除
     */
    public static final TableField<CasLoginLogRecord, Byte> ISACTIVE = createField("isactive", org.jooq.impl.SQLDataType.TINYINT.nullable(false).defaultValue(org.jooq.impl.DSL.inline("1", org.jooq.impl.SQLDataType.TINYINT)), CAS_LOGIN_LOG, "逻辑删除");

    /**
     * No further instances allowed
     */
    private CasLoginLog() {
        this(DSL.name("cas_login_log"), null);
    }

    private CasLoginLog(Name alias, Table<CasLoginLogRecord> aliased) {
        this(alias, aliased, null);
    }

    private CasLoginLog(Name alias, Table<CasLoginLogRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "智牛SaaS平台登录日志表, owner: guiqingqing, manager: zhengkeshuang");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Schema getSchema() {
        return BoxinSaasTenant.BOXIN_SAAS_TENANT;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<CasLoginLogRecord, ULong> getIdentity() {
        return Keys.IDENTITY_CAS_LOGIN_LOG;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<CasLoginLogRecord> getPrimaryKey() {
        return Keys.KEY_CAS_LOGIN_LOG_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<CasLoginLogRecord>> getKeys() {
        return Arrays.<UniqueKey<CasLoginLogRecord>>asList(Keys.KEY_CAS_LOGIN_LOG_PRIMARY);
    }
}

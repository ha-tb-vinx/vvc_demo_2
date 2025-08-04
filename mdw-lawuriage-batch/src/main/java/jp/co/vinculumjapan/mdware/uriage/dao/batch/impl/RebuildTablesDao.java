package jp.co.vinculumjapan.mdware.uriage.dao.batch.impl;

import java.sql.ResultSet;
import java.util.StringTokenizer;

import jp.co.vinculumjapan.swc.commons.dao.DaoIf;
import jp.co.vinculumjapan.swc.commons.dao.DaoInvokerIf;
import jp.co.vinculumjapan.swc.commons.dao.PreparedStatementEx;

/**
 *  <P>タイトル: テーブル再編成処理</p>
 *  <P>説明:テーブル再編成を行う処理です。</p>
 *  <P>著作権: Copyright (c) 2010</p>
 *  <P>会社名: Vinculum Japan Corporation</P>
 *  @JUnit jp.co.vinculumjapan.mdware.uriage.dao.batch.impl.RebuildIndexesDaoInvoker
 *  @author K.shibuya
 *  @version 1.0 2010/05/07 初版作成
 */
public class RebuildTablesDao implements DaoIf {

    String input_ = null;

    public void setInputObject(Object input) throws Exception {
        input_ = (String) input;
    }

    public Object getOutputObject() throws Exception {
        return null;
    }

    public void executeDataAccess(DaoInvokerIf daoInvoker) throws Exception {
        StringTokenizer tokenizer = new StringTokenizer(input_,",");
        while (tokenizer.hasMoreTokens()) {
            //テーブル再編成処理
            String tableName = tokenizer.nextToken();
            daoInvoker.infoLog(daoInvoker.getUserId() + "：テーブル再編成を開始します。(" + tableName + ")");

            StringBuffer tableRebuildSql = new StringBuffer();
            tableRebuildSql.append("ALTER TABLE ").append(tableName).append(" MOVE");
            daoInvoker.getDataBase().executeUpdate(tableRebuildSql.toString());

            //再編成されたテーブルのインデックスをリビルド
            PreparedStatementEx pstmnt = daoInvoker.getDataBase().prepareStatement(getTargetIndexesSQL(tableName));
            ResultSet rs = pstmnt.executeQuery();
            while (rs.next()) {
                daoInvoker.infoLog(daoInvoker.getUserId() + "：インデックスリビルドを開始します。(INDEX:" + rs.getString("INDEX_NAME") + ")");
                StringBuffer indexRebuildSql = new StringBuffer();
                indexRebuildSql.append("ALTER INDEX " + rs.getString("OWNER") + "." + rs.getString("INDEX_NAME") + " REBUILD");
                daoInvoker.getDataBase().executeUpdate(indexRebuildSql.toString());
            }
        }
    }

    private String getTargetIndexesSQL(String tableName) {
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT ");
        sb.append("  OWNER, TABLE_NAME, INDEX_NAME ");
        sb.append("FROM ");
        sb.append("  ALL_INDEXES ");
        sb.append(" WHERE ");
        sb.append("  TABLE_NAME = ");
        sb.append("'").append(tableName).append("' ");
        sb.append("ORDER BY ");
        sb.append("  TABLE_NAME, INDEX_NAME ");
        return sb.toString();
    }
}

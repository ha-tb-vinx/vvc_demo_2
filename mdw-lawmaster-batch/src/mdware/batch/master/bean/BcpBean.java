package mdware.batch.master.bean;

/**
 * <p>タイトル: RB Site</p>
 * <p>説明: </p>
 * <p>著作権: Copyright (c) 2002</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author 未入力
 * @version 1.0
 * @version 1.1 kaneda Oracle、SQLServerに対応するために改変
 */

public class BcpBean {

	private String bcpCode = null;
	private String dataSyubetuCode = null;
	private String masterName = null;
	private String tableName = null;
	private String userId = null;
	private String password = null;
	private String serverName = null;
	private String formatFile = null;
	private String dbKind = null;
	private String[] primaryKey = null;  //一つだけ

	public BcpBean() {
	}

	public BcpBean(String bcpCode, String dataSyubetuCode, String masterName, String tableName, String userId, String password, String serverName, String formatFile, String dbKind, String[] primaryKey) {
		this.bcpCode = bcpCode;
		this.dataSyubetuCode = dataSyubetuCode;
		this.masterName = masterName;
		this.tableName = tableName;
		this.userId = userId;
		this.password = password;
		this.serverName = serverName;
		this.formatFile = formatFile;
		this.dbKind = dbKind;
		this.primaryKey = primaryKey;
	}

    public String getDataSyubetuCode() {
        return dataSyubetuCode;
    }
    public void setDataSyubetuCode(String dataSyubetuCode) {
        this.dataSyubetuCode = dataSyubetuCode;
    }
    public String getFormatFile() {
        return formatFile;
    }
    public void setFormatFile(String formatFile) {
        this.formatFile = formatFile;
    }
    public String getMasterName() {
        return masterName;
    }
    public void setMasterName(String masterName) {
        this.masterName = masterName;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getServerName() {
        return serverName;
    }
    public void setServerName(String serverName) {
        this.serverName = serverName;
    }
    public String getTableName() {
        return tableName;
    }
    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getBcpCode() {
        return bcpCode;
    }
    public void setBcpCode(String bcpCode) {
        this.bcpCode = bcpCode;
    }
    public String[] getPrimaryKey() {
        return primaryKey;
    }
    public String getPrimaryKeyString() {
		String ret = "";
		for (int i = 0 ; i < this.primaryKey.length ; i++) {
			if (i == 0) {
			    ret += primaryKey[i];
			} else {
			    ret += " , " + primaryKey[i];
			}
		}
        return ret;
    }
// 20030212 @ADD yamanaka start
    public String getConnectedPrimaryKey() {
		String ret = "";
		for (int i = 0 ; i < this.primaryKey.length ; i++) {
			if (i == 0) {
			    ret += primaryKey[i];
			} else {
			    ret += " + " + primaryKey[i];
			}
		}
        return ret;
    }
// 20030212 @ADD yamanaka end
    public void setPrimaryKey(String[] primaryKey) {
        this.primaryKey = primaryKey;
    }
    
	public String getDBKind() {
		return this.dbKind;
	}
	public void setDBKind(String dbKind) {
		this.dbKind = dbKind;
	}

}
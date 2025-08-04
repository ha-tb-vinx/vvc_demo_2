package mdware.common.batch.util.file.record;

import java.io.*;
import java.util.*;

/**
 * <p>タイトル: ImportBean</p>
 * <p>説明: </p>
 * <p>著作権: Copyright (c) 2002</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author yamanaka
 * @version 1.00 2003.12.29
 */

public class ImportBean {
	private String fieldNb = null;
	private String fieldType = null;
	private String prefixSize = null;
	private String dataSize = null;
	private String endChar = null;
	private String colNb = null;
	private String colName = null;
	private String compareNb = null;

	public ImportBean() {
	}

	public ImportBean(String fieldNb, String fieldType, String prefixSize, String dataSize, String endChar, String colNb, String colName, String compareNb) {
		this.fieldNb = fieldNb;
		this.fieldType = fieldType;
		this.prefixSize = prefixSize;
		this.dataSize = dataSize;
		this.endChar = endChar;
		this.colNb = colNb;
		this.colName = colName;
		this.compareNb = compareNb;
	}

    public String getColName() {
        return colName;
    }
    public void setColName(String colName) {
        this.colName = colName;
    }
    public String getColNb() {
        return colNb;
    }
    public void setColNb(String colNb) {
        this.colNb = colNb;
    }
    public void setCompareNb(String compareNb) {
        this.compareNb = compareNb;
    }
    public String getCompareNb() {
        return compareNb;
    }
    public String getDataSize() {
        return dataSize;
    }
    public void setDataSize(String dataSize) {
        this.dataSize = dataSize;
    }
    public String getEndChar() {
        return endChar;
    }
    public void setEndChar(String endChar) {
        this.endChar = endChar;
    }
    public String getFieldNb() {
        return fieldNb;
    }
    public void setFieldNb(String fieldNb) {
        this.fieldNb = fieldNb;
    }
    public String getFieldType() {
        return fieldType;
    }
    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }
    public String getPrefixSize() {
        return prefixSize;
    }
    public void setPrefixSize(String prefixSize) {
        this.prefixSize = prefixSize;
    }


}

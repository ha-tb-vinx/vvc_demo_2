#!/bin/csh
#===============================================================================
#	ジョブID　　：　URIB161099
#	ファイル名　：　OnMemoryMasterImportBackup.sh
#	引数　　　　：　
#	処理内容　　：　オンメモリ用マスタデータバックアップ処理
#	作成者　　　：　新坂
#	作成日　　　：　2009.05.26
#	更新履歴　　：　2009.07.04 VJC.福永 ファイルバックアップ処理追加
#===============================================================================

# 共通変数読込
source ./CommonEnv.sh

# ジョブIDセット
set JOB_ID=URIB161
set EXE_MACRO_PATH=$MACRO_PATH/$JOB_ID

# ログファイル名セット
set LOG_FILE=$LOG_PATH/$JOB_ID.log

# 処理マクロ名セット
set ON_MEMORY_MASTER_IMPORT=OnMemoryMasterImport.opb

# バックアップ対象ファイルセット
set BACKUP_FILE=IF_R_FI_HANBAI.csv

#----------------------------------------------------------------------
# ファイルバックアップ(会計用販売マスタ)
#----------------------------------------------------------------------
echo "$BACKUP_FILE BACKUP START" >> $LOG_FILE
mv -f $CSV_PATH/$BACKUP_FILE $BACKUP_PATH

# 
@ result = $status
if ( $result != 0 ) then
	echo "### $BACKUP_FILE BACKUP ABORT END" >> $LOG_FILE
	exit($result)
else
	# ファイルバックアップは正常終了しました。
endif

echo "$BACKUP_FILE BACKUP NORMAL END" >> $LOG_FILE


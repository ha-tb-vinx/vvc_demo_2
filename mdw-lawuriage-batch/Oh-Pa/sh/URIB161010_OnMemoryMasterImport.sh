#!/bin/csh
#===============================================================================
#	ジョブID　　：　URIB161010
#	ファイル名　：　OnMemoryMasterImport.sh
#	引数　　　　：　
#	処理内容　　：　オンメモリ用マスタデータ取込処理
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
set ON_MEMORY_MASTER_IMPORT=URIB161010_OnMemoryMasterImport.opb

# バックアップ対象ファイルセット
set BACKUP_FILE=IF_R_FI_HANBAI.csv

#----------------------------------------------------------------------
# マクロ実行(オンメモリ用マスタデータ取込処理)
#----------------------------------------------------------------------
echo "$ON_MEMORY_MASTER_IMPORT START" >> $LOG_FILE
$OPA_EXE $USER_INFO \
	$EXE_MACRO_PATH/$ON_MEMORY_MASTER_IMPORT \
	$COMMON_PATH \
	$EXE_MACRO_PATH \
	$D5T_PATH \
	$CSV_PATH \
	$CTLG_PATH \
	$COMP_CD \
 >> $LOG_FILE

# 
@ result = $status
if ( $result != 0 ) then
	echo "### $ON_MEMORY_MASTER_IMPORT ABORT END" >> $LOG_FILE
	exit($result)
else
	# opa_execomは正常終了しました。
endif

echo "$ON_MEMORY_MASTER_IMPORT NORMAL END" >> $LOG_FILE


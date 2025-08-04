#!/bin/csh
#===============================================================================
#	ジョブID　　：　URIB061010
#	ファイル名　：　ShinTanpinMeisaiDataImport.sh
#	引数　　　　：　
#	処理内容　　：　新単品明細データ取込処理
#	作成者　　　：　新坂
#	作成日　　　：　2009.05.26
#	更新履歴　　：　2009.07.06 VJC.福永 バッチをマクロ単位に分割
#===============================================================================

# 共通変数読込
source ./CommonEnv.sh

# ジョブIDセット
set JOB_ID=URIB061
set EXE_MACRO_PATH=$MACRO_PATH/$JOB_ID

# ログファイル名セット
set LOG_FILE=$LOG_PATH/$JOB_ID.log

# 処理マクロ名セット
set SHIN_TANPIN_MEISAI_DATA_IMP=URIB061010_ShinTanpinMeisaiDataImport.opb

#----------------------------------------------------------------------
# マクロ実行(新単品明細データ取込処理)
#----------------------------------------------------------------------
echo "$SHIN_TANPIN_MEISAI_DATA_IMP START" >> $LOG_FILE
$OPA_EXE $USER_INFO \
	$EXE_MACRO_PATH/$SHIN_TANPIN_MEISAI_DATA_IMP \
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
	echo "### $SHIN_TANPIN_MEISAI_DATA_IMP ABORT END" >> $LOG_FILE
	exit($result)
else
	# opa_execomは正常終了しました。
endif
echo "$SHIN_TANPIN_MEISAI_DATA_IMP NORMAL END" >> $LOG_FILE

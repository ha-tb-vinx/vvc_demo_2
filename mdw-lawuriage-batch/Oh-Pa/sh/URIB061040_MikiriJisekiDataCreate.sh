#!/bin/csh
#===============================================================================
#	ジョブID　　：　URIB061040
#	ファイル名　：　MikiriJisekiDataCreate
#	引数　　　　：　
#	処理内容　　：　見切実績データ抽出処理
#	作成者　　　：　新坂
#	作成日　　　：　2009.05.26
#	更新履歴　　：　2009.07.03 VJC.福永 結果csvの出力先指定変更の為、引数追加
#	　　　　　　：　2009.07.06 VJC.福永 バッチをマクロ単位に分割
#===============================================================================

# 共通変数読込
source ./CommonEnv.sh

# ジョブIDセット
set JOB_ID=URIB061
set EXE_MACRO_PATH=$MACRO_PATH/$JOB_ID

# ログファイル名セット
set LOG_FILE=$LOG_PATH/$JOB_ID.log

# 処理マクロ名セット
set MIKIRI_JISEKI_DATA_CREATE=URIB061040_MikiriJisekiDataCreate.opb

#----------------------------------------------------------------------
# マクロ実行(見切実績データ抽出処理)
#----------------------------------------------------------------------
# 2009.07.03 VJC.福永 結果csvの出力先指定変更の為、引数追加（$OUT_PATH \）
echo "$MIKIRI_JISEKI_DATA_CREATE START" >> $LOG_FILE
$OPA_EXE $USER_INFO \
	$EXE_MACRO_PATH/$MIKIRI_JISEKI_DATA_CREATE \
	$COMMON_PATH \
	$EXE_MACRO_PATH \
	$D5T_PATH \
	$CSV_PATH \
	$CTLG_PATH \
	$COMP_CD \
	$OUT_PATH \
 >> $LOG_FILE

# 
@ result = $status
if ( $result != 0 ) then
	echo "### $MIKIRI_JISEKI_DATA_CREATE ABORT END" >> $LOG_FILE
	exit($result)
else
	# opa_execomは正常終了しました。
endif
echo "$MIKIRI_JISEKI_DATA_CREATE NORMAL END" >> $LOG_FILE

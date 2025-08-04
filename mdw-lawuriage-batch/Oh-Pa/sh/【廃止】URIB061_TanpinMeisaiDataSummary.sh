#!/bin/csh
#===============================================================================
#	ジョブID　　：　URIB061
#	ファイル名　：　TanpinMeisaiDataSummary.sh
#	引数　　　　：　
#	処理内容　　：　品明細情報集計処理
#	作成者　　　：　新坂
#	作成日　　　：　2009.05.26
#	更新履歴　　：　2009.07.03 VJC.福永 結果csvの出力先指定変更の為、引数追加
#	　　　　　　：　2009.07.04 VJC.福永 ファイルバックアップ処理追加
#===============================================================================

# 共通変数読込
source ./CommonEnv.sh

# ジョブIDセット
set JOB_ID=URIB061
set EXE_MACRO_PATH=$MACRO_PATH/$JOB_ID

# ログファイル名セット
set LOG_FILE=$LOG_PATH/$JOB_ID.log

# 処理マクロ名セット
set SHIN_TANPIN_MEISAI_DATA_IMP=ShinTanpinMeisaiDataImport.opb
set INSHIZEI_KENSU_COUNT=InshizeiKensuCount.opb
set NEJOUGE_DATA_CREATE=NejougeDataCreate.opb
set MIKIRI_JISEKI_DATA_CHUSYUTSU=MikiriJisekiDataChusyutsu.opb
set DPT_UCHI_URIAGE_DATA_CALC=DPTUchiUriageDataCalc.opb

#2009.07.04 VJC.福永 ファイルバックアップ処理追加
# バックアップ対象ファイルセット
set BACKUP_FILE1=SPAL.ftp
set BACKUP_FILE2=SPAL_SABUN.ftp

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

#----------------------------------------------------------------------
# マクロ実行(印紙税件数カウント処理)
#----------------------------------------------------------------------
# 2009.07.03 VJC.福永 結果csvの出力先指定変更の為、引数追加（$OUT_PATH \）
echo "$INSHIZEI_KENSU_COUNT START" >> $LOG_FILE
$OPA_EXE $USER_INFO \
	$EXE_MACRO_PATH/$INSHIZEI_KENSU_COUNT \
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
	echo "### $INSHIZEI_KENSU_COUNT ABORT END" >> $LOG_FILE
	exit($result)
else
	# opa_execomは正常終了しました。
endif
echo "$INSHIZEI_KENSU_COUNT NORMAL END" >> $LOG_FILE

#----------------------------------------------------------------------
# マクロ実行(値上下データ作成処理)
#----------------------------------------------------------------------
# 2009.07.03 VJC.福永 結果csvの出力先指定変更の為、引数追加（$OUT_PATH \）
echo "$NEJOUGE_DATA_CREATE START" >> $LOG_FILE
$OPA_EXE $USER_INFO \
	$EXE_MACRO_PATH/$NEJOUGE_DATA_CREATE \
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
	echo "### $NEJOUGE_DATA_CREATE ABORT END" >> $LOG_FILE
	exit($result)
else
	# opa_execomは正常終了しました。
endif
echo "$NEJOUGE_DATA_CREATE NORMAL END" >> $LOG_FILE

#----------------------------------------------------------------------
# マクロ実行(見切実績データ抽出処理)
#----------------------------------------------------------------------
# 2009.07.03 VJC.福永 結果csvの出力先指定変更の為、引数追加（$OUT_PATH \）
echo "$MIKIRI_JISEKI_DATA_CHUSYUTSU START" >> $LOG_FILE
$OPA_EXE $USER_INFO \
	$EXE_MACRO_PATH/$MIKIRI_JISEKI_DATA_CHUSYUTSU \
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
	echo "### $MIKIRI_JISEKI_DATA_CHUSYUTSU ABORT END" >> $LOG_FILE
	exit($result)
else
	# opa_execomは正常終了しました。
endif
echo "$MIKIRI_JISEKI_DATA_CHUSYUTSU NORMAL END" >> $LOG_FILE

#----------------------------------------------------------------------
# マクロ実行(DPT打ち売上データ集計処理)
#----------------------------------------------------------------------
# 2009.07.03 VJC.福永 結果csvの出力先指定変更の為、引数追加（$OUT_PATH \）
echo "$DPT_UCHI_URIAGE_DATA_CALC START" >> $LOG_FILE
$OPA_EXE $USER_INFO \
	$EXE_MACRO_PATH/$DPT_UCHI_URIAGE_DATA_CALC \
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
	echo "### $DPT_UCHI_URIAGE_DATA_CALC ABORT END" >> $LOG_FILE
	exit($result)
else
	# opa_execomは正常終了しました。
endif
echo "$DPT_UCHI_URIAGE_DATA_CALC NORMAL END" >> $LOG_FILE

#2009.07.04 VJC.福永 ファイルバックアップ処理追加
#----------------------------------------------------------------------
# ファイルバックアップ(新単品明細ログ)
#----------------------------------------------------------------------
echo "$BACKUP_FILE1 BACKUP START" >> $LOG_FILE
mv -f $CSV_PATH/$BACKUP_FILE1 $BACKUP_PATH

# 
@ result = $status
if ( $result != 0 ) then
	echo "### $BACKUP_FILE1 BACKUP ABORT END" >> $LOG_FILE
	exit($result)
else
	# ファイルバックアップは正常終了しました。
endif

echo "$BACKUP_FILE1 BACKUP NORMAL END" >> $LOG_FILE

#----------------------------------------------------------------------
# ファイルバックアップ(新単品明細ログ差分)
#----------------------------------------------------------------------
echo "$BACKUP_FILE2 BACKUP START" >> $LOG_FILE
mv -f $CSV_PATH/$BACKUP_FILE2 $BACKUP_PATH

# 
@ result = $status
if ( $result != 0 ) then
	echo "### $BACKUP_FILE2 BACKUP ABORT END" >> $LOG_FILE
	exit($result)
else
	# ファイルバックアップは正常終了しました。
endif

echo "$BACKUP_FILE2 BACKUP NORMAL END" >> $LOG_FILE


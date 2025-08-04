#!/bin/csh
#===============================================================================
#	ジョブID　　：　URIB061099
#	ファイル名　：　TanpinMeisaiDataBackup.sh
#	引数　　　　：　
#	処理内容　　：　新単品明細情報バックアップ処理
#	作成者　　　：　新坂
#	作成日　　　：　2009.05.26
#	更新履歴　　：　2009.07.04 VJC.福永 ファイルバックアップ処理追加
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
set SHIN_TANPIN_MEISAI_DATA_IMP=ShinTanpinMeisaiDataImport.opb
set INSHIZEI_KENSU_COUNT=InshizeiKensuCount.opb
set NEJOUGE_DATA_CREATE=NejougeDataCreate.opb
set MIKIRI_JISEKI_DATA_CHUSYUTSU=MikiriJisekiDataChusyutsu.opb
set DPT_UCHI_URIAGE_DATA_CALC=DPTUchiUriageDataCalc.opb

#2009.07.04 VJC.福永 ファイルバックアップ処理追加
# バックアップ対象ファイルセット
set BACKUP_FILE1=SPAL.ftp
set BACKUP_FILE2=SPAL_SABUN.ftp

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


#!/bin/csh
#===============================================================================
#	ジョブID　　：　URIB061060
#	ファイル名　：　ErrorLogDelete.sh
#	引数　　　　：　
#	処理内容　　：　エラーログ削除
#	作成者　　　：　林
#	作成日　　　：　2010.08.10
#===============================================================================

# 共通変数読込
source ./CommonEnv.sh

# ジョブIDセット
set JOB_ID=URIB061

# ログファイル名セット
set LOG_FILE=$LOG_PATH/$JOB_ID.log


#----------------------------------------------------------------------
# エラーログファイル削除処理実行
#----------------------------------------------------------------------
echo "ERROR_LOG_FILE_DELETE START" >> $LOG_FILE
set FILE_STR=URIB_061030ErrLog_`date -d "32 days ago" +"%Y%m%d"`
find $CSV_PATH -maxdepth 1 -mindepth 1 -name "$FILE_STR*" -exec rm {} \;

# 
@ result = $status
if ( $result != 0 ) then
	echo "### ERROR_LOG_FILE_DELETE ABORT END" >> $LOG_FILE
	exit($result)
else
	# エラーログファイル削除処理は正常終了しました。
endif
echo "ERROR_LOG_FILE_DELETE NORMAL END" >> $LOG_FILE

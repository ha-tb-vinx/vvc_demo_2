#!/bin/csh
#===============================================================================
#	ファイル名　：　CommonEnv.sh
#	引数　　　　：　
#	処理内容　　：　共通変数定義
#	作成者　　　：　新坂
#	作成日　　　：　2009.04.22
#	更新履歴　　：　2009.07.03 VJC.福永 本番機設定用に変更
#	　　　　　　：　2009.07.04 VJC.福永 バックアップパス追加
#===============================================================================

# OH-PAパス
set OPA_EXE=/opt/FJBSCohpa/bin/opa_execom

# ユーザ情報パス
set USER_INFO=mmuriage_op/mmuriage_op

# ベースディレクトリパス（データ）
set BASE_DIR_DATA=/share01/ftpdata

# ベースディレクトリパス（アプリ）
set BASE_DIR_AP=/share02/mdware/uriage/ohpa

# Oh-Paマクロパス
set MACRO_PATH=$BASE_DIR_AP/opb

# Oh-Paマクロ共通処理パス
set COMMON_PATH=$MACRO_PATH/common

# D5Tパス
set D5T_PATH=$BASE_DIR_AP/d5t

# CSVパス
set CSV_PATH=$BASE_DIR_DATA/ftp_receive/uriage

# CSVバックアップパス
set BACKUP_PATH=$BASE_DIR_DATA/ftp_receive/uriage/backup

# カタログパス
set CTLG_PATH=$BASE_DIR_AP/ctlg

# 法人コード
set COMP_CD=0000

# 出力パス
set OUT_PATH=$BASE_DIR_DATA/ftp_send/uriage

# ログ出力パス
set LOG_PATH=$BASE_DIR_AP/log

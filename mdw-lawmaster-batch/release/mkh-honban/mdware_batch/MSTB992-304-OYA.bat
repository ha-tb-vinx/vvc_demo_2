@echo off
setlocal enabledelayedexpansion
REM ----------------------------------------------------------------------------
REM  JOB-ID      : MSTB992
REM  BATCH-ID    : MSTB992-304-OYA
REM  IF          : MDware => POS
REM  IF-NAME     : SubClass
REM  FUNCTION    : COPY
REM  CRATOR      : G.SASAKI
REM  CREATE DATE : 2020/08/18
REM ----------------------------------------------------------------------------
REM  0-1. COMMON-SET
REM ----------------------------------------------------------------------------

REM (1)BATCH-ID 
SET BAT_ID=MSTB992-304-OYA

REM (2)LOG-DIR 
SET LOG_DIR=E:\mdware_mkh\batch\master\log\

REM (3)LOG-FILE-NAME 
SET LOG_FILE=IF_SEND_POS_OYA.log

REM (4)NG-LOG
SET NG-FIL=E:\mdware_mkh\batch\master\log\POS-IF-NG\C*

REM (5)SEND-DIR
set DirPath=H:\POS-HongKong\q5\mdware\LIVE\download\

REM 前回NGログを削除
del %NG-FIL%


REM ----------------------------------------------------------------------------
REM  1. POS-I/F SEND(COPY) 処理
REM     送信フォルダは店舗単位で存在する。
REM     存在する店舗フォルダ名を取得し、
REM     送信（コピー）処理を実行する子batにコピー先フォルダを引数で与え実行
REM ----------------------------------------------------------------------------

echo -------------------- [START] -------------------- >>%LOG_DIR%%LOG_FILE%

echo [%BAT_ID%][%DATE% %TIME%]                         >>%LOG_DIR%%LOG_FILE%

echo [1][START][COPY SUB-CLASS FILE]                   >>%LOG_DIR%%LOG_FILE%

   for /D %%F in (%DirPath%*) do (
      SET senddir=%%F
      echo COPY-TO-DIR --- !senddir!\                  >>%LOG_DIR%%LOG_FILE%
      
      call E:\mdware_mkh\batch\master\batch\batfiles\MSTB992-304.bat !senddir!\
      IF !ERRORLEVEL! NEQ 0 goto ABEND
     )

echo [1][E N D][COPY SUB-CLASS FILE]                   >>%LOG_DIR%%LOG_FILE%


REM ----------------------------------------------------------------------------
REM  2. POS-I/F SEND(COPY) 時のNG-CHECK処理
REM     送信処理の子batにNGがあったかチェックする
REM     1店舗でもNGが発生していた場合は、処理を異常終了し検知させる
REM ----------------------------------------------------------------------------

echo [2][START][COPY N G CHCK]                         >>%LOG_DIR%%LOG_FILE%

dir %NG-FIL% | findstr NG                              >>%LOG_DIR%%LOG_FILE%

IF exist %NG-FIL% (
       echo *************** COPY-NG ***************    >>%LOG_DIR%%LOG_FILE%
          goto ABEND
         )
       echo *** COPY-OK ***                            >>%LOG_DIR%%LOG_FILE%

echo [2][E N D][COPY N G CHCK]                         >>%LOG_DIR%%LOG_FILE%


REM ----------------------------------------------------------------------------
REM   2-2. END
REM ----------------------------------------------------------------------------

REM (1)NORMAL-END
echo [%BAT_ID%][%DATE% %TIME%]                         >>%LOG_DIR%%LOG_FILE%
echo -------------------- [ END ] -------------------- >>%LOG_DIR%%LOG_FILE%
echo.>>%LOG_DIR%%LOG_FILE%
CD %BATCHF_HOME%
exit 0


REM (2)ABNORMAL-END
:ABEND
echo [X-X][NG][ FILE COPY ] >>%LOG_DIR%%LOG_FILE%
echo [%BAT_ID%][%DATE% %TIME%] >>%LOG_DIR%%LOG_FILE%
echo -------------------- [ END ] -------------------- >>%LOG_DIR%%LOG_FILE%
echo.>>%LOG_DIR%%LOG_FILE%
CD %BATCHF_HOME%
exit 1

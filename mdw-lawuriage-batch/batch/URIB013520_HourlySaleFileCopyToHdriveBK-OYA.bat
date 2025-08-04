@echo off
setlocal enabledelayedexpansion
REM ----------------------------------------------------------------------------
REM  JOB-ID      : URIB013520
REM  BATCH-ID    : URIB013520_HourlySaleFileCopyToHdriveBK-OYA
REM  IF          : POS => MDware
REM  IF-NAME     : Sale-File
REM  FUNCTION    : COPY to Hdrive BackUp
REM  CRATOR      : G.SASAKI
REM  CREATE DATE : 2020/08/20
REM ----------------------------------------------------------------------------
REM  0-1. COMMON-SET
REM ----------------------------------------------------------------------------

REM (1)BATCH-ID 
SET BAT_ID=URIB013520_HourlySaleFileCopyToHdriveBK-OYA

REM (2)LOG-DIR 
SET LOG_DIR=@filter.shellscript.baseDirectory.value@\log\

REM (3)LOG-FILE-NAME 
SET LOG_FILE=IF_RECEIVE_POS_HOURLY_OYA.log

REM (4)NG-LOG
SET NG-FIL=@filter.env.baseDirectory.value@\batch\uriage\log\POS-IF-NG\R*

REM (5)SEND-DIR
set DirPath=@filter.pos.send.Directory.value@\mdware\LIVE\upload\ 

REM 前回NGログを削除
del %NG-FIL%


REM ----------------------------------------------------------------------------
REM  1. POS-I/F RECEIVE (COPY) 処理
REM     送信フォルダは店舗単位で存在する。
REM     存在する店舗フォルダ名を取得し、
REM     送信（コピー）処理を実行する子batに移動先フォルダを引数で与え実行
REM ----------------------------------------------------------------------------

echo -------------------- [START] --------------------  >>%LOG_DIR%%LOG_FILE%

echo [%BAT_ID%][%DATE% %TIME%]                          >>%LOG_DIR%%LOG_FILE%

echo [1][START][COPY DIV FILE]                          >>%LOG_DIR%%LOG_FILE%

   for /D %%F in (%DirPath%*) do (
      SET senddir=%%F
      set num=%%F
      set num=!num:~-3,3!
      echo COPY-TO-DIR --- !senddir!\                   >>%LOG_DIR%%LOG_FILE%
      
      call @filter.shellscript.baseDirectory.value@\batch\URIB013520_HourlySaleFileCopyToHdriveBK.bat !senddir!\ !num!
      IF !ERRORLEVEL! NEQ 0 goto ABEND
     )

echo [1][E N D][COPY DIV FILE]                          >>%LOG_DIR%%LOG_FILE%


REM ----------------------------------------------------------------------------
REM  2. POS-I/F RECEIVE(COPY) 時のNG-CHECK処理
REM     送信処理の子batにNGがあったかチェックする
REM     1店舗でもNGが発生していた場合は、処理を異常終了し検知させる
REM ----------------------------------------------------------------------------

echo [2][START][COPY N G CHCK]                          >>%LOG_DIR%%LOG_FILE%

dir %NG-FIL% | findstr NG                               >>%LOG_DIR%%LOG_FILE%
IF exist %NG-FIL% (
       echo **************** COPY-NG ****************   >>%LOG_DIR%%LOG_FILE%
          goto ABEND
         )
       echo COPY-OK                                     >>%LOG_DIR%%LOG_FILE%

echo [2][E N D][COPY N G CHCK]                          >>%LOG_DIR%%LOG_FILE%


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
echo [X-X][NG][ FILE COPY ]                            >>%LOG_DIR%%LOG_FILE%
echo [%BAT_ID%][%DATE% %TIME%]                         >>%LOG_DIR%%LOG_FILE%
echo -------------------- [ END ] -------------------- >>%LOG_DIR%%LOG_FILE%
echo.>>%LOG_DIR%%LOG_FILE%
CD %BATCHF_HOME%
exit 1

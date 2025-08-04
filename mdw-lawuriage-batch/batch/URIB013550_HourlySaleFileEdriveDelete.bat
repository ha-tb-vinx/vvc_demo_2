@echo off
REM ----------------------------------------------------------------------------
REM  JOB-ID      : URIB013550
REM  BATCH-ID    : URIB013550
REM  IF          : POS => MDware
REM  IF-NAME     : HourlySaleFile
REM  FUNCTION    : EdriveFileDelete
REM  CRATOR      : G.SASAKI
REM  CREATE DATE : 2016/05/25
REM ----------------------------------------------------------------------------
REM  1-1. COMMON-SET
REM ----------------------------------------------------------------------------
REM (1)BATCH-ID 
SET BAT_ID=URIB013550_HourlySaleFile_Delete

REM (2)LOG-DIR 
SET LOG_DIR=@filter.shellscript.baseDirectory.value@\log\

REM (3)LOG-FILE-NAME 
SET LOG_FILE=IF_RECEIVE_POS_HOURLY.log

REM ----------------------------------------------------------------------------
REM   1. MAIN START
REM ----------------------------------------------------------------------------
echo -------------------- [START] -------------------- >>%LOG_DIR%%LOG_FILE%
echo [%BAT_ID%][%DATE% %TIME%] >>%LOG_DIR%%LOG_FILE%


dir /a-d /b @filter.env.baseDirectory.value@\datas@filter.env.value@\POS\q5\mdware\upload\R >>%LOG_DIR%%LOG_FILE%
dir /a-d /b @filter.env.baseDirectory.value@\datas@filter.env.value@\POS\q5\mdware\upload\R\wk >>%LOG_DIR%%LOG_FILE%
del /Q @filter.env.baseDirectory.value@\datas@filter.env.value@\POS\q5\mdware\upload\R
del /Q @filter.env.baseDirectory.value@\datas@filter.env.value@\POS\q5\mdware\upload\R\wk
IF %ERRORLEVEL% NEQ 0 goto error
echo [1-1][OK][ DEL Edrive SeleFile ] >>%LOG_DIR%%LOG_FILE%


echo [%BAT_ID%][%DATE% %TIME%] >>%LOG_DIR%%LOG_FILE%
echo -------------------- [ END ] -------------------- >>%LOG_DIR%%LOG_FILE%
echo.>>%LOG_DIR%%LOG_FILE%
CD %BATCHF_HOME%
exit 0

REM (2)ABNORMAL-END
:error
echo [1-X][NG][ FILE DEL ] >>%LOG_DIR%%LOG_FILE%
echo [%BAT_ID%][%DATE% %TIME%] >>%LOG_DIR%%LOG_FILE%
echo -------------------- [ END ] -------------------- >>%LOG_DIR%%LOG_FILE%
echo.>>%LOG_DIR%%LOG_FILE%
CD %BATCHF_HOME%
exit 1


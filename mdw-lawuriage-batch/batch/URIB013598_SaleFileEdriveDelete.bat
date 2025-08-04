@echo off
REM ----------------------------------------------------------------------------
REM  JOB-ID      : URIB013598
REM  BATCH-ID    : URIB013598
REM  IF          : POS => MDware
REM  IF-NAME     : SALE-FILE
REM  FUNCTION    : DELETE-E:drive-IF-FILE
REM  CRATOR      : G.SASAKI
REM  CREATE DATE : 2020/09/15
REM  Comment     : MSTB908-902.bat copy
REM ----------------------------------------------------------------------------
REM  1-1. COMMON-SET
REM ----------------------------------------------------------------------------
REM (1)BATCH-ID 
SET BAT_ID=URIB013598

REM (2)LOG-DIR 
SET LOG_DIR=@filter.shellscript.baseDirectory.value@\log\

REM (3)LOG-FILE-NAME 
SET LOG_FILE=IF_RECEIVE_POS.log

REM ----------------------------------------------------------------------------
REM   2-1. MAIN START
REM ----------------------------------------------------------------------------
echo -------------------- [START] --------------------     >>%LOG_DIR%%LOG_FILE%
echo [%BAT_ID%][%DATE% %TIME%]                             >>%LOG_DIR%%LOG_FILE%
echo [DELETE-E:drive-SaleFile]                             >>%LOG_DIR%%LOG_FILE%

REM DEL-FILE-CHCK
dir /a-d /b @filter.env.baseDirectory.value@\datas@filter.env.value@\POS\q5\mdware\upload\S\S* >>%LOG_DIR%%LOG_FILE%
dir /a-d /b @filter.env.baseDirectory.value@\datas@filter.env.value@\POS\q5\mdware\upload\S\wk >>%LOG_DIR%%LOG_FILE%

REM FILE-DELETE
del @filter.env.baseDirectory.value@\datas@filter.env.value@\POS\q5\mdware\upload\S\S*
del /Q @filter.env.baseDirectory.value@\datas@filter.env.value@\POS\q5\mdware\upload\S\wk
IF %ERRORLEVEL% NEQ 0 goto error
echo [1-1][OK][ DEL-SaleFile ]                             >>%LOG_DIR%%LOG_FILE%


REM ----------------------------------------------------------------------------
REM   2. MAIN END
REM ----------------------------------------------------------------------------
REM (1)NORMAL-END
echo [%BAT_ID%][%DATE% %TIME%] >>%LOG_DIR%%LOG_FILE%
echo -------------------- [ END ] --------------------     >>%LOG_DIR%%LOG_FILE%
echo.                                                      >>%LOG_DIR%%LOG_FILE%
CD %BATCHF_HOME%
exit 0

REM (2)ABNORMAL-END
:error
echo [1-X][NG][ FILE DEL ]                                 >>%LOG_DIR%%LOG_FILE%
echo [%BAT_ID%][%DATE% %TIME%] >>%LOG_DIR%%LOG_FILE%
echo -------------------- [ END ] --------------------     >>%LOG_DIR%%LOG_FILE%
echo.>>%LOG_DIR%%LOG_FILE%
CD %BATCHF_HOME%
exit 1

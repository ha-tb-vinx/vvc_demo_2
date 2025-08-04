@echo off
REM ----------------------------------------------------------------------------
REM  JOB-ID      : MSTB994
REM  BATCH-ID    : MSTB994-901
REM  IF          : MDware => POS
REM  IF-NAME     : 5FILE (DIV DEPT CLASS SUBCLASS ITEM)
REM  FUNCTION    : DELETE-E:drive-IF-FILE
REM  CRATOR      : M.Son
REM  CREATE DATE : 2017/01/26
REM  UPDATE      : 2017/05/18 S.Takayama #5059
REM ----------------------------------------------------------------------------
REM  1-1. COMMON-SET
REM ----------------------------------------------------------------------------
REM (1)BATCH-ID 
SET BAT_ID=MSTB994-901

REM (2)LOG-DIR 
SET LOG_DIR=E:\mdware_law\batch\master\log\

REM (3)LOG-FILE-NAME 
SET LOG_FILE=IF_SEND_POS.log

REM (4)BKUP-DAY-STI
call E:\mdware_law\batch\z_common\SetBatchDAY.bat

REM ----------------------------------------------------------------------------
REM   1. MAIN START
REM ----------------------------------------------------------------------------
echo -------------------- [START] -------------------- >>%LOG_DIR%%LOG_FILE%
echo [%BAT_ID%][%DATE% %TIME%] >>%LOG_DIR%%LOG_FILE%
echo [Edirve File Delete] >>%LOG_DIR%%LOG_FILE%

dir /a-d /b E:\mdware_law\datas\MKV\POS\q5\mdware\download_sti\%BKUP_DAY_STI% >>%LOG_DIR%%LOG_FILE%
rd /s /q E:\mdware_law\datas\MKV\POS\q5\mdware\download_sti\%BKUP_DAY_STI%
echo [1-1][OK][ DEL DIV FILE] >>%LOG_DIR%%LOG_FILE%

REM ----------------------------------------------------------------------------
REM   2. MAIN END
REM ----------------------------------------------------------------------------
REM (1)NORMAL-END
echo [%BAT_ID%][%DATE% %TIME%] >>%LOG_DIR%%LOG_FILE%
echo -------------------- [ END ] -------------------- >>%LOG_DIR%%LOG_FILE%
echo.>>%LOG_DIR%%LOG_FILE%
CD %BATCHF_HOME%
exit 0


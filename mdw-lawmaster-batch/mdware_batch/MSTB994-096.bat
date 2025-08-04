@echo off
REM ----------------------------------------------------------------------------
REM  JOB-ID      : MSTB994
REM  BATCH-ID    : MSTB994-096
REM  IF          : MDware => POS
REM  IF-NAME     : ITEM
REM  FUNCTION    : BACKUP-E:drive
REM  CRATOR      : M.Son
REM  CREATE DATE : 2017/01/26
REM  UPDATE      : 2017/05/18 S.Takayama #5059
REM ----------------------------------------------------------------------------
REM  1-1. COMMON-SET
REM ----------------------------------------------------------------------------
REM (1)BATCH-ID 
SET BAT_ID=MSTB994-096

REM (2)LOG-DIR 
SET LOG_DIR=E:\mdware_law\batch\master\log\

REM (3)LOG-FILE-NAME 
SET LOG_FILE=IF_SEND_POS.log

REM (4)BACKUP-DIR
call E:\mdware_law\batch\z_common\SetBatchDAY.bat
SET WK_TIME=%time: =0%
SET BKUP_TIME=%WK_TIME:~0,2%%WK_TIME:~3,2%
SET BKUPDIR=%BKUP_DAY%-%BKUP_TIME%

REM ----------------------------------------------------------------------------
REM  1-2. COPY-SET
REM ----------------------------------------------------------------------------
REM (1)COPY-FROM-DIR 
SET SND_FROM_DIR=E:\mdware_law\datas\MKV\POS\q5\mdware\download_sti\%BKUP_DAY_STI%

REM (3)COPY-TO-DIR 
SET SND_TO_DIR=E:\mdware_law\datas\MKV\POS\q5\mdware\download_sti\backup\%BKUPDIR%\%BKUP_DAY_STI%

REM ----------------------------------------------------------------------------
REM   2-1. MAIN START
REM ----------------------------------------------------------------------------
echo -------------------- [START] -------------------- >>%LOG_DIR%%LOG_FILE%
echo [%BAT_ID%][%DATE% %TIME%] >>%LOG_DIR%%LOG_FILE%
echo [Edrive BKUP ITEM FILE] >>%LOG_DIR%%LOG_FILE%

REM ----------------------------------------------------------------------------
REM   2-2. BEFORE COPY CHECK
REM ----------------------------------------------------------------------------
REM (1)CHECK COPY-FROM-DIR 
IF not exist %SND_FROM_DIR% (
goto end
)
mkdir E:\mdware_law\datas\MKV\POS\q5\mdware\download_sti\backup\%BKUPDIR%\%BKUP_DAY_STI%

REM (2)CHECK-COPY-TO-DIR 
IF not exist %SND_TO_DIR% (
echo [1-2][NG][CHECK-COPY- TO -DIR] >>%LOG_DIR%%LOG_FILE%
echo %SND_TO_DIR% directory not exist!>>%LOG_DIR%%LOG_FILE%
goto error
)
echo [1-2][OK][CHECK-COPY- TO -DIR] >>%LOG_DIR%%LOG_FILE%

REM ----------------------------------------------------------------------------
REM   2-3. COPY
REM ----------------------------------------------------------------------------
REM (1)COPY
XCOPY %SND_FROM_DIR% %SND_TO_DIR% /E /H /I /Y 
IF %ERRORLEVEL% NEQ 0 goto error
echo [2-1][OK][ FILE COPY ] >>%LOG_DIR%%LOG_FILE%

REM (2)COPY FILE CHCK
dir /a-d /b %SND_FROM_DIR% >>%LOG_DIR%%LOG_FILE%
:end

REM ----------------------------------------------------------------------------
REM   2-4. END
REM ----------------------------------------------------------------------------
REM (1)NORMAL-END
echo [%BAT_ID%][%DATE% %TIME%] >>%LOG_DIR%%LOG_FILE%
echo -------------------- [ END ] -------------------- >>%LOG_DIR%%LOG_FILE%
echo.>>%LOG_DIR%%LOG_FILE%
CD %BATCHF_HOME%
exit 0

REM (2)ABNORMAL-END
:error
echo [2-1][NG][ FILE COPY ] >>%LOG_DIR%%LOG_FILE%
echo [%BAT_ID%][%DATE% %TIME%] >>%LOG_DIR%%LOG_FILE%
echo -------------------- [ END ] -------------------- >>%LOG_DIR%%LOG_FILE%
echo.>>%LOG_DIR%%LOG_FILE%
CD %BATCHF_HOME%
exit 1

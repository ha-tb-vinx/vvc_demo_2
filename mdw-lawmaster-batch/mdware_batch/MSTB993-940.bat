@echo off
REM ----------------------------------------------------------------------------
REM  JOB-ID      : MSTB993
REM  BATCH-ID    : MSTB993-940
REM  IF          : MDware => POS
REM  IF-NAME     : MASTER-IF-FILE
REM  FUNCTION    : DELETE-E:drive-BKUP-IF-FILE STI-FOLDER
REM  CRATOR      : M.Akagi
REM  CREATE DATE : 2017/05/15
REM ----------------------------------------------------------------------------
REM  1-1. COMMON-SET
REM ----------------------------------------------------------------------------
REM (1)BATCH-ID 
SET BAT_ID=MSTB993-940

REM (2)LOG-DIR 
SET LOG_DIR=E:\mdware_law\batch\master\log\

REM (3)LOG-FILE-NAME 
SET LOG_FILE=IF_SEND_POS.log

REM (4)BACKUP-DIR
call E:\mdware_law\batch\z_common\SetBatchDAY.bat
SET NOW_DATE=%YY%/%MM%/%DD%
SET DEL_DATE=%DEL_YY%/%DEL_MM%/%DEL_DD%
SET ROOT_FOLDER=E:\mdware_law\datas\MKV\POS\q5\mdware\download_sti
SET DEL_TARGET=backup\%DEL_YY%-%DEL_MM%%DEL_DD%

REM ----------------------------------------------------------------------------
REM   2-1. MAIN START
REM ----------------------------------------------------------------------------
echo -------------------- [START] -------------------- >>%LOG_DIR%%LOG_FILE%
echo [%BAT_ID%][%DATE% %TIME%] >>%LOG_DIR%%LOG_FILE%
echo [DELETE-H:drive-IF-FILE] >>%LOG_DIR%%LOG_FILE%

echo [TODAY:%NOW_DATE%] >>%LOG_DIR%%LOG_FILE%
echo [DELDT:%DEL_DATE%] >>%LOG_DIR%%LOG_FILE%

echo [DELFOLDER:%ROOT_FOLDER%\%DEL_TARGET%] >>%LOG_DIR%%LOG_FILE%
for /d %%1 in (%ROOT_FOLDER%\%DEL_TARGET%*) do (
    rmdir /s /q %%1
    IF %ERRORLEVEL% NEQ 0 goto error
)

echo [1-1][OK][ DEL POS FILE] >>%LOG_DIR%%LOG_FILE%

REM ----------------------------------------------------------------------------
REM   2. MAIN END
REM ----------------------------------------------------------------------------
REM (1)NORMAL-END
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

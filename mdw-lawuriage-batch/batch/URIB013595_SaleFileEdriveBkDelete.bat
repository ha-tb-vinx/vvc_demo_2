@echo off
REM ----------------------------------------------------------------------------
REM  JOB-ID      : URIB013595
REM  BATCH-ID    : URIB013595
REM  IF          : POS => MDware
REM  IF-NAME     : SALE-FILE
REM  FUNCTION    : DELETE-E:drive-BKUP-IF-FILE
REM  CRATOR      : M.Akagi
REM  CREATE DATE : 2017/05/15
REM ----------------------------------------------------------------------------
REM  1-1. COMMON-SET
REM ----------------------------------------------------------------------------
REM (1)BATCH-ID 
SET BAT_ID=URIB013595

REM (2)LOG-DIR 
SET LOG_DIR=@filter.shellscript.baseDirectory.value@\log\

REM (3)LOG-FILE-NAME 
SET LOG_FILE=IF_RECEIVE_POS.log

REM (4)BACKUP-DIR
call @filter.env.baseDirectory.value@\batch\z_common\SetBatchDAY.bat
SET NOW_DATE=%YY%/%MM%/%DD%
SET DEL_DATE=%DEL_YY%/%DEL_MM%/%DEL_DD%
SET DEL_DATE_ZIP=%DEL_YY%%DEL_MM%%DEL_DD%
SET ROOT_FOLDER=@filter.env.baseDirectory.value@\datas@filter.env.value@\POS\q5\mdware\upload
SET DEL_TARGET=backup\%DEL_YY%-%DEL_MM%%DEL_DD%

REM ----------------------------------------------------------------------------
REM   2-1. MAIN START
REM ----------------------------------------------------------------------------
echo -------------------- [START] -------------------- >>%LOG_DIR%%LOG_FILE%
echo [%BAT_ID%][%DATE% %TIME%] >>%LOG_DIR%%LOG_FILE%
echo [DELETE-H:drive-IF-FILE] >>%LOG_DIR%%LOG_FILE%

echo [TODAY:%NOW_DATE%] >>%LOG_DIR%%LOG_FILE%
echo [DELDT:%DEL_DATE%] >>%LOG_DIR%%LOG_FILE%

echo [DELFOLDER:%ROOT_FOLDER%\R\%DEL_TARGET%] >>%LOG_DIR%%LOG_FILE%
for /d %%1 in (%ROOT_FOLDER%\R\%DEL_TARGET%*) do (
    rmdir /s /q %%1
    IF %ERRORLEVEL% NEQ 0 goto error
)
echo [DELFOLDER:%ROOT_FOLDER%\S\%DEL_TARGET%] >>%LOG_DIR%%LOG_FILE%
for /d %%1 in (%ROOT_FOLDER%\S\%DEL_TARGET%*) do (
    rmdir /s /q %%1
    IF %ERRORLEVEL% NEQ 0 goto error
)
echo [DELFILE:%ROOT_FOLDER%\S\%DEL_DATE_ZIP%*PosJournalImport.zip] >>%LOG_DIR%%LOG_FILE%
del /q %ROOT_FOLDER%\S\%DEL_DATE_ZIP%*PosJournalImport.zip
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

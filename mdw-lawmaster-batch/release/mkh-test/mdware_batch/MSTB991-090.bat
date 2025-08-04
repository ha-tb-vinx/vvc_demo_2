@echo off
REM ----------------------------------------------------------------------------
REM  JOB-ID      : MSTB991
REM  BATCH-ID    : MSTB991-090
REM  IF          : MDware => FAST
REM  IF-NAME     : FAST向けIFファイル削除 全IF
REM  FUNCTION    : 送信ファイルをEドライブ上から削除する
REM  CRATOR      : Y.MOCHIZUKI
REM  CREATE DATE : 2020/10/01
REM  UPDATE      : 
rem ----------------------------------------------------------------------------
REM  1-1. COMMON-SET
REM ----------------------------------------------------------------------------
REM (1)BATCH-ID 
SET BAT_ID=MSTB991-090

REM (2)LOG-DIR 
SET LOG_DIR=E:\mdware_mkh\batch\master\log

REM (3)LOG-FILE-NAME 
SET LOG_FILE=IF_SEND_FAST.log

REM ----------------------------------------------------------------------------
REM  1-2. DELETE-SET
REM ----------------------------------------------------------------------------
REM (1)DELETE-DIR
SET DELETE_DIR=E:\mdware_mkh\datas\MKH\FAST

REM (2)DELETE-FILE-NAME
SET DELETE_FILE_NAME=fast_*.csv


REM ----------------------------------------------------------------------------
REM   2-1. MAIN START
REM ----------------------------------------------------------------------------
echo -------------------- [START] --------------------     >>%LOG_DIR%\%LOG_FILE%
echo [%BAT_ID%][%DATE% %TIME%]                             >>%LOG_DIR%\%LOG_FILE%


REM ----------------------------------------------------------------------------
REM   2-2. BEFORE DELETE CHECK
REM ----------------------------------------------------------------------------
REM (1)CHECK DELETE-DIR -----------------------------------------------------
IF not exist %DELETE_DIR% (
echo [1-1][NG][CHECK DELETE-DIR]                           >>%LOG_DIR%\%LOG_FILE%
echo %DELETE_DIR% directory does not exist!                >>%LOG_DIR%\%LOG_FILE%
goto error
)
echo [1-1][OK][CHECK DELETE-DIR]                           >>%LOG_DIR%\%LOG_FILE%

REM (2)CHECK DELETE-FILE-------------------------------------------------------
IF not exist %DELETE_DIR%\%DELETE_FILE_NAME% (
echo [1-2][NG][CHECK DELETE-FILE]                          >>%LOG_DIR%\%LOG_FILE%
echo %DELETE_DIR%\%DELETE_FILE_NAME% is not exist!         >>%LOG_DIR%\%LOG_FILE%
goto error
)
echo [1-2][OK][CHECK DELETE-FILE]                          >>%LOG_DIR%\%LOG_FILE%

echo DELETE-FILE                                           >>%LOG_DIR%\%LOG_FILE%
dir /a-d %DELETE_DIR%\%DELETE_FILE_NAME% | findstr :       >>%LOG_DIR%\%LOG_FILE%


REM ----------------------------------------------------------------------------
REM   2-3. DELETE
REM ----------------------------------------------------------------------------
REM (1)DELETE
DEL %DELETE_DIR%\%DELETE_FILE_NAME%
IF %ERRORLEVEL% NEQ 0 (
echo [2-1][NG][ FILE DELETE ]                              >>%LOG_DIR%\%LOG_FILE%
goto error
)
echo [2-1][OK][ FILE DELETE ]                              >>%LOG_DIR%\%LOG_FILE%

echo %DELETE_DIR%\%DELETE_FILE_NAME% is deleted            >>%LOG_DIR%\%LOG_FILE%


REM ----------------------------------------------------------------------------
REM   2-4. END
REM ----------------------------------------------------------------------------
REM (1)NORMAL-END
echo [%BAT_ID%][%DATE% %TIME%]                             >>%LOG_DIR%\%LOG_FILE%
echo --------------------- [ END ] ---------------------   >>%LOG_DIR%\%LOG_FILE%
echo.                                                      >>%LOG_DIR%\%LOG_FILE%
CD %BATCHF_HOME%
exit 0

REM (2)ABNORMAL-END
:error
echo [%BAT_ID%][%DATE% %TIME%]                             >>%LOG_DIR%\%LOG_FILE%
echo ------------------- [ ABEND ] -------------------     >>%LOG_DIR%\%LOG_FILE%
echo.                                                      >>%LOG_DIR%\%LOG_FILE%
CD %BATCHF_HOME%
exit 1

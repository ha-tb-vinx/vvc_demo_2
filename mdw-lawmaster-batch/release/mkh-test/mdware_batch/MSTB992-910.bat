@echo off
setlocal enabledelayedexpansion
REM ----------------------------------------------------------------------------
REM  JOB-ID      : MSTB992
REM  BATCH-ID    : MSTB992-910
REM  IF          : MDware => POS
REM  IF-NAME     : MASTER-IF-FILE
REM  FUNCTION    : DELETE-H:drive-BKUP-IF-FILE
REM  CRATOR      : J.KOJIMA
REM  CREATE DATE : 2017/05/15
REM  UPDATE      : 2020/08/18
REM ----------------------------------------------------------------------------
REM  1-1. COMMON-SET
REM ----------------------------------------------------------------------------
REM (1)BATCH-ID 
SET BAT_ID=MSTB992-910

REM (2)LOG-DIR 
SET LOG_DIR=E:\mdware_mkh\batch\master\log\

REM (3)LOG-FILE-NAME 
SET LOG_FILE=IF_SEND_POS.log

REM (4)BACKUP-DIR
call E:\mdware_mkh\batch\z_common\SetBatchDAY.bat
SET NOW_DATE=%YY%/%MM%/%DD%
SET DEL_DATE=%DEL_YY%/%DEL_MM%/%DEL_DD%
REM SET ROOT_FOLDER=H:\POS-HongKong\q5\mdware\download
SET ROOT_FOLDER=%1
SET DEL_TARGET=backup\%DEL_YY%\%DEL_MM%\%DEL_DD%\

REM ----------------------------------------------------------------------------
REM   2-1. MAIN START
REM ----------------------------------------------------------------------------
echo -------------------- [START] -------------------- >>%LOG_DIR%%LOG_FILE%
echo [%BAT_ID%][%DATE% %TIME%] >>%LOG_DIR%%LOG_FILE%
echo [DELETE-H:drive-IF-FILE] >>%LOG_DIR%%LOG_FILE%

echo [TODAY:%NOW_DATE%] >>%LOG_DIR%%LOG_FILE%
echo [DELDT:%DEL_DATE%] >>%LOG_DIR%%LOG_FILE%

REM echo [DELFOLDER:%ROOT_FOLDER%\A\%DEL_TARGET%] >>%LOG_DIR%%LOG_FILE%
REM rmdir /s /q %ROOT_FOLDER%\A\%DEL_TARGET%
REM echo [DELFOLDER:%ROOT_FOLDER%\C\%DEL_TARGET%] >>%LOG_DIR%%LOG_FILE%
REM rmdir /s /q %ROOT_FOLDER%\C\%DEL_TARGET%
REM echo [DELFOLDER:%ROOT_FOLDER%\D\%DEL_TARGET%] >>%LOG_DIR%%LOG_FILE%
REM rmdir /s /q %ROOT_FOLDER%\D\%DEL_TARGET%
REM echo [DELFOLDER:%ROOT_FOLDER%\I\%DEL_TARGET%] >>%LOG_DIR%%LOG_FILE%
REM rmdir /s /q %ROOT_FOLDER%\I\%DEL_TARGET%
REM echo [DELFOLDER:%ROOT_FOLDER%\K\%DEL_TARGET%] >>%LOG_DIR%%LOG_FILE%
REM rmdir /s /q %ROOT_FOLDER%\K\%DEL_TARGET%
REM echo [DELFOLDER:%ROOT_FOLDER%\L\%DEL_TARGET%] >>%LOG_DIR%%LOG_FILE%
REM rmdir /s /q %ROOT_FOLDER%\L\%DEL_TARGET%
REM echo [DELFOLDER:%ROOT_FOLDER%\Q\%DEL_TARGET%] >>%LOG_DIR%%LOG_FILE%
REM rmdir /s /q %ROOT_FOLDER%\Q\%DEL_TARGET%
REM echo [DELFOLDER:%ROOT_FOLDER%\T\%DEL_TARGET%] >>%LOG_DIR%%LOG_FILE%
REM rmdir /s /q %ROOT_FOLDER%\T\%DEL_TARGET%
echo [DELFOLDER:%ROOT_FOLDER%%DEL_TARGET%] >>%LOG_DIR%%LOG_FILE%
rmdir /s /q %ROOT_FOLDER%%DEL_TARGET%

echo [1-1][OK][ DEL POS FILE] >>%LOG_DIR%%LOG_FILE%

REM ----------------------------------------------------------------------------
REM   2. MAIN END
REM ----------------------------------------------------------------------------
REM (1)NORMAL-END
echo [%BAT_ID%][%DATE% %TIME%] >>%LOG_DIR%%LOG_FILE%
echo -------------------- [ END ] -------------------- >>%LOG_DIR%%LOG_FILE%
echo.>>%LOG_DIR%%LOG_FILE%
CD %BATCHF_HOME%
rem exit 0
exit /b 0

REM (2)ABNORMAL-END
:error
echo [1-X][NG][ FILE DEL ] >>%LOG_DIR%%LOG_FILE%
echo [%BAT_ID%][%DATE% %TIME%] >>%LOG_DIR%%LOG_FILE%
echo -------------------- [ END ] -------------------- >>%LOG_DIR%%LOG_FILE%
echo.>>%LOG_DIR%%LOG_FILE%
CD %BATCHF_HOME%
rem exit 1
type nul > %LOG_DIR%POS-IF-NG\P-File-NG.Store-CD-%SND_TO_DIR:~-7,6%
exit /b

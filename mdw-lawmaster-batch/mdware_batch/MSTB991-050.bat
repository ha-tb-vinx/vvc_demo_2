@echo off
REM ----------------------------------------------------------------------------
REM  JOB-ID      : MSTB991-050
REM  BATCH-ID    : MSTB991-050
REM  IF          : MDware => FAST
REM  IF-NAME     : 【FAST向けI/F】FAST向け全I/Fファイル Edrive bkup処理
REM  FUNCTION    : 送信ファイルをEドライブ上にバックアップを取得する
REM  CRATOR      : Y.MOCHIZUKI
REM  CREATE DATE : 2020/09/24
REM  UPDATE      : 
REM ----------------------------------------------------------------------------
REM  1-1. COMMON-SET
REM ----------------------------------------------------------------------------
REM (1)BATCH-ID 
SET BAT_ID=MSTB991-050

REM (2)LOG-DIR 
SET LOG_DIR=E:\mdware_law\batch\master\log\

REM (3)LOG-FILE-NAME 
SET LOG_FILE=IF_SEND_FAST.log

REM (4)BACKUP-DIR
call E:\mdware_law\batch\z_common\SetBatchDAY.bat
SET WK_TIME=%time: =0%
SET BKUP_TIME=%WK_TIME:~0,2%%WK_TIME:~3,2%
SET BKUPDIR=%BKUP_DAY%-%BKUP_TIME%


REM ----------------------------------------------------------------------------
REM  1-2. COPY-SET
REM ----------------------------------------------------------------------------
REM (1)COPY-FROM-DIR 
SET SND_FROM_DIR=E:\mdware_law\datas\MKV\FAST

REM (2)COPY-FROM-FILE-NAME
SET INFILENAME=fast_*.csv

REM (3)COPY-TO-DIR 
SET SND_TO_DIR=E:\mdware_law\datas\MKV\FAST\backup\%BKUPDIR%
mkdir %SND_TO_DIR%

REM (4)COPY-TO-FILE-NAME
SET OUTFILENAME=fast_*.csv


REM ----------------------------------------------------------------------------
REM   2-1. MAIN START
REM ----------------------------------------------------------------------------
echo -------------------- [START] --------------------    >>%LOG_DIR%\%LOG_FILE%
echo [%BAT_ID%][%DATE% %TIME%]                            >>%LOG_DIR%\%LOG_FILE%
echo [Edrive BKUP FAST-IF-FILE]                           >>%LOG_DIR%\%LOG_FILE%


REM ----------------------------------------------------------------------------
REM   2-2. BEFORE COPY CHECK
REM ----------------------------------------------------------------------------
REM (1)CHECK COPY-FROM-DIR -----------------------------------------------------
IF not exist %SND_FROM_DIR% (
echo [1-1][NG][CHECK COPY-FROM-DIR]                       >>%LOG_DIR%\%LOG_FILE%
echo %SND_FROM_DIR% directory does not exist!             >>%LOG_DIR%\%LOG_FILE%
goto error
)
echo [1-1][OK][CHECK COPY-FROM-DIR]                       >>%LOG_DIR%\%LOG_FILE%

REM (2)CHECK COPY-FROM-FILE-----------------------------------------------------
IF not exist %SND_FROM_DIR%\%INFILENAME% (
echo [1-2][NG][CHECK COPY-FROM-FIL]                       >>%LOG_DIR%\%LOG_FILE%
echo %SND_FROM_DIR%\%INFILENAME% file not exist!          >>%LOG_DIR%\%LOG_FILE%
goto error
)
echo [1-2][OK][CHECK COPY-FROM-FIL]                       >>%LOG_DIR%\%LOG_FILE%

REM (3)CHECK-COPY-TO-DIR -------------------------------------------------------
IF not exist %SND_TO_DIR% (
echo [1-3][NG][CHECK-COPY- TO -DIR]                       >>%LOG_DIR%\%LOG_FILE%
echo %SND_TO_DIR% directory not exist!                    >>%LOG_DIR%\%LOG_FILE%
goto error
)
echo [1-3][OK][CHECK-COPY- TO -DIR]                       >>%LOG_DIR%\%LOG_FILE%


REM ----------------------------------------------------------------------------
REM   2-3. COPY
REM ----------------------------------------------------------------------------
REM (1)COPY
COPY /B /Y %SND_FROM_DIR%\%INFILENAME% %SND_TO_DIR%\%OUTFILENAME%
IF %ERRORLEVEL% NEQ 0 (
echo [2-1][NG][FILE COPY]                                 >>%LOG_DIR%\%LOG_FILE%
goto error
)
echo [2-1][OK][ FILE COPY ]                               >>%LOG_DIR%\%LOG_FILE%

REM (2)COPY FILE CHCK
dir /a-d %SND_TO_DIR% | findstr :                         >>%LOG_DIR%\%LOG_FILE%


REM ----------------------------------------------------------------------------
REM   2-4. END
REM ----------------------------------------------------------------------------
REM (1)NORMAL-END
echo [%BAT_ID%][%DATE% %TIME%]                            >>%LOG_DIR%\%LOG_FILE%
echo -------------------- [ END ] --------------------    >>%LOG_DIR%\%LOG_FILE%
echo.                                                     >>%LOG_DIR%\%LOG_FILE%
CD %BATCHF_HOME%
exit 0

REM (2)ABNORMAL-END
:error
echo [%BAT_ID%][%DATE% %TIME%]                            >>%LOG_DIR%\%LOG_FILE%
echo ------------------- [ ABEND ] -------------------    >>%LOG_DIR%\%LOG_FILE%
echo.                                                     >>%LOG_DIR%\%LOG_FILE%
CD %BATCHF_HOME%
exit 1

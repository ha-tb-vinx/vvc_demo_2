@echo off
setlocal enabledelayedexpansion
REM ----------------------------------------------------------------------------
REM  JOB-ID      : URIB011130
REM  BATCH-ID    : URIB011130
REM  IF          : POS => MDware
REM  IF-NAME     : SailFile
REM  FUNCTION    : COPY to Hdrive BackUp
REM  CRATOR      : G.SASAKI
REM  CREATE DATE : 2016/05/25
REM  UPDATE      : 2020/08/18
REM ----------------------------------------------------------------------------
REM  1-1. COMMON-SET
REM ----------------------------------------------------------------------------
REM (1)BATCH-ID 
SET BAT_ID=URIB011130_SailFile_CopyToHdriveBkup

REM (2)LOG-DIR 
SET LOG_DIR=@filter.env.baseDirectory.value@\batch\uriage\log\

REM (3)LOG-FILE-NAME 
SET LOG_FILE=IF_RECEIVE_POS.log

REM (4)BACKUP-DIR
call @filter.env.baseDirectory.value@\batch\z_common\SetBatchDAY.bat
SET BKUPDIR=%YY%\%MM%\%DD%

REM ----------------------------------------------------------------------------
REM  1-2. COPY-SET
REM ----------------------------------------------------------------------------
REM (1)COPY-FROM-DIR 
SET SND_FROM_DIR=@filter.env.baseDirectory.value@\datas@filter.env.value@\POS\q5\mdware\upload\S\

REM (2)COPY-FROM-FILE-NAME
REM SET INFILENAME=S???????.???
SET INFILENAME=S???????.%2

REM (3)COPY-TO-DIR
REM mkdir H:\POS\q5\mdware\upload\S\backup\%BKUPDIR%
REM mkdir H:\POS\q5\mdware\upload\R\backup\%BKUPDIR%
mkdir %1\backup\%BKUPDIR%\
REM SET SND_TO_DIR=H:\POS\q5\mdware\upload\S\backup\%BKUPDIR%\
SET SND_TO_DIR=%1\backup\%BKUPDIR%\

REM (4)COPY-TO-FILE-NAME
SET OUTFILENAME=S*

REM ----------------------------------------------------------------------------
REM   2-1. MAIN START
REM ----------------------------------------------------------------------------
echo -------------------- [START] --------------------     >>%LOG_DIR%%LOG_FILE%
echo [%BAT_ID%][%DATE% %TIME%] >>%LOG_DIR%%LOG_FILE%

REM ----------------------------------------------------------------------------
REM   2-2. BEFORE COPY CHECK
REM ----------------------------------------------------------------------------
REM (1)CHECK COPY-FROM-DIR 
IF not exist %SND_FROM_DIR% (
echo [1-1][NG][CHECK COPY-FROM-DIR]                        >>%LOG_DIR%%LOG_FILE%
echo %SND_FROM_DIR% directory does not exist!              >>%LOG_DIR%%LOG_FILE%
goto error
)
echo [1-1][OK][CHECK COPY-FROM-DIR]                        >>%LOG_DIR%%LOG_FILE%

REM (2)CHECK COPY-FROM-FILE
IF not exist %SND_FROM_DIR%%INFILENAME% (
echo [1-2][NG][ NO COPY-FROM-FIL]                          >>%LOG_DIR%%LOG_FILE%
goto end
)
echo [1-2][OK][CHECK COPY-FROM-FIL]                        >>%LOG_DIR%%LOG_FILE%

REM (3)CHECK-COPY-TO-DIR
IF not exist %SND_TO_DIR% (
echo [1-3][NG][CHECK-COPY- TO -DIR]                        >>%LOG_DIR%%LOG_FILE%
echo %SND_TO_DIR% directory not exist!                     >>%LOG_DIR%%LOG_FILE%
goto error
)
echo [1-3][OK][CHECK-COPY- TO -DIR]                        >>%LOG_DIR%%LOG_FILE%

REM (4)CHECK-COPY-TO-FILE
REM IF exist %SND_TO_DIR%%OUTFILENAME% (
REM echo [1-4][NG][CHECK-COPY- TO -FIL] >>%LOG_DIR%%LOG_FILE%
REM echo %SND_TO_DIR%%OUTFILENAME% file exist!>>%LOG_DIR%%LOG_FILE%
REM goto error
REM )
REM echo [1-4][OK][CHECK-COPY- TO -FIL] >>%LOG_DIR%%LOG_FILE%


REM ----------------------------------------------------------------------------
REM   2-3. COPY
REM ----------------------------------------------------------------------------
REM (1)COPY
COPY /B /Y %SND_FROM_DIR%%INFILENAME% %SND_TO_DIR%
IF %ERRORLEVEL% NEQ 0 goto error
echo [2-1][OK][ FILE COPY ]                                >>%LOG_DIR%%LOG_FILE%

REM (2)COPY FILE CHCK
REM dir /a-d /b %SND_TO_DIR% >>%LOG_DIR%%LOG_FILE%

:end
REM ----------------------------------------------------------------------------
REM   2-4. END
REM ----------------------------------------------------------------------------
REM (1)NORMAL-END
echo [%BAT_ID%][%DATE% %TIME%]                             >>%LOG_DIR%%LOG_FILE%
echo -------------------- [ END ] --------------------     >>%LOG_DIR%%LOG_FILE%
echo.                                                      >>%LOG_DIR%%LOG_FILE%
CD %BATCHF_HOME%
rem exit 0
exit /b 0

REM (2)ABNORMAL-END
:error
echo [2-1][NG][ FILE COPY ]                                >>%LOG_DIR%%LOG_FILE%
echo [%BAT_ID%][%DATE% %TIME%]                             >>%LOG_DIR%%LOG_FILE%
echo -------------------- [ END ] --------------------     >>%LOG_DIR%%LOG_FILE%
echo.                                                      >>%LOG_DIR%%LOG_FILE%
CD %BATCHF_HOME%
rem exit 1
type nul > %LOG_DIR%POS-IF-NG\S-File-NG.StoreCD-%SND_TO_DIR:~-7,6%
exit /b

@echo off
setlocal enabledelayedexpansion
REM ----------------------------------------------------------------------------
REM  JOB-ID      : URIB011110
REM  BATCH-ID    : URIB011110
REM  IF          : POS => MDware
REM  IF-NAME     : Sale-File
REM  FUNCTION    : MOVE to Edive
REM  CRATOR      : G.SASAKI
REM  CREATE DATE : 2016/05/25
REM  UPDATE      : 2020/08/18
REM ----------------------------------------------------------------------------
REM  1-1. COMMON-SET
REM ----------------------------------------------------------------------------
REM (1)BATCH-ID 
SET BAT_ID=URIB011110_SaleFileMoveToEdrive

REM (2)LOG-DIR 
SET LOG_DIR=@filter.env.baseDirectory.value@\batch\uriage\log\

REM (3)LOG-FILE-NAME 
SET LOG_FILE=IF_RECEIVE_POS.log

REM ----------------------------------------------------------------------------
REM  1-2. MOVE-SET
REM ----------------------------------------------------------------------------
REM (1)MOVE-FROM-DIR 
REM SET SND_FROM_DIR=H:\POS\q5\mdware\upload\S\
SET SND_FROM_DIR=%1

REM (2)MOVE-FROM-FILE-NAME
SET INFILENAME=S???????.???

REM (3)MOVE-TO-DIR 
REM SET SND_TO_DIR=E:\mdware_law\datas\FIVI\POS\q5\mdware\upload\S\
SET SND_TO_DIR=@filter.env.baseDirectory.value@\datas@filter.env.value@\POS\q5\mdware\upload\S\

REM (4)MOVE-TO-FILE-NAME
REM SET OUTFILENAME=S*
SET OUTFILENAME=S*%SND_TO_DIR:~-5,1%.%SND_TO_DIR:~-4,3%

REM ----------------------------------------------------------------------------
REM   2-1. MAIN START
REM ----------------------------------------------------------------------------
echo -------------------- [START] --------------------      >>%LOG_DIR%%LOG_FILE%
echo [%BAT_ID%][%DATE% %TIME%] >>%LOG_DIR%%LOG_FILE%

REM ----------------------------------------------------------------------------
REM   2-2. BEFORE MOVE CHECK
REM ----------------------------------------------------------------------------
REM (1)CHECK MOVE-FROM-DIR 
IF not exist %SND_FROM_DIR% (
echo [1-1][NG][CHECK MOVE-FROM-DIR]                         >>%LOG_DIR%%LOG_FILE%
echo %SND_FROM_DIR% directory does not exist!               >>%LOG_DIR%%LOG_FILE%
goto error
)
echo [1-1][OK][CHECK MOVE-FROM-DIR]                         >>%LOG_DIR%%LOG_FILE%

REM (2)CHECK MOVE-FROM-FILE
IF not exist %SND_FROM_DIR%%INFILENAME% (
echo [1-2][OK][ NO MOVE-FROM-FIL]                           >>%LOG_DIR%%LOG_FILE%
type nul > %SND_TO_DIR%S_ZERO__.xxx
echo [ZERO FILE CRT and MOVE to E]                          >>%LOG_DIR%%LOG_FILE%
goto end
)
echo [1-2][OK][CHECK MOVE-FROM-FIL]                         >>%LOG_DIR%%LOG_FILE%

REM (3)CHECK-MOVE-TO-DIR 
IF not exist %SND_TO_DIR% (
echo [1-3][NG][CHECK-MOVE- TO -DIR]                         >>%LOG_DIR%%LOG_FILE%
echo %SND_TO_DIR% directory not exist!                      >>%LOG_DIR%%LOG_FILE%
goto error
)
echo [1-3][OK][CHECK-MOVE- TO -DIR]                         >>%LOG_DIR%%LOG_FILE%

REM (4)CHECK-MOVE-TO-FILE
IF exist %SND_TO_DIR%%OUTFILENAME% (
echo [1-4][NG][CHECK-MOVE- TO -FIL]                         >>%LOG_DIR%%LOG_FILE%
echo %SND_TO_DIR%%OUTFILENAME% file exist!                  >>%LOG_DIR%%LOG_FILE%
goto error
 )
echo [1-4][OK][CHECK-MOVE- TO -FIL]                         >>%LOG_DIR%%LOG_FILE%


REM ----------------------------------------------------------------------------
REM   2-3. MOVE
REM ----------------------------------------------------------------------------
REM (1)MOVE
MOVE %SND_FROM_DIR%%INFILENAME% %SND_TO_DIR%
IF %ERRORLEVEL% NEQ 0 goto error
echo [2-1][OK][ FILE MOVE ]                                 >>%LOG_DIR%%LOG_FILE%

REM (2)MOVE FILE CHCK
dir /a-d /b %SND_TO_DIR%                                    >>%LOG_DIR%%LOG_FILE%


:end
REM ----------------------------------------------------------------------------
REM   2-4. END
REM ----------------------------------------------------------------------------
REM (1)NORMAL-END
echo [%BAT_ID%][%DATE% %TIME%]                              >>%LOG_DIR%%LOG_FILE%
echo -------------------- [ END ] --------------------      >>%LOG_DIR%%LOG_FILE%
echo.                                                       >>%LOG_DIR%%LOG_FILE%
CD %BATCHF_HOME%
rem exit 0
exit /b

REM (2)ABNORMAL-END
:error
echo [2-1][NG][ FILE MOVE ]                                 >>%LOG_DIR%%LOG_FILE%
echo [%BAT_ID%][%DATE% %TIME%]                              >>%LOG_DIR%%LOG_FILE%
echo -------------------- [ END ] --------------------      >>%LOG_DIR%%LOG_FILE%
echo.                                                       >>%LOG_DIR%%LOG_FILE%
CD %BATCHF_HOME%
rem exit 1
type nul > %LOG_DIR%POS-IF-NG\S-File-NG.Store-CD-%SND_TO_DIR:~-7,6%
exit /b

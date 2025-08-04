@echo off
setlocal enabledelayedexpansion
REM ----------------------------------------------------------------------------
REM  JOB-ID      : URIB013510
REM  BATCH-ID    : URIB013510
REM  IF          : POS => MDware
REM  IF-NAME     : HourlySale-File
REM  FUNCTION    : MOVE to Edive
REM  CRATOR      : G.SASAKI
REM  CREATE DATE : 2016/05/25
REM  UPDATE      : 2016/12/08 T.KAMEI
REM  UPDATE      : 2017/02/07 J.ENDO #3887
REM  UPDATE      : 2017/02/08 J.ENDO #3926
REM  UPDATE      : 2017/05/18 S.Takayama #5059
REM  UPDATE      : 2020/08/18
REM ----------------------------------------------------------------------------
REM  1-1. COMMON-SET
REM ----------------------------------------------------------------------------
REM (1)BATCH-ID 
SET BAT_ID=URIB013510_HourlySaleFile_MoveToEdive

REM (2)LOG-DIR 
SET LOG_DIR=@filter.shellscript.baseDirectory.value@\log\

REM (3)LOG-FILE-NAME 
SET LOG_FILE=IF_RECEIVE_POS_HOURLY.log

REM ----------------------------------------------------------------------------
REM  1-2. MOVE-SET
REM ----------------------------------------------------------------------------
REM (1)MOVE-FROM-DIR 
REM SET SND_FROM_DIR=H:\POS\q5\mdware\upload\R\
SET SND_FROM_DIR=%1

REM (2)MOVE-FROM-FILE-NAME
SET INFILENAME=R???????.???

REM (3)MOVE-TO-DIR 
REM SET SND_TO_DIR=E:\mdware_law\datas\FIVI\POS\q5\mdware\upload\R\
SET SND_TO_DIR=@filter.env.baseDirectory.value@\datas@filter.env.value@\POS\q5\mdware\upload\R\

REM (4)MOVE-TO-FILE-NAME
REM SET OUTFILENAME=R*
SET OUTFILENAME=R*%SND_TO_DIR:~-5,1%.%SND_TO_DIR:~-4,3%
REM #3926 2017.02.08 J.ENDO ADD(S)
call @filter.env.baseDirectory.value@\batch\z_common\SetBatchDAY.bat
REM #3926 2017.02.08 J.ENDO ADD(E)

REM ----------------------------------------------------------------------------
REM   2-1. MAIN START
REM ----------------------------------------------------------------------------
echo -------------------- [START] --------------------      >>%LOG_DIR%%LOG_FILE%
echo [%BAT_ID%][%DATE% %TIME%]                              >>%LOG_DIR%%LOG_FILE%

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
REM 2016.12.08 T.KAMEI MOD(S)
REM echo [1-2][NG][ NO MOVE-FROM-FIL] >>%LOG_DIR%%LOG_FILE%
echo [1-2][OK][ NO MOVE-FROM-FIL]                          >>%LOG_DIR%%LOG_FILE%
REM #3398 2016.12.20 T.KAMEI MOD(S)
setlocal ENABLEDELAYEDEXPANSION
REM #3887 2017.02.07 J.ENDO MOD(S)
REM SET MONTH=%date:~5,2%
REM IF !MONTH! == 12 (
REM SET HENKANMM=C
REM ) ELSE IF !MONTH! == 11 (
REM SET HENKANMM=B
REM ) ELSE IF !MONTH! == 10 (
REM SET HENKANMM=A
REM ) ELSE (
REM SET HENKANMM=!MONTH:~1,1!
REM )
REM type nul > %SND_TO_DIR%R%date:~3,1%!HENKANMM!%date:~8,2%019.999
SET MONTH=%BKUP_DAY:~5,2%
IF !MONTH! == 12 (
SET HENKANMM=C
) ELSE IF !MONTH! == 11 (
SET HENKANMM=B
) ELSE IF !MONTH! == 10 (
SET HENKANMM=A
) ELSE (
SET HENKANMM=!MONTH:~1,1!
)
type nul > %SND_TO_DIR%R%BKUP_DAY:~3,1%!HENKANMM!%BKUP_DAY:~7,2%019.999
REM #3887 2017.02.07 J.ENDO MOD(E)
ENDLOCAL
REM type nul > %SND_TO_DIR%R_ZERO__.xxx
REM #3398 2016.12.20 T.KAMEI MOD(E)
echo [ZERO FILE CRT and MOVE to E]                          >>%LOG_DIR%%LOG_FILE%
REM 2016.12.08 T.KAMEI MOD(E)
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
type nul > %LOG_DIR%POS-IF-NG\R-File-NG.Store-CD-%SND_TO_DIR:~-7,6%
exit /b

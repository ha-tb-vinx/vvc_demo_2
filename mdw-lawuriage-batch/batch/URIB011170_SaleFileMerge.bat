@echo off
REM ----------------------------------------------------------------------------
REM  JOB-ID      : URIB011170
REM  BATCH-ID    : URIB011170
REM  IF          : POS => MDware
REM  IF-NAME     : SailFile
REM  FUNCTION    : File Merge
REM  CRATOR      : G.SASAKI
REM  CREATE DATE : 2016/05/25
REM ----------------------------------------------------------------------------
REM  1-1. COMMON-SET
REM ----------------------------------------------------------------------------
REM (1)BATCH-ID 
SET BAT_ID=URIB011170_SaleFile_Merge

REM (2)LOG-DIR 
SET LOG_DIR=@filter.shellscript.baseDirectory.value@\log\

REM (3)LOG-FILE-NAME 
SET LOG_FILE=IF_RECEIVE_POS.log


REM ----------------------------------------------------------------------------
REM  1-2. COPY-SET
REM ----------------------------------------------------------------------------
REM (1)FROM-DIR 
SET FROM_DIR=@filter.env.baseDirectory.value@\datas@filter.env.value@\POS\q5\mdware\upload\S\wk\

REM (2)COPY-FROM-FILE-NAME
SET INFILENAME=S???????.???

REM (3)TO-DIR 
SET TO_DIR=@filter.env.baseDirectory.value@\datas@filter.env.value@\POS\q5\mdware\upload\S\

REM (4)COPY-TO-FILE-NAME
SET OUTFILENAME=ALL_SALE_FILE.txt

REM ----------------------------------------------------------------------------
REM   2-1. MAIN START
REM ----------------------------------------------------------------------------
echo -------------------- [START] -------------------- >>%LOG_DIR%%LOG_FILE%
echo [%BAT_ID%][%DATE% %TIME%] >>%LOG_DIR%%LOG_FILE%

REM ----------------------------------------------------------------------------
REM   2-2. BEFORE COPY CHECK
REM ----------------------------------------------------------------------------
REM (1)CHECK COPY-FROM-DIR 
IF not exist %FROM_DIR% (
echo [1-1][NG][CHECK-FROM-DIR] >>%LOG_DIR%%LOG_FILE%
echo %FROM_DIR% directory does not exist!>>%LOG_DIR%%LOG_FILE%
goto error
)
echo [1-1][OK][CHECK-FROM-DIR] >>%LOG_DIR%%LOG_FILE%

REM (2)CHECK COPY-FROM-FILE
IF not exist %FROM_DIR%%INFILENAME% (
echo [1-2][OK][NO-FROM-FIL] >>%LOG_DIR%%LOG_FILE%
echo %FROM_DIR%%INFILENAME% file not exist!>>%LOG_DIR%%LOG_FILE%
goto error
)
echo [1-2][OK][CHECK FROM-FIL] >>%LOG_DIR%%LOG_FILE%

REM (3)CHECK-COPY-TO-DIR 
IF not exist %TO_DIR% (
echo [1-3][NG][CHECK-TO-DIR] >>%LOG_DIR%%LOG_FILE%
echo %TO_DIR% directory not exist!>>%LOG_DIR%%LOG_FILE%
goto error
)
echo [1-3][OK][CHECK-TO-DIR] >>%LOG_DIR%%LOG_FILE%

REM (4)CHECK-COPY-TO-FILE
IF exist %TO_DIR%%OUTFILENAME% (
echo [1-4][NG][CHECK-TO-FIL] >>%LOG_DIR%%LOG_FILE%
echo %TO_DIR%%OUTFILENAME% file exist!>>%LOG_DIR%%LOG_FILE%
goto error
)
echo [1-4][OK][CHECK-TO-FIL] >>%LOG_DIR%%LOG_FILE%


REM ----------------------------------------------------------------------------
REM   2-3. COPY
REM ----------------------------------------------------------------------------
REM (1)COPY
type %FROM_DIR%%INFILENAME% > %TO_DIR%%OUTFILENAME%
IF %ERRORLEVEL% NEQ 0 goto error
echo [2-1][OK][ FILE MERGE ] >>%LOG_DIR%%LOG_FILE%

REM (2)COPY FILE CHCK
dir /a-d /b %TO_DIR%%OUTFILENAME% >>%LOG_DIR%%LOG_FILE%
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

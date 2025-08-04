@echo off
net use Z: \\172.29.5.71\FAST P@ssWord /user:FAST /PERSISTENT:NO
REM ----------------------------------------------------------------------------
REM  JOB-ID      : URIB83050
REM  BATCH-ID    : URIB83050_fastIFSEND_saleinvoic.bat
REM  IF          : MDware => FAST
REM  IF-NAME     :�yFAST��I/F�z���V�[�g�i�����j�t�@�C���̑��M����
REM  FUNCTION    : ���M�t�@�C����Edrive���瑗�M���Zdrive(COMServer)��COPY
REM  CRATOR      : G.SASAKI
REM  CREATE DATE : 2020/09/25
rem ----------------------------------------------------------------------------
REM  1-1. COMMON-SET
REM ----------------------------------------------------------------------------
REM (1)BATCH-ID 
SET BAT_ID=URIB83050

REM (2)LOG-DIR 
SET LOG_DIR=@filter.shellscript.baseDirectory.value@\log

REM (3)LOG-FILE-NAME 
SET LOG_FILE=IF_SEND_FAST.log

REM ----------------------------------------------------------------------------
REM  1-2. COPY-SET
REM ----------------------------------------------------------------------------
REM (1)COPY-FROM-DIR
SET SND_FROM_DIR=@filter.env.baseDirectory.value@\datas@filter.env.value@\FAST

REM (2)COPY-FROM-FILE-NAME
SET INFILENAME=fast_sales_invoice_????????.csv

REM (3)COPY-TO-DIR 
SET SND_TO_DIR=Z:\

REM (4)COPY-TO-FILE-NAME
SET OUTFILENAME=fast_sales_invoice_????????.csv

REM ----------------------------------------------------------------------------
REM   2-1. MAIN START
REM ----------------------------------------------------------------------------
echo -------------------- [START] --------------------     >>%LOG_DIR%\%LOG_FILE%
echo [%BAT_ID%][%DATE% %TIME%]                             >>%LOG_DIR%\%LOG_FILE%

REM ----------------------------------------------------------------------------
REM   2-2. BEFORE COPY CHECK
REM ----------------------------------------------------------------------------

REM (1)CHECK COPY-FROM-DIR -----------------------------------------------------
IF not exist %SND_FROM_DIR% (
echo [1-1][NG][CHECK COPY-FROM-DIR]                        >>%LOG_DIR%\%LOG_FILE%
echo %SND_FROM_DIR% directory does not exist!              >>%LOG_DIR%\%LOG_FILE%
goto error
)
echo [1-1][OK][CHECK COPY-FROM-DIR]                        >>%LOG_DIR%\%LOG_FILE%

REM (2)CHECK-COPY-TO-DIR -------------------------------------------------------
IF not exist %SND_TO_DIR% (
echo [1-2][NG][CHECK-COPY- TO -DIR]                        >>%LOG_DIR%\%LOG_FILE%
echo %SND_TO_DIR% directory not exist!                     >>%LOG_DIR%\%LOG_FILE%
goto error
)
echo [1-2][OK][CHECK-COPY- TO -DIR]                        >>%LOG_DIR%\%LOG_FILE%

REM (3)CHECK COPY-FROM-FILE ----------------------------------------------------
IF not exist %SND_FROM_DIR%\%INFILENAME% (
echo [1-3][NG][CHECK-COPY- FROM -FIL]                      >>%LOG_DIR%\%LOG_FILE%
echo %SND_FROM_DIR%\%INFILENAME% not exist!                >>%LOG_DIR%\%LOG_FILE%
goto error
)
echo [1-3][OK][CHECK COPY-FROM-FIL]                        >>%LOG_DIR%\%LOG_FILE%

REM (4)CHECK-COPY-TO-FILE ------------------------------------------------------
REM IF exist %SND_TO_DIR%\%OUTFILENAME% (
REM echo [1-4][NG][CHECK-COPY- TO -FIL]                        >>%LOG_DIR%\%LOG_FILE%
REM echo %SND_TO_DIR%%OUTFILENAME% file exist!                 >>%LOG_DIR%\%LOG_FILE%
REM goto error
REM )
REM echo [1-4][OK][CHECK-COPY- TO -FIL]                        >>%LOG_DIR%\%LOG_FILE%

REM ----------------------------------------------------------------------------
REM   2-3. COPY
REM ----------------------------------------------------------------------------
REM (1)COPY
COPY /B /Y %SND_FROM_DIR%\%INFILENAME% %SND_TO_DIR%%OUTFILENAME%
IF %ERRORLEVEL% NEQ 0 (
echo [2-1][NG][FILE COPY]                                  >>%LOG_DIR%\%LOG_FILE%
goto error
)
echo [2-1][OK][ FILE COPY ]                                >>%LOG_DIR%\%LOG_FILE%

REM (2)COPY FILE CHCK
dir /a-d %SND_TO_DIR%%OUTFILENAME% | findstr :             >>%LOG_DIR%\%LOG_FILE%


REM ----------------------------------------------------------------------------
REM   2-4. END
REM ----------------------------------------------------------------------------

REM (1)NORMAL-END
echo [%BAT_ID%][%DATE% %TIME%]                             >>%LOG_DIR%\%LOG_FILE%
echo --------------------- [ END ] ---------------------   >>%LOG_DIR%\%LOG_FILE%
echo.                                                      >>%LOG_DIR%\%LOG_FILE%
CD %BATCHF_HOME%
net use Z: /delete /y
exit 0

REM (2)ABNORMAL-END
:error
echo [%BAT_ID%][%DATE% %TIME%]                             >>%LOG_DIR%\%LOG_FILE%
echo ------------------- [ ABEND ] -------------------     >>%LOG_DIR%\%LOG_FILE%
echo.                                                      >>%LOG_DIR%\%LOG_FILE%
CD %BATCHF_HOME%
net use Z: /delete /y
exit 1

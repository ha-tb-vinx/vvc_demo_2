@echo off
REM ----------------------------------------------------------------------------
REM  JOB-ID      : MSTB992
REM  BATCH-ID    : MSTB992-300
REM  IF          : MDware => POS
REM  IF-NAME     : MASTER ALL FILE
REM  FUNCTION    : ALL FILE => SABUN FILE MARGE
REM  CRATOR      : G.SASAKI
REM  CREATE DATE : 2016/12/10
REM ----------------------------------------------------------------------------
REM  1-1. COMMON-SET
REM ----------------------------------------------------------------------------
REM (1)BATCH-ID 
SET BAT_ID=MSTB992-300

REM (2)LOG-DIR 
SET LOG_DIR=E:\mdware_law\batch\master\log\

REM (3)LOG-FILE-NAME 
SET LOG_FILE=IF_SEND_POS.log

REM ----------------------------------------------------------------------------
REM  1-2. COPY-FROM-DIR-SET (download_all)
REM ----------------------------------------------------------------------------
REM (1) DIV
SET SND_FROM_DIR_D=E:\mdware_law\datas\MKV\POS\q5\mdware\download_all\D\
REM (2) DEPT
SET SND_FROM_DIR_T=E:\mdware_law\datas\MKV\POS\q5\mdware\download_all\T\
REM (3) CLASS
SET SND_FROM_DIR_A=E:\mdware_law\datas\MKV\POS\q5\mdware\download_all\A\
REM (4) SUB CLASS
SET SND_FROM_DIR_C=E:\mdware_law\datas\MKV\POS\q5\mdware\download_all\C\
REM (5) ITEM
SET SND_FROM_DIR_I=E:\mdware_law\datas\MKV\POS\q5\mdware\download_all\I\
REM (6) DISCOUNT
SET SND_FROM_DIR_K=E:\mdware_law\datas\MKV\POS\q5\mdware\download_all\K\
REM (7) PAYMENT
SET SND_FROM_DIR_L=E:\mdware_law\datas\MKV\POS\q5\mdware\download_all\L\

REM ----------------------------------------------------------------------------
REM  1-3. COPY-FROM-FILENAME-SET
REM ----------------------------------------------------------------------------
REM (1) DIV
SET INFILENAME_D=D*_noncompleted
REM (2) DEPT
SET INFILENAME_T=T*_noncompleted
REM (3) CLASS
SET INFILENAME_A=A*_noncompleted
REM (4) SUB CLASS
SET INFILENAME_C=C*_noncompleted
REM (5) ITEM
SET INFILENAME_I=I*_noncompleted
REM (6) DISCOUNT
SET INFILENAME_K=K*_noncompleted
REM (7) PAYMENT
SET INFILENAME_L=L*_noncompleted

REM ----------------------------------------------------------------------------
REM  1-4. COPY-TO-DIR-SET (download)
REM ----------------------------------------------------------------------------
REM (1) DIV
SET SND_TO_DIR_D=E:\mdware_law\datas\MKV\POS\q5\mdware\download\D\
REM (2) DEPT
SET SND_TO_DIR_T=E:\mdware_law\datas\MKV\POS\q5\mdware\download\T\
REM (3) CLASS
SET SND_TO_DIR_A=E:\mdware_law\datas\MKV\POS\q5\mdware\download\A\
REM (4) SUB CLASS
SET SND_TO_DIR_C=E:\mdware_law\datas\MKV\POS\q5\mdware\download\C\
REM (5) ITEM
SET SND_TO_DIR_I=E:\mdware_law\datas\MKV\POS\q5\mdware\download\I\
REM (6) DISCOUNT
SET SND_TO_DIR_K=E:\mdware_law\datas\MKV\POS\q5\mdware\download\K\
REM (7) PAYMENT
SET SND_TO_DIR_L=E:\mdware_law\datas\MKV\POS\q5\mdware\download\L\

REM ----------------------------------------------------------------------------
REM  1-5. COPY-TO-FILENAME-SET
REM ----------------------------------------------------------------------------
REM (1) DIV
SET OUTFILENAME_D=D*_noncompleted
REM (2) DEPT
SET OUTFILENAME_T=T*_noncompleted
REM (3) CLASS
SET OUTFILENAME_A=A*_noncompleted
REM (4) SUB CLASS
SET OUTFILENAME_C=C*_noncompleted
REM (5) ITEM
SET OUTFILENAME_I=I*_noncompleted
REM (6) DISCOUNT
SET OUTFILENAME_K=K*_noncompleted
REM (7) PAYMENT
SET OUTFILENAME_L=L*_noncompleted

REM ----------------------------------------------------------------------------
REM   2-1. COPY START
REM ----------------------------------------------------------------------------
echo -------------------- [START] -------------------- >>%LOG_DIR%%LOG_FILE%
echo [%BAT_ID%][%DATE% %TIME%] >>%LOG_DIR%%LOG_FILE%
echo [ ALL FILE MARGE ] >>%LOG_DIR%%LOG_FILE%

REM (1) DIV --------------------------------------------------------------------
IF not exist %SND_FROM_DIR_D%%INFILENAME_D% (
echo [1-1][OK][ NO COPY-DIV-FIL ] >>%LOG_DIR%%LOG_FILE%
) else (
COPY /B /Y %SND_FROM_DIR_D%%INFILENAME_D% %SND_TO_DIR_D%%OUTFILENAME_D%
IF %ERRORLEVEL% NEQ 0 goto error
echo [1-1][OK][ OK COPY-DIV-FIL ] >>%LOG_DIR%%LOG_FILE%
)
REM (2) DEPT -------------------------------------------------------------------
IF not exist %SND_FROM_DIR_T%%INFILENAME_T% (
echo [2-1][OK][ NO COPY-DPT-FIL ] >>%LOG_DIR%%LOG_FILE%
) else (
COPY /B /Y %SND_FROM_DIR_T%%INFILENAME_T% %SND_TO_DIR_T%%OUTFILENAME_T%
IF %ERRORLEVEL% NEQ 0 goto error
echo [2-1][OK][ OK COPY-DPT-FIL ] >>%LOG_DIR%%LOG_FILE%
)
REM (3) CLASS ------------------------------------------------------------------
IF not exist %SND_FROM_DIR_A%%INFILENAME_A% (
echo [3-1][OK][ NO COPY-CLASS-FIL ] >>%LOG_DIR%%LOG_FILE%
) else (
COPY /B /Y %SND_FROM_DIR_A%%INFILENAME_A% %SND_TO_DIR_A%%OUTFILENAME_A%
IF %ERRORLEVEL% NEQ 0 goto error
echo [3-1][OK][ OK COPY-CLASS-FIL ] >>%LOG_DIR%%LOG_FILE%
)
REM (4) SUB CLASS --------------------------------------------------------------
IF not exist %SND_FROM_DIR_C%%INFILENAME_C% (
echo [4-1][OK][ NO COPY-SUBCLASS-FIL ] >>%LOG_DIR%%LOG_FILE%
) else (
COPY /B /Y %SND_FROM_DIR_C%%INFILENAME_C% %SND_TO_DIR_C%%OUTFILENAME_C%
IF %ERRORLEVEL% NEQ 0 goto error
echo [4-1][OK][ OK COPY-SUBCLASS-FIL ] >>%LOG_DIR%%LOG_FILE%
)
REM (5) ITEM--------------------------------------------------------------------
IF not exist %SND_FROM_DIR_I%%INFILENAME_I% (
echo [5-1][OK][ NO COPY-ITEM-FIL ] >>%LOG_DIR%%LOG_FILE%
) else (
COPY /B /Y %SND_FROM_DIR_I%%INFILENAME_I% %SND_TO_DIR_I%%OUTFILENAME_I%
IF %ERRORLEVEL% NEQ 0 goto error
echo [5-1][OK][ OK COPY-ITEM-FIL ] >>%LOG_DIR%%LOG_FILE%
)
REM (6) DISCOUNT----------------------------------------------------------------
IF not exist %SND_FROM_DIR_K%%INFILENAME_K% (
echo [6-1][OK][ NO COPY-DISCOUNT-FIL ] >>%LOG_DIR%%LOG_FILE%
) else (
COPY /B /Y %SND_FROM_DIR_K%%INFILENAME_K% %SND_TO_DIR_K%%OUTFILENAME_K%
IF %ERRORLEVEL% NEQ 0 goto error
echo [6-1][OK][ OK COPY-DISCOUNT-FIL ] >>%LOG_DIR%%LOG_FILE%
)
REM (7) PAYMENT-----------------------------------------------------------------
IF not exist %SND_FROM_DIR_L%%INFILENAME_L% (
echo [7-1][OK][ NO COPY-PAYMENT-FIL ] >>%LOG_DIR%%LOG_FILE%
) else (
COPY /B /Y %SND_FROM_DIR_L%%INFILENAME_L% %SND_TO_DIR_L%%OUTFILENAME_L%
IF %ERRORLEVEL% NEQ 0 goto error
echo [7-1][OK][ OK COPY-PAYMENT-FIL ] >>%LOG_DIR%%LOG_FILE%
)
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
echo [9-1][NG][ FILE COPY ] >>%LOG_DIR%%LOG_FILE%
echo [%BAT_ID%][%DATE% %TIME%] >>%LOG_DIR%%LOG_FILE%
echo -------------------- [ END ] -------------------- >>%LOG_DIR%%LOG_FILE%
echo.>>%LOG_DIR%%LOG_FILE%
CD %BATCHF_HOME%
exit 1

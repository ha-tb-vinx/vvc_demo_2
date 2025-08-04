@echo off
REM ----------------------------------------------------------------------------
REM  JOB-ID      : MSTB992
REM  BATCH-ID    : MSTB992-901
REM  IF          : MDware => POS
REM  IF-NAME     : 5FILE (DIV DEPT CLASS SUBCLASS ITEM)
REM  FUNCTION    : DELETE-E:drive-IF-FILE
REM  CRATOR      : G.SASAKI
REM  CREATE DATE : 2016/05/05
REM ----------------------------------------------------------------------------
REM  1-1. COMMON-SET
REM ----------------------------------------------------------------------------
REM (1)BATCH-ID 
SET BAT_ID=MSTB992-901

REM (2)LOG-DIR 
SET LOG_DIR=E:\mdware_law\batch\master\log\

REM (3)LOG-FILE-NAME 
SET LOG_FILE=IF_SEND_POS.log

REM ----------------------------------------------------------------------------
REM   1. MAIN START
REM ----------------------------------------------------------------------------
echo -------------------- [START] -------------------- >>%LOG_DIR%%LOG_FILE%
echo [%BAT_ID%][%DATE% %TIME%] >>%LOG_DIR%%LOG_FILE%
echo [Edirve File Delete] >>%LOG_DIR%%LOG_FILE%

dir /a-d /b E:\mdware_law\datas\MKV\POS\q5\mdware\download\D\ >>%LOG_DIR%%LOG_FILE%
del E:\mdware_law\datas\MKV\POS\q5\mdware\download\D\D*
IF %ERRORLEVEL% NEQ 0 goto error
echo [1-1][OK][ DEL DIV FILE] >>%LOG_DIR%%LOG_FILE%

dir /a-d /b E:\mdware_law\datas\MKV\POS\q5\mdware\download\T\ >>%LOG_DIR%%LOG_FILE%
del E:\mdware_law\datas\MKV\POS\q5\mdware\download\T\T*
IF %ERRORLEVEL% NEQ 0 goto error
echo [1-2][OK][ DEL DPT FILE] >>%LOG_DIR%%LOG_FILE%

dir /a-d /b E:\mdware_law\datas\MKV\POS\q5\mdware\download\A\ >>%LOG_DIR%%LOG_FILE%
del E:\mdware_law\datas\MKV\POS\q5\mdware\download\A\A*
IF %ERRORLEVEL% NEQ 0 goto error
echo [1-3][OK][ DEL CLASS FILE] >>%LOG_DIR%%LOG_FILE%

dir /a-d /b E:\mdware_law\datas\MKV\POS\q5\mdware\download\C\ >>%LOG_DIR%%LOG_FILE%
del E:\mdware_law\datas\MKV\POS\q5\mdware\download\C\C*
IF %ERRORLEVEL% NEQ 0 goto error
echo [1-4][OK][ DEL SUBCLASS FILE] >>%LOG_DIR%%LOG_FILE%

dir /a-d /b E:\mdware_law\datas\MKV\POS\q5\mdware\download\I\ >>%LOG_DIR%%LOG_FILE%
del E:\mdware_law\datas\MKV\POS\q5\mdware\download\I\I*
IF %ERRORLEVEL% NEQ 0 goto error
echo [1-5][OK][ DEL ITEM FILE] >>%LOG_DIR%%LOG_FILE%

dir /a-d /b E:\mdware_law\datas\MKV\POS\q5\mdware\download\K\ >>%LOG_DIR%%LOG_FILE%
del E:\mdware_law\datas\MKV\POS\q5\mdware\download\K\K*
IF %ERRORLEVEL% NEQ 0 goto error
echo [1-6][OK][ DEL Discount type FILE] >>%LOG_DIR%%LOG_FILE%

dir /a-d /b E:\mdware_law\datas\MKV\POS\q5\mdware\download\L\ >>%LOG_DIR%%LOG_FILE%
del E:\mdware_law\datas\MKV\POS\q5\mdware\download\L\L*
IF %ERRORLEVEL% NEQ 0 goto error
echo [1-7][OK][ DEL Payment type FILE] >>%LOG_DIR%%LOG_FILE%

dir /a-d /b E:\mdware_law\datas\MKV\POS\q5\mdware\download\Q\ >>%LOG_DIR%%LOG_FILE%
del E:\mdware_law\datas\MKV\POS\q5\mdware\download\Q\Q*
IF %ERRORLEVEL% NEQ 0 goto error
echo [1-8][OK][ DEL VAT NUMBER FILE] >>%LOG_DIR%%LOG_FILE%


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


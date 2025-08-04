@echo off
REM ----------------------------------------------------------------------------
REM  JOB-ID      : URIB013
REM  BATCH-ID    : URIB013597
REM  FUNCTION    : SALEFILE-IF-LOG-Rotation
REM  CRATOR      : S.Takayama
REM  CREATE DATE : 2017/05/18
REM ----------------------------------------------------------------------------
REM  1-1. COMMON-SET
REM ----------------------------------------------------------------------------
REM (1)BATCH-ID 
SET BAT_ID=URIB013597

REM (2)BACKUP-DIR
call @filter.env.baseDirectory.value@\batch\z_common\SetBatchDAY.bat
SET WK_TIME=%time: =0%
SET BKUP_TIME=%WK_TIME:~0,2%%WK_TIME:~3,2%
SET BKUPDIR=backup\%YY%-%MM%%DD%-%BKUP_TIME%
SET BKUPHEADER=%YY%-%MM%-%DD%-

REM ----------------------------------------------------------------------------
REM  1-2. ROTATION-SET
REM ----------------------------------------------------------------------------
REM (1)COPY-FROM-DIR 
SET SND_FROM_DIR=@filter.shellscript.baseDirectory.value@\log\
SET SND_FROM_FIR1=IF_RECEIVE_CRM.log
SET SND_FROM_FIR2=IF_RECEIVE_POS.log
SET SND_FROM_FIR3=IF_RECEIVE_POS_HOURLY.log

REM (2)COPY-TO-DIR 
mkdir %SND_FROM_DIR%%BKUPDIR%
SET SND_TO_DIR=%SND_FROM_DIR%%BKUPDIR%\

REM (3)DEL-DIR
SET DELDIR=@filter.env.baseDirectory.value@\batch\master\log\backup\%DEL_YY%-%DEL_MM%%DEL_DD%*

REM ----------------------------------------------------------------------------
REM   2-1. MAIN START
REM ----------------------------------------------------------------------------
echo -------------------- [START] --------------------
echo [%BAT_ID%][%DATE% %TIME%]

REM (1)LOG-FILE-MOVE
REM MOVE /Y %SND_FROM_DIR%%SND_FROM_FIR1% %SND_TO_DIR%%BKUPHEADER%%SND_FROM_FIR1%
REM IF %ERRORLEVEL% NEQ 0 goto error

MOVE /Y %SND_FROM_DIR%%SND_FROM_FIR2% %SND_TO_DIR%%BKUPHEADER%%SND_FROM_FIR2%
IF %ERRORLEVEL% NEQ 0 goto error

MOVE /Y %SND_FROM_DIR%%SND_FROM_FIR3% %SND_TO_DIR%%BKUPHEADER%%SND_FROM_FIR3%
IF %ERRORLEVEL% NEQ 0 goto error

REM (2)LOG-ROTATION
for /d %%1 in (%DELDIR%) do (
    rmdir /s /q %%1
    IF %ERRORLEVEL% NEQ 0 goto error
)

REM NORMAL-END
echo [%BAT_ID%][%DATE% %TIME%] 
echo -------------------- [ END ] -------------------- 
CD %BATCHF_HOME%
exit 0

REMABNORMAL-END
:error
echo [1-X][NG][ FILE MOVE ] 
echo [%BAT_ID%][%DATE% %TIME%] 
echo -------------------- [ END ] -------------------- 
CD %BATCHF_HOME%
exit 1

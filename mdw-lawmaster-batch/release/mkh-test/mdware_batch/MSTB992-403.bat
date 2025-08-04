@echo off
setlocal enabledelayedexpansion
REM ----------------------------------------------------------------------------
REM  JOB-ID      : MSTB992
REM  BATCH-ID    : MSTB992-403
REM  IF          : MDware => POS
REM  IF-NAME     : CLASS
REM  FUNCTION    : RENAME
REM  CRATOR      : T.YAJIMA
REM  CREATE DATE : 2016/04/19
REM  UPDATE      : 2020/08/18
REM ----------------------------------------------------------------------------
REM  1-1. COMMON-SET
REM ----------------------------------------------------------------------------
REM (1)BATCH-ID 
SET BAT_ID=MSTB992-403

REM (2)LOG-DIR 
SET LOG_DIR=E:\mdware_mkh\batch\master\log\

REM (3)LOG-FILE-NAME 
SET LOG_FILE=IF_SEND_POS.log

REM ----------------------------------------------------------------------------
REM  1-2. RENAME-INFO-SET
REM   当bat実行時に引数として、親batからコピー先のフォルダ名を渡す
REM   フォルダ名は店舗単位で作成されている
REM   送信ファイルも店舗単位の為、フォルダ名から取得する
REM   
REM   送信先フォルダ例: H:\POS-HongKong\q5\mdware\LIVE\download\000001 （店舗単位フォルダ）
REM   送信ファイル   例: A0706010.001_noncompleted
REM ----------------------------------------------------------------------------
REM (1)RENAME-TARGET-DIR
REM SET TARGET_DIR=H:\POS-HongKong\q5\mdware\download\A\
SET TARGET_DIR=%1

REM (2)RENAME-TARGET-FILE-NAME
REM SET TARGETFILENAME=A*_noncompleted
SET TARGETFILENAME=A*%TARGET_DIR:~-5,1%.%TARGET_DIR:~-4,3%_noncompleted

REM (3)COPY-FROM-DIR 
SET SND_FROM_DIR=E:\mdware_mkh\datas\MKH\POS\q5\mdware\download\A\

REM (4)STATUS-SET 
SET ERRO_CHECK_STATUS=OK

REM ----------------------------------------------------------------------------
REM   2-1. MAIN START
REM ----------------------------------------------------------------------------
echo -------------------- [START] -------------------- >>%LOG_DIR%%LOG_FILE%
echo [%BAT_ID%][%DATE% %TIME%] >>%LOG_DIR%%LOG_FILE%
echo [RENAME CLASS FILE] >>%LOG_DIR%%LOG_FILE%

REM ----------------------------------------------------------------------------
REM   2-2. BEFORE RENAME CHECK
REM ----------------------------------------------------------------------------
REM (1)CHECK SND-FROM-DIR
IF not exist %SND_FROM_DIR% (
    echo [1-1][NG][CHECK RROM-DIR] >>%LOG_DIR%%LOG_FILE%
    echo %SND_FROM_DIR% rename from-directory not exist!>>%LOG_DIR%%LOG_FILE%
    echo. >>%LOG_DIR%%LOG_FILE%
    goto error
)
echo [1-1][OK][CHECK RENAME-RROM-DIR] >>%LOG_DIR%%LOG_FILE%

REM (2)CHECK RENAME-TARGET-FILE
IF not exist %SND_FROM_DIR%\%TARGETFILENAME% (
    echo [1-2][OK][ NO RENAME-FIL] >>%LOG_DIR%%LOG_FILE%
    echo. >>%LOG_DIR%%LOG_FILE%
    goto end
)
echo [1-2][OK][CHECK RENAME-FIL] >>%LOG_DIR%%LOG_FILE%

REM (3)CHECK RENAME-DIR
IF not exist %TARGET_DIR% (
    echo [1-3][NG][CHECK RENAME-DIR] >>%LOG_DIR%%LOG_FILE%
    echo %TARGET_DIR% rename directory not exist!>>%LOG_DIR%%LOG_FILE%
    echo. >>%LOG_DIR%%LOG_FILE%
    goto error
)	
echo [1-3][OK][CHECK RENAME-DIR] >>%LOG_DIR%%LOG_FILE%


REM ----------------------------------------------------------------------------
REM   2-3. RENAME
REM   LOOP_START
REM      (1)      : FILE_NAME_GET FROM Edrive
REM      (2)      : CHECK-Hdrive-COMPLETED-FILE
REM      (3)      : CHECK-Hdrive-RENAME-FILE
REM      (4)      : FILE_RENAME  RENAME_CHECK_STATUS  OK => RENAME
REM                                                   NG => NOT_RENAME(ERRO_CHECK_STATUS = NG)
REM   LOOP_END
REM   (5)         : RENAME_CHECK_STATUS  OK => 2-4. END_(1)NORMAL-END
REM                                      NG => 2-4. END_(2)ABNORMAL-END
REM ----------------------------------------------------------------------------

REM LOOP_START -----------------------------------------------------------------
REM (1)TARGET-FILE_NAME_SET
for /F %%A in ('dir /b %SND_FROM_DIR%%TARGETFILENAME%') do (
    SET OUTPUT_FIL=%%A
    SET RENAMEFIL=!OUTPUT_FIL:~0,12!
    SET RENAME_CHECK_STATUS=OK

    REM (2)CHECK-Hdrive-COMPLETED-FILE
    IF not exist %TARGET_DIR%!OUTPUT_FIL! (
        echo [2-1][NG][CHECK-Hdrive-COMPLETED-FIL] >>%LOG_DIR%%LOG_FILE%
        echo %TARGET_DIR%!OUTPUT_FIL! file not exist!>>%LOG_DIR%%LOG_FILE%
        SET RENAME_CHECK_STATUS=NG
    ) ELSE (
        echo [2-1][OK][CHECK-Hdrive-COMPLETED-FIL] >>%LOG_DIR%%LOG_FILE%
    )

    REM (3)CHECK-Hdrive-RENAME-FILE
    IF exist %TARGET_DIR%!RENAMEFIL! (
        echo [2-2][NG][CHECK-Hdrive-RENAME-FIL] >>%LOG_DIR%%LOG_FILE%
        echo %TARGET_DIR%!RENAMEFIL! file exist!>>%LOG_DIR%%LOG_FILE%
        SET RENAME_CHECK_STATUS=NG
    ) ELSE (
        echo [2-2][OK][CHECK-Hdrive- RENAME  -FIL] >>%LOG_DIR%%LOG_FILE%
    )

    REM (4)RENAME
    IF !RENAME_CHECK_STATUS! == OK (
        ren %TARGET_DIR%%!OUTPUT_FIL! !RENAMEFIL!
        IF !ERRORLEVEL! NEQ 0 (
            echo [2-3][NG][CHECK RENAME-FIL] >>%LOG_DIR%%LOG_FILE%
            echo %TARGET_DIR%%TARGETFILENAME% rename Processing error!>>%LOG_DIR%\%LOG_FILE%
            echo. >>%LOG_DIR%%LOG_FILE%
            SET ERRO_CHECK_STATUS=NG
        ) ELSE (
            echo [2-3][OK][ FILE RENAME ] %SND_TO_DIR%!RENAMEFIL! >>%LOG_DIR%%LOG_FILE%
            dir /a-d %TARGET_DIR%!RENAMEFIL! | findstr :          >>%LOG_DIR%%LOG_FILE%
            echo. >>%LOG_DIR%%LOG_FILE%
        )
    ) ELSE (
        echo. >>%LOG_DIR%%LOG_FILE%
        SET ERRO_CHECK_STATUS=NG
    )

REM LOOP_END -------------------------------------------------------------------
)

REM (5)ERRO_CHECK_STATUS_CHECK
IF %ERRO_CHECK_STATUS% == NG (
    goto error
)

REM ----------------------------------------------------------------------------
REM   2-4. END
REM ----------------------------------------------------------------------------
:end
REM (1)NORMAL-END
echo [%BAT_ID%][%DATE% %TIME%] >>%LOG_DIR%%LOG_FILE%
echo -------------------- [ END ] -------------------- >>%LOG_DIR%%LOG_FILE%
echo.>>%LOG_DIR%%LOG_FILE%
CD %BATCHF_HOME%
REM exit 0
exit /b


REM (2)ABNORMAL-END
:error
echo [X-X][NG][ FILE-RENAME ABNORMAL-END ] >>%LOG_DIR%%LOG_FILE%
echo [%BAT_ID%][%DATE% %TIME%] >>%LOG_DIR%%LOG_FILE%
echo -------------------- [ END ] -------------------- >>%LOG_DIR%%LOG_FILE%
echo.>>%LOG_DIR%%LOG_FILE%
CD %BATCHF_HOME%
type nul > %LOG_DIR%POS-IF-NG\A-File-RENAME-NG.Store-CD-%TARGET_DIR:~-7,6%
REM exit 1
exit /b


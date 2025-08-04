@echo off
setlocal enabledelayedexpansion
REM ----------------------------------------------------------------------------
REM  JOB-ID      : MSTB992
REM  BATCH-ID    : MSTB992-306
REM  IF          : MDware => POS
REM  IF-NAME     : Discount type
REM  FUNCTION    : COPY
REM  CRATOR      : S.Takayama
REM  CREATE DATE : 2017/05/17
REM  UPDATE      : 2020/08/18
REM ----------------------------------------------------------------------------
REM  1-1. COMMON-SET
REM ----------------------------------------------------------------------------
REM (1)BATCH-ID 
SET BAT_ID=MSTB992-306

REM (2)LOG-DIR 
SET LOG_DIR=E:\mdware_mkh\batch\master\log\

REM (3)LOG-FILE-NAME 
SET LOG_FILE=IF_SEND_POS.log

REM ----------------------------------------------------------------------------
REM  1-2. COPY-SET
REM   当bat実行時に引数として、親batからコピー先のフォルダ名を渡す
REM   フォルダ名は店舗単位で作成されている
REM   送信ファイルも店舗単位の為、フォルダ名から取得する
REM   
REM   送信先フォルダ例: H:\POS-HongKong\q5\mdware\LIVE\download\000001 （店舗単位フォルダ）
REM   送信ファイル   例: K0706010.001_noncompleted
REM ----------------------------------------------------------------------------
REM (1)COPY-FROM-DIR 
SET SND_FROM_DIR=E:\mdware_mkh\datas\MKH\POS\q5\mdware\download\K\

REM (2)COPY-FROM-FILE-NAME
REM SET INFILENAME=K*_noncompleted

REM (3)COPY-TO-DIR 
REM SET SND_TO_DIR=H:\POS-HongKong\q5\mdware\download\K\

SET SND_TO_DIR=%1
SET INFILENAME=K*%SND_TO_DIR:~-5,1%.%SND_TO_DIR:~-4,3%_noncompleted

REM (4)STATUS-SET 
SET ERRO_CHECK_STATUS=OK

REM ----------------------------------------------------------------------------
REM   2-1. MAIN START
REM      (1)      : DIR_CHECK(SND_FROM_DIR)  NG => ERR
REM                                          OK => NEXT
REM      (2)      : DIR_CHECK(SND_TO_DIR)    NG => ERR
REM                                          OK => NEXT
REM      (3)      : FIL_CHECK(SND_FROM_DIR)  OK => END
REM                                          OK => NEXT
REM   LOOP_START
REM      (4)      : FILE_NAME_GET(SND_FROM_DIR)
REM      (5)      : ERROR_CHECK(FILE)
REM      (6)      : NO_COMP_CHECK(SND_TO_DIR)
REM      (7)      : FILE_COPY  COPY_CHECK_STATUS  OK => COPY
REM                                               NG => NOT_COPY(ERRO_CHECK_STATUS = NG)
REM   LOOP_END
REM   (8)         : COPY_CHECK_STATUS  OK => 2-4. END_(1)NORMAL-END
REM                                    NG => 2-4. END_(2)ABNORMAL-END
REM ----------------------------------------------------------------------------
echo -------------------- [START] -------------------- >>%LOG_DIR%%LOG_FILE%
echo [%BAT_ID%][%DATE% %TIME%] >>%LOG_DIR%%LOG_FILE%
echo [COPY DISCOUNT FILE] >>%LOG_DIR%%LOG_FILE%

REM (1)CHECK COPY-FROM-DIR 
IF not exist %SND_FROM_DIR% (
    echo [1-1][NG][CHECK COPY-FROM-DIR] >>%LOG_DIR%%LOG_FILE%
    echo %SND_FROM_DIR% directory does not exist!>>%LOG_DIR%%LOG_FILE%
    echo. >>%LOG_DIR%%LOG_FILE%
    goto error
)
echo [1-1][OK][CHECK COPY-FROM-DIR] >>%LOG_DIR%%LOG_FILE%

REM (2)CHECK-COPY-TO-DIR 
IF not exist %SND_TO_DIR% (
    echo [1-2][NG][CHECK-COPY- TO -DIR] >>%LOG_DIR%%LOG_FILE%
    echo %SND_TO_DIR% directory not exist!>>%LOG_DIR%%LOG_FILE%
    echo. >>%LOG_DIR%%LOG_FILE%
    goto error
)
echo [1-2][OK][CHECK-COPY- TO -DIR] >>%LOG_DIR%%LOG_FILE%

REM (3)CHECK COPY-FROM-FILE
IF not exist %SND_FROM_DIR%%INFILENAME% (
    echo [1-3][NG][ NO COPY-FROM-FIL] >>%LOG_DIR%%LOG_FILE%
    echo. >>%LOG_DIR%%LOG_FILE%
    goto error
)
echo [1-3][OK][CHECK COPY-FROM-FIL] >>%LOG_DIR%%LOG_FILE%

REM LOOP_START -----------------------------------------------------------------
REM (4)COPY-FROM-FILE-NAME
for /F %%A in ('dir /b %SND_FROM_DIR%%INFILENAME%') do (
    SET NOCOMPFILE=%%A
    SET RENAMEFIL=!NOCOMPFILE:~0,12!
    SET COPY_CHECK_STATUS=OK

    REM (5)CHECK-COPY-TO-FILE
    IF exist %SND_TO_DIR%!NOCOMPFILE! (
        echo [2-1][NG][CHECK-COPY- TO -FIL] >>%LOG_DIR%%LOG_FILE%
        echo %SND_TO_DIR%!NOCOMPFILE! file exist!>>%LOG_DIR%%LOG_FILE%
        SET COPY_CHECK_STATUS=NG
    ) ELSE (
        echo [2-1][OK][ CHECK COPY TO FIL ] >>%LOG_DIR%%LOG_FILE%
    )

    REM (6)CHECK-COPY_NO_COMP-TO-FILE
    IF exist %SND_TO_DIR%!RENAMEFIL! (
        echo [2-2][NG][CHECK-COPY- TO -FIL] >>%LOG_DIR%%LOG_FILE%
        echo %SND_TO_DIR%!RENAMEFIL! file exist!>>%LOG_DIR%%LOG_FILE%
        SET COPY_CHECK_STATUS=NG
    ) ELSE (
        echo [2-2][OK][ CHECK COPY TO FIL ] >>%LOG_DIR%%LOG_FILE%
    )

    REM (7)COPY
    IF !COPY_CHECK_STATUS! == OK (
        COPY /B /Y %SND_FROM_DIR%!NOCOMPFILE! !SND_TO_DIR!!NOCOMPFILE!
        IF !ERRORLEVEL! NEQ 0 (
            echo [2-3][NG][ FILE COPY ] >>%LOG_DIR%%LOG_FILE%
            echo. >>%LOG_DIR%%LOG_FILE%
            SET ERRO_CHECK_STATUS=NG
        ) ELSE (
            echo [2-3][OK][ FILE COPY ] %SND_TO_DIR%!NOCOMPFILE! >>%LOG_DIR%%LOG_FILE%
            echo. >>%LOG_DIR%%LOG_FILE%
        )
    ) ELSE (
        echo. >>%LOG_DIR%%LOG_FILE%
        SET ERRO_CHECK_STATUS=NG
    )
REM LOOP_END -------------------------------------------------------------------
)

REM (8)ERRO_CHECK_STATUS_CHECK
IF %ERRO_CHECK_STATUS% == NG (
    goto error
)

REM ----------------------------------------------------------------------------
REM   2-2. END
REM ----------------------------------------------------------------------------
:end
REM (1)NORMAL-END
echo [%BAT_ID%][%DATE% %TIME%] >>%LOG_DIR%%LOG_FILE%
echo -------------------- [ END ] -------------------- >>%LOG_DIR%%LOG_FILE%
echo.>>%LOG_DIR%%LOG_FILE%
CD %BATCHF_HOME%
rem exit 0
exit /b 0

REM (2)ABNORMAL-END
:error
echo [3-1][NG][ FILE COPY ] >>%LOG_DIR%%LOG_FILE%
echo [%BAT_ID%][%DATE% %TIME%] >>%LOG_DIR%%LOG_FILE%
echo -------------------- [ END ] -------------------- >>%LOG_DIR%%LOG_FILE%
echo.>>%LOG_DIR%%LOG_FILE%
CD %BATCHF_HOME%
rem exit 1
type nul > %LOG_DIR%POS-IF-NG\K-File-NG.Store-CD-%SND_TO_DIR:~-7,6%
exit /b


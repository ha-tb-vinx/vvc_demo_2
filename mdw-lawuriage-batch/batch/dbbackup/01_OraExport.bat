rem @ECHO OFF
rem **********************************************************************************
rem 自動発注サーバー　オラクル論理バックアップ（EXPORT）処理	2007/10/10	T.Tsubaki
rem 実行場所：運用系DBサーバ（mredrd01）
rem **********************************************************************************

for /f "tokens=1,2,3 delims=/ " %%a in ('date /t') do SET YYYYMMDD=%%a%%b%%c
for /f "tokens=1,2 delims=: " %%a in ('time /t') do SET HHMI=%%a%%b

CALL @filter.shellscript.baseDirectory.value@\batch\dbbackup\SetBatchPath.bat

ECHO %date% %time% **********オラクル論理バックアップ（EXPORT）処理開始**********>> %LOG_PATH%

ECHO %date% %time% オラクル論理バックアップ（KSPORTAL）を実行します。 >> %LOG_PATH%
EXP %PORTAL_USER%/%PORTAL_PASSWD%@%ORACLE_SID% file=%DMP_DIR%\%PORTAL_USER%_01.DMP LOG=%DMP_DIR%\%PORTAL_USER%_export.log
rem ===ダンプファイルおよびログファイル名の変更===
move %DMP_DIR%\%PORTAL_USER%_01.DMP %DMP_DIR%\%PORTAL_USER%_01.DMP_%YYYYMMDD%%HHMI%
move %DMP_DIR%\%PORTAL_USER%_export.log %DMP_DIR%\%PORTAL_USER%_export.log_%YYYYMMDD%%HHMI%
ECHO %date% %time% オラクル論理バックアップ（KSPORTAL）を終了します。 >> %LOG_PATH%
IF %ERRORLEVEL% NEQ 0 GOTO ERR_RBT


ECHO %date% %time% オラクル論理バックアップ（KSJISEKI）を実行します。 >> %LOG_PATH%
EXP %JISEKI_USER%/%JISEKI_PASSWD%@%ORACLE_SID% file=%DMP_DIR%\%JISEKI_USER%_01.DMP LOG=%DMP_DIR%\%JISEKI_USER%_export.log
rem ===ダンプファイルおよびログファイル名の変更===
move %DMP_DIR%\%JISEKI_USER%_01.DMP %DMP_DIR%\%JISEKI_USER%_01.DMP_%YYYYMMDD%%HHMI%
move %DMP_DIR%\%JISEKI_USER%_export.log %DMP_DIR%\%JISEKI_USER%_export.log_%YYYYMMDD%%HHMI%
ECHO %date% %time% オラクル論理バックアップ（KSJISEKI）を終了します。 >> %LOG_PATH%
IF %ERRORLEVEL% NEQ 0 GOTO ERR_RBT


ECHO %date% %time% オラクル論理バックアップ（KSAUTO）を実行します。 >> %LOG_PATH% 
EXP %AUTO_USER%/%AUTO_PASSWD%@%ORACLE_SID% file=%DMP_DIR%\%AUTO_USER%_01.DMP LOG=%DMP_DIR%\%AUTO_USER%_export.log
rem ===ダンプファイルおよびログファイル名の変更===
move %DMP_DIR%\%AUTO_USER%_01.DMP %DMP_DIR%\%AUTO_USER%_01.DMP_%YYYYMMDD%%HHMI%
move %DMP_DIR%\%AUTO_USER%_export.log %DMP_DIR%\%AUTO_USER%_export.log_%YYYYMMDD%%HHMI%
ECHO %date% %time% オラクル論理バックアップ（KSAUTO）を終了します。 >> %LOG_PATH% 
IF %ERRORLEVEL% NEQ 0 GOTO ERR_RBT

ECHO %date% %time% **********オラクル論理バックアップ（EXPORT）処理終了********** >> %LOG_PATH%

GOTO END


:ERR_RBT
ECHO %date% %time% オラクル論理バックアップ（EXPORT）処理が異常終了しました。 >> %LOG_PATH%
%EXECJAVA% jp.co.vinculumjapan.swc.commons.dao.BatchController AB-91-02-03

EXIT 9

:END
ECHO. >> %LOG_PATH%


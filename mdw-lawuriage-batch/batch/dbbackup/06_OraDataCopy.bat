rem @ECHO OFF
rem ******************************************************************************************************
rem 自動発注サーバー　Oracle関連ファイルコピー処理（運用系→待機系）	2007/10/10	T.Tsubaki
rem 実行場所：運用系DBサーバ（mredrd01）
rem ******************************************************************************************************

for /f "tokens=1,2,3 delims=/ " %%a in ('date /t') do SET YYYYMMDD=%%a%%b%%c

CALL @filter.shellscript.baseDirectory.value@\batch\dbbackup\SetBatchPath.bat

ECHO %date% %time% **********Oracle関連ファイルコピー処理（運用系→待機系）開始**********>> %LOG_PATH%

rem ===Redoログのコピー===
ECHO %date% %time% Redoログのコピーを行います。 >> %LOG_PATH%
xcopy %ACTIVE_CDRV% %STANDBY_CDRV% /S /E /H /C /Y /R >> %LOG_PATH%

rem ===アーカイブログ、制御ファイルのコピー===
ECHO %date% %time% Redoログ、制御ファイルのコピーを行います。 >> %LOG_PATH%
xcopy %ACTIVE_DDRV% %STANDBY_DDRV% /S /E /H /C /Y /R >> %LOG_PATH%

rem ===spfile,pfile,pwdfileのコピー===
ECHO %date% %time% spfile,pfile,pwdfileのコピーを行います。 >> %LOG_PATH%
copy %ACTIVE_DDRV2%\SPFILE%ORACLE_SID%.ora %STANDBY_DDRV2% >> %LOG_PATH%
rem copy %ACTIVE_DDRV2%\INIT%ORACLE_SID%.ora %STANDBY_DDRV2% >> %LOG_PATH%
copy %ACTIVE_DDRV2%\PWD%ORACLE_SID%.ora %STANDBY_DDRV2% >> %LOG_PATH%

rem ===データファイル（テーブル）のコピー===
xcopy %ACTIVE_TABLE% %STANDBY_TABLE% /S /E /H /C /Y /R >> %LOG_PATH%

rem ===データファイル（インデックス）のコピー===
xcopy %ACTIVE_INDEX% %STANDBY_INDEX% /S /E /H /C /Y /R >> %LOG_PATH%

IF %ERRORLEVEL% NEQ 0 GOTO ERR_RBT

ECHO %date% %time% **********Oracle関連ファイルコピー処理（運用系→待機系）終了********** >> %LOG_PATH%

GOTO END


:ERR_RBT
ECHO %date% %time% Oracle関連ファイルコピー処理（運用系→待機系）が異常終了しました。 >> %LOG_PATH%
%EXECJAVA% jp.co.vinculumjapan.swc.commons.dao.BatchController AB-91-02-08

EXIT 9

:END
ECHO. >> %LOG_PATH%

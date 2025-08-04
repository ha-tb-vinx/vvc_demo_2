rem @ECHO OFF
rem **********************************************************
rem 自動発注サーバー　オラクル起動処理	2007/10/25	N.MURATA
rem 実行場所：運用系 or 待機系DBサーバ（ho160 or ho161）
rem **********************************************************

for /f "tokens=1,2,3 delims=/ " %%a in ('date /t') do SET YYYYMMDD=%%a%%b%%c

CALL @filter.shellscript.baseDirectory.value@\batch\dbbackup\SetBatchPath.bat

ECHO %date% %time% **********ORACLE起動処理開始**********>> %LOG_PATH%

ECHO %date% %time% ORACLEを起動します（IMMEDIATE）。 >> %LOG_PATH%

net start %ORA_SERVICE% >> %LOG_PATH%

IF %ERRORLEVEL% NEQ 0 GOTO ERR_RBT

ECHO %date% %time% **********ORACLE起動処理終了********** >> %LOG_PATH%

GOTO END


:ERR_RBT
ECHO %date% %time% ORACLE起動処理が異常終了しました。 >> %LOG_PATH%
%EXECJAVA% jp.co.vinculumjapan.swc.commons.dao.BatchController AB-91-02-09

EXIT 9

:END
ECHO. >> %LOG_PATH%


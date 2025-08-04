rem @ECHO OFF
rem **********************************************************
rem 自動発注サーバー　オラクル停止処理	2007/10/10	T.Tsubaki
rem 実行場所：待機系DBサーバ（ho161）
rem **********************************************************

for /f "tokens=1,2,3 delims=/ " %%a in ('date /t') do SET YYYYMMDD=%%a%%b%%c

CALL @filter.shellscript.baseDirectory.value@\batch\dbbackup\SetBatchPath.bat

ECHO %date% %time% **********ORACLE停止処理開始（待機系）**********>> %LOG_PATH%

ECHO %date% %time% ORACLEを停止します（IMMEDIATE）。 >> %LOG_PATH%

sqlplus %SYS_USER%/%SYS_PASSWD%@MDWARE as sysdba @%BATCHF_HOME%\02_OraShutDown.sql >> %LOG_PATH%

net stop %ORA_SERVICE% >> %LOG_PATH%

IF %ERRORLEVEL% NEQ 0 GOTO ERR_RBT

ECHO %date% %time% **********ORACLE停止処理終了（待機系）********** >> %LOG_PATH%

GOTO END


:ERR_RBT
ECHO %date% %time% ORACLE停止処理が異常終了しました。 >> %LOG_PATH%

EXIT 9

:END
ECHO. >> %LOG_PATH%


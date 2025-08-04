rem @ECHO OFF
rem **************************************************************
rem 自動発注サーバー　アラートログ待避処理	2007/10/10	T.Tsubaki
rem 実行場所：運用系DBサーバ（mredrd01）
rem **************************************************************

for /f "tokens=1,2,3 delims=/ " %%a in ('date /t') do SET YYYYMMDD=%%a%%b%%c
for /f "tokens=1,2 delims=: " %%a in ('time /t') do SET HHMI=%%a%%b

CALL @filter.shellscript.baseDirectory.value@\batch\dbbackup\SetBatchPath.bat

ECHO %date% %time% **********アラートログ待避処理開始**********>> %LOG_PATH%

ECHO %date% %time% アラートログを待避します。 >> %LOG_PATH%
move %ORALART_HOME%\%ALARTLOG% %ORALART_HOME%\%ALARTLOG%.%YYYYMMDD%.%HHMI%

IF %ERRORLEVEL% NEQ 0 GOTO ERR_RBT

ECHO %date% %time% **********アラートログ待避処理終了********** >> %LOG_PATH%

GOTO END


:ERR_RBT
ECHO %date% %time% アラートログ待避処理が異常終了しました。 >> %LOG_PATH%
%EXECJAVA% jp.co.vinculumjapan.swc.commons.dao.BatchController AB-91-02-06

EXIT 9

:END
ECHO. >> %LOG_PATH%


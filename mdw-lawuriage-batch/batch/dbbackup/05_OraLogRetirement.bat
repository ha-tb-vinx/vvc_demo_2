rem @ECHO OFF
rem ******************************************************
rem 自動発注サーバー　ログ削除処理	2007/10/10	T.Tsubaki
rem 実行場所：運用系DBサーバ（mredrd01）
rem ******************************************************

for /f "tokens=1,2,3 delims=/ " %%a in ('date /t') do SET YYYYMMDD=%%a%%b%%c

CALL @filter.shellscript.baseDirectory.value@\batch\dbbackup\SetBatchPath.bat

ECHO %date% %time% **********ログ削除処理開始**********>> %LOG_PATH%

ECHO %date% %time% アーカイブログを削除します。 >> %LOG_PATH%
%BATCHF_HOME%\FileDelete.vbs %ORARC_HOME% d %ORARC_EXPIRE% >> %LOG_PATH%

ECHO %date% %time% リスナーログを削除します。 >> %LOG_PATH%
%BATCHF_HOME%\FileDelete.vbs %ORANET_HOME% d %ORANET_EXPIRE% >> %LOG_PATH%

ECHO %date% %time% アラートログを削除します。 >> %LOG_PATH%
%BATCHF_HOME%\FileDelete.vbs %ORALART_HOME% d %ORALART_EXPIRE% >> %LOG_PATH%

ECHO %date% %time% ダンプファイルを削除します。 >> %LOG_PATH%
%BATCHF_HOME%\FileDelete.vbs %DMP_DIR% d %DMP_EXPIRE% >> %LOG_PATH%

ECHO %date% %time% バッチログを削除します。 >> %LOG_PATH%
%BATCHF_HOME%\FileDelete.vbs %LOG_HOME% d %LOG_EXPIRE% >> %LOG_PATH%

IF %ERRORLEVEL% NEQ 0 GOTO ERR_RBT

ECHO %date% %time% **********ログ削除処理終了********** >> %LOG_PATH%

GOTO END


:ERR_RBT
ECHO %date% %time% ログ削除処理が異常終了しました。 >> %LOG_PATH%
%EXECJAVA% jp.co.vinculumjapan.swc.commons.dao.BatchController AB-91-02-07

EXIT 9

:END

ECHO. >> %LOG_PATH%


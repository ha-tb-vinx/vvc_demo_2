@echo off
rem =======================================
rem MSTB902-040 POS単価変更ログデータバックアップ処理
rem =======================================
call %~dp0\SetBatchPath.bat
%execjava% mdware.common.batch.util.control.BatchController MSTB902-040 %CLS_HOME%\batch\properties release use
exit %ERRORLEVEL%

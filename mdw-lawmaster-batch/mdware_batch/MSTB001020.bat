@echo off
rem =======================================
rem MSTB001020 指定日POS送信終了処理（MSTB001020）
rem =======================================
call %~dp0\SetBatchPath.bat
%execjava% mdware.common.batch.util.control.BatchController MSTB001020 %CLS_HOME%\batch\properties release use
exit %ERRORLEVEL%

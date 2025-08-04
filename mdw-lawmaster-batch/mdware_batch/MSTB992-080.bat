@echo off
rem =======================================
rem MSTB992-080 POS用ファイル送信
rem =======================================
call %~dp0\SetBatchPath.bat
%execjava% mdware.common.batch.util.control.BatchController MSTB992-080 %CLS_HOME%\batch\properties release use
exit %ERRORLEVEL%

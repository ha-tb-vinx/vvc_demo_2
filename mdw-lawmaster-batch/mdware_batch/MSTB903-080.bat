@echo off
rem =======================================
rem MSTB903-080 POP用マスタファイル送信
rem =======================================
call %~dp0\SetBatchPath.bat
%execjava% mdware.common.batch.util.control.BatchController MSTB903-080 %CLS_HOME%\batch\properties release use
exit %ERRORLEVEL%

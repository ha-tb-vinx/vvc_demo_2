@echo off
rem =======================================
rem MB01-01-01 オンライン日付更新
rem =======================================
call %~dp0\SetBatchPath.bat
%execjava% mdware.common.batch.util.control.BatchController MB01-01-01 %CLS_HOME%\batch\properties release use
exit %ERRORLEVEL% 

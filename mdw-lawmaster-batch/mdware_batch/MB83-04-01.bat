@echo off
rem =======================================
rem MB83-04-01 店別例外反映処理
rem =======================================
call %~dp0\SetBatchPath.bat
%execjava% mdware.common.batch.util.control.BatchController MB83-04-01 %CLS_HOME%\batch\properties release use
exit %ERRORLEVEL% 

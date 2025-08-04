@echo off
rem =======================================
rem MB38-0C-07 単品店自動作成
rem =======================================
call %~dp0\SetBatchPath.bat
%execjava% mdware.common.batch.util.control.BatchController MB38-0C-07 %CLS_HOME%\batch\properties release use
exit %ERRORLEVEL% 

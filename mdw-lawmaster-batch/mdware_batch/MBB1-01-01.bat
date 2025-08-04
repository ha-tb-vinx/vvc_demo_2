@echo off
rem =======================================
rem MBB1-01-01 廃番実施処理
rem =======================================
call %~dp0\SetBatchPath.bat
%execjava% mdware.common.batch.util.control.BatchController MBB1-01-01 %CLS_HOME%\batch\properties release use
exit %ERRORLEVEL% 

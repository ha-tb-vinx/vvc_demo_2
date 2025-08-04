@echo off
rem =======================================
rem MSTB09-02-01 退役処理（POSIFデータ）
rem =======================================
call %~dp0\SetBatchPath.bat
%execjava% mdware.common.batch.util.control.BatchController MSTB09-02-01 %CLS_HOME%\batch\properties release use
exit %ERRORLEVEL% 

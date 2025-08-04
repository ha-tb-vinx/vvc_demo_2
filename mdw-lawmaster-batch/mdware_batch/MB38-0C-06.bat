@echo off
rem =======================================
rem MB38-0C-06 アラーム編集処理
rem =======================================
call %~dp0\SetBatchPath.bat
%execjava% mdware.common.batch.util.control.BatchController MB38-0C-06 %CLS_HOME%\batch\properties release use
exit %ERRORLEVEL% 

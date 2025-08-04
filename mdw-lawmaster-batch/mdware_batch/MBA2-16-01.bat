@echo off
rem =======================================
rem MBA2-16-01 IF税率マスタ作成
rem =======================================
call %~dp0\SetBatchPath.bat
%execjava% mdware.common.batch.util.control.BatchController MBA2-16-01 %CLS_HOME%\batch\properties release use
exit %ERRORLEVEL% 

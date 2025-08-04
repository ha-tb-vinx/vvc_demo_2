@echo off
rem =======================================
rem MBA2-11-01 IF発注納品基準日マスタ作成
rem =======================================
call %~dp0\SetBatchPath.bat
%execjava% mdware.common.batch.util.control.BatchController MBA2-11-01 %CLS_HOME%\batch\properties release use
exit %ERRORLEVEL% 

@echo off
rem =======================================
rem MBA2-08-01 IF店グルーピングマスタ作成
rem =======================================
call %~dp0\SetBatchPath.bat
%execjava% mdware.common.batch.util.control.BatchController MBA2-08-01 %CLS_HOME%\batch\properties release use
exit %ERRORLEVEL% 

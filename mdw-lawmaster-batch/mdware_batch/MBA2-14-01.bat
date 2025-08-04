@echo off
rem =======================================
rem MBA2-14-01 IF物流経路マスタ作成処理
rem =======================================
call %~dp0\SetBatchPath.bat
%execjava% mdware.common.batch.util.control.BatchController MBA2-14-01 %CLS_HOME%\batch\properties release use
exit %ERRORLEVEL% 

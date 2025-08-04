@echo off
rem =======================================
rem MBA2-23-01 IF計量器マスタ作成処理
rem =======================================
call %~dp0\SetBatchPath.bat
%execjava% mdware.common.batch.util.control.BatchController MBA2-23-01 %CLS_HOME%\batch\properties release use
exit %ERRORLEVEL% 

@echo off
rem =======================================
rem MBA2-03-01 IF分類５マスタ作成
rem =======================================
call %~dp0\SetBatchPath.bat
%execjava% mdware.common.batch.util.control.BatchController MBA2-03-01 %CLS_HOME%\batch\properties release use
exit %ERRORLEVEL% 

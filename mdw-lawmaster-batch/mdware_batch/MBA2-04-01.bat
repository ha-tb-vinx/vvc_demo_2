@echo off
rem =======================================
rem MBA2-04-01 IF大分類１マスタ作成
rem =======================================
call %~dp0\SetBatchPath.bat
%execjava% mdware.common.batch.util.control.BatchController MBA2-04-01 %CLS_HOME%\batch\properties release use
exit %ERRORLEVEL% 

@echo off
rem =======================================
rem MBA2-05-01 IF大分類２マスタ作成
rem =======================================
call %~dp0\SetBatchPath.bat
%execjava% mdware.common.batch.util.control.BatchController MBA2-05-01 %CLS_HOME%\batch\properties release use
exit %ERRORLEVEL% 

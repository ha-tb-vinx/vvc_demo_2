@echo off
rem =======================================
rem MBA2-20-01 IF昨対比マスタ作成処理
rem =======================================
call %~dp0\SetBatchPath.bat
%execjava% mdware.common.batch.util.control.BatchController MBA2-20-01 %CLS_HOME%\batch\properties release use
exit %ERRORLEVEL% 

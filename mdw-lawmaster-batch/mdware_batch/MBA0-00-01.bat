@echo off
rem =======================================
rem MBA0-00-01 新店取扱登録条件初期処理
rem =======================================
call %~dp0\SetBatchPath.bat
%execjava% mdware.common.batch.util.control.BatchController MBA0-00-01 %CLS_HOME%\batch\properties release use
exit %ERRORLEVEL% 

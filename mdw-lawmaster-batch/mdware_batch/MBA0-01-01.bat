@echo off
rem =======================================
rem MBA0-01-01 新店用単品店取扱マスタ作成処理
rem =======================================
call %~dp0\SetBatchPath.bat
%execjava% mdware.common.batch.util.control.BatchController MBA0-01-01 %CLS_HOME%\batch\properties release use
exit %ERRORLEVEL% 

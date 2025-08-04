@echo off
rem =======================================
rem MBA0-05-01 新店用物流経路マスタ作成処理
rem =======================================
call %~dp0\SetBatchPath.bat
%execjava% mdware.common.batch.util.control.BatchController MBA0-05-01 %CLS_HOME%\batch\properties release use
exit %ERRORLEVEL% 

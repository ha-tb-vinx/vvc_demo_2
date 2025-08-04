@echo off
rem =======================================
rem MBA0-04-01 新店用店グルーピングマスタ作成処理
rem =======================================
call %~dp0\SetBatchPath.bat
%execjava% mdware.common.batch.util.control.BatchController MBA0-04-01 %CLS_HOME%\batch\properties release use
exit %ERRORLEVEL% 

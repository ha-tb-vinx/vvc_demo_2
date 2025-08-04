@echo off
rem =======================================
rem MBA3-90-01 情報分析用ファイル送信
rem =======================================
call %~dp0\SetBatchPath.bat
%execjava% mdware.common.batch.util.control.BatchController MBA3-90-01 %CLS_HOME%\batch\properties release use
exit %ERRORLEVEL% 

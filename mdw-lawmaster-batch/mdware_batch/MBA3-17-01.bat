@echo off
rem =======================================
rem MBA3-17-01 情報分析用メッセージマスタ生成
rem =======================================
call %~dp0\SetBatchPath.bat
%execjava% mdware.common.batch.util.control.BatchController MBA3-17-01 %CLS_HOME%\batch\properties release use
exit %ERRORLEVEL% 

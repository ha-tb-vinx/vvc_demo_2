@echo off
rem =======================================
rem MBA3-14-01 情報分析用計量器マスタ生成
rem =======================================
call %~dp0\SetBatchPath.bat
%execjava% mdware.common.batch.util.control.BatchController MBA3-14-01 %CLS_HOME%\batch\properties release use
exit %ERRORLEVEL% 

@echo off
rem =======================================
rem MBA3-10-01 情報分析用商品マスタ生成
rem =======================================
call %~dp0\SetBatchPath.bat
%execjava% mdware.common.batch.util.control.BatchController MBA3-10-01 %CLS_HOME%\batch\properties release use
exit %ERRORLEVEL% 

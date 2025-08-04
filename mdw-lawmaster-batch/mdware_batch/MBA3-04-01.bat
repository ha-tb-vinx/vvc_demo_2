@echo off
rem =======================================
rem MBA3-04-01 情報分析用大分類１マスタ生成
rem =======================================
call %~dp0\SetBatchPath.bat
%execjava% mdware.common.batch.util.control.BatchController MBA3-04-01 %CLS_HOME%\batch\properties release use
exit %ERRORLEVEL% 

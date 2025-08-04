@echo off
rem =======================================
rem MSTB992-050 POS用IF単品メンテ作成
rem =======================================
call %~dp0\SetBatchPath.bat
%execjava% mdware.common.batch.util.control.BatchController MSTB992-050 %CLS_HOME%\batch\properties release use
exit %ERRORLEVEL%

@echo off
rem =======================================
rem MSTB992-061 POS用IF単品メンテバックアップ
rem =======================================
call %~dp0\SetBatchPath.bat
%execjava% mdware.common.batch.util.control.BatchController MSTB992-061 %CLS_HOME%\batch\properties release use
exit %ERRORLEVEL%

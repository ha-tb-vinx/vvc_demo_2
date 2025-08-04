@echo off
rem =======================================
rem MSTB993-051 イニシャルPLU用IF単品メンテ作成
rem =======================================
call %~dp0\SetBatchPath.bat
%execjava% mdware.common.batch.util.control.BatchController MSTB993-051 %CLS_HOME%\batch\properties release use
exit %ERRORLEVEL%

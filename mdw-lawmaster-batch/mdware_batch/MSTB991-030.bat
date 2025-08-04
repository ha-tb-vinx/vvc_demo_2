@echo off
rem =======================================
rem MSTB991-030 FAST-IF Merchandise hierarchy Create
rem =======================================
call %~dp0\SetBatchPath.bat
%execjava% mdware.common.batch.util.control.BatchController MSTB991-030 %CLS_HOME%\batch\properties release use
exit %ERRORLEVEL%

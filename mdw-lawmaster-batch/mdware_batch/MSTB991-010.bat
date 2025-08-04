@echo off
rem =======================================
rem MSTB991-010 FAST-IF Item List Create
rem =======================================
call %~dp0\SetBatchPath.bat
%execjava% mdware.common.batch.util.control.BatchController MSTB991-010 %CLS_HOME%\batch\properties release use
exit %ERRORLEVEL%

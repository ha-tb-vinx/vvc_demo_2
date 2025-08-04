@echo off
rem =======================================
rem MSTB920020 EMG POS for IF maintenance Backup
rem =======================================
call %~dp0\SetBatchPath.bat
%EXECJAVA% mdware.common.batch.util.control.BatchController MSTB920-020 %CLS_HOME%\batch\properties release use
IF %ERRORLEVEL% NEQ 0 GOTO error

:end
exit 0

:error
exit 1

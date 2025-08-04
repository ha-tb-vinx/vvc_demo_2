@echo off
rem =======================================
rem MSTB920060 緊急POS用IF種別マスタ作成
rem =======================================
call %~dp0\SetBatchPath.bat
%EXECJAVA% mdware.common.batch.util.control.BatchController MSTB920-060 %CLS_HOME%\batch\properties release use
IF %ERRORLEVEL% NEQ 0 GOTO error

:end
exit 0

:error
exit 1

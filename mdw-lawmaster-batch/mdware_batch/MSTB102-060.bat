@echo off
rem =======================================
rem MSTB102060 �w����n���p�[�\�����f����
rem =======================================
call %~dp0\SetBatchPath.bat
%EXECJAVA% mdware.common.batch.util.control.BatchController MSTB102-060 %CLS_HOME%\batch\properties release use
IF %ERRORLEVEL% NEQ 0 GOTO error

:end
exit 0

:error
exit 1

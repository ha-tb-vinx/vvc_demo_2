@echo off
rem =======================================
rem MSTB102010 �w����o�b�`�����p�}�X�^�쐬����
rem =======================================
call %~dp0\SetBatchPath.bat
%EXECJAVA% mdware.common.batch.util.control.BatchController MSTB102-010 %CLS_HOME%\batch\properties release use
IF %ERRORLEVEL% NEQ 0 GOTO error

:end
exit 0

:error
exit 1

@echo off
rem =======================================
rem MSTB992-091 POS�p�t�@�C���o�b�N�A�b�v
rem =======================================
call %~dp0\SetBatchPath.bat
%execjava% mdware.common.batch.util.control.BatchController MSTB992-091 %CLS_HOME%\batch\properties release use
exit %ERRORLEVEL%

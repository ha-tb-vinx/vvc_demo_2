@echo off
rem =======================================
rem MSTB992-061 POS�pIF�P�i�����e�o�b�N�A�b�v
rem =======================================
call %~dp0\SetBatchPath.bat
%execjava% mdware.common.batch.util.control.BatchController MSTB992-061 %CLS_HOME%\batch\properties release use
exit %ERRORLEVEL%

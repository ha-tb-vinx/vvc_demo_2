@echo off
rem =======================================
rem MSTB992-051 POS�pIF�P�i�����e�쐬
rem =======================================
call %~dp0\SetBatchPath.bat
%execjava% mdware.common.batch.util.control.BatchController MSTB992-051 %CLS_HOME%\batch\properties release use
exit %ERRORLEVEL%

@echo off
rem =======================================
rem MSTB994-051 �w���POS�pIF�P�i�����e�쐬
rem =======================================
call %~dp0\SetBatchPath.bat
%execjava% mdware.common.batch.util.control.BatchController MSTB994-051 %CLS_HOME%\batch\properties release use
exit %ERRORLEVEL%

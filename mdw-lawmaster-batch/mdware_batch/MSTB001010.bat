@echo off
rem =======================================
rem MSTB001010 �w���POS���M�J�n�����iMSTB001010�j
rem =======================================
call %~dp0\SetBatchPath.bat
%execjava% mdware.common.batch.util.control.BatchController MSTB001010 %CLS_HOME%\batch\properties release use
exit %ERRORLEVEL%

@echo off
rem =======================================
rem MSTB001020 �w���POS���M�I�������iMSTB001020�j
rem =======================================
call %~dp0\SetBatchPath.bat
%execjava% mdware.common.batch.util.control.BatchController MSTB001020 %CLS_HOME%\batch\properties release use
exit %ERRORLEVEL%

@echo off
rem =======================================
rem MBZ0-02-01 ジョブ終了
rem =======================================
call %~dp0\SetBatchPath.bat
%execjava% mdware.common.batch.util.control.BatchController MBZ0-02-01 %CLS_HOME%\batch\properties release use
exit %ERRORLEVEL%

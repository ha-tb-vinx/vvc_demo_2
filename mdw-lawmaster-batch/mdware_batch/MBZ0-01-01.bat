@echo off
rem =======================================
rem MBZ0-01-01 先行ジョブ終了待機
rem =======================================
call %~dp0\SetBatchPath.bat
%execjava% mdware.common.batch.util.control.BatchController MBZ0-01-01 %CLS_HOME%\batch\properties release use
exit %ERRORLEVEL%

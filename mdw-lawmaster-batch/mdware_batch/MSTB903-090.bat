@echo off
rem =======================================
rem MSTB903-090 POP用バックアップ処理
rem =======================================
call %~dp0\SetBatchPath.bat
%execjava% mdware.common.batch.util.control.BatchController MSTB903-090 %CLS_HOME%\batch\properties release use
exit %ERRORLEVEL%

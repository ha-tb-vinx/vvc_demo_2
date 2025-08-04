@echo off
rem =======================================
rem MSTB101-070 ハンパー構成バックアップ処理
rem =======================================
call %~dp0\SetBatchPath.bat
%execjava% mdware.common.batch.util.control.BatchController MSTB101-070 %CLS_HOME%\batch\properties release use
exit %ERRORLEVEL%

@echo off
rem =======================================
rem MSTB995-080 計量器用テーブルバックアップ
rem =======================================
call %~dp0\SetBatchPath.bat
%execjava% mdware.common.batch.util.control.BatchController MSTB995-080 %CLS_HOME%\batch\properties release use
exit %ERRORLEVEL%

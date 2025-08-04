@echo off
rem =======================================
rem MSTB995-050 計量器用マスタファイル作成
rem =======================================
call %~dp0\SetBatchPath.bat
%execjava% mdware.common.batch.util.control.BatchController MSTB995-050 %CLS_HOME%\batch\properties release use
exit %ERRORLEVEL%

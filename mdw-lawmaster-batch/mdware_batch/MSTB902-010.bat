@echo off
rem =======================================
rem MSTB902-010 WK_POS単価変更ログ取込
rem =======================================
call %~dp0\SetBatchPath.bat
%execjava% mdware.common.batch.util.control.BatchController MSTB902-010 %CLS_HOME%\batch\properties release use
exit %ERRORLEVEL%

@echo off
rem =======================================
rem MSTB903-070 POP用マスタファイル作成
rem =======================================
call %~dp0\SetBatchPath.bat
%execjava% mdware.common.batch.util.control.BatchController MSTB903-070 %CLS_HOME%\batch\properties release use
exit %ERRORLEVEL%

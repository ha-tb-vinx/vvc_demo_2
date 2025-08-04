@echo off
rem =======================================
rem MSTB903-100 POP用テーブルバックアップ
rem =======================================
call %~dp0\SetBatchPath.bat
%execjava% mdware.common.batch.util.control.BatchController MSTB903-100 %CLS_HOME%\batch\properties release use
exit %ERRORLEVEL%

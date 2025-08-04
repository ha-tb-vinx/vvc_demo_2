@echo off
rem =======================================
rem MSTB992-101 POS用テーブルバックアップ
rem =======================================
call %~dp0\SetBatchPath.bat
%execjava% mdware.common.batch.util.control.BatchController MSTB992-101 %CLS_HOME%\batch\properties release use
exit %ERRORLEVEL%

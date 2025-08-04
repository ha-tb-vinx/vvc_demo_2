@echo off
rem =======================================
rem MSTB920-080 緊急POS用テーブルバックアップ
rem =======================================
call %~dp0\SetBatchPath.bat
%execjava% mdware.common.batch.util.control.BatchController MSTB920-080 %CLS_HOME%\batch\properties release use
exit %ERRORLEVEL%